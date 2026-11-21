package com.eventmanagement.dao;

import com.eventmanagement.model.Event;
import com.eventmanagement.model.Registration;
import com.eventmanagement.model.Student;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Data Access Object for Registration transactions.
 * Enforces duplicate checks and referential lookups.
 * Course: 25ITT32 Java Programming & 25ITT31 DBMS | Reg No: 31
 */
public class RegistrationDAO {
    private final DataStore store = DataStore.getInstance();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public boolean isStudentRegistered(int eventId, int studentId) {
        return store.registrations.values().stream()
                .anyMatch(r -> r.getEventId() == eventId &&
                               r.getStudentId() == studentId &&
                               "CONFIRMED".equalsIgnoreCase(r.getStatus()));
    }

    public Registration insertRegistration(int eventId, int studentId) {
        int regId = store.regIdGen.incrementAndGet();
        String dateStr = LocalDateTime.now().format(FORMATTER);
        Registration reg = new Registration(regId, eventId, studentId, dateStr, "CONFIRMED");

        Event event = store.events.get(eventId);
        Student student = store.students.get(studentId);
        if (event != null) reg.setEventName(event.getEventName());
        if (student != null) {
            reg.setStudentName(student.getName());
            reg.setRegisterNo(student.getRegisterNo());
        }

        store.registrations.put(regId, reg);
        return reg;
    }

    public Optional<Registration> getRegistrationById(int registrationId) {
        return Optional.ofNullable(store.registrations.get(registrationId));
    }

    public List<Registration> getAllRegistrations() {
        return new ArrayList<>(store.registrations.values());
    }

    public List<Registration> getRegistrationsByEvent(int eventId) {
        return store.registrations.values().stream()
                .filter(r -> r.getEventId() == eventId)
                .collect(Collectors.toList());
    }

    public List<Registration> getRegistrationsByStudent(int studentId) {
        return store.registrations.values().stream()
                .filter(r -> r.getStudentId() == studentId)
                .collect(Collectors.toList());
    }

    public boolean cancelRegistration(int registrationId) {
        Registration reg = store.registrations.get(registrationId);
        if (reg != null && "CONFIRMED".equalsIgnoreCase(reg.getStatus())) {
            reg.setStatus("CANCELLED");
            return true;
        }
        return false;
    }
}
