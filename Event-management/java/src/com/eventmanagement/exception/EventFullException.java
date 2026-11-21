package com.eventmanagement.exception;

/**
 * Exception thrown when an event has reached its maximum capacity.
 * Course: 25ITT32 Java Programming (Unit 3: Exception Handling) | Reg No: 31
 */
public class EventFullException extends Exception {
    public EventFullException(String message) {
        super(message);
    }

    public EventFullException(String eventName, int maxCapacity) {
        super(String.format("Registration Failed: Event '%s' is completely full (Capacity limit: %d seats reached).",
                eventName, maxCapacity));
    }
}
