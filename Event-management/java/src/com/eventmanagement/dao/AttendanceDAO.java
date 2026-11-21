package com.eventmanagement.dao;

import com.eventmanagement.model.Attendance;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Data Access Object for Attendance.
 * Course: 25ITT32 Java Programming & 25ITT31 DBMS | Reg No: 31
 */
public class AttendanceDAO {
    private final DataStore store = DataStore.getInstance();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public boolean markAttendance(int registrationId, String status) {
        String cleanStatus = status.trim().toUpperCase();
        if (!cleanStatus.equals("PRESENT") && !cleanStatus.equals("ABSENT")) {
            throw new IllegalArgumentException("Status must be PRESENT or ABSENT");
        }

        // Check if attendance already exists for this registration
        for (Attendance a : store.attendances.values()) {
            if (a.getRegistrationId() == registrationId) {
                a.setStatus(cleanStatus);
                a.setMarkedAt(LocalDateTime.now().format(FORMATTER));
                return true;
            }
        }

        int id = store.attIdGen.incrementAndGet();
        Attendance att = new Attendance(id, registrationId, cleanStatus, LocalDateTime.now().format(FORMATTER));
        store.attendances.put(id, att);
        return true;
    }

    public Optional<Attendance> getAttendanceByRegistrationId(int registrationId) {
        return store.attendances.values().stream()
                .filter(a -> a.getRegistrationId() == registrationId)
                .findFirst();
    }

    public List<Attendance> getAllAttendance() {
        return new ArrayList<>(store.attendances.values());
    }
}
