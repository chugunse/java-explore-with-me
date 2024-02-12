package stm.ticket.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import stm.ticket.dto.TicketDto;
import stm.ticket.model.Ticket;
import stm.ticket.storage.TicketRepository;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryImpl implements TicketRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public TicketDto saveTickets(Ticket ticket, int amount) {
        return null;
    }
}
