package com.eventmanagement.model;

/**
 * Model representing a College Event.
 * Supports Symposiums, Workshops, Seminars, and Cultural Events.
 *
 * Course: 25ITT32 Java Programming | Reg No: 31
 */
public class Event {
    private int eventId;
    private String eventName;
    private String category;
    private String department;
    private String eventDate;
    private String venue;
    private int maxCapacity;
    private int currentRegistrations;

    public Event() {}

    public Event(int eventId, String eventName, String category, String department,
                 String eventDate, String venue, int maxCapacity, int currentRegistrations) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.category = category;
        this.department = department;
        this.eventDate = eventDate;
        this.venue = venue;
        this.maxCapacity = maxCapacity;
        this.currentRegistrations = currentRegistrations;
    }

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }

    public int getCurrentRegistrations() { return currentRegistrations; }
    public void setCurrentRegistrations(int currentRegistrations) { this.currentRegistrations = currentRegistrations; }

    public int getAvailableSeats() {
        return Math.max(0, maxCapacity - currentRegistrations);
    }

    public boolean hasAvailableSeats() {
        return currentRegistrations < maxCapacity;
    }

    public void incrementRegistration() {
        this.currentRegistrations++;
    }

    public void decrementRegistration() {
        if (this.currentRegistrations > 0) {
            this.currentRegistrations--;
        }
    }

    public double getUtilizationRate() {
        if (maxCapacity == 0) return 0.0;
        return ((double) currentRegistrations / maxCapacity) * 100.0;
    }

    @Override
    public String toString() {
        return String.format("[%d] %-38s | %-10s | %-18s | Date: %s | Seats: %d/%d (%.1f%%)",
                eventId, eventName, category, department, eventDate, currentRegistrations, maxCapacity, getUtilizationRate());
    }
}
