package ru.practicum.event.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.UpdateEventAdminRequest;
import ru.practicum.event.dto.stateDto.ActionStateDto;
import ru.practicum.event.mapper.LocationMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.service.EventServiceAdmin;
import ru.practicum.event.service.RequestAndViewsService;
import ru.practicum.event.storage.EventRepository;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.exception.model.BadRequestException;
import ru.practicum.exception.model.ConflictException;
import ru.practicum.util.DateFormatter;
import ru.practicum.util.FindObjectInRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class EventServiceAdminImpl implements EventServiceAdmin {
    private final EventRepository eventRepository;
    private final FindObjectInRepository findObjectInRepository;
    private final RequestAndViewsService requestAndViewsService;

    @Override

    public EventFullDto updateEventById(Long eventId, UpdateEventAdminRequest updateEvent, HttpServletRequest request) {
        Event event = findObjectInRepository.getEventById(eventId);
        ofNullable(updateEvent.getAnnotation()).ifPresent(event::setAnnotation);
        ofNullable(updateEvent.getCategory())
                .ifPresent(category -> event.setCategory(findObjectInRepository.getCategoryById(category)));
        ofNullable(updateEvent.getDescription()).ifPresent(event::setDescription);
        ofNullable(updateEvent.getEventDate())
                .ifPresent(date -> event.setEventDate(DateFormatter.creatDataFromString(date)));
        ofNullable(updateEvent.getLocation())
                .ifPresent(location -> event.setLocation(LocationMapper.locationDtoToLocation(location)));
        ofNullable(updateEvent.getPaid()).ifPresent(event::setPaid);
        ofNullable(updateEvent.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        ofNullable(updateEvent.getRequestModeration()).ifPresent(event::setRequestModeration);

        if (updateEvent.getStateAction() != null) {
            checkEventStatusAvailability(event, updateEvent);
            if (!event.getState().equals(EventState.PUBLISHED) && updateEvent.getStateAction().equals(ActionStateDto.PUBLISH_EVENT)) {
                event.setPublishedOn(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            }
            event.setState(determiningTheStatusForEvent(updateEvent.getStateAction()));
        }
        ofNullable(updateEvent.getTitle()).ifPresent(event::setTitle);
        requestAndViewsService.confirmedRequestsForOneEvent(event);
        return EventMapper.eventToEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<EventFullDto> getAllEventsForAdmin(List<Long> users,
                                                   List<String> states,
                                                   List<Long> categories,
                                                   LocalDateTime start,
                                                   LocalDateTime end,
                                                   int from,
                                                   int size) {
        List<Event> events;
        Pageable page = PageRequest.of(from / size, size);
        if (states != null) {
            List<EventState> eventStates = states.stream().map(EventState::valueOf).collect(Collectors.toList());
            events = eventRepository.findAllByAdmin(users, eventStates, categories, start, end, page);
        } else {
            events = eventRepository.findAllByAdmin(users, null, categories, start, end, page);
        }
        requestAndViewsService.setViewsToEventList(events);
        requestAndViewsService.confirmedRequestForListEvent(events);
        return EventMapper.eventToEventFullDtoList(events);
    }

    private void checkEventStatusAvailability(Event event, UpdateEventAdminRequest updateEvent) {
        if (updateEvent.getStateAction().equals(ActionStateDto.PUBLISH_EVENT)
                && !event.getState().equals(EventState.PENDING)) {
            throw new ConflictException("Нельзя опубликовать событие не находящееся в статусе ожидания публикации");
        }
        if (updateEvent.getStateAction().equals(ActionStateDto.REJECT_EVENT)
                && event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Нельзя отклонить запрос на публикацию уже опубликованного события");
        }
    }

    private EventState determiningTheStatusForEvent(ActionStateDto stateAction) {
        switch (stateAction) {
            case SEND_TO_REVIEW:
                return EventState.PENDING;
            case CANCEL_REVIEW:
                return EventState.CANCELED;
            case PUBLISH_EVENT:
                return EventState.PUBLISHED;
            case REJECT_EVENT:
                return EventState.CANCELED;
            default:
                throw new BadRequestException("Статус не соответствует модификатору доступа");
        }
    }
}
