package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;

public interface CommentServiceAdmin {
    CommentDto updateCommentById(Long commentId, NewCommentDto newCommentDto);

    void deleteCommentById(Long commentId);
}
