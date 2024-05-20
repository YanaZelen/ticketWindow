package com.test_stm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.test_stm.DatabaseUtil;
import com.test_stm.model.Carrier;

public class CarrierDAO {

    public List<Carrier> getAllCarriers() throws SQLException {
        List<Carrier> carriers = new ArrayList<>();
        String sql = "SELECT * FROM carriers";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Carrier carrier = new Carrier();
                carrier.setId(rs.getLong("id"));
                carrier.setName(rs.getString("name"));
                carrier.setPhone(rs.getString("phone"));
                carriers.add(carrier);
            }
        }
        return carriers;
    }

    public void createCarrier(Carrier carrier) throws SQLException {
        String sql = "INSERT INTO carrier (name, phone) VALUES (?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, carrier.getName());
            stmt.setString(2, carrier.getPhone());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    carrier.setId(generatedKeys.getLong(1));
                }
            }
        }
    }
}
