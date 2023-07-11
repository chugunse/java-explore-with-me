package ru.practicum.ewm.controller;

import dto.EndpointHitDto;
import dto.ViewStatsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.EndpointHitService;
import util.Constants;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class EwmStatsServiceController {

    private final EndpointHitService service;

    @PostMapping("/hit")
    public EndpointHitDto createEndpointHit(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        log.info("EwmStatsServiceController - POST: /hit endpointHitDto={}", endpointHitDto);
        return service.createEndpointHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getViewStats(
            @RequestParam @DateTimeFormat(pattern = Constants.DATE_TIME_PATTERN) LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = Constants.DATE_TIME_PATTERN) LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("EwmStatsServiceController - GET: /stats start={}, end={}, uris={}, unique={}",
                start, end, uris, unique);
        return service.getViewStats(start, end, uris, unique);
    }
}
