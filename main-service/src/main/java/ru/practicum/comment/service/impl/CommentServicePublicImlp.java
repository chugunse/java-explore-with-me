package ru.practicum.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.service.CommentServicePublic;
import ru.practicum.comment.storage.CommentRepository;
import ru.practicum.exception.model.BadRequestException;
import ru.practicum.util.FindObjectInRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServicePublicImlp implements CommentServicePublic {
    private final CommentRepository commentRepository;
    private final FindObjectInRepository findObjectInRepository;

    @Override
    public List<CommentDto> getCommentsByEventId(Long eventId, Integer from, Integer size) {
        findObjectInRepository.checkEventById(eventId);
        Pageable page = PageRequest.of(from / size, size);
        List<Comment> comments = commentRepository.findAllByEventId(eventId, page);
        return CommentMapper.commentListToCommentDtoList(comments);
    }

    @Override
    public CommentDto getCommentById(Long eventId, Long commentId) {
        Comment comment = findObjectInRepository.getCommentById(commentId);
        if (!comment.getEvent().getId().equals(eventId)) {
            throw new BadRequestException("комментарий id = " + commentId + " не относится к событию id = " + eventId);
        }
        return CommentMapper.commentToCommentDto(comment);
    }
}
