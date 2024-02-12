package stm.trip.storage;

import stm.trip.model.Trip;

import java.util.List;
import java.util.Map;

public interface TripRepository {
    Trip save(Trip trip);

    void deleteById(int id);

    Trip getById(int id);

    Integer getSoldCount(int id);

    Map<Integer, Integer> getAllSoldCount();

    List<Trip> getAll();
}
