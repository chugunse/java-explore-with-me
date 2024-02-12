package stm.ticket.service;

import stm.ticket.dto.TicketDto;
import stm.ticket.dto.TicketNewDto;

import java.time.LocalDateTime;

public interface TicketService {

    void getAllTickets(LocalDateTime rangeStart,
                       LocalDateTime rangeEnd,
                       String departurePoint,
                       String destinationPoint,
                       String carrier,
                       Integer from,
                       Integer size);
}
