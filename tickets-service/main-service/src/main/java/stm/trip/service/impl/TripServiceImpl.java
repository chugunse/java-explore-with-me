package stm.trip.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import stm.exception.model.ResourceNotFoundException;
import stm.route.model.Route;
import stm.route.storage.RouteRepository;
import stm.trip.dto.TripFullDto;
import stm.trip.dto.TripNewDto;
import stm.trip.dto.TripPublicDto;
import stm.trip.dto.TripUpdateDto;
import stm.trip.mapper.TripMapper;
import stm.trip.model.Trip;
import stm.trip.service.TripService;
import stm.trip.storage.TripRepository;
import stm.util.Constants;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private final RouteRepository routeRepository;
    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    @Override
    public TripFullDto addTrip(TripNewDto dto) {
        Route route = routeRepository.getById(dto.getRoute());
        Trip trip = tripMapper.toModel(dto);
        trip.setRoute(route);
        trip = tripRepository.save(trip);
        return tripMapper.toFullDto(trip);
    }

    @Override
    public void deleteTrip(int id) {
        tripRepository.deleteById(id);
    }

    @Override
    public TripFullDto getTripById(int id) {
        Trip trip;
        try {
            trip = tripRepository.getById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException(exception.getMessage());
        }
        TripFullDto dto = tripMapper.toFullDto(trip);
        dto.setSold(tripRepository.getSoldCount(id));
        return dto;
    }

    @Override
    public List<TripFullDto> getAllTrips() {
        List<TripFullDto> listDto = tripMapper.toFullDtoList(tripRepository.getAll());
        Map<Integer, Integer> mapSold = tripRepository.getAllSoldCount();
        for (TripFullDto trip : listDto) {
            if (mapSold.containsKey(trip.getId())) {
                trip.setSold(mapSold.get(trip.getId()));
            }
        }
        return listDto;
    }

    @Override
    public List<TripPublicDto> getAllTripsToPublic() {
        List<TripPublicDto> list = new ArrayList<>();
        List<Trip> tripList = tripRepository.getAll();
        Map<Integer, Integer> mapSold = tripRepository.getAllSoldCount();
        for (Trip trip : tripList){
            int available = trip.getAmount();
            if (mapSold.containsKey(trip.getId())){
                available = available - mapSold.get(trip.getId());
            }
            TripPublicDto tripPublicDto = tripMapper.toPublicDto(trip);
            tripPublicDto.setAvailable(available);
            list.add(tripPublicDto);
        }
        return list;
    }

    @Override
    public TripFullDto updateTrip(TripUpdateDto dto, int id) {
        Trip trip = tripRepository.getById(id);
        ofNullable(dto.getTitle()).ifPresent(trip::setTitle);
        Optional.of(dto.getPrice()).ifPresent(trip::setPrice);
        if (dto.getDateTime() != null) {
            trip.setDateTime(LocalDateTime.parse(dto.getDateTime(), Constants.formatterDateTime));
        }
        tripRepository.save(trip);
        TripFullDto tripFullDto = tripMapper.toFullDto(trip);
        tripFullDto.setSold(tripRepository.getSoldCount(id));
        return tripFullDto;
    }
}
