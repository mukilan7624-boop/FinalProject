package com.eventmanagement.model;

/**
 * Model representing Attendance record.
 * Course: 25ITT32 Java Programming | Reg No: 31
 */
public class Attendance {
    private int attendanceId;
    private int registrationId;
    private String status; // PRESENT, ABSENT
    private String markedAt;

    public Attendance() {}

    public Attendance(int attendanceId, int registrationId, String status, String markedAt) {
        this.attendanceId = attendanceId;
        this.registrationId = registrationId;
        this.status = status;
        this.markedAt = markedAt;
    }

    public int getAttendanceId() { return attendanceId; }
    public void setAttendanceId(int attendanceId) { this.attendanceId = attendanceId; }

    public int getRegistrationId() { return registrationId; }
    public void setRegistrationId(int registrationId) { this.registrationId = registrationId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMarkedAt() { return markedAt; }
    public void setMarkedAt(String markedAt) { this.markedAt = markedAt; }

    public boolean isPresent() {
        return "PRESENT".equalsIgnoreCase(status);
    }

    @Override
    public String toString() {
        return String.format("AttendanceID: %d | RegistrationID: %d | Status: %s | Time: %s",
                attendanceId, registrationId, status, markedAt);
    }
}
