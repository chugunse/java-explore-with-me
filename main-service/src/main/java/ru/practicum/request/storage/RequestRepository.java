package ru.practicum.request.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.Event;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.RequestStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findByRequesterIdAndEventId(Long userId, Long eventId);

    Long countRequestByEventIdAndStatus(Long eventId, RequestStatus state);

    Optional<Request> findRequestById(Long id);

    List<Request> findAllByRequesterId(Long id);

    List<Request> findAllByEventInAndStatus(List<Event> event, RequestStatus status);

    List<Request> findAllByEventId(Long eventId);

    List<Request> findAllByIdIsIn(List<Long> requestId);
}