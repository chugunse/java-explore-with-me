package stm.route.storage.imlp;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import stm.carrier.model.Carrier;
import stm.route.model.Route;
import stm.route.storage.RouteRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class RouteRepositoryImpl implements RouteRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public int save(Route route) {
        final String sqlQuery = "INSERT INTO route (route_number, departure_point, " +
                "destination_point, carrier_id, duration) " +
                "VALUES (?, ?, ?, ?, ?)";
        KeyHolder generatedId = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, route.getRouteNumber());
            stmt.setString(2, route.getDeparturePoint());
            stmt.setString(3, route.getDestinationPoint());
            stmt.setInt(4, route.getCarrier().getId());
            stmt.setObject(5, route.getDuration());
            return stmt;
        }, generatedId);
        return Objects.requireNonNull(generatedId.getKey()).intValue();
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM route WHERE id = ?", id);
    }

    @Override
    public Route getById(int id) {
        final String sqlQuery = "SELECT route.id, route.route_number, " +
                "route.departure_point, route.destination_point, route.duration," +
                "c.id carId, c.company, c.phone " +
                "FROM route " +
                "join carrier c on c.id = route.carrier_id " +
                "where route.id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::makeRoute, id);
    }

    @Override
    public List<Route> getAll() {
        final String sqlQuery = "SELECT route.id, route.route_number, " +
                "route.departure_point, route.destination_point, route.duration," +
                "c.id carId, c.company, c.phone " +
                "FROM route " +
                "join carrier c on c.id = route.carrier_id";
        return jdbcTemplate.query(sqlQuery, this::makeRoute);
    }

    @Override
    public Route update(Route route) {
        final String sqlQuery = "UPDATE route " +
                "SET route_number = ?, departure_point = ?, destination_point = ?, carrier_id = ?, duration = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(connection -> {
            final PreparedStatement stmt = connection.prepareStatement(sqlQuery);
            stmt.setString(1, route.getRouteNumber());
            stmt.setString(2, route.getDeparturePoint());
            stmt.setString(3, route.getDestinationPoint());
            stmt.setInt(4, route.getCarrier().getId());
            stmt.setObject(5, route.getDuration());
            stmt.setInt(6, route.getId());
            return stmt;
        });
        return route;
    }

    private Route makeRoute(ResultSet resultSet, int rowNum) throws SQLException {
        int id = resultSet.getInt("id");
        String routeNumber = resultSet.getString("route_number");
        String departurePoint = resultSet.getString("departure_point");
        String destinationPoint = resultSet.getString("destination_point");
        LocalTime duration = resultSet.getTime("duration").toLocalTime();
        Carrier carrier = new Carrier(resultSet.getInt("carId"),
                resultSet.getString("company"),
                resultSet.getString("phone"));
        return new Route(id, routeNumber, departurePoint, destinationPoint, carrier, duration);
    }
}
