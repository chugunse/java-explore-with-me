package stm.route.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stm.carrier.dto.CarrierFullDto;
import stm.carrier.model.Carrier;

@NoArgsConstructor
@Getter
@Setter
public class RouteFullDto {
    private int id;
    private String routeNumber;
    private String departurePoint;
    private String destinationPoint;
    private CarrierFullDto carrier;
    private String duration;
}
