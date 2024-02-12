package stm.trip.model;

import lombok.Data;
import stm.route.model.Route;

import java.time.LocalDateTime;

@Data
public class Trip {
    private int id;
    private String Title;
    private Route route;
    private LocalDateTime dateTime;
    private int price;
    private int amount;
}
