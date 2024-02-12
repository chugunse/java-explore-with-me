package stm.route.storage;

import stm.route.model.Route;

import java.util.List;

public interface RouteRepository {
    int save(Route route);

    void deleteById(int id);

    Route getById(int id);

    List<Route> getAll();

    Route update(Route route);
}
