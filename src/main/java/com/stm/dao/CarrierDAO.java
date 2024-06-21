package com.stm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.stm.util.DatabaseUtil;
import com.stm.model.Carrier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CarrierDAO {

    public List<Carrier> getAllCarriers() {
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
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + sql + " due to error: ", ex);
            return null;
        }
        return carriers;
    }

    public Carrier createCarrier(Carrier carrier) {
        String sql = "INSERT INTO carriers (name, phone) VALUES (?, ?)";
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
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + sql + " due to error: ", ex);
            return null;
        }

        return carrier;
    }

    public Carrier getCarrierById(Long id) {
        Carrier carrier = null;
        String sql = "SELECT * FROM carriers WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    carrier = new Carrier();
                    carrier.setId(rs.getLong("id"));
                    carrier.setName(rs.getString("name"));
                    carrier.setPhone(rs.getString("phone"));
                }
            }
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + sql + " due to error: ", ex);
            return null;
        }
        return carrier;
    }

    public void updateCarrier(Carrier carrier) {
        String sql = "UPDATE carriers SET name = ?, phone = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, carrier.getName());
            stmt.setString(2, carrier.getPhone());
            stmt.setLong(3, carrier.getId());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + sql + " due to error: ", ex);
        }
    }

    public void deleteCarrier(Long id) {
        String sql = "DELETE FROM carriers WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + sql + " due to error: ", ex);
        }
    }
}
