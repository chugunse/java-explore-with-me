package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;

public interface CommentServicePrivate {
    CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto);

    CommentDto updateCommentById(Long userId, Long eventId, Long commentId, NewCommentDto newCommentDto);

    void deleteCommentById(Long userId, Long eventId, Long commentId);
}
