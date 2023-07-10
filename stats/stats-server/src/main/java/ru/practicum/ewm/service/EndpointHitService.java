package ru.practicum.ewm.service;

import dto.EndpointHitDto;
import dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitService {
    EndpointHitDto createEndpointHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getViewStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
