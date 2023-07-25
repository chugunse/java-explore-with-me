package ru.practicum.event.service;

import dto.ViewStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.Event;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.request.model.Request;
import ru.practicum.request.model.RequestStatus;
import ru.practicum.request.storage.RequestRepository;
import util.Constants;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestAndViewsService {
    private final RequestRepository requestRepository;
    private final StatsClient client;

    public void confirmedRequestsForOneEvent(Event event) {
        event.setConfirmedRequests(requestRepository
                .countRequestByEventIdAndStatus(event.getId(), RequestStatus.CONFIRMED));
    }

    public void confirmedRequestForListEvent(List<Event> events) {
        Map<Event, Long> requestsPerEvent = requestRepository.findAllByEventInAndStatus(events, RequestStatus.CONFIRMED)
                .stream()
                .collect(Collectors.groupingBy(Request::getEvent, Collectors.counting()));
        if (!requestsPerEvent.isEmpty()) {
            for (Event event : events) {
                event.setConfirmedRequests(requestsPerEvent.get(event));
            }
        } else {
            events.forEach(event -> event.setConfirmedRequests(0L));
        }
    }

    public void setViewsToOneEvent(Event event) {
        setViewsToEventList(List.of(event));
    }

    public void setViewsToEventList(List<Event> events) {
        List<Long> idEvents = events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());
        String start = LocalDateTime.now().minusYears(100).format(Constants.formatter);
        String end = LocalDateTime.now().format(Constants.formatter);
        String eventsUri = "/events/";
        List<String> uris = idEvents.stream().map(id -> eventsUri + id).collect(Collectors.toList());
        List<ViewStatsDto> viewStatDtos = client.getStats(start, end, uris, true);
        Map<Long, Long> hits = viewStatDtos.stream()
                .collect(Collectors.
                        toMap(v -> Long.parseLong(v.getUri().substring(eventsUri.length())),ViewStatsDto::getHits));
        events.forEach(e -> e.setViews(hits.get(e.getId())));
    }
}
