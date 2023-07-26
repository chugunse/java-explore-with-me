package ru.practicum.event.service.impl;

import dto.EndpointHitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.service.EventServicePublic;
import ru.practicum.event.service.RequestAndViewsService;
import ru.practicum.event.storage.EventRepository;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.exception.model.BadRequestException;
import ru.practicum.exception.model.ResourceNotFoundException;
import util.Constants;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServicePublicImpl implements EventServicePublic {
    private final EventRepository eventRepository;
    private final RequestAndViewsService requestAndViewsService;
    private final StatsClient client;

    @Value("${app.name}")
    private String appName;

    @Override
    public EventFullDto getPublicEventById(Long id, HttpServletRequest request) {
        EndpointHitDto endpointHitDto = EndpointHitDto.builder()
                .app(appName)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
        client.hitRequest(endpointHitDto);
        Event event = eventRepository.findByIdAndStateIs(id, EventState.PUBLISHED)
                .orElseThrow(() -> new ResourceNotFoundException("Событие c id = " + id + " не найдено"));
        requestAndViewsService.confirmedRequestsForOneEvent(event);
        requestAndViewsService.setViewsToOneEvent(event);
        return EventMapper.eventToEventFullDto(event);
    }

    @Override
    public List<EventShortDto> getAllPublicEvents(String text,
                                                  List<Long> categories,
                                                  Boolean paid,
                                                  LocalDateTime rangeStart,
                                                  LocalDateTime rangeEnd,
                                                  Boolean onlyAvailable,
                                                  String sort,
                                                  Integer from,
                                                  Integer size,
                                                  HttpServletRequest request) {
        if (rangeStart != null && rangeEnd != null) {
            if (rangeEnd.isBefore(rangeStart)) {
                throw new BadRequestException("ошибка в веденных параметрах запроса времени событий");
            }
        }
        PageRequest page = PageRequest.of(from, size);
        String rangeStartStr = (rangeStart != null) ? rangeStart.format(Constants.formatter) : null;
        String rangeEndStr = (rangeEnd != null) ? rangeEnd.format(Constants.formatter) : null;
        List<Event> events = eventRepository.findAllByPublic(text, categories, paid, rangeStartStr, rangeEndStr,
                sort, page);
        EndpointHitDto hitDto = EndpointHitDto.builder()
                .app(appName)
                .uri("/events")
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now())
                .build();
        client.hitRequest(hitDto);
        if (!events.isEmpty()) {
            requestAndViewsService.confirmedRequestForListEvent(events);
            requestAndViewsService.setViewsToEventList(events);
            if (!onlyAvailable) {
                return EventMapper.eventToEventShortDtoList(events
                        .stream()
                        .filter(e -> e.getParticipantLimit() >= e.getConfirmedRequests())
                        .collect(Collectors.toList()));
            }
        }
        return new ArrayList<>();
    }
}
