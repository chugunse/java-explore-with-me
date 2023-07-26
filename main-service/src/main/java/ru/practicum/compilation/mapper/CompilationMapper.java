package ru.practicum.compilation.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;

import java.util.ArrayList;
import java.util.Set;

@UtilityClass
public class CompilationMapper {
    public Compilation newCompilationDtoToCompilationAndEvents(NewCompilationDto newCompilationDto, Set<Event> events) {
        return Compilation.builder()
                .events(events)
                .pinned(newCompilationDto.isPinned())
                .title(newCompilationDto.getTitle())
                .build();
    }

    public CompilationDto compilationToCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .events(EventMapper.eventToEventShortDtoList(new ArrayList<>(compilation.getEvents())))
                .pinned(compilation.isPinned())
                .title(compilation.getTitle())
                .build();
    }
}