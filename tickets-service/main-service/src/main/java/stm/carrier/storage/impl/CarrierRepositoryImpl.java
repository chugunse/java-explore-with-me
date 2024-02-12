package stm.carrier.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import stm.carrier.model.Carrier;
import stm.carrier.storage.CarrierRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class CarrierRepositoryImpl implements CarrierRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Carrier save(Carrier carrier) {
        final String sqlQuery = "INSERT INTO carrier (company, phone) VALUES ( ?, ?)";
        KeyHolder generatedId = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, carrier.getCompany());
            stmt.setString(2, carrier.getPhone());
            return stmt;
        }, generatedId);
        carrier.setId(Objects.requireNonNull(generatedId.getKey()).intValue());
        return carrier;
    }

    @Override
    public Carrier getById(int id) {
        final String sqlQuery = "SELECT * FROM carrier WHERE id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::makeCarrier, id);
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM carrier WHERE id = ?", id);
    }

    @Override
    public List<Carrier> getAll() {
        final String sqlQuery = "SELECT id, company, phone FROM carrier ";
        return jdbcTemplate.query(sqlQuery, this::makeCarrier);
    }

    @Override
    public Carrier update(Carrier carrier) {
        final String sqlQuery = "UPDATE carrier SET company = ?, phone = ? WHERE id = ?";
        jdbcTemplate.update(sqlQuery, carrier.getCompany(), carrier.getPhone(), carrier.getId());
        return carrier;
    }


    private Carrier makeCarrier(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String company = rs.getString("company");
        String phone = rs.getString("phone");
        return new Carrier(id, company, phone);
    }
}
