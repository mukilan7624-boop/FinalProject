-- ==============================================================================
-- Analytical & Operational SQL Queries
-- Course: 25ITT31 - Database Management Systems | Reg No: 31
-- ==============================================================================

-- ------------------------------------------------------------------------------
-- Query 1: Comprehensive Event Participation Summary
-- Computes total registrations, attended count, attendance rate %, and average rating
-- ------------------------------------------------------------------------------
SELECT 
    e.event_id,
    e.event_name,
    e.category,
    e.department,
    e.max_capacity,
    COUNT(DISTINCT r.registration_id) AS total_registrations,
    ROUND((COUNT(DISTINCT r.registration_id) * 100.0 / e.max_capacity), 2) AS capacity_utilization_pct,
    COUNT(DISTINCT CASE WHEN a.status = 'PRESENT' THEN a.registration_id END) AS attended_count,
    ROUND((COUNT(DISTINCT CASE WHEN a.status = 'PRESENT' THEN a.registration_id END) * 100.0 / 
           NULLIF(COUNT(DISTINCT r.registration_id), 0)), 2) AS attendance_turnout_pct,
    ROUND(AVG(f.rating), 2) AS average_feedback_rating
FROM events e
LEFT JOIN registrations r ON e.event_id = r.event_id AND r.status = 'CONFIRMED'
LEFT JOIN attendance a ON r.registration_id = a.registration_id
LEFT JOIN feedback f ON r.registration_id = f.registration_id
GROUP BY e.event_id, e.event_name, e.category, e.department, e.max_capacity
ORDER BY total_registrations DESC;

-- ------------------------------------------------------------------------------
-- Query 2: Department-wise Student Participation Analysis
-- Identifies which departments actively engage in co-curricular events
-- ------------------------------------------------------------------------------
SELECT 
    s.department AS student_department,
    COUNT(DISTINCT s.student_id) AS total_students_participated,
    COUNT(r.registration_id) AS total_event_registrations,
    SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) AS total_attended,
    ROUND(AVG(f.rating), 2) AS dept_avg_feedback
FROM students s
JOIN registrations r ON s.student_id = r.student_id
LEFT JOIN attendance a ON r.registration_id = a.registration_id
LEFT JOIN feedback f ON r.registration_id = f.registration_id
GROUP BY s.department
ORDER BY total_event_registrations DESC;

-- ------------------------------------------------------------------------------
-- Query 3: Individual Student Participation Profile (Filtered for Reg No: 31)
-- ------------------------------------------------------------------------------
SELECT 
    s.register_no,
    s.name AS student_name,
    e.event_name,
    e.category,
    r.registration_date,
    COALESCE(a.status, 'NOT MARKED') AS attendance_status,
    COALESCE(CAST(f.rating AS VARCHAR), 'N/A') AS feedback_rating,
    COALESCE(f.comments, 'No feedback submitted') AS feedback_comments
FROM students s
JOIN registrations r ON s.student_id = r.student_id
JOIN events e ON r.event_id = e.event_id
LEFT JOIN attendance a ON r.registration_id = a.registration_id
LEFT JOIN feedback f ON r.registration_id = f.registration_id
WHERE s.register_no = '25IT031'
ORDER BY r.registration_date ASC;

-- ------------------------------------------------------------------------------
-- Query 4: Category-wise Event Popularity and Satisfaction (Symposium vs Workshop vs Cultural)
-- ------------------------------------------------------------------------------
SELECT 
    e.category,
    COUNT(DISTINCT e.event_id) AS number_of_events,
    COUNT(r.registration_id) AS total_registrations,
    ROUND(AVG(f.rating), 2) AS category_avg_rating,
    ROUND(SUM(CASE WHEN a.status = 'PRESENT' THEN 1 ELSE 0 END) * 100.0 / 
          NULLIF(COUNT(r.registration_id), 0), 2) AS category_turnout_pct
FROM events e
LEFT JOIN registrations r ON e.event_id = r.event_id
LEFT JOIN attendance a ON r.registration_id = a.registration_id
LEFT JOIN feedback f ON r.registration_id = f.registration_id
GROUP BY e.category
ORDER BY total_registrations DESC;

-- ------------------------------------------------------------------------------
-- Query 5: Subquery - Events with Higher than Average Feedback Rating
-- ------------------------------------------------------------------------------
SELECT 
    e.event_id,
    e.event_name,
    e.category,
    ROUND(AVG(f.rating), 2) AS event_rating
FROM events e
JOIN registrations r ON e.event_id = r.event_id
JOIN feedback f ON r.registration_id = f.registration_id
GROUP BY e.event_id, e.event_name, e.category
HAVING AVG(f.rating) > (SELECT AVG(rating) FROM feedback)
ORDER BY event_rating DESC;

-- ------------------------------------------------------------------------------
-- Query 6: Create Reporting View for Quick Dashboard Access
-- ------------------------------------------------------------------------------
DROP VIEW IF EXISTS view_event_dashboard;
CREATE VIEW view_event_dashboard AS
SELECT 
    e.event_id,
    e.event_name,
    e.category,
    e.event_date,
    e.venue,
    e.max_capacity,
    e.current_registrations,
    (e.max_capacity - e.current_registrations) AS available_seats,
    CASE 
        WHEN e.current_registrations >= e.max_capacity THEN 'HOUSEFULL'
        WHEN e.current_registrations >= (e.max_capacity * 0.8) THEN 'FILLING FAST'
        ELSE 'AVAILABLE'
    END AS seat_status
FROM events e;
