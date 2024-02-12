package stm.trip.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import stm.carrier.model.Carrier;
import stm.route.model.Route;
import stm.trip.model.Trip;
import stm.trip.storage.TripRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class TripRepositoryImpl implements TripRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Trip save(Trip trip) {
        final String sqlQueryForTrip = "INSERT INTO trip (title, route_id, date_time, price, amount) " +
                "VALUES (?, ?, ?, ?, ?)";
        final String sqlQueryForTicket = "INSERT INTO ticket (trip_id, place_number) " +
                "VALUES (?, ?)";
        List<Integer> places = Stream.iterate(1, n -> n + 1)
                .limit(trip.getAmount())
                .collect(Collectors.toList());
        KeyHolder generatedId = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement stmt = connection.prepareStatement(sqlQueryForTrip, new String[]{"id"});
            stmt.setString(1, trip.getTitle());
            stmt.setInt(2, trip.getRoute().getId());
            stmt.setObject(3, trip.getDateTime());
            stmt.setInt(4, trip.getPrice());
            stmt.setInt(5, trip.getAmount());
            return stmt;
        }, generatedId);
        trip.setId(Objects.requireNonNull(generatedId.getKey()).intValue());
        jdbcTemplate.batchUpdate(sqlQueryForTicket, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, trip.getId());
                ps.setInt(2, places.get(i));
            }
            @Override
            public int getBatchSize() {
                return places.size();
            }
        });
        return trip;
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM trip WHERE id = ?", id);
    }

    @Override
    public Trip getById(int id) {
        final String sqlQuery ="SELECT t.id as tId, t.title, t.date_time, t.price, t.amount, " +
                "t.route_id, r.route_number, r.destination_point, r.departure_point, r.duration, " +
                "c.id as cId, c.company, c.phone " +
                "from trip as t " +
                "join route r on r.id = t.route_id " +
                "join carrier c on c.id = r.carrier_id " +
                "where t.id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::makeTrip, id);
    }

    @Override
    public Integer getSoldCount(int id) {
        final String sqlQuery = "select count(id) from ticket where trip_id = ? and user_id is not null";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> rs.getInt("count"), id);
    }

    @Override
    public Map<Integer, Integer> getAllSoldCount(){
        final String sqlQuery = "select trip_id, count(id) " +
                "from ticket " +
                "where user_id is not null " +
                "group by trip_id";
        return jdbcTemplate.query(sqlQuery, rs -> {
            HashMap<Integer,Integer>result = new HashMap<>();
            while (rs.next()){
                result.put(rs.getInt("trip_id"), rs.getInt("count"));
            }
            return result;
        });
    }

    @Override
    public List<Trip> getAll() {
        final String sqlQuery ="SELECT t.id as tId, t.title, t.date_time, t.price, t.amount, " +
                "t.route_id, r.route_number, r.destination_point, r.departure_point, r.duration, " +
                "c.id as cId, c.company, c.phone " +
                "from trip as t " +
                "join route r on r.id = t.route_id " +
                "join carrier c on c.id = r.carrier_id";
        return jdbcTemplate.query(sqlQuery, this::makeTrip);
    }

    private Trip makeTrip(ResultSet resultSet, int rowNum) throws SQLException {
        Trip trip = new Trip();
        trip.setId(resultSet.getInt("tId"));
        trip.setTitle(resultSet.getString("title"));
        trip.setDateTime(resultSet.getObject("date_time", LocalDateTime.class));
        trip.setPrice(resultSet.getInt("price"));
        trip.setAmount(resultSet.getInt("amount"));
        Carrier carrier = Carrier.builder()
                .id(resultSet.getInt("cId"))
                .company(resultSet.getString("company"))
                .phone(resultSet.getString("phone"))
                .build();
        Route route = Route.builder()
                .id(resultSet.getInt("route_id"))
                .routeNumber(resultSet.getString("route_number"))
                .departurePoint(resultSet.getString("departure_point"))
                .destinationPoint(resultSet.getString("destination_point"))
                .duration(resultSet.getTime("duration").toLocalTime())
                .carrier(carrier)
                .build();
        trip.setRoute(route);
        return trip;
    }
}
