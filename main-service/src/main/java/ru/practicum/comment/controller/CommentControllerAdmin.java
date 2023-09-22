package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.service.CommentServiceAdmin;

@RestController
@RequestMapping(path = "/admin/comments")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CommentControllerAdmin {
    private final CommentServiceAdmin commentServiceAdmin;

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateCommentById(@PathVariable Long commentId,
                                        @RequestBody @Validated NewCommentDto newCommentDto) {
        log.info("изменение комментария {} админом {}", commentId, newCommentDto);
        return commentServiceAdmin.updateCommentById(commentId, newCommentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(@PathVariable Long commentId) {
        log.info("удаление комментария {} админом", commentId);
        commentServiceAdmin.deleteCommentById(commentId);
    }
}
