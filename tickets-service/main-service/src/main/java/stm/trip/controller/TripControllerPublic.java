package stm.trip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import stm.trip.dto.TripFullDto;
import stm.trip.dto.TripPublicDto;
import stm.trip.service.impl.TripServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripControllerPublic {
    private final TripServiceImpl tripService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TripPublicDto> getAllTrips() {
        return tripService.getAllTripsToPublic();
    }
}
