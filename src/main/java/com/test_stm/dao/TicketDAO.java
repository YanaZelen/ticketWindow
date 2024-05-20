package com.test_stm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.test_stm.DatabaseUtil;
import com.test_stm.model.Carrier;
import com.test_stm.model.Ticket;

public class TicketDAO {

    public List<Ticket> getAllTickets() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getLong("id"));
                ticket.setRouteId(rs.getLong("route_id"));
                ticket.setDateTime(rs.getTimestamp("dateTime").toLocalDateTime());
                ticket.setSeatNumber(rs.getString("seat_number"));
                ticket.setPrice(rs.getDouble("price"));
                ticket.setAvailable(rs.getBoolean("is_available"));
                tickets.add(ticket);
            }
        }
        return tickets;
    } 
    
    public Ticket getTicketById(Long id) throws SQLException {
        Ticket ticket = null;
        String sql = "SELECT * FROM tickets WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ticket = new Ticket();
                    ticket.setId(rs.getLong("id"));
                    ticket.setDateTime(rs.getTimestamp("dateTime").toLocalDateTime());
                    ticket.setRouteId(rs.getLong("route_id"));
                    ticket.setSeatNumber(rs.getString("seat_number"));
                    ticket.setPrice(rs.getDouble("price"));
                    ticket.setAvailable(rs.getBoolean("isAvailable"));
                }
            }
        }
        return ticket;
    }

    public void createTicket(Ticket ticket) throws SQLException {
        String sql = "INSERT INTO tickets (id, route_id, dateTime, seat_number, price, is_available) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, ticket.getRouteId());
            stmt.setTimestamp(2, Timestamp.valueOf(ticket.getDateTime()));
            stmt.setString(3, ticket.getSeatNumber());
            stmt.setDouble(4, ticket.getPrice());
            stmt.setBoolean(5, ticket.isAvailable());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        ticket.setId(generatedKeys.getLong(1));
                    }
                }
            }
        }
    }

    public void updateTicket(Ticket ticket) throws SQLException {
        String sql = "UPDATE tickets SET route_id = ?, dateTime = ?, seat_number = ?, price = ?, is_available = ? WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, ticket.getRouteId());
            stmt.setTimestamp(2, Timestamp.valueOf(ticket.getDateTime()));
            stmt.setString(3, ticket.getSeatNumber());
            stmt.setDouble(4, ticket.getPrice());
            stmt.setBoolean(5, ticket.isAvailable());
            stmt.setLong(6, ticket.getId());

            stmt.executeUpdate();
        }
    }

    public void deleteTicket(Long id) throws SQLException {
        String sql = "DELETE FROM tickets WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public boolean purchaseTicket(Long id) throws SQLException {
        String selectSql = "SELECT is_available FROM tickets WHERE id = ?";
        String updateSql = "UPDATE tickets SET is_available = ? WHERE id = ? AND is_available = ?";

        try (Connection conn = DatabaseUtil.getConnection()) {
            conn.setAutoCommit(false);  

            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setLong(1, id);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        boolean isAvailable = rs.getBoolean("is_available");
                        if (!isAvailable) {
                            conn.rollback();
                            return false;  
                        }
                    } else {
                        conn.rollback();
                        throw new SQLException("Ticket not found");
                    }
                }
            }

            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setBoolean(1, false);  
                updateStmt.setLong(2, id);
                updateStmt.setBoolean(3, true);  

                int affectedRows = updateStmt.executeUpdate();
                if (affectedRows == 0) {
                    conn.rollback();
                    return false;  
                }
            }

            conn.commit();  
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Ticket> getAllTicketsForUser(Long userId) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE user_id = ? AND is_available = false";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ticket ticket = new Ticket();
                    ticket.setId(rs.getLong("id"));
                    ticket.setDateTime(rs.getTimestamp("dateTime").toLocalDateTime());
                    ticket.setRouteId(rs.getLong("route_id"));
                    ticket.setSeatNumber(rs.getString("seat_number"));
                    ticket.setPrice(rs.getDouble("price"));
                    ticket.setUserId(rs.getLong("user_id"));
                    tickets.add(ticket);
                }
            }
        }
        return tickets;
    }
}
