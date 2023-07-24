package ru.practicum.event.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.category.model.Category;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.mapper.LocationMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.event.service.EventServicePrivate;
import ru.practicum.event.service.RequestAndViewsService;
import ru.practicum.event.storage.EventRepository;
import ru.practicum.exception.model.BadRequestException;
import ru.practicum.exception.model.ConflictException;
import ru.practicum.exception.model.ForbiddenException;
import ru.practicum.exception.model.ResourceNotFoundException;
import ru.practicum.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.dto.RequestStatusDto;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.RequestStatus;
import ru.practicum.request.storage.RequestRepository;
import ru.practicum.users.model.User;
import ru.practicum.util.DateFormatter;
import ru.practicum.util.FindObjectInRepository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class EventServicePrivateImpl implements EventServicePrivate {
    private final EventRepository eventRepository;
    private final FindObjectInRepository findObjectInRepository;
    private final RequestAndViewsService requestAndViewsService;
    private final RequestRepository requestRepository;

    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        User user = findObjectInRepository.getUserById(userId);
        Category category = findObjectInRepository.getCategoryById(newEventDto.getCategory());
        Long views = 0L;
        Long confirmedRequests = 0L;
        Event event = EventMapper.newEventDtoToCreateEvent(newEventDto, user, category, views, confirmedRequests);
        return EventMapper.eventToEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<EventShortDto> getAllEventsByUserId(Long userId, Integer from, Integer size) {
        findObjectInRepository.checkUserById(userId);
        List<Event> events = eventRepository.findAllByInitiatorId(userId, PageRequest.of(from, size));
        requestAndViewsService.confirmedRequestForListEvent(events);
        requestAndViewsService.setViewsToEventList(events);
        return EventMapper.eventToEventShortDtoList(events);
    }

    @Override
    public EventFullDto getEventByIdAndByUserId(Long userId, Long eventId) {
        User user = findObjectInRepository.getUserById(userId);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Событие c id = " + eventId + " не найдено"));
        checkOwnerEvent(event, user);
        requestAndViewsService.confirmedRequestsForOneEvent(event);
        requestAndViewsService.setViewsToOneEvent(event);
        return EventMapper.eventToEventFullDto(event);

    }

    @Override
    public EventFullDto updateEventByIdAndByUserId(Long userId, Long eventId,
                                                   UpdateEventUserRequest updateEventUserRequest) {
        Event event = findObjectInRepository.getEventById(eventId);
        User user = findObjectInRepository.getUserById(userId);
        checkOwnerEvent(event, user);
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Статус события <" + event.getState() + "> не позволяет редоктировать");
        }
        if (updateEventUserRequest.getStateAction() != null) {
            switch (updateEventUserRequest.getStateAction()) {
                case SEND_TO_REVIEW:
                    event.setState(EventState.PENDING);
                    break;
                case CANCEL_REVIEW:
                    event.setState(EventState.CANCELED);
                    break;
                default:
                    throw new BadRequestException("Статус не соответствует модификатору доступа");
            }
        }
        ofNullable(updateEventUserRequest.getAnnotation()).ifPresent(event::setAnnotation);
        if (updateEventUserRequest.getCategory() != null) {
            Category category = findObjectInRepository.getCategoryById(updateEventUserRequest.getCategory());
            event.setCategory(category);
        }
        ofNullable(updateEventUserRequest.getDescription()).ifPresent(event::setDescription);
        ofNullable(updateEventUserRequest.getEventDate())
                .ifPresent(date -> event.setEventDate(DateFormatter.creatDataFromString(date)));
        ofNullable(updateEventUserRequest.getLocation())
                .ifPresent(location -> event.setLocation(LocationMapper.locationDtoToLocation(location)));
        ofNullable(updateEventUserRequest.getPaid()).ifPresent(event::setPaid);
        ofNullable(updateEventUserRequest.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        ofNullable(updateEventUserRequest.getRequestModeration()).ifPresent(event::setRequestModeration);
        ofNullable(updateEventUserRequest.getTitle()).ifPresent(event::setTitle);
        event.setViews(0L);
        event.setConfirmedRequests(0L);
        return EventMapper.eventToEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<ParticipationRequestDto> getRequestsForThisEvent(Long userId, Long eventId) {
        Event event = findObjectInRepository.getEventById(eventId);
        User user = findObjectInRepository.getUserById(userId);
        checkOwnerEvent(event, user);
        List<Request> requests = requestRepository.findAllByEventId(event.getId());
        return RequestMapper.requestToParticipationRequestDtoList(requests);
    }

    @Override
    public EventRequestStatusUpdateResult changeRequestsStatus(Long userId,
                                                               Long eventId,
                                                               EventRequestStatusUpdateRequest eventRequest) {
        Event event = findObjectInRepository.getEventById(eventId);
        User user = findObjectInRepository.getUserById(userId);
        checkOwnerEvent(event, user);
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("нет опубликованного события по id = " + eventId);
        }
        requestAndViewsService.confirmedRequestsForOneEvent(event);
        if (event.getParticipantLimit() == 0 || !event.isRequestModeration()) {
            throw new ForbiddenException("Подтверждение заявок для данного события не требуется");
        } else if (event.getParticipantLimit() <= event.getConfirmedRequests()) {
            throw new ConflictException("Достигнут лимит по заявкам на данное событие с id= " + eventId);
        }
        List<Request> requests = requestRepository.findAllByIdIsIn(eventRequest.getRequestIds());
        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();
        for (Request request : requests) {
            if (!request.getStatus().equals(RequestStatus.PENDING)) {
                throw new ConflictException("Статус заявки  на участие с id = " + request.getId() +
                        " не позволяет подтвердить участие, статус = " + request.getStatus());
            }
        }
        if (eventRequest.getStatus().equals(RequestStatusDto.CONFIRMED)) {
            for (Request request : requests) {
                if (event.getParticipantLimit() > event.getConfirmedRequests()) {
                    request.setStatus(RequestStatus.CONFIRMED);
                    confirmedRequests.add(RequestMapper.requestToParticipationRequestDto(request));
                } else {
                    request.setStatus(RequestStatus.REJECTED);
                    rejectedRequests.add(RequestMapper.requestToParticipationRequestDto(request));
                }
            }
        } else {
            for (Request request : requests) {
                request.setStatus(RequestStatus.REJECTED);
                rejectedRequests.add(RequestMapper.requestToParticipationRequestDto(request));
            }
        }
        requestRepository.saveAll(requests);
        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmedRequests)
                .rejectedRequests(rejectedRequests)
                .build();
    }


    private void checkOwnerEvent(Event event, User user) {
        if (!event.getInitiator().getId().equals(user.getId())) {
            throw new ForbiddenException("Событие с id=" + event.getId()
                    + " не принадлежит пользователю с id=" + user.getId());
        }
    }
}
