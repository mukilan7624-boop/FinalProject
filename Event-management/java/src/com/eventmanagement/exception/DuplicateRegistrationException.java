package com.eventmanagement.exception;

/**
 * Exception thrown when a student attempts to register for an event they have already registered for.
 * Course: 25ITT32 Java Programming (Unit 3: Exception Handling) | Reg No: 31
 */
public class DuplicateRegistrationException extends Exception {
    public DuplicateRegistrationException(String message) {
        super(message);
    }

    public DuplicateRegistrationException(int eventId, int studentId) {
        super(String.format("Registration Failed: Student ID [%d] is already registered for Event ID [%d]. Duplicate registrations are prohibited.",
                studentId, eventId));
    }
}
