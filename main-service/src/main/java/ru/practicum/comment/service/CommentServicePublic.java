package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentDto;

import java.util.List;

public interface CommentServicePublic {
    List<CommentDto> getCommentsByEventId(Long eventId, Integer from, Integer size);

    CommentDto getCommentById(Long eventId, Long commentId);
}
