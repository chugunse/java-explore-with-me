package ru.practicum.ewm.model;

import dto.ViewStatsDto;

import java.util.List;
import java.util.stream.Collectors;

public class ViewStatMapper {
    public static List<ViewStatsDto> toDto(List<ViewStat> viewStats) {
        return viewStats
                .stream()
                .map(viewStat -> new ViewStatsDto(viewStat.getApp(), viewStat.getUri(), viewStat.getHits()))
                .collect(Collectors.toList());
    }
}
