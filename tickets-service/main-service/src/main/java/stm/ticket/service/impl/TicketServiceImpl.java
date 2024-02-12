package stm.ticket.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stm.exception.model.BadRequestException;
import stm.route.model.Route;
import stm.route.storage.RouteRepository;
import stm.ticket.dto.TicketDto;
import stm.ticket.dto.TicketNewDto;
import stm.ticket.mapper.TicketMapper;
import stm.ticket.model.Ticket;
import stm.ticket.service.TicketService;
import stm.ticket.storage.TicketRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final RouteRepository routeRepository;

    @Override
    public void getAllTickets(LocalDateTime rangeStart, LocalDateTime rangeEnd, String departurePoint, String destinationPoint, String carrier, Integer from, Integer size) {
        if (rangeStart != null && rangeEnd != null) {
            if (rangeEnd.isBefore(rangeStart)) {
                throw new BadRequestException("ошибка в веденных параметрах запроса времени событий");
            }
        }
    }
}
