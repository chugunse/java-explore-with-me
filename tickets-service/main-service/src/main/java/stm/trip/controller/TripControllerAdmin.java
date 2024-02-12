package stm.trip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import stm.trip.dto.TripFullDto;
import stm.trip.dto.TripNewDto;
import stm.trip.dto.TripUpdateDto;
import stm.trip.service.impl.TripServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/admin/trips")
@RequiredArgsConstructor
public class TripControllerAdmin {
    private final TripServiceImpl tripService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TripFullDto addTrip(@RequestBody @Validated TripNewDto dto){
        return tripService.addTrip(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrip(@PathVariable int id){
        tripService.deleteTrip(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TripFullDto getTripById(@PathVariable int id){
        return tripService.getTripById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TripFullDto> getAllTrips(){
        return tripService.getAllTrips();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TripFullDto patchTrip(@RequestBody @Validated TripUpdateDto dto, @PathVariable int id){
        return tripService.updateTrip(dto, id);
    }
}
