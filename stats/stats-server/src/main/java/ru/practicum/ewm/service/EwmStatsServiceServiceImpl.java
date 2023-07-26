package ru.practicum.ewm.service;

import dto.EndpointHitDto;
import dto.ViewStatsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.exception.ValidationDateException;
import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.model.EndpointHitMapper;
import ru.practicum.ewm.model.ViewStatMapper;
import ru.practicum.ewm.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class EwmStatsServiceServiceImpl implements EndpointHitService {
    private final EndpointHitRepository endpointHitRepository;

    @Override
    @Transactional
    public EndpointHitDto createEndpointHit(EndpointHitDto endpointHitDto) {
        log.debug("EwmStatsServiceService - method call 'createEndpointHit' with params: endpointHitDto={}",
                endpointHitDto);
        EndpointHit endpointHitModel = EndpointHitMapper.toModel(endpointHitDto);
        log.info(endpointHitModel.toString());
        return EndpointHitMapper.toDto(
                endpointHitRepository.save(
                        endpointHitModel));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewStatsDto> getViewStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        log.debug("EwmStatsServiceService - method call 'getViewStats' with params: start={}, end={}, uris={}, " +
                "unique={}", start, end, uris, unique);
        if (end.isBefore(start) || start.isAfter(end)) {
            throw new ValidationDateException("дата начала поиска не может быть позже конца");
        }
        if (unique) {
            return ViewStatMapper.toDto(endpointHitRepository.findViewStatsUniqueIp(uris, start, end));
        } else {
            return ViewStatMapper.toDto(endpointHitRepository.findViewStats(uris, start, end));
        }
    }

}
