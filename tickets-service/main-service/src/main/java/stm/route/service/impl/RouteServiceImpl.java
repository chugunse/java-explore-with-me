package stm.route.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import stm.carrier.dto.CarrierFullDto;
import stm.carrier.mapper.CarrierMapper;
import stm.carrier.model.Carrier;
import stm.carrier.storage.CarrierRepository;
import stm.exception.model.ResourceNotFoundException;
import stm.route.dto.RouteNewDto;
import stm.route.dto.RouteFullDto;
import stm.route.dto.RoutePatchDto;
import stm.route.mapper.RouteMapper;
import stm.route.model.Route;
import stm.route.service.RouteService;
import stm.route.storage.RouteRepository;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;
    private final CarrierRepository carrierRepository;
    private final RouteMapper routeMapper;
    private final CarrierMapper carrierMapper;

    @Override
    public RouteFullDto addRoute(RouteNewDto dto) {
        Carrier carrier;
        try {
            carrier = carrierRepository.getById(dto.getCarrierId());
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException(exception.getMessage());
        }
        Route route = routeMapper.toRouteModel(dto);
        route.setCarrier(carrier);
        route.setId(routeRepository.save(route));
        return routeMapper.toRouteFullDto(route);
    }

    @Override
    public void deleteRoute(int id) {
        routeRepository.deleteById(id);
    }

    @Override
    public RouteFullDto getRouteById(int id) {
        return routeMapper.toRouteFullDto(getById(id));
    }

    @Override
    public List<RouteFullDto> getAllRoutes() {
        return routeMapper.toRouteFullDtoList(routeRepository.getAll());
    }

    @Override
    public RouteFullDto patchRoute(int id, RoutePatchDto dto) {
        RouteFullDto old = getRouteById(id);
        ofNullable(dto.getDeparturePoint()).ifPresent(old::setDeparturePoint);
        ofNullable(dto.getDestinationPoint()).ifPresent(old::setDestinationPoint);
        ofNullable(dto.getDuration()).ifPresent(old::setDuration);
        ofNullable(dto.getRouteNumber()).ifPresent(old::setRouteNumber);
        if (dto.getCarrierId() != null) {
            CarrierFullDto carrierFullDto = carrierMapper
                    .toCarrierFullDto(carrierRepository.getById(dto.getCarrierId()));
            old.setCarrier(carrierFullDto);
        }
        routeRepository.update(routeMapper.toRouteModel(old));
        return old;
    }

    public Route getById(int id) {
        try {
            return routeRepository.getById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }
}
