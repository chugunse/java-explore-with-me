package ru.practicum.event.service;

import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.UpdateEventAdminRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventServiceAdmin {
    EventFullDto updateEventById(Long eventId,
                                 UpdateEventAdminRequest updateEventAdminRequest,
                                 HttpServletRequest request);


    List<EventFullDto> getAllEventsForAdmin(List<Long> users,
                                            List<String> states,
                                            List<Long> categories,
                                            LocalDateTime start,
                                            LocalDateTime end,
                                            int from,
                                            int size);

}

