package ru.practicum.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.service.RequestServicePrivate;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/requests")
@Validated
@RequiredArgsConstructor
@Slf4j
public class RequestControllerPrivate {
    private final RequestServicePrivate requestServicePrivate;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ParticipationRequestDto addRequestById(@PathVariable Long userId,
                                           @NotNull @RequestParam Long eventId) {
        log.info("добавление запроса на участие в событии {}, от пользователя {}", eventId, userId);
        return requestServicePrivate.addRequestEventById(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    ParticipationRequestDto updateRequestById(@PathVariable Long userId,
                                              @PathVariable Long requestId) {
        log.info("отмена запроса {} на участие от пользователя {}", requestId, userId);
        return requestServicePrivate.updateRequestStatus(userId, requestId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ParticipationRequestDto> getAllRequestsUserById(@PathVariable Long userId) {
        log.info("запрос всех запросов на участие во всех событиях пользователя {}", userId);
        return requestServicePrivate.getAllRequestsUserById(userId);
    }
}
