package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.service.EventServicePublic;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static util.Constants.DATE_TIME_PATTERN;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
@Slf4j
public class EventControllerPublic {
    private final EventServicePublic eventServicePublic;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    EventFullDto getPublicEventById(@PathVariable Long id, HttpServletRequest request) {
        log.info("запрос события по id = {}", id);
        return eventServicePublic.getPublicEventById(id, request);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    List<EventShortDto> getAllPublicEvents(@RequestParam(required = false) String text,
                                           @RequestParam(required = false) List<Long> categories,
                                           @RequestParam(required = false) Boolean paid,
                                           @RequestParam(required = false)
                                           @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeStart,
                                           @RequestParam(required = false)
                                           @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeEnd,
                                           @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                           @RequestParam(defaultValue = "EVENT_DATE") String sort,
                                           @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                           @Positive @RequestParam(defaultValue = "10") Integer size,
                                           HttpServletRequest request) {
        log.info("Запрос событий с параметрами text {}, categories {}, paid {}, rangeStart {}, rangeEnd {}, onlyAvailable {}, " +
                        "sort {}, from {}, size {}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        return eventServicePublic.getAllPublicEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request);
    }
}
