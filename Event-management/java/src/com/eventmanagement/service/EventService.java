package com.eventmanagement.service;

import com.eventmanagement.dao.*;
import com.eventmanagement.exception.*;
import com.eventmanagement.model.*;
import java.util.*;

/**
 * Service Layer coordinating core business logic, validations, and ACID integrity.
 * Course: 25ITT32 Java Programming & 25ITT33 Software Engineering | Reg No: 31
 */
public class EventService {
    private final EventDAO eventDAO = new EventDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final RegistrationDAO registrationDAO = new RegistrationDAO();
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final FeedbackDAO feedbackDAO = new FeedbackDAO();

    public List<Event> listAllEvents() {
        return eventDAO.getAllEvents();
    }

    public Optional<Event> getEvent(int eventId) {
        return eventDAO.getEventById(eventId);
    }

    public boolean createEvent(Event event) {
        if (event.getMaxCapacity() <= 0) {
            throw new IllegalArgumentException("Maximum event capacity must be greater than zero.");
        }
        return eventDAO.createEvent(event);
    }

    public boolean updateEvent(Event event) {
        return eventDAO.updateEvent(event);
    }

    public boolean deleteEvent(int eventId) {
        return eventDAO.deleteEvent(eventId);
    }

    /**
     * Registers a student for an event while enforcing:
     * 1. Event existence
     * 2. Student existence
     * 3. Capacity limit (throws EventFullException)
     * 4. Duplicate registration prevention (throws DuplicateRegistrationException)
     */
    public synchronized Registration registerStudent(int eventId, int studentId)
            throws EventNotFoundException, EventFullException, DuplicateRegistrationException {

        // 1. Verify Event
        Event event = eventDAO.getEventById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        // 2. Verify Student
        Student student = studentDAO.getStudentById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student with ID [" + studentId + "] not found."));

        // 3. Verify Capacity Limit
        if (!event.hasAvailableSeats()) {
            throw new EventFullException(event.getEventName(), event.getMaxCapacity());
        }

        // 4. Verify Duplicate Registration
        if (registrationDAO.isStudentRegistered(eventId, studentId)) {
            throw new DuplicateRegistrationException(eventId, studentId);
        }

        // 5. Proceed with registration
        Registration reg = registrationDAO.insertRegistration(eventId, studentId);
        eventDAO.incrementRegistrationCount(eventId);

        return reg;
    }

    /**
     * Cancels an existing registration and frees up a seat.
     */
    public boolean cancelRegistration(int registrationId) {
        Optional<Registration> regOpt = registrationDAO.getRegistrationById(registrationId);
        if (regOpt.isPresent()) {
            Registration reg = regOpt.get();
            boolean success = registrationDAO.cancelRegistration(registrationId);
            if (success) {
                eventDAO.decrementRegistrationCount(reg.getEventId());
            }
            return success;
        }
        return false;
    }

    /**
     * Marks student attendance (PRESENT or ABSENT).
     */
    public boolean markAttendance(int registrationId, String status) {
        Registration reg = registrationDAO.getRegistrationById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Registration ID [" + registrationId + "] not found."));
        return attendanceDAO.markAttendance(registrationId, status);
    }

    /**
     * Records feedback and ratings from attendees.
     * Enforces prerequisite that only attended students (PRESENT) can rate.
     */
    public boolean submitFeedback(int registrationId, int rating, String comments) {
        Registration reg = registrationDAO.getRegistrationById(registrationId)
                .orElseThrow(() -> new IllegalArgumentException("Registration ID [" + registrationId + "] not found."));

        Optional<Attendance> attOpt = attendanceDAO.getAttendanceByRegistrationId(registrationId);
        if (attOpt.isEmpty() || !attOpt.get().isPresent()) {
            throw new IllegalStateException("Feedback Rejected: Only students marked as PRESENT can submit feedback.");
        }

        return feedbackDAO.submitFeedback(registrationId, rating, comments);
    }

    public List<Registration> getStudentRegistrations(int studentId) {
        return registrationDAO.getRegistrationsByStudent(studentId);
    }

    public Optional<Student> findStudentByRegisterNo(String regNo) {
        return studentDAO.getStudentByRegisterNo(regNo);
    }
}
