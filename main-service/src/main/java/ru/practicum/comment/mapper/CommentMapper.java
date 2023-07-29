package ru.practicum.comment.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.model.Comment;
import ru.practicum.event.model.Event;
import ru.practicum.users.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CommentMapper {
    public Comment newCommentDtoToComment(NewCommentDto newCommentDto, User user, Event event) {
        return Comment.builder()
                .event(event)
                .owner(user)
                .text(newCommentDto.getText())
                .created(LocalDateTime.now())
                .build();
    }

    public CommentDto commentToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .owner(comment.getOwner().getName())
                .text(comment.getText())
                .created(comment.getCreated())
                .updated(comment.getUpdated())
                .changedBy(comment.getChangedBy())
                .build();
    }

    public List<CommentDto> commentListToCommentDtoList(List<Comment> comments) {
        return comments.stream()
                .map(CommentMapper::commentToCommentDto)
                .collect(Collectors.toList());
    }
}
