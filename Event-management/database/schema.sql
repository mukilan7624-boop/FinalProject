-- ==============================================================================
-- College Event Management and Participation Analysis System
-- Academic Year: 2026-2027 (ODD) | Class: 25IT3A&B | Reg No: 31
-- Course: 25ITT31 - Database Management Systems
-- ==============================================================================

-- Drop tables if they already exist (in reverse foreign key dependency order)
DROP TABLE IF EXISTS feedback;
DROP TABLE IF EXISTS attendance;
DROP TABLE IF EXISTS registrations;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS students;

-- ------------------------------------------------------------------------------
-- 1. Table: students
-- Stores participant student information
-- ------------------------------------------------------------------------------
CREATE TABLE students (
    student_id INTEGER PRIMARY KEY AUTOINCREMENT,
    register_no VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contact_number VARCHAR(15)
);

-- ------------------------------------------------------------------------------
-- 2. Table: events
-- Stores college events (Symposium, Workshop, Seminar, Cultural)
-- ------------------------------------------------------------------------------
CREATE TABLE events (
    event_id INTEGER PRIMARY KEY AUTOINCREMENT,
    event_name VARCHAR(150) NOT NULL,
    category VARCHAR(50) NOT NULL CHECK(category IN ('Symposium', 'Workshop', 'Seminar', 'Cultural')),
    department VARCHAR(50) NOT NULL,
    event_date DATE NOT NULL,
    venue VARCHAR(100) NOT NULL,
    max_capacity INTEGER NOT NULL CHECK(max_capacity > 0),
    current_registrations INTEGER DEFAULT 0 CHECK(current_registrations >= 0)
);

-- ------------------------------------------------------------------------------
-- 3. Table: registrations
-- Stores event enrollments, enforces capacity limits and duplicate registration prevention
-- ------------------------------------------------------------------------------
CREATE TABLE registrations (
    registration_id INTEGER PRIMARY KEY AUTOINCREMENT,
    event_id INTEGER NOT NULL,
    student_id INTEGER NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'CONFIRMED' CHECK(status IN ('CONFIRMED', 'CANCELLED', 'WAITLISTED')),
    FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    CONSTRAINT uq_event_student UNIQUE (event_id, student_id)
);

-- ------------------------------------------------------------------------------
-- 4. Table: attendance
-- Records physical or virtual presence for registered participants
-- ------------------------------------------------------------------------------
CREATE TABLE attendance (
    attendance_id INTEGER PRIMARY KEY AUTOINCREMENT,
    registration_id INTEGER NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL CHECK(status IN ('PRESENT', 'ABSENT')),
    marked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (registration_id) REFERENCES registrations(registration_id) ON DELETE CASCADE
);

-- ------------------------------------------------------------------------------
-- 5. Table: feedback
-- Captures post-event student ratings (1-5 scale) and qualitative feedback
-- ------------------------------------------------------------------------------
CREATE TABLE feedback (
    feedback_id INTEGER PRIMARY KEY AUTOINCREMENT,
    registration_id INTEGER NOT NULL UNIQUE,
    rating INTEGER NOT NULL CHECK(rating BETWEEN 1 AND 5),
    comments TEXT,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (registration_id) REFERENCES registrations(registration_id) ON DELETE CASCADE
);

-- ------------------------------------------------------------------------------
-- Indexes for Optimized Querying & Fast Lookups
-- ------------------------------------------------------------------------------
CREATE INDEX idx_events_category ON events(category);
CREATE INDEX idx_events_dept ON events(department);
CREATE INDEX idx_registrations_event ON registrations(event_id);
CREATE INDEX idx_registrations_student ON registrations(student_id);
CREATE INDEX idx_attendance_status ON attendance(status);
CREATE INDEX idx_feedback_rating ON feedback(rating);

-- ------------------------------------------------------------------------------
-- Database Trigger: Automatically update event current_registrations
-- ------------------------------------------------------------------------------
CREATE TRIGGER trg_after_registration_insert
AFTER INSERT ON registrations
FOR EACH ROW
WHEN NEW.status = 'CONFIRMED'
BEGIN
    UPDATE events
    SET current_registrations = current_registrations + 1
    WHERE event_id = NEW.event_id;
END;

CREATE TRIGGER trg_after_registration_cancel
AFTER UPDATE OF status ON registrations
FOR EACH ROW
WHEN OLD.status = 'CONFIRMED' AND NEW.status = 'CANCELLED'
BEGIN
    UPDATE events
    SET current_registrations = current_registrations - 1
    WHERE event_id = NEW.event_id;
END;
