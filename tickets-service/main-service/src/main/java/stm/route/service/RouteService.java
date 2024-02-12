package stm.route.service;

import stm.route.dto.RouteNewDto;
import stm.route.dto.RouteFullDto;
import stm.route.dto.RoutePatchDto;

import java.util.List;

public interface RouteService {

    RouteFullDto addRoute(RouteNewDto dto);

    void deleteRoute(int id);

    RouteFullDto getRouteById(int id);

    List<RouteFullDto> getAllRoutes();

    RouteFullDto patchRoute(int id, RoutePatchDto dto);
}
