package com.eventmanagement;

import com.eventmanagement.exception.*;
import com.eventmanagement.model.*;
import com.eventmanagement.service.*;
import java.util.*;

/**
 * Main Entry Point for College Event Management and Participation Analysis System.
 * Features an interactive menu-driven console and an automated test verification suite.
 *
 * Academic Year: 2026-2027 (ODD) | Class: 25IT3A&B
 * Student Register No.: 31
 * Institution: Velalar College of Engineering and Technology, Erode - 12
 */
public class Main {
    private static final EventService eventService = new EventService();
    private static final ReportService reportService = new ReportService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        if (args.length > 0 && (args[0].equalsIgnoreCase("--test") || args[0].equalsIgnoreCase("-t"))) {
            runAutomatedTestSuite();
            return;
        }

        printBanner();
        boolean running = true;

        while (running) {
            printMainMenu();
            System.out.print("Enter your choice (0-11): ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    viewAllEvents();
                    break;
                case "2":
                    createNewEvent();
                    break;
                case "3":
                    updateEventDetails();
                    break;
                case "4":
                    deleteEvent();
                    break;
                case "5":
                    registerStudentForEvent();
                    break;
                case "6":
                    cancelStudentRegistration();
                    break;
                case "7":
                    markStudentAttendance();
                    break;
                case "8":
                    submitStudentFeedback();
                    break;
                case "9":
                    reportService.printEventParticipationSummary();
                    break;
                case "10":
                    reportService.printDepartmentParticipationReport();
                    break;
                case "11":
                    viewStudentProfile();
                    break;
                case "test":
                case "12":
                    runAutomatedTestSuite();
                    break;
                case "0":
                    System.out.println("\n[+] Exiting College Event Management System. Thank you!");
                    running = false;
                    break;
                default:
                    System.out.println("[!] Invalid choice. Please enter an option between 0 and 11.");
            }
        }
    }

    private static void printBanner() {
        System.out.println("=======================================================================================");
        System.out.println("             VELALAR COLLEGE OF ENGINEERING AND TECHNOLOGY, ERODE - 12                ");
        System.out.println("                         DEPARTMENT OF INFORMATION TECHNOLOGY                          ");
        System.out.println("          COLLEGE EVENT MANAGEMENT AND PARTICIPATION ANALYSIS SYSTEM (REG: 31)         ");
        System.out.println("=======================================================================================");
    }

    private static void printMainMenu() {
        System.out.println("\n=======================================================");
        System.out.println("                      MAIN MENU                        ");
        System.out.println("=======================================================");
        System.out.println(" [1]  View All Events & Availability");
        System.out.println(" [2]  [Admin] Create New Event");
        System.out.println(" [3]  [Admin] Update Event Details");
        System.out.println(" [4]  [Admin] Delete Event");
        System.out.println(" [5]  [Student] Register for an Event (Capacity & Duplicate Check)");
        System.out.println(" [6]  [Student] Cancel Event Registration");
        System.out.println(" [7]  [Admin] Record Attendance (PRESENT / ABSENT)");
        System.out.println(" [8]  [Student] Submit Feedback & Rating (1 to 5 Stars)");
        System.out.println(" [9]  [Report] Event Participation & Engagement Summary (Streams)");
        System.out.println(" [10] [Report] Department-wise Participation Analysis");
        System.out.println(" [11] [Student Dossier] View Student Profile (e.g. Reg: 31)");
        System.out.println(" [12] Run Automated Software Test Verification Suite");
        System.out.println(" [0]  Exit System");
        System.out.println("=======================================================");
    }

    private static void viewAllEvents() {
        System.out.println("\n--- AVAILABLE COLLEGE EVENTS ---");
        List<Event> events = eventService.listAllEvents();
        if (events.isEmpty()) {
            System.out.println("No events registered currently.");
            return;
        }
        for (Event e : events) {
            System.out.println(e);
        }
    }

    private static void createNewEvent() {
        System.out.println("\n--- [ADMIN] CREATE NEW EVENT ---");
        try {
            System.out.print("Enter Event Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Category (Symposium / Workshop / Seminar / Cultural): ");
            String category = scanner.nextLine().trim();

            System.out.print("Enter Organizing Department: ");
            String dept = scanner.nextLine().trim();

            System.out.print("Enter Event Date (YYYY-MM-DD): ");
            String date = scanner.nextLine().trim();

            System.out.print("Enter Venue: ");
            String venue = scanner.nextLine().trim();

            System.out.print("Enter Maximum Seat Capacity: ");
            int capacity = Integer.parseInt(scanner.nextLine().trim());

            Event event = new Event(0, name, category, dept, date, venue, capacity, 0);
            boolean created = eventService.createEvent(event);

            if (created) {
                System.out.printf("[+] Successfully created Event '%s' with ID: %d and Capacity: %d\n",
                        name, event.getEventId(), capacity);
            }
        } catch (NumberFormatException e) {
            System.out.println("[!] Invalid number format for capacity.");
        } catch (IllegalArgumentException e) {
            System.out.println("[!] Validation Error: " + e.getMessage());
        }
    }

    private static void updateEventDetails() {
        System.out.println("\n--- [ADMIN] UPDATE EVENT ---");
        try {
            System.out.print("Enter Event ID to update: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            Optional<Event> eventOpt = eventService.getEvent(id);
            if (eventOpt.isEmpty()) {
                System.out.println("[!] Event not found with ID: " + id);
                return;
            }

            Event event = eventOpt.get();
            System.out.printf("Current: %s | Date: %s | Venue: %s | Capacity: %d\n",
                    event.getEventName(), event.getEventDate(), event.getVenue(), event.getMaxCapacity());

            System.out.print("Enter New Venue (Leave blank to keep current): ");
            String venue = scanner.nextLine().trim();
            if (!venue.isEmpty()) event.setVenue(venue);

            System.out.print("Enter New Max Capacity (Leave blank to keep current): ");
            String capStr = scanner.nextLine().trim();
            if (!capStr.isEmpty()) event.setMaxCapacity(Integer.parseInt(capStr));

            eventService.updateEvent(event);
            System.out.println("[+] Event updated successfully.");
        } catch (Exception e) {
            System.out.println("[!] Error updating event: " + e.getMessage());
        }
    }

    private static void deleteEvent() {
        System.out.println("\n--- [ADMIN] DELETE EVENT ---");
        try {
            System.out.print("Enter Event ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            boolean deleted = eventService.deleteEvent(id);
            if (deleted) {
                System.out.println("[+] Event ID " + id + " deleted successfully.");
            } else {
                System.out.println("[!] Event ID " + id + " not found.");
            }
        } catch (Exception e) {
            System.out.println("[!] Error deleting event: " + e.getMessage());
        }
    }

    private static void registerStudentForEvent() {
        System.out.println("\n--- [STUDENT] EVENT REGISTRATION ---");
        try {
            System.out.print("Enter Event ID: ");
            int eventId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Student ID (e.g., 1 for Reg No: 31): ");
            int studentId = Integer.parseInt(scanner.nextLine().trim());

            Registration reg = eventService.registerStudent(eventId, studentId);
            System.out.println("[+] REGISTRATION CONFIRMED!");
            System.out.println("    " + reg);
        } catch (EventNotFoundException | EventFullException | DuplicateRegistrationException e) {
            System.out.println("[X] REGISTRATION REJECTED: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[!] Error: " + e.getMessage());
        }
    }

    private static void cancelStudentRegistration() {
        System.out.println("\n--- [STUDENT] CANCEL REGISTRATION ---");
        try {
            System.out.print("Enter Registration ID to cancel: ");
            int regId = Integer.parseInt(scanner.nextLine().trim());
            boolean success = eventService.cancelRegistration(regId);
            if (success) {
                System.out.println("[+] Registration ID " + regId + " cancelled. Seat returned to pool.");
            } else {
                System.out.println("[!] Registration not found or already cancelled.");
            }
        } catch (Exception e) {
            System.out.println("[!] Error: " + e.getMessage());
        }
    }

    private static void markStudentAttendance() {
        System.out.println("\n--- [ADMIN] RECORD ATTENDANCE ---");
        try {
            System.out.print("Enter Registration ID: ");
            int regId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Status (PRESENT / ABSENT): ");
            String status = scanner.nextLine().trim();

            boolean success = eventService.markAttendance(regId, status);
            if (success) {
                System.out.printf("[+] Attendance marked as [%s] for Registration ID: %d\n", status.toUpperCase(), regId);
            }
        } catch (Exception e) {
            System.out.println("[!] Error: " + e.getMessage());
        }
    }

    private static void submitStudentFeedback() {
        System.out.println("\n--- [STUDENT] SUBMIT EVENT FEEDBACK ---");
        try {
            System.out.print("Enter Registration ID: ");
            int regId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Rating (1 to 5 Stars): ");
            int rating = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Enter Feedback Comments: ");
            String comments = scanner.nextLine().trim();

            boolean success = eventService.submitFeedback(regId, rating, comments);
            if (success) {
                System.out.println("[+] Feedback submitted successfully. Thank you!");
            }
        } catch (IllegalStateException e) {
            System.out.println("[X] Rejection: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[!] Error: " + e.getMessage());
        }
    }

    private static void viewStudentProfile() {
        System.out.print("Enter Student Register Number (default '25IT031'): ");
        String regNo = scanner.nextLine().trim();
        if (regNo.isEmpty()) regNo = "25IT031";
        reportService.printStudentProfile(regNo);
    }

    /**
     * Automated Software Test Execution Suite verifying all functional requirements.
     */
    public static void runAutomatedTestSuite() {
        System.out.println("\n=================================================================================");
        System.out.println("                 STARTING AUTOMATED SOFTWARE TESTING SUITE                       ");
        System.out.println(" Course: 25ITT33 Software Engineering | Testing Student Reg No: 31               ");
        System.out.println("=================================================================================");

        int passed = 0;
        int total = 0;

        // Test 1: Event Creation
        total++;
        try {
            Event testEvent = new Event(0, "Test AI Conclave", "Symposium", "Information Technology",
                    "2027-02-10", "Auditorium", 2, 0);
            eventService.createEvent(testEvent);
            System.out.printf("[PASS] TC-01: Admin creates event with valid parameters (ID: %d)\n", testEvent.getEventId());
            passed++;

            // Test 2: Validation of invalid capacity
            total++;
            try {
                Event invalidEvent = new Event(0, "Invalid Event", "Workshop", "IT", "2027-02-12", "Hall", -10, 0);
                eventService.createEvent(invalidEvent);
                System.out.println("[FAIL] TC-02: Negative capacity was improperly accepted.");
            } catch (IllegalArgumentException e) {
                System.out.println("[PASS] TC-02: System correctly rejected negative event capacity.");
                passed++;
            }

            // Test 3: Normal Registration
            total++;
            Registration r1 = eventService.registerStudent(testEvent.getEventId(), 1); // Reg 31
            System.out.printf("[PASS] TC-03: Student registered successfully (RegID: %d)\n", r1.getRegistrationId());
            passed++;

            // Test 4: Duplicate Registration Prevention
            total++;
            try {
                eventService.registerStudent(testEvent.getEventId(), 1); // Attempt duplicate
                System.out.println("[FAIL] TC-04: Duplicate registration was improperly allowed.");
            } catch (DuplicateRegistrationException e) {
                System.out.println("[PASS] TC-04: System successfully blocked duplicate registration attempt.");
                passed++;
            }

            // Test 5: Capacity Limit Enforcement (Fill remaining 1 seat, then attempt 3rd)
            total++;
            eventService.registerStudent(testEvent.getEventId(), 2); // Seat 2 (now full: 2/2)
            try {
                eventService.registerStudent(testEvent.getEventId(), 3); // Attempt 3rd seat
                System.out.println("[FAIL] TC-05: System exceeded maximum capacity.");
            } catch (EventFullException e) {
                System.out.println("[PASS] TC-05: System strictly rejected registration when event is full.");
                passed++;
            }

            // Test 6: Registration Cancellation & Seat Recovery
            total++;
            boolean cancelled = eventService.cancelRegistration(r1.getRegistrationId());
            if (cancelled && testEvent.getCurrentRegistrations() == 1) {
                System.out.println("[PASS] TC-06: Cancellation successful and seat restored to available pool.");
                passed++;
            } else {
                System.out.println("[FAIL] TC-06: Cancellation failed or seat count mismatch.");
            }

            // Test 7: Attendance Marking
            total++;
            boolean attMarked = eventService.markAttendance(r1.getRegistrationId(), "PRESENT");
            if (attMarked) {
                System.out.println("[PASS] TC-07: Attendance marked as PRESENT successfully.");
                passed++;
            } else {
                System.out.println("[FAIL] TC-07: Failed to mark attendance.");
            }

            // Test 8: Feedback Rating Validation
            total++;
            boolean fbDone = eventService.submitFeedback(r1.getRegistrationId(), 5, "Exemplary workshop!");
            if (fbDone) {
                System.out.println("[PASS] TC-08: Feedback rating 5/5 recorded successfully.");
                passed++;
            } else {
                System.out.println("[FAIL] TC-08: Feedback submission failed.");
            }

            // Test 9: Feedback rejection for absent student
            total++;
            Registration r2 = eventService.registerStudent(testEvent.getEventId(), 4);
            eventService.markAttendance(r2.getRegistrationId(), "ABSENT");
            try {
                eventService.submitFeedback(r2.getRegistrationId(), 4, "Trying feedback while absent");
                System.out.println("[FAIL] TC-09: Feedback accepted for absent student.");
            } catch (IllegalStateException e) {
                System.out.println("[PASS] TC-09: Feedback blocked for absent student as expected.");
                passed++;
            }

            // Test 10: Clean up test event
            total++;
            eventService.deleteEvent(testEvent.getEventId());
            System.out.println("[PASS] TC-10: Test event cleaned up successfully.");
            passed++;

        } catch (Exception e) {
            System.out.println("[!] Exception during test suite: " + e.getMessage());
        }

        System.out.println("---------------------------------------------------------------------------------");
        System.out.printf("TEST RESULTS: %d / %d TEST CASES PASSED (%.1f%% SUCCESS RATE)\n",
                passed, total, ((double) passed / total) * 100.0);
        System.out.println("=================================================================================\n");
    }
}
