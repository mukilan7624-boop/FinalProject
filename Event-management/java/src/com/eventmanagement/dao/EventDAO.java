package com.eventmanagement.dao;

import com.eventmanagement.model.Event;
import java.sql.*;
import java.util.*;

/**
 * Data Access Object for Event entity.
 * Handles SQL database transactions with seamless fallback to DataStore.
 * Course: 25ITT32 Java Programming & 25ITT31 DBMS | Reg No: 31
 */
public class EventDAO {
    private final DataStore store = DataStore.getInstance();

    public List<Event> getAllEvents() {
        if (DBConnection.isJdbcAvailable()) {
            List<Event> list = new ArrayList<>();
            String sql = "SELECT * FROM events ORDER BY event_id ASC";
            try (Connection conn = DBConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
                return list;
            } catch (SQLException e) {
                // Fallback to store on error
            }
        }
        return new ArrayList<>(store.events.values());
    }

    public Optional<Event> getEventById(int eventId) {
        if (DBConnection.isJdbcAvailable()) {
            String sql = "SELECT * FROM events WHERE event_id = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, eventId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(mapResultSet(rs));
                    }
                }
            } catch (SQLException e) {
                // Fallback
            }
        }
        return Optional.ofNullable(store.events.get(eventId));
    }

    public boolean createEvent(Event event) {
        if (DBConnection.isJdbcAvailable()) {
            String sql = "INSERT INTO events (event_name, category, department, event_date, venue, max_capacity, current_registrations) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, event.getEventName());
                pstmt.setString(2, event.getCategory());
                pstmt.setString(3, event.getDepartment());
                pstmt.setString(4, event.getEventDate());
                pstmt.setString(5, event.getVenue());
                pstmt.setInt(6, event.getMaxCapacity());
                pstmt.setInt(7, 0);
                int affected = pstmt.executeUpdate();
                if (affected > 0) {
                    try (ResultSet keys = pstmt.getGeneratedKeys()) {
                        if (keys.next()) event.setEventId(keys.getInt(1));
                    }
                    store.events.put(event.getEventId(), event);
                    return true;
                }
            } catch (SQLException e) {
                // Fallback to memory
            }
        }
        int id = store.eventIdGen.incrementAndGet();
        event.setEventId(id);
        store.events.put(id, event);
        return true;
    }

    public boolean updateEvent(Event event) {
        if (DBConnection.isJdbcAvailable()) {
            String sql = "UPDATE events SET event_name=?, category=?, department=?, event_date=?, venue=?, max_capacity=? " +
                         "WHERE event_id=?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, event.getEventName());
                pstmt.setString(2, event.getCategory());
                pstmt.setString(3, event.getDepartment());
                pstmt.setString(4, event.getEventDate());
                pstmt.setString(5, event.getVenue());
                pstmt.setInt(6, event.getMaxCapacity());
                pstmt.setInt(7, event.getEventId());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                // Ignore
            }
        }
        store.events.put(event.getEventId(), event);
        return true;
    }

    public boolean deleteEvent(int eventId) {
        if (DBConnection.isJdbcAvailable()) {
            String sql = "DELETE FROM events WHERE event_id=?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, eventId);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                // Ignore
            }
        }
        return store.events.remove(eventId) != null;
    }

    public void incrementRegistrationCount(int eventId) {
        Optional<Event> eventOpt = getEventById(eventId);
        eventOpt.ifPresent(Event::incrementRegistration);
    }

    public void decrementRegistrationCount(int eventId) {
        Optional<Event> eventOpt = getEventById(eventId);
        eventOpt.ifPresent(Event::decrementRegistration);
    }

    private Event mapResultSet(ResultSet rs) throws SQLException {
        return new Event(
                rs.getInt("event_id"),
                rs.getString("event_name"),
                rs.getString("category"),
                rs.getString("department"),
                rs.getString("event_date"),
                rs.getString("venue"),
                rs.getInt("max_capacity"),
                rs.getInt("current_registrations")
        );
    }
}
