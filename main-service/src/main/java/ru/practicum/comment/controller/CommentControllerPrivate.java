package ru.practicum.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.service.CommentServicePrivate;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/users/{userId}/comments")
@RequiredArgsConstructor
@Validated
@Slf4j
public class CommentControllerPrivate {
    private final CommentServicePrivate commentServicePrivate;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@PathVariable Long userId,
                                 @RequestParam @NotNull Long eventId,
                                 @RequestBody @Validated NewCommentDto newCommentDto) {
        log.info("Добавление комментария от юзера {}, к событию {}, комментарий {}", userId, eventId, newCommentDto);
        return commentServicePrivate.addComment(userId, eventId, newCommentDto);
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateCommentById(@PathVariable Long userId,
                                        @RequestParam @NotNull Long eventId,
                                        @PathVariable Long commentId,
                                        @RequestBody @Validated NewCommentDto newCommentDto) {
        log.info("изменение коментария {}, юзером {}, к событию {}, {}", commentId, userId, eventId, newCommentDto);
        return commentServicePrivate.updateCommentById(userId, eventId, commentId, newCommentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentById(@PathVariable Long userId,
                                  @RequestParam @NotNull Long eventId,
                                  @PathVariable Long commentId) {
        log.info("удаление комментария {}, к событию {}, юзером {}", commentId, eventId, userId);
        commentServicePrivate.deleteCommentById(userId, eventId, commentId);
    }
}
