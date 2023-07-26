package ru.practicum.compilation.service;

import dto.ViewStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.storage.CompilationRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.service.RequestAndViewsService;
import ru.practicum.request.model.RequestStatus;
import ru.practicum.request.storage.RequestRepository;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.util.FindObjectInRepository;
import util.Constants;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServicePublicImlp implements CompilationServicePublic {
    private final CompilationRepository compilationRepository;
    private final FindObjectInRepository findObjectInRepository;
    private final RequestAndViewsService requestAndViewsService;
    private final RequestRepository requestRepository;
    private final StatsClient client;

    @Override
    public CompilationDto getCompilationById(Long compId) {
        findObjectInRepository.checkCompilation(compId);
        Compilation compilation = findObjectInRepository.getCompilationById(compId);
        if (compilation.getEvents() != null && !compilation.getEvents().isEmpty()) {
            List<Event> events = new ArrayList<>(compilation.getEvents());
            requestAndViewsService.confirmedRequestForListEvent(events);
            requestAndViewsService.setViewsToEventList(events);
            compilation.setEvents(new HashSet<>(events));
        }
        return CompilationMapper.compilationToCompilationDto(compilation);
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        List<Compilation> compilations = compilationRepository.findAllByPinnedIs(pinned, pageable);
        if (compilations.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> uris = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        String eventsUri = "/events/";
        for (Compilation compilation : compilations) {
            if (!compilation.getEvents().isEmpty()) {
                for (Event event : compilation.getEvents()) {
                    uris.add(eventsUri + event.getId());
                    events.add(event);
                }
            }
        }
        List<ViewStatsDto> views = client.getStats(LocalDateTime.now().minusYears(100).format(Constants.formatter),
                LocalDateTime.now().format(Constants.formatter),
                uris,
                true);
        Map<Long, Long> requests = new HashMap<>();
        if (!events.isEmpty()) {
            requests = requestRepository.findAllByEventInAndStatus(events, RequestStatus.CONFIRMED)
                    .stream()
                    .collect(Collectors.groupingBy(r -> r.getEvent().getId(), Collectors.counting()));
        }
        Map<Long, Long> hits = new HashMap<>();
        for (ViewStatsDto statsDto : views) {
            String uri = statsDto.getUri();
            hits.put(Long.parseLong(uri.substring(eventsUri.length())), statsDto.getHits());
        }
        for (Compilation compilation : compilations) {
            for (Event event : compilation.getEvents()) {
                event.setViews(hits.get(event.getId()));
                event.setConfirmedRequests(requests.getOrDefault(event.getId(), 0L));
            }
        }
        return compilations
                .stream()
                .map(CompilationMapper::compilationToCompilationDto)
                .collect(Collectors.toList());
    }
}
