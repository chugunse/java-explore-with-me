package stm.trip.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stm.route.dto.RoutePublicDto;

@NoArgsConstructor
@Setter
@Getter
public class TripPublicDto {
    private String title;
    private RoutePublicDto route;
    private String dateTime;
    private int price;
    private int available;
}