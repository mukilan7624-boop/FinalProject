package com.eventmanagement.model;

/**
 * Model representing a Student participant.
 * Course: 25ITT32 Java Programming | Reg No: 31
 */
public class Student {
    private int studentId;
    private String registerNo;
    private String name;
    private String department;
    private String email;
    private String contactNumber;

    public Student() {}

    public Student(int studentId, String registerNo, String name, String department, String email, String contactNumber) {
        this.studentId = studentId;
        this.registerNo = registerNo;
        this.name = name;
        this.department = department;
        this.email = email;
        this.contactNumber = contactNumber;
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getRegisterNo() { return registerNo; }
    public void setRegisterNo(String registerNo) { this.registerNo = registerNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    @Override
    public String toString() {
        return String.format("[%s] %-20s | %-25s | %s", registerNo, name, department, email);
    }
}
