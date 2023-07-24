package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.event.service.EventServicePrivate;
import ru.practicum.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.request.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@Validated
@RequiredArgsConstructor
@Slf4j
public class EventControllerPrivate {
    private final EventServicePrivate eventServicePrivate;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EventFullDto addEvent(@PathVariable Long userId,
                          @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Добавление события от юзера {}, {}", userId, newEventDto);
        return eventServicePrivate.addEvent(userId, newEventDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<EventShortDto> getAllEventsByUserId(@PathVariable Long userId,
                                             @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                             @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Запрос событий созданных юзером = {}, from = {}, size = {}", userId, from, size);
        return eventServicePrivate.getAllEventsByUserId(userId, from, size);
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    EventFullDto getEventByIdAndByUserId(@PathVariable Long userId,
                                         @PathVariable Long eventId) {
        log.info("Запрос события юзером = {}, id события = {}", userId, eventId);
        return eventServicePrivate.getEventByIdAndByUserId(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    EventFullDto updatePrivateEventByIdAndByUserId(@PathVariable Long userId,
                                                   @PathVariable Long eventId,
                                                   @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        log.info("Обновление события юзером = {}, id события = {}, {}", userId, eventId, updateEventUserRequest);
        return eventServicePrivate.updateEventByIdAndByUserId(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    List<ParticipationRequestDto> getRequestsForThisEvent(@PathVariable Long userId,
                                                          @PathVariable Long eventId) {
        log.info("получение запросов на участие в событии от юзера = {}, в событии = {}", userId, eventId);
        return eventServicePrivate.getRequestsForThisEvent(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    EventRequestStatusUpdateResult changeRequestsStatus(@PathVariable Long userId,
                                                        @PathVariable Long eventId,
                                                        @RequestBody EventRequestStatusUpdateRequest eventRequest) {
        log.info("изменение статусов запросов на участи в событии юзера = {}, событие = {}, {}",
                userId, eventId, eventRequest);
        return eventServicePrivate.changeRequestsStatus(userId, eventId, eventRequest);
    }
}
