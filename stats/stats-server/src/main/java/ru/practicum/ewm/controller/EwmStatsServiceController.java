package ru.practicum.ewm.controller;

import dto.HitDto;
import dto.StatsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.service.EndpointHitService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class EwmStatsServiceController {
    private final EndpointHitService service;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PostMapping("/hit")
    @Transactional
    public HitDto createEndpointHit(@RequestBody @Valid HitDto hitDto) {
        log.info("EwmStatsServiceController - POST: /hit endpointHitDto={}", hitDto);
        return service.createEndpointHit(hitDto);
    }

    @GetMapping("/stats")
    @Transactional
    public List<StatsDto> getViewStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("EwmStatsServiceController - GET: /stats start={}, end={}, uris={}, unique={}",
                start, end, uris, unique);
        LocalDateTime startLDT = LocalDateTime.parse(start, FORMATTER);
        LocalDateTime endLDT = LocalDateTime.parse(end, FORMATTER);
        return service.getViewStats(startLDT, endLDT, uris, unique);
    }
}
