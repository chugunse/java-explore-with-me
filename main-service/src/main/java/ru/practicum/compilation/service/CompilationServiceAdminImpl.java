package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.storage.CompilationRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.service.RequestAndViewsService;
import ru.practicum.event.storage.EventRepository;
import ru.practicum.util.FindObjectInRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class CompilationServiceAdminImpl implements CompilationServiceAdmin {
    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;
    private final RequestAndViewsService requestAndViewsService;

    private final FindObjectInRepository findObjectInRepository;

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        Set<Event> eventsSet = new HashSet<>();
        if (newCompilationDto.getEvents() != null) {
            List<Event> eventsList = eventRepository.findAllById(newCompilationDto.getEvents());
            requestAndViewsService.confirmedRequestForListEvent(eventsList);
            requestAndViewsService.setViewsToEventList(eventsList);
            eventsSet = new HashSet<>(eventsList);
        }
        Compilation compilation = CompilationMapper
                .newCompilationDtoToCompilationAndEvents(newCompilationDto, eventsSet);
        compilationRepository.save(compilation);
        return CompilationMapper.compilationToCompilationDto(compilation);
    }

    @Override
    public void deleteCompilation(Long compId) {
        findObjectInRepository.checkCompilation(compId);
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest request) {
        findObjectInRepository.checkCompilation(compId);
        Compilation compilation = findObjectInRepository.getCompilationById(compId);
        if (request.getEvents() != null) {
            List<Event> eventsList = eventRepository.findAllById(request.getEvents());
            requestAndViewsService.confirmedRequestForListEvent(eventsList);
            requestAndViewsService.setViewsToEventList(eventsList);
            compilation.setEvents(new HashSet<>(eventsList));
        }
        ofNullable(request.getPinned()).ifPresent(compilation::setPinned);
        ofNullable(request.getTitle()).ifPresent(compilation::setTitle);
        compilationRepository.save(compilation);
        return CompilationMapper.compilationToCompilationDto(compilation);
    }
}
