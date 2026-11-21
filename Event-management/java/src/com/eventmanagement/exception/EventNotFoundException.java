package com.eventmanagement.exception;

/**
 * Exception thrown when a requested event cannot be found.
 * Course: 25ITT32 Java Programming (Unit 3: Exception Handling) | Reg No: 31
 */
public class EventNotFoundException extends Exception {
    public EventNotFoundException(String message) {
        super(message);
    }

    public EventNotFoundException(int eventId) {
        super("Event with ID [" + eventId + "] was not found in the database.");
    }
}
