package stm.ticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stm.route.dto.RouteFullDto;
import stm.route.dto.RoutePublicDto;
import stm.route.model.Route;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class TicketDto {
    private int id;
    private String trip;
    private RoutePublicDto route;
    private LocalDateTime dateTime;
    private int placeNumber;
    private int price;
}
