package stm.ticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stm.route.model.Route;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class TicketNewDto {
    private String trip;
    private int route;
    private String dateTime;
    private int price;
}
