package stm.route.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import stm.carrier.model.Carrier;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@Builder
public class Route {
    private int id;
    private String routeNumber;
    private String departurePoint;
    private String destinationPoint;
    private Carrier carrier;
    private LocalTime duration;
}