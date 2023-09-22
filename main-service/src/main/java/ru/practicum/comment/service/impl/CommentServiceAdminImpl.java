package ru.practicum.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.ChangedBy;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.service.CommentServiceAdmin;
import ru.practicum.comment.storage.CommentRepository;
import ru.practicum.util.FindObjectInRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceAdminImpl implements CommentServiceAdmin {
    private final CommentRepository commentRepository;
    private final FindObjectInRepository findObjectInRepository;

    @Override
    public CommentDto updateCommentById(Long commentId, NewCommentDto newCommentDto) {
        Comment comment = findObjectInRepository.getCommentById(commentId);
        comment.setText(newCommentDto.getText());
        comment.setUpdated(LocalDateTime.now());
        comment.setChangedBy(ChangedBy.ADMIN);
        return CommentMapper.commentToCommentDto(commentRepository.save(comment));
    }

    @Override
    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
