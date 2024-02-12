package stm.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import stm.route.model.Route;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Ticket {
    private int id;
    private String trip;
    private Route route;
    private LocalDateTime dateTime;
    private int placeNumber;
    private int price;
}
