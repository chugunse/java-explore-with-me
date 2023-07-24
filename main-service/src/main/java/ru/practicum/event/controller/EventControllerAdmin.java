package ru.practicum.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.UpdateEventAdminRequest;
import ru.practicum.event.service.EventServiceAdmin;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static util.Constants.DATE_TIME_PATTERN;

@RestController
@RequestMapping(path = "/admin/events")
@Validated
@RequiredArgsConstructor
@Slf4j
public class EventControllerAdmin {
    private final EventServiceAdmin eventServiceAdmin;

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEventById(@PathVariable Long eventId,
                                        @RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest,
                                        HttpServletRequest request) {
        log.info("Обновление события с id = {}, {}", eventId, updateEventAdminRequest);
        return eventServiceAdmin.updateEventById(eventId, updateEventAdminRequest, request);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getAllEventsForAdmin(@RequestParam(required = false) List<Long> users,
                                                   @RequestParam(required = false) List<String> states,
                                                   @RequestParam(required = false) List<Long> categories,
                                                   @RequestParam(required = false)
                                                   @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime start,
                                                   @RequestParam(required = false)
                                                   @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime end,
                                                   @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                   @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Запрос событий с users {}, states {}, categories {}, rangeStart {}, rangeEnd {}, from {}, size {}",
                users, states, categories, start, end, from, size);
        return eventServiceAdmin.getAllEventsForAdmin(users, states, categories, start, end, from, size);
    }
}
