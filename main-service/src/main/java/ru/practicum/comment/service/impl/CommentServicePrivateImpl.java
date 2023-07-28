package ru.practicum.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.ChangedBy;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.service.CommentServicePrivate;
import ru.practicum.comment.storage.CommentRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.exception.model.ConflictException;
import ru.practicum.exception.model.ForbiddenException;
import ru.practicum.users.model.User;
import ru.practicum.util.FindObjectInRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServicePrivateImpl implements CommentServicePrivate {
    private final CommentRepository commentRepository;
    private final FindObjectInRepository findObjectInRepository;

    @Override
    public CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto) {
        User user = findObjectInRepository.getUserById(userId);
        Event event = findObjectInRepository.getEventById(eventId);
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Нельзя добавить комментарий к не опубликованному событию");
        }
        Comment comment = commentRepository.save(CommentMapper.newCommentDtoToComment(newCommentDto, user, event));
        return CommentMapper.commentToCommentDto(comment);
    }

    @Override
    public CommentDto updateCommentById(Long userId, Long eventId, Long commentId, NewCommentDto newCommentDto) {
        Comment comment = checkAccess(userId, eventId, commentId);
        if (comment.getChangedBy() == ChangedBy.ADMIN) {
            throw new ForbiddenException("Нельзя править комментарий после администратора");
        }
        comment.setText(newCommentDto.getText());
        comment.setUpdated(LocalDateTime.now());
        comment.setChangedBy(ChangedBy.USER);
        return CommentMapper.commentToCommentDto(commentRepository.save(comment));
    }

    @Override
    public void deleteCommentById(Long userId, Long eventId, Long commentId) {
        Comment comment = checkAccess(userId, eventId, commentId);
        commentRepository.delete(comment);
    }

    private Comment checkAccess(Long userId, Long eventId, Long commentId) {
        findObjectInRepository.checkUserById(userId);
        findObjectInRepository.checkEventById(eventId);
        Comment comment = findObjectInRepository.getCommentById(commentId);
        if (!comment.getEvent().getId().equals(eventId)) {
            throw new ConflictException("комменарий с id = " + commentId + " не относится к событию с id = " + eventId);
        }
        if (!comment.getOwner().getId().equals(userId)) {
            throw new ConflictException("комменарий с id = " + commentId + " нельзя изменять юзеру с id = " + userId);
        }
        return comment;
    }
}
