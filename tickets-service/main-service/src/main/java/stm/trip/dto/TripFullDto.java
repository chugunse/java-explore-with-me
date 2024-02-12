package stm.trip.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stm.route.model.Route;

@NoArgsConstructor
@Setter
@Getter
public class TripFullDto {
    private int id;
    private String title;
    private Route route;
    private String dateTime;
    private int price;
    private int amount;
    private int sold;
}
