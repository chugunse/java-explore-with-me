package ru.practicum.event.service;

import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.List;

public interface EventServicePrivate {
    EventFullDto addEvent(Long userId, NewEventDto newEventDto);

    List<EventShortDto> getAllEventsByUserId(Long userId, Integer from, Integer size);

    EventFullDto getEventByIdAndByUserId(Long userId, Long eventId);

    EventFullDto updateEventByIdAndByUserId(Long userId, Long eventId,
                                            UpdateEventUserRequest updateEventUserRequest);

    List<ParticipationRequestDto> getRequestsForThisEvent(Long userId, Long eventId);

    EventRequestStatusUpdateResult changeRequestsStatus(Long userId,
                                                        Long eventId,
                                                        EventRequestStatusUpdateRequest eventRequest);
}
