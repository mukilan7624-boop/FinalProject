package com.eventmanagement.service;

import com.eventmanagement.dao.*;
import com.eventmanagement.model.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Reporting Service implementing analytics using Java 8 Streams, Lambdas, and Collectors.
 * Course: 25ITT32 Java Programming (Unit 5: Generics, Collections, Lambdas & Streams) | Reg No: 31
 */
public class ReportService {
    private final EventDAO eventDAO = new EventDAO();
    private final RegistrationDAO registrationDAO = new RegistrationDAO();
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final FeedbackDAO feedbackDAO = new FeedbackDAO();
    private final StudentDAO studentDAO = new StudentDAO();

    public void printEventParticipationSummary() {
        List<Event> events = eventDAO.getAllEvents();
        List<Registration> registrations = registrationDAO.getAllRegistrations();
        List<Attendance> attendances = attendanceDAO.getAllAttendance();
        List<Feedback> feedbacks = feedbackDAO.getAllFeedbacks();

        System.out.println("\n=========================================================================================================");
        System.out.println("                         COLLEGE EVENT PARTICIPATION & ENGAGEMENT SUMMARY REPORT                         ");
        System.out.println("=========================================================================================================");
        System.out.printf("%-4s | %-38s | %-10s | %-8s | %-9s | %-8s | %-9s | %-6s\n",
                "ID", "Event Name", "Category", "Capacity", "Confirmed", "Capacity%", "Attended", "Avg ★");
        System.out.println("---------------------------------------------------------------------------------------------------------");

        events.stream().forEach(e -> {
            long confirmed = registrations.stream()
                    .filter(r -> r.getEventId() == e.getEventId() && "CONFIRMED".equalsIgnoreCase(r.getStatus()))
                    .count();

            List<Integer> regIds = registrations.stream()
                    .filter(r -> r.getEventId() == e.getEventId() && "CONFIRMED".equalsIgnoreCase(r.getStatus()))
                    .map(Registration::getRegistrationId)
                    .collect(Collectors.toList());

            long attended = attendances.stream()
                    .filter(a -> regIds.contains(a.getRegistrationId()) && a.isPresent())
                    .count();

            OptionalDouble avgRating = feedbacks.stream()
                    .filter(f -> regIds.contains(f.getRegistrationId()))
                    .mapToInt(Feedback::getRating)
                    .average();

            double capPct = (e.getMaxCapacity() > 0) ? ((double) confirmed / e.getMaxCapacity()) * 100.0 : 0.0;
            String ratingStr = avgRating.isPresent() ? String.format("%.2f", avgRating.getAsDouble()) : "N/A";

            System.out.printf("%-4d | %-38s | %-10s | %-8d | %-9d | %-7.1f%% | %-8d | %-6s\n",
                    e.getEventId(),
                    e.getEventName().length() > 38 ? e.getEventName().substring(0, 35) + "..." : e.getEventName(),
                    e.getCategory(),
                    e.getMaxCapacity(),
                    confirmed,
                    capPct,
                    attended,
                    ratingStr);
        });
        System.out.println("=========================================================================================================\n");
    }

    public void printDepartmentParticipationReport() {
        List<Student> students = studentDAO.getAllStudents();
        List<Registration> registrations = registrationDAO.getAllRegistrations();

        System.out.println("\n======================================================================");
        System.out.println("               DEPARTMENT-WISE STUDENT PARTICIPATION                 ");
        System.out.println("======================================================================");

        Map<String, Long> deptRegistrations = registrations.stream()
                .filter(r -> "CONFIRMED".equalsIgnoreCase(r.getStatus()))
                .map(r -> students.stream()
                        .filter(s -> s.getStudentId() == r.getStudentId())
                        .findFirst()
                        .map(Student::getDepartment)
                        .orElse("Unknown"))
                .collect(Collectors.groupingBy(dept -> dept, Collectors.counting()));

        System.out.printf("%-35s | %s\n", "Department Name", "Total Registrations");
        System.out.println("----------------------------------------------------------------------");
        deptRegistrations.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .forEach(entry -> System.out.printf("%-35s | %d registrations\n", entry.getKey(), entry.getValue()));
        System.out.println("======================================================================\n");
    }

    public void printStudentProfile(String registerNo) {
        Optional<Student> studentOpt = studentDAO.getStudentByRegisterNo(registerNo);
        if (studentOpt.isEmpty()) {
            System.out.println("[-] No student found with Register Number: " + registerNo);
            return;
        }

        Student s = studentOpt.get();
        List<Registration> regs = registrationDAO.getRegistrationsByStudent(s.getStudentId());

        System.out.println("\n================================================================================");
        System.out.printf("STUDENT PARTICIPATION DOSSIER: %s (%s)\n", s.getName(), s.getRegisterNo());
        System.out.printf("Department: %s | Email: %s\n", s.getDepartment(), s.getEmail());
        System.out.println("================================================================================");

        if (regs.isEmpty()) {
            System.out.println("No event registrations found for this student.");
        } else {
            System.out.printf("%-8s | %-35s | %-12s | %-10s | %s\n",
                    "Reg ID", "Event Name", "Status", "Attendance", "Feedback");
            System.out.println("--------------------------------------------------------------------------------");

            for (Registration r : regs) {
                Event ev = eventDAO.getEventById(r.getEventId()).orElse(null);
                String evName = (ev != null) ? ev.getEventName() : "Unknown";
                if (evName.length() > 35) evName = evName.substring(0, 32) + "...";

                Optional<Attendance> att = attendanceDAO.getAttendanceByRegistrationId(r.getRegistrationId());
                String attStr = att.map(Attendance::getStatus).orElse("PENDING");

                Optional<Feedback> fb = feedbackDAO.getFeedbackByRegistrationId(r.getRegistrationId());
                String fbStr = fb.map(f -> f.getRating() + "★ (" + f.getComments() + ")").orElse("None");

                System.out.printf("%-8d | %-35s | %-12s | %-10s | %s\n",
                        r.getRegistrationId(), evName, r.getStatus(), attStr, fbStr);
            }
        }
        System.out.println("================================================================================\n");
    }
}
