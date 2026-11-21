package com.eventmanagement.dao;

import com.eventmanagement.model.Student;
import java.util.*;

/**
 * Data Access Object for Student entity.
 * Course: 25ITT32 Java Programming & 25ITT31 DBMS | Reg No: 31
 */
public class StudentDAO {
    private final DataStore store = DataStore.getInstance();

    public List<Student> getAllStudents() {
        return new ArrayList<>(store.students.values());
    }

    public Optional<Student> getStudentById(int studentId) {
        return Optional.ofNullable(store.students.get(studentId));
    }

    public Optional<Student> getStudentByRegisterNo(String registerNo) {
        return store.students.values().stream()
                .filter(s -> s.getRegisterNo().equalsIgnoreCase(registerNo.trim()))
                .findFirst();
    }
}
