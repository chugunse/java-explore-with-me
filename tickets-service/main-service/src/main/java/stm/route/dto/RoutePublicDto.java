package stm.route.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stm.carrier.dto.CarrierDto;

@NoArgsConstructor
@Getter
@Setter
public class RoutePublicDto {
    private String routeNumber;
    private String departurePoint;
    private String destinationPoint;
    private CarrierDto carrier;
    private String duration;
}