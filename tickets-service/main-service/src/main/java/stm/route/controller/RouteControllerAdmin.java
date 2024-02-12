package stm.route.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import stm.route.dto.RouteNewDto;
import stm.route.dto.RouteFullDto;
import stm.route.dto.RoutePatchDto;
import stm.route.service.RouteService;

import java.util.List;

@RestController
@RequestMapping("/admin/routes")
@RequiredArgsConstructor
@Validated
public class RouteControllerAdmin {
    private final RouteService routeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RouteFullDto addRoute(@RequestBody @Validated RouteNewDto dto) {
        return routeService.addRoute(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoute(@PathVariable int id){
        routeService.deleteRoute(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RouteFullDto getRouteById(@PathVariable int id){
        return routeService.getRouteById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RouteFullDto> getAllRoutes(){
        return routeService.getAllRoutes();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RouteFullDto patchRoute(@PathVariable int id , @RequestBody @Validated RoutePatchDto dto){
        return routeService.patchRoute(id, dto);
    }
}
