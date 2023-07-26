package ru.practicum.request.service;

import ru.practicum.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestServicePrivate {
    ParticipationRequestDto addRequestEventById(Long userId, Long eventId);

    ParticipationRequestDto updateRequestStatus(Long userId, Long requestId);

    List<ParticipationRequestDto> getAllRequestsUserById(Long userId);
}
