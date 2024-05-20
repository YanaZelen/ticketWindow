package com.test_stm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.test_stm.DatabaseUtil;
import com.test_stm.model.Route;

public class RouteDAO {

    public List<Route> getAllRoutes() throws SQLException {
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
        }
        return routes;
    }

    public void saveRoute(Route route) throws SQLException {
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
        }
    }
}

