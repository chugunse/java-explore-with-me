package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.RequestStatus;
import ru.practicum.request.storage.RequestRepository;
import ru.practicum.exception.model.ConflictException;
import ru.practicum.exception.model.ResourceNotFoundException;
import ru.practicum.users.model.User;
import ru.practicum.util.FindObjectInRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServicePrivateImpl implements RequestServicePrivate {
    private final RequestRepository requestRepository;
    private final FindObjectInRepository findObjectInRepository;

    @Override
    public ParticipationRequestDto addRequestEventById(Long userId, Long eventId) {
        User user = findObjectInRepository.getUserById(userId);
        Event event = findObjectInRepository.getEventById(eventId);
        event.setConfirmedRequests(requestRepository
                .countRequestByEventIdAndStatus(event.getId(), RequestStatus.CONFIRMED));
        if (requestRepository.findByRequesterIdAndEventId(userId, eventId).isPresent()) {
            throw new ConflictException("Пользователь с id = " + userId
                    + " уже участник события с id = " + eventId);
        }
        if (user.getId().equals(event.getInitiator().getId())) {
            throw new ConflictException("Пользователь с id = " + user.getId()
                    + "владелец события с id = " + event.getId() + " и не может подавать заявку на участие");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Событие с id = " + event.getId()
                    + " не опубликовано, нельзя подавать запросы на участие");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() <= event.getConfirmedRequests()) {
            throw new ConflictException("Превышен лимит заявок на участие в событии с id = " + eventId);
        }
        Request request = Request.builder()
                .created(LocalDateTime.now())
                .event(event)
                .requester(user)
                .status(!event.isRequestModeration() || event.getParticipantLimit() == 0 ? RequestStatus.CONFIRMED : RequestStatus.PENDING)
                .build();
        return RequestMapper.requestToParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public ParticipationRequestDto updateRequestStatus(Long userId, Long requestId) {
        User user = findObjectInRepository.getUserById(userId);
        Request request = requestRepository.findRequestById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Запрос на участие в событии с id = " + requestId
                        + " не найден"));
        if (!request.getRequester().equals(user)) {
            throw new ConflictException("Пользователь с id= " + userId
                    + "не подавал заявку с id= " + request.getId());
        }
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.requestToParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getAllRequestsUserById(Long userId) {
        findObjectInRepository.checkUserById(userId);
        List<Request> requests = requestRepository.findAllByRequesterId(userId);
        return requests.stream().map(RequestMapper::requestToParticipationRequestDto)
                .collect(Collectors.toList());
    }
}
