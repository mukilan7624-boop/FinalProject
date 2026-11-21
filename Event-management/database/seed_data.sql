-- ==============================================================================
-- Seed Dataset for College Event Management and Participation Analysis System
-- Student Register No.: 31 | VCET Information Technology
-- ==============================================================================

-- 1. Insert Students
INSERT INTO students (register_no, name, department, email, contact_number) VALUES
('25IT031', 'Madhavan S (Reg 31)', 'Information Technology', 'madhavan.25it031@velalarengg.ac.in', '9842100031'),
('25IT001', 'Aarav Kumar', 'Information Technology', 'aarav.25it001@velalarengg.ac.in', '9842100001'),
('25IT061', 'Dharani R', 'Information Technology', 'dharani.25it061@velalarengg.ac.in', '9842100061'),
('25IT091', 'Kavya S', 'Information Technology', 'kavya.25it091@velalarengg.ac.in', '9842100091'),
('25ITL01', 'Naveen Prashanth L', 'Information Technology', 'naveen.25itl01@velalarengg.ac.in', '9842100099'),
('25CS012', 'Bala Subramanian', 'Computer Science and Engg', 'bala.25cs012@velalarengg.ac.in', '9842100012'),
('25CS045', 'Gokul Nathan', 'Computer Science and Engg', 'gokul.25cs045@velalarengg.ac.in', '9842100045'),
('25EC018', 'Harish V', 'Electronics and Comm Engg', 'harish.25ec018@velalarengg.ac.in', '9842100018'),
('25EC052', 'Janani M', 'Electronics and Comm Engg', 'janani.25ec052@velalarengg.ac.in', '9842100052'),
('25AI022', 'Manoj Kumar', 'Artificial Intelligence & DS', 'manoj.25ai022@velalarengg.ac.in', '9842100022'),
('25AI049', 'Pavithra K', 'Artificial Intelligence & DS', 'pavithra.25ai049@velalarengg.ac.in', '9842100049'),
('25ME015', 'Karthik Raja', 'Mechanical Engineering', 'karthik.25me015@velalarengg.ac.in', '9842100015');

-- 2. Insert Events
INSERT INTO events (event_name, category, department, event_date, venue, max_capacity, current_registrations) VALUES
('CYBERFEST 2026: National Tech Symposium', 'Symposium', 'Information Technology', '2026-12-05', 'Main Auditorium', 120, 0),
('Hands-on Workshop on Generative AI & LLMs', 'Workshop', 'Information Technology', '2026-12-12', 'IT Computing Lab 3', 45, 0),
('National Seminar on Cloud & Distributed Systems', 'Seminar', 'Computer Science and Engg', '2026-12-18', 'Seminar Hall 1', 60, 0),
('VIBES 2026: Annual Inter-College Cultural Fest', 'Cultural', 'Student Affairs', '2026-12-24', 'Open Air Amphitheatre', 250, 0),
('Embedded IoT & Robotics Workshop', 'Workshop', 'Electronics and Comm Engg', '2027-01-08', 'IoT Research Lab', 35, 0),
('Deep Learning Bootcamp for Computer Vision', 'Workshop', 'Artificial Intelligence & DS', '2027-01-15', 'AI Innovation Lab', 40, 0),
('TechConclave 2027: Paper Presentation', 'Symposium', 'Information Technology', '2027-01-22', 'Conference Hall 2', 50, 0),
('Rhythm & Beats: Music and Fine Arts Evening', 'Cultural', 'Cultural Club', '2027-01-29', 'College Ground Stage', 300, 0);

-- 3. Insert Registrations
-- Event 1: CYBERFEST 2026 (Symposium)
INSERT INTO registrations (event_id, student_id, registration_date, status) VALUES
(1, 1, '2026-11-20 09:30:00', 'CONFIRMED'), -- Reg 31
(1, 2, '2026-11-20 09:35:00', 'CONFIRMED'),
(1, 3, '2026-11-20 09:40:00', 'CONFIRMED'),
(1, 4, '2026-11-20 10:00:00', 'CONFIRMED'),
(1, 5, '2026-11-20 10:15:00', 'CONFIRMED'),
(1, 6, '2026-11-20 10:20:00', 'CONFIRMED'),
(1, 7, '2026-11-20 10:30:00', 'CONFIRMED'),
(1, 8, '2026-11-20 11:00:00', 'CONFIRMED'),
(1, 10, '2026-11-20 11:15:00', 'CONFIRMED');

-- Event 2: Hands-on Workshop on Generative AI
INSERT INTO registrations (event_id, student_id, registration_date, status) VALUES
(2, 1, '2026-11-21 08:30:00', 'CONFIRMED'), -- Reg 31
(2, 2, '2026-11-21 08:45:00', 'CONFIRMED'),
(2, 6, '2026-11-21 09:00:00', 'CONFIRMED'),
(2, 7, '2026-11-21 09:10:00', 'CONFIRMED'),
(2, 10, '2026-11-21 09:20:00', 'CONFIRMED'),
(2, 11, '2026-11-21 09:30:00', 'CONFIRMED');

-- Event 3: National Seminar on Cloud
INSERT INTO registrations (event_id, student_id, registration_date, status) VALUES
(3, 1, '2026-11-22 14:00:00', 'CONFIRMED'), -- Reg 31
(3, 3, '2026-11-22 14:10:00', 'CONFIRMED'),
(3, 4, '2026-11-22 14:25:00', 'CONFIRMED'),
(3, 6, '2026-11-22 14:30:00', 'CONFIRMED'),
(3, 12, '2026-11-22 14:40:00', 'CONFIRMED');

-- Event 4: VIBES 2026 (Cultural)
INSERT INTO registrations (event_id, student_id, registration_date, status) VALUES
(4, 1, '2026-11-23 10:00:00', 'CONFIRMED'), -- Reg 31
(4, 2, '2026-11-23 10:05:00', 'CONFIRMED'),
(4, 3, '2026-11-23 10:10:00', 'CONFIRMED'),
(4, 4, '2026-11-23 10:15:00', 'CONFIRMED'),
(4, 5, '2026-11-23 10:20:00', 'CONFIRMED'),
(4, 8, '2026-11-23 10:30:00', 'CONFIRMED'),
(4, 9, '2026-11-23 10:45:00', 'CONFIRMED'),
(4, 11, '2026-11-23 11:00:00', 'CONFIRMED'),
(4, 12, '2026-11-23 11:30:00', 'CONFIRMED');

-- Event 5: Embedded IoT Workshop
INSERT INTO registrations (event_id, student_id, registration_date, status) VALUES
(5, 8, '2026-11-24 09:00:00', 'CONFIRMED'),
(5, 9, '2026-11-24 09:15:00', 'CONFIRMED'),
(5, 12, '2026-11-24 09:30:00', 'CONFIRMED');

-- Event 6: Deep Learning Bootcamp
INSERT INTO registrations (event_id, student_id, registration_date, status) VALUES
(6, 1, '2026-11-25 15:00:00', 'CONFIRMED'), -- Reg 31
(6, 6, '2026-11-25 15:10:00', 'CONFIRMED'),
(6, 7, '2026-11-25 15:20:00', 'CONFIRMED'),
(6, 10, '2026-11-25 15:30:00', 'CONFIRMED'),
(6, 11, '2026-11-25 15:45:00', 'CONFIRMED');

-- 4. Insert Attendance Records
-- CYBERFEST (Reg 1 to 9)
INSERT INTO attendance (registration_id, status, marked_at) VALUES
(1, 'PRESENT', '2026-12-05 09:00:00'), -- Reg 31
(2, 'PRESENT', '2026-12-05 09:02:00'),
(3, 'PRESENT', '2026-12-05 09:05:00'),
(4, 'ABSENT',  '2026-12-05 09:30:00'),
(5, 'PRESENT', '2026-12-05 09:10:00'),
(6, 'PRESENT', '2026-12-05 09:12:00'),
(7, 'PRESENT', '2026-12-05 09:15:00'),
(8, 'PRESENT', '2026-12-05 09:18:00'),
(9, 'ABSENT',  '2026-12-05 09:30:00');

-- Generative AI Workshop (Reg 10 to 15)
INSERT INTO attendance (registration_id, status, marked_at) VALUES
(10, 'PRESENT', '2026-12-12 08:50:00'), -- Reg 31
(11, 'PRESENT', '2026-12-12 08:55:00'),
(12, 'PRESENT', '2026-12-12 09:00:00'),
(13, 'PRESENT', '2026-12-12 09:02:00'),
(14, 'PRESENT', '2026-12-12 09:05:00'),
(15, 'PRESENT', '2026-12-12 09:10:00');

-- Cloud Seminar (Reg 16 to 20)
INSERT INTO attendance (registration_id, status, marked_at) VALUES
(16, 'PRESENT', '2026-12-18 13:55:00'), -- Reg 31
(17, 'PRESENT', '2026-12-18 14:00:00'),
(18, 'ABSENT',  '2026-12-18 14:30:00'),
(19, 'PRESENT', '2026-12-18 14:05:00'),
(20, 'PRESENT', '2026-12-18 14:10:00');

-- VIBES 2026 Cultural (Reg 21 to 29)
INSERT INTO attendance (registration_id, status, marked_at) VALUES
(21, 'PRESENT', '2026-12-24 09:45:00'), -- Reg 31
(22, 'PRESENT', '2026-12-24 09:50:00'),
(23, 'PRESENT', '2026-12-24 09:55:00'),
(24, 'PRESENT', '2026-12-24 10:00:00'),
(25, 'PRESENT', '2026-12-24 10:05:00'),
(26, 'PRESENT', '2026-12-24 10:10:00'),
(27, 'ABSENT',  '2026-12-24 10:30:00'),
(28, 'PRESENT', '2026-12-24 10:15:00'),
(29, 'PRESENT', '2026-12-24 10:20:00');

-- 5. Insert Feedback Records (only for attended students)
INSERT INTO feedback (registration_id, rating, comments, submitted_at) VALUES
(1, 5, 'Outstanding national symposium! The coding hackathon round was top notch.', '2026-12-05 16:30:00'), -- Reg 31
(2, 4, 'Great technical talks, well organized paper presentation tracks.', '2026-12-05 16:35:00'),
(3, 5, 'Loved the technical quiz and project display arena.', '2026-12-05 16:40:00'),
(5, 4, 'Very informative sessions by industry professionals.', '2026-12-05 16:45:00'),
(6, 4, 'Well-coordinated event with good participation from neighboring colleges.', '2026-12-05 16:50:00'),
(7, 3, 'Content was good, but venue audio system had minor echoes.', '2026-12-05 16:55:00'),
(8, 5, 'One of the best symposiums hosted by the IT department!', '2026-12-05 17:00:00'),
(10, 5, 'Superb hands-on coding experience on LangChain and Transformers.', '2026-12-12 16:00:00'), -- Reg 31
(11, 5, 'Clear practical demonstrations and code notebooks provided.', '2026-12-12 16:05:00'),
(12, 4, 'Instructor was knowledgeable, pace was slightly fast for beginners.', '2026-12-12 16:10:00'),
(13, 5, 'Loved implementing live multi-agent workflows!', '2026-12-12 16:15:00'),
(14, 5, 'High practical value for final year projects.', '2026-12-12 16:20:00'),
(15, 4, 'Great workshop, would love an advanced follow-up level.', '2026-12-12 16:25:00'),
(16, 4, 'Good overview of AWS and Kubernetes container orchestration.', '2026-12-18 16:45:00'), -- Reg 31
(17, 3, 'Informative but needed more hands-on lab exercises.', '2026-12-18 16:50:00'),
(19, 4, 'Well-structured presentation on microservices architecture.', '2026-12-18 16:55:00'),
(20, 4, 'Valuable career insights into Cloud DevOps engineering.', '2026-12-18 17:00:00'),
(21, 5, 'Energetic cultural fest! Fantastic music and dance performances.', '2026-12-24 18:00:00'), -- Reg 31
(22, 5, 'Incredible lighting, stage setup, and joyful atmosphere.', '2026-12-24 18:05:00'),
(23, 4, 'Wonderful engagement and fun crowd activities.', '2026-12-24 18:10:00'),
(24, 4, 'Great student talent showcase across departments.', '2026-12-24 18:15:00'),
(25, 5, 'Unforgettable evening! Perfectly executed.', '2026-12-24 18:20:00'),
(26, 4, 'Food and refreshment stalls were great.', '2026-12-24 18:25:00'),
(28, 5, 'Memorable celebration, congratulations to the organizing committee.', '2026-12-24 18:30:00'),
(29, 4, 'Very entertaining cultural night.', '2026-12-24 18:35:00');
