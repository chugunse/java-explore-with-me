package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.service.CommentServicePublic;

import java.util.List;

@RestController
@RequestMapping(path = "/events/{eventId}/comments")
@Slf4j
@RequiredArgsConstructor
public class CommentControllerPublic {
    private final CommentServicePublic commentServicePublic;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getCommentsByEventId(@PathVariable Long eventId,
                                                 @RequestParam(defaultValue = "0") Integer from,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        log.info("получение комментарие к событию {}, from {}, size {}", eventId, from, size);
        return commentServicePublic.getCommentsByEventId(eventId, from, size);
    }

    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getCommentById(@PathVariable Long commentId,
                                     @PathVariable Long eventId) {
        log.info("получить комментарий по id = {}, к событию {}", commentId, eventId);
        return commentServicePublic.getCommentById(eventId, commentId);
    }
}
