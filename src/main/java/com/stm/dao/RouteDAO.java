package com.stm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.stm.DatabaseUtil;
import com.stm.model.Route;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class RouteDAO {

    public List<Route> getAllRoutes() {
        List<Route> routes = new ArrayList<>();
        String sql = "SELECT * FROM routes";
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Route route = new Route();
                route.setId(rs.getLong("id"));
                route.setDeparturePoint(rs.getString("departure_point"));
                route.setDestinationPoint(rs.getString("destination_point"));
                route.setCarrierId(rs.getLong("carrier_id"));
                route.setDurationInMinutes(rs.getInt("duration_in_minutes"));
                routes.add(route);
            }
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + sql + " due to error: ", ex);
            return null;
        }
        return routes;
    }

    public void createRoute(Route route) {
        String sql = "INSERT INTO route (departure_point, destination_point, carrier_id, duration_in_minutes) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, route.getDeparturePoint());
            stmt.setString(2, route.getDestinationPoint());
            stmt.setLong(3, route.getCarrierId());
            stmt.setInt(4, route.getDurationInMinutes());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    route.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + sql + " due to error: ", ex);
        }
    }

    public Route getRouteById(Long id) {
        Route route = null;
        String sql = "SELECT * FROM routes WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    route = new Route();
                    route.setId(rs.getLong("id"));
                    route.setDeparturePoint(rs.getString("departure_point"));
                    route.setDestinationPoint(rs.getString("destination_point"));
                    route.setCarrierId(rs.getLong("carrier_id"));
                    route.setDurationInMinutes(rs.getInt("duration_in_minutes"));
                }
            }
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + sql + " due to error: ", ex);
            return null;
        }
        return route;
    }

    public void updateRoute(Route route) {
        String sql = "UPDATE routes SET departure_point = ?, destination_point = ?, carrier_id = ?, duration_in_minutes = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, route.getDeparturePoint());
            stmt.setString(2, route.getDestinationPoint());
            stmt.setLong(3, route.getCarrierId());
            stmt.setInt(4, route.getDurationInMinutes());
            stmt.setLong(5, route.getId());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + sql + " due to error: ", ex);
        }
    }

    public void deleteRoute(Long id) {
        String sql = "DELETE FROM routes WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + sql + " due to error: ", ex);
        }
    }
}
