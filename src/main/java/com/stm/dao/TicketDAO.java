package com.stm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.stm.DatabaseUtil;
import com.stm.model.Ticket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TicketDAO {

    public List<Ticket> getAllTickets() {
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
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + sql + " due to error: ", ex);
            return null;
        }
        return tickets;
    }

    public Ticket getTicketById(Long id) {
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
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + sql + " due to error: ", ex);
            return null;
        }
        return ticket;
    }

    public Ticket createTicket(Ticket ticket) {
        String sql = "INSERT INTO tickets (route_id, dateTime, seat_number, price, is_available) VALUES (?, ?, ?, ?, ?)";
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
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + sql + " due to error: ", ex);
            return null;
        }
        return ticket;
    }

    public void updateTicket(Ticket ticket) {
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
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + sql + " due to error: ", ex);
        }
    }

    public void deleteTicket(Long id) {
        String sql = "DELETE FROM tickets WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + sql + " due to error: ", ex);
        }
    }

    public boolean purchaseTicket(Long id) {
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
            } catch (SQLException ex) {
                log.error("Can't execute SQL: " + updateSql + " due to error: ", ex);
                return false;
            }

            conn.commit();
            return true;
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + selectSql + " due to error: ", ex);
            return false;
        }
    }

    public List<Ticket> getAllTicketsForUser(Long userId) {
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
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + sql + " due to error: ", ex);
            return null;
        }
        return tickets;
    }

    public Page<Ticket> searchTickets(String departure, String destination, String carrier, LocalDateTime dateTime,
            int pageNumber, int pageSize) {
        List<Ticket> tickets = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM tickets WHERE 1=1"); // заведомо правдивое условие для
                                                                                  // последующего добавления условий
        List<Object> params = new ArrayList<>();

        addSearchCriteria(sql, params, "departure", departure);
        addSearchCriteria(sql, params, "destination", destination);
        addSearchCriteria(sql, params, "carrier", carrier);
        if (dateTime != null) {
            sql.append(" AND dateTime = ?");
            params.add(dateTime);
        }

        sql.append(" LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add(pageNumber * pageSize);

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            setParameters(pstmt, params);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    tickets.add(mapResultSetToTicket(rs));
                }
            }
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + sql + " due to error: ", ex);
            return null;
        }

        long totalRecords = getTotalCount(departure, destination, carrier, dateTime);

        return new PageImpl<>(tickets, PageRequest.of(pageNumber, pageSize), totalRecords);
    }

    private void addSearchCriteria(StringBuilder sql, List<Object> params, String column, String value) {
        if (value != null) {
            sql.append(" AND ").append(column).append(" LIKE ?");
            params.add("%" + value + "%");
        }
    }

    private void setParameters(PreparedStatement pstmt, List<Object> params) throws SQLException {
        int index = 1;
        for (Object param : params) {
            if (param instanceof LocalDateTime) {
                pstmt.setTimestamp(index, Timestamp.valueOf((LocalDateTime) param));
            } else {
                pstmt.setObject(index, param);
            }
            index++;
        }
    }

    private Ticket mapResultSetToTicket(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();
        ticket.setId(rs.getLong("id"));
        ticket.setDateTime(rs.getTimestamp("dateTime").toLocalDateTime());
        ticket.setRouteId(rs.getLong("route_id"));
        ticket.setSeatNumber(rs.getString("seat_number"));
        ticket.setPrice(rs.getDouble("price"));
        ticket.setUserId(rs.getLong("user_id"));
        return ticket;
    }

    private long getTotalCount(String departure, String destination, String carrier, LocalDateTime dateTime) {
        StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM tickets WHERE 1=1");
        List<Object> params = new ArrayList<>();

        addSearchCriteria(countSql, params, "departure", departure);
        addSearchCriteria(countSql, params, "destination", destination);
        addSearchCriteria(countSql, params, "carrier", carrier);
        if (dateTime != null) {
            countSql.append(" AND dateTime = ?");
            params.add(dateTime);
        }

        try (Connection conn = DatabaseUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(countSql.toString())) {

            setParameters(pstmt, params);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException ex) {
            log.error("Can't execute SQL: " + countSql + " due to error: ", ex);
            return 0;
        }
        return 0;
    }
}