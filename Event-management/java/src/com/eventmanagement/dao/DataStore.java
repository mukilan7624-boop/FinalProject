package com.eventmanagement.dao;

import com.eventmanagement.model.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Shared In-Memory Data Store providing zero-dependency execution
 * and complete synchronization with SQL seed data.
 * Course: 25ITT32 Java Programming | Reg No: 31
 */
public class DataStore {
    private static final DataStore INSTANCE = new DataStore();

    public final Map<Integer, Event> events = new ConcurrentHashMap<>();
    public final Map<Integer, Student> students = new ConcurrentHashMap<>();
    public final Map<Integer, Registration> registrations = new ConcurrentHashMap<>();
    public final Map<Integer, Attendance> attendances = new ConcurrentHashMap<>();
    public final Map<Integer, Feedback> feedbacks = new ConcurrentHashMap<>();

    public final AtomicInteger eventIdGen = new AtomicInteger(10);
    public final AtomicInteger regIdGen = new AtomicInteger(40);
    public final AtomicInteger attIdGen = new AtomicInteger(40);
    public final AtomicInteger fbIdGen = new AtomicInteger(40);

    private DataStore() {
        seedInitialData();
    }

    public static DataStore getInstance() {
        return INSTANCE;
    }

    private void seedInitialData() {
        // Students (including Reg No: 31)
        students.put(1, new Student(1, "25IT031", "Madhavan S (Reg 31)", "Information Technology", "madhavan.25it031@velalarengg.ac.in", "9842100031"));
        students.put(2, new Student(2, "25IT001", "Aarav Kumar", "Information Technology", "aarav.25it001@velalarengg.ac.in", "9842100001"));
        students.put(3, new Student(3, "25IT061", "Dharani R", "Information Technology", "dharani.25it061@velalarengg.ac.in", "9842100061"));
        students.put(4, new Student(4, "25IT091", "Kavya S", "Information Technology", "kavya.25it091@velalarengg.ac.in", "9842100091"));
        students.put(5, new Student(5, "25ITL01", "Naveen Prashanth L", "Information Technology", "naveen.25itl01@velalarengg.ac.in", "9842100099"));
        students.put(6, new Student(6, "25CS012", "Bala Subramanian", "Computer Science and Engg", "bala.25cs012@velalarengg.ac.in", "9842100012"));
        students.put(7, new Student(7, "25CS045", "Gokul Nathan", "Computer Science and Engg", "gokul.25cs045@velalarengg.ac.in", "9842100045"));
        students.put(8, new Student(8, "25EC018", "Harish V", "Electronics and Comm Engg", "harish.25ec018@velalarengg.ac.in", "9842100018"));
        students.put(9, new Student(9, "25EC052", "Janani M", "Electronics and Comm Engg", "janani.25ec052@velalarengg.ac.in", "9842100052"));
        students.put(10, new Student(10, "25AI022", "Manoj Kumar", "Artificial Intelligence & DS", "manoj.25ai022@velalarengg.ac.in", "9842100022"));
        students.put(11, new Student(11, "25AI049", "Pavithra K", "Artificial Intelligence & DS", "pavithra.25ai049@velalarengg.ac.in", "9842100049"));
        students.put(12, new Student(12, "25ME015", "Karthik Raja", "Mechanical Engineering", "karthik.25me015@velalarengg.ac.in", "9842100015"));

        // Events
        events.put(1, new Event(1, "CYBERFEST 2026: National Tech Symposium", "Symposium", "Information Technology", "2026-12-05", "Main Auditorium", 120, 9));
        events.put(2, new Event(2, "Hands-on Workshop on Generative AI & LLMs", "Workshop", "Information Technology", "2026-12-12", "IT Computing Lab 3", 45, 6));
        events.put(3, new Event(3, "National Seminar on Cloud & Distributed Systems", "Seminar", "Computer Science and Engg", "2026-12-18", "Seminar Hall 1", 60, 5));
        events.put(4, new Event(4, "VIBES 2026: Annual Cultural Fest", "Cultural", "Student Affairs", "2026-12-24", "Open Air Amphitheatre", 250, 9));
        events.put(5, new Event(5, "Embedded IoT & Robotics Workshop", "Workshop", "Electronics and Comm Engg", "2027-01-08", "IoT Research Lab", 35, 3));
        events.put(6, new Event(6, "Deep Learning Bootcamp for Computer Vision", "Workshop", "Artificial Intelligence & DS", "2027-01-15", "AI Innovation Lab", 40, 5));
        events.put(7, new Event(7, "TechConclave 2027: Paper Presentation", "Symposium", "Information Technology", "2027-01-22", "Conference Hall 2", 50, 0));
        events.put(8, new Event(8, "Rhythm & Beats: Music and Fine Arts Evening", "Cultural", "Cultural Club", "2027-01-29", "College Ground Stage", 300, 0));

        // Registrations
        addReg(1, 1, 1, "2026-11-20 09:30:00"); // Reg 31
        addReg(2, 1, 2, "2026-11-20 09:35:00");
        addReg(3, 1, 3, "2026-11-20 09:40:00");
        addReg(4, 1, 4, "2026-11-20 10:00:00");
        addReg(5, 1, 5, "2026-11-20 10:15:00");
        addReg(6, 1, 6, "2026-11-20 10:20:00");
        addReg(7, 1, 7, "2026-11-20 10:30:00");
        addReg(8, 1, 8, "2026-11-20 11:00:00");
        addReg(9, 1, 10, "2026-11-20 11:15:00");

        addReg(10, 2, 1, "2026-11-21 08:30:00"); // Reg 31
        addReg(11, 2, 2, "2026-11-21 08:45:00");
        addReg(12, 2, 6, "2026-11-21 09:00:00");
        addReg(13, 2, 7, "2026-11-21 09:10:00");
        addReg(14, 2, 10, "2026-11-21 09:20:00");
        addReg(15, 2, 11, "2026-11-21 09:30:00");

        addReg(16, 3, 1, "2026-11-22 14:00:00"); // Reg 31
        addReg(17, 3, 3, "2026-11-22 14:10:00");
        addReg(18, 3, 4, "2026-11-22 14:25:00");
        addReg(19, 3, 6, "2026-11-22 14:30:00");
        addReg(20, 3, 12, "2026-11-22 14:40:00");

        addReg(21, 4, 1, "2026-11-23 10:00:00"); // Reg 31
        addReg(22, 4, 2, "2026-11-23 10:05:00");
        addReg(23, 4, 3, "2026-11-23 10:10:00");
        addReg(24, 4, 4, "2026-11-23 10:15:00");
        addReg(25, 4, 5, "2026-11-23 10:20:00");
        addReg(26, 4, 8, "2026-11-23 10:30:00");
        addReg(27, 4, 9, "2026-11-23 10:45:00");
        addReg(28, 4, 11, "2026-11-23 11:00:00");
        addReg(29, 4, 12, "2026-11-23 11:30:00");

        // Attendance
        attendances.put(1, new Attendance(1, 1, "PRESENT", "2026-12-05 09:00:00"));
        attendances.put(2, new Attendance(2, 2, "PRESENT", "2026-12-05 09:02:00"));
        attendances.put(3, new Attendance(3, 3, "PRESENT", "2026-12-05 09:05:00"));
        attendances.put(4, new Attendance(4, 4, "ABSENT", "2026-12-05 09:30:00"));
        attendances.put(5, new Attendance(5, 5, "PRESENT", "2026-12-05 09:10:00"));
        attendances.put(6, new Attendance(6, 6, "PRESENT", "2026-12-05 09:12:00"));
        attendances.put(7, new Attendance(7, 7, "PRESENT", "2026-12-05 09:15:00"));
        attendances.put(8, new Attendance(8, 8, "PRESENT", "2026-12-05 09:18:00"));
        attendances.put(9, new Attendance(9, 9, "ABSENT", "2026-12-05 09:30:00"));

        attendances.put(10, new Attendance(10, 10, "PRESENT", "2026-12-12 08:50:00"));
        attendances.put(11, new Attendance(11, 11, "PRESENT", "2026-12-12 08:55:00"));
        attendances.put(12, new Attendance(12, 12, "PRESENT", "2026-12-12 09:00:00"));
        attendances.put(13, new Attendance(13, 13, "PRESENT", "2026-12-12 09:02:00"));
        attendances.put(14, new Attendance(14, 14, "PRESENT", "2026-12-12 09:05:00"));
        attendances.put(15, new Attendance(15, 15, "PRESENT", "2026-12-12 09:10:00"));

        attendances.put(16, new Attendance(16, 16, "PRESENT", "2026-12-18 13:55:00"));
        attendances.put(17, new Attendance(17, 17, "PRESENT", "2026-12-18 14:00:00"));
        attendances.put(18, new Attendance(18, 18, "ABSENT", "2026-12-18 14:30:00"));
        attendances.put(19, new Attendance(19, 19, "PRESENT", "2026-12-18 14:05:00"));
        attendances.put(20, new Attendance(20, 20, "PRESENT", "2026-12-18 14:10:00"));

        attendances.put(21, new Attendance(21, 21, "PRESENT", "2026-12-24 09:45:00"));
        attendances.put(22, new Attendance(22, 22, "PRESENT", "2026-12-24 09:50:00"));
        attendances.put(23, new Attendance(23, 23, "PRESENT", "2026-12-24 09:55:00"));
        attendances.put(24, new Attendance(24, 24, "PRESENT", "2026-12-24 10:00:00"));
        attendances.put(25, new Attendance(25, 25, "PRESENT", "2026-12-24 10:05:00"));
        attendances.put(26, new Attendance(26, 26, "PRESENT", "2026-12-24 10:10:00"));
        attendances.put(27, new Attendance(27, 27, "ABSENT", "2026-12-24 10:30:00"));
        attendances.put(28, new Attendance(28, 28, "PRESENT", "2026-12-24 10:15:00"));
        attendances.put(29, new Attendance(29, 29, "PRESENT", "2026-12-24 10:20:00"));

        // Feedback
        feedbacks.put(1, new Feedback(1, 1, 5, "Outstanding national symposium! Coding hackathon was top notch.", "2026-12-05 16:30:00"));
        feedbacks.put(2, new Feedback(2, 2, 4, "Great technical talks and paper presentations.", "2026-12-05 16:35:00"));
        feedbacks.put(3, new Feedback(3, 3, 5, "Loved the technical quiz and project display arena.", "2026-12-05 16:40:00"));
        feedbacks.put(4, new Feedback(4, 5, 4, "Informative industry expert sessions.", "2026-12-05 16:45:00"));
        feedbacks.put(5, new Feedback(5, 6, 4, "Well-coordinated event with great networking.", "2026-12-05 16:50:00"));
        feedbacks.put(6, new Feedback(6, 7, 3, "Content was good, sound system had slight echoes.", "2026-12-05 16:55:00"));
        feedbacks.put(7, new Feedback(7, 8, 5, "One of the best symposiums hosted by IT department!", "2026-12-05 17:00:00"));

        feedbacks.put(8, new Feedback(8, 10, 5, "Superb hands-on experience on LangChain and LLMs.", "2026-12-12 16:00:00"));
        feedbacks.put(9, new Feedback(9, 11, 5, "Clear practical demonstrations and notebooks.", "2026-12-12 16:05:00"));
        feedbacks.put(10, new Feedback(10, 12, 4, "Good pace and practical insights.", "2026-12-12 16:10:00"));
        feedbacks.put(11, new Feedback(11, 13, 5, "Loved implementing live multi-agent workflows!", "2026-12-12 16:15:00"));
        feedbacks.put(12, new Feedback(12, 14, 5, "Extremely useful for final year projects.", "2026-12-12 16:20:00"));
        feedbacks.put(13, new Feedback(13, 15, 4, "Would love an advanced level follow-up.", "2026-12-12 16:25:00"));

        feedbacks.put(14, new Feedback(14, 16, 4, "Great overview of AWS Cloud and Kubernetes.", "2026-12-18 16:45:00"));
        feedbacks.put(15, new Feedback(15, 17, 3, "Informative, wanted more live lab time.", "2026-12-18 16:50:00"));
        feedbacks.put(16, new Feedback(16, 19, 4, "Well-structured microservices architecture session.", "2026-12-18 16:55:00"));
        feedbacks.put(17, new Feedback(17, 20, 4, "Valuable career advice for Cloud DevOps roles.", "2026-12-18 17:00:00"));

        feedbacks.put(18, new Feedback(18, 21, 5, "Energetic cultural fest! Fantastic dance performances.", "2026-12-24 18:00:00"));
        feedbacks.put(19, new Feedback(19, 22, 5, "Incredible stage setup and vibrant atmosphere.", "2026-12-24 18:05:00"));
        feedbacks.put(20, new Feedback(20, 23, 4, "Fun crowd engagement and lively music.", "2026-12-24 18:10:00"));
        feedbacks.put(21, new Feedback(21, 24, 4, "Great display of student talents.", "2026-12-24 18:15:00"));
        feedbacks.put(22, new Feedback(22, 25, 5, "Unforgettable night! Loved every moment.", "2026-12-24 18:20:00"));
        feedbacks.put(23, new Feedback(23, 26, 4, "Great food and stalls.", "2026-12-24 18:25:00"));
        feedbacks.put(24, new Feedback(24, 28, 5, "Hearty congrats to the student coordinators.", "2026-12-24 18:30:00"));
        feedbacks.put(25, new Feedback(25, 29, 4, "Splendid performance all around.", "2026-12-24 18:35:00"));
    }

    private void addReg(int regId, int eventId, int studentId, String date) {
        Registration reg = new Registration(regId, eventId, studentId, date, "CONFIRMED");
        Event e = events.get(eventId);
        Student s = students.get(studentId);
        if (e != null) reg.setEventName(e.getEventName());
        if (s != null) {
            reg.setStudentName(s.getName());
            reg.setRegisterNo(s.getRegisterNo());
        }
        registrations.put(regId, reg);
    }
}
