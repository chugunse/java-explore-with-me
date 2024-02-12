package stm.ticket.storage;

import stm.ticket.dto.TicketDto;
import stm.ticket.model.Ticket;

public interface TicketRepository {
    TicketDto saveTickets(Ticket ticket, int amount);
}
