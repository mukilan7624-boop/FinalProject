package com.eventmanagement.model;

/**
 * Model representing an Event Registration transaction.
 * Course: 25ITT32 Java Programming | Reg No: 31
 */
public class Registration {
    private int registrationId;
    private int eventId;
    private int studentId;
    private String registrationDate;
    private String status; // CONFIRMED, CANCELLED

    // Additional display fields for joined query representation
    private String eventName;
    private String studentName;
    private String registerNo;

    public Registration() {}

    public Registration(int registrationId, int eventId, int studentId, String registrationDate, String status) {
        this.registrationId = registrationId;
        this.eventId = eventId;
        this.studentId = studentId;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    public int getRegistrationId() { return registrationId; }
    public void setRegistrationId(int registrationId) { this.registrationId = registrationId; }

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(String registrationDate) { this.registrationDate = registrationDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getRegisterNo() { return registerNo; }
    public void setRegisterNo(String registerNo) { this.registerNo = registerNo; }

    @Override
    public String toString() {
        return String.format("RegID: %-4d | EventID: %-3d (%s) | StudentID: %-3d (%s) | Status: %s | Date: %s",
                registrationId, eventId, (eventName != null ? eventName : "N/A"),
                studentId, (registerNo != null ? registerNo : "N/A"), status, registrationDate);
    }
}
