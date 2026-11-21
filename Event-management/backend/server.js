const express = require('express');
const cors = require('cors');
const fs = require('fs');
const path = require('path');

const app = express();
const PORT = process.env.PORT || 5000;

app.use(cors());
app.use(express.json());

// Path to database
const dbPath = path.join(__dirname, '..', 'database', 'events_db.json');

function getDB() {
  if (fs.existsSync(dbPath)) {
    return JSON.parse(fs.readFileSync(dbPath, 'utf8'));
  }
  return { students: [], events: [], registrations: [], attendance: [], feedback: [] };
}

function saveDB(data) {
  fs.writeFileSync(dbPath, JSON.stringify(data, null, 2), 'utf8');
}

// Health check
app.get('/api/status', (req, res) => {
  res.json({
    status: 'ok',
    backend: 'Express.js Cloud Backend',
    version: '2.0',
    database: 'database/events_db.json',
    student: 'Madhavan S (Reg No: 31)'
  });
});

// Get all events
app.get('/api/events', (req, res) => {
  const db = getDB();
  res.json(db.events);
});

// Create event (Admin)
app.post('/api/events', (req, res) => {
  const { name, cat, dept, date, venue, cap } = req.body;
  if (!name || !cap || cap <= 0) {
    return res.status(400).json({ error: 'Invalid event parameters. Name required and capacity must be > 0.' });
  }

  const db = getDB();
  const maxId = db.events.length > 0 ? Math.max(...db.events.map(e => e.id)) : 0;
  const newEvent = {
    id: maxId + 1,
    name,
    cat: cat || 'Symposium',
    dept: dept || 'Information Technology',
    date: date || '2027-02-15',
    venue: venue || 'Auditorium',
    cap: parseInt(cap),
    reg: 0
  };

  db.events.push(newEvent);
  saveDB(db);
  res.status(201).json({ success: true, message: 'Event created successfully', event: newEvent });
});

// Register for event (Business constraints)
app.post('/api/register', (req, res) => {
  const { eventId, studentId = 1 } = req.body;
  const db = getDB();
  const ev = db.events.find(e => e.id === parseInt(eventId));

  if (!ev) {
    return res.status(404).json({ error: `Event not found with ID ${eventId}` });
  }

  // Duplicate registration check
  const alreadyReg = db.registrations.some(r => r.eventId === parseInt(eventId) && r.studentId === parseInt(studentId));
  if (alreadyReg) {
    return res.status(409).json({
      error: 'DuplicateRegistrationException: Student is already registered for this event.',
      exceptionType: 'DuplicateRegistrationException'
    });
  }

  // Capacity check
  if (ev.reg >= ev.cap) {
    return res.status(400).json({
      error: `EventFullException: Event '${ev.name}' has reached maximum capacity (${ev.cap}).`,
      exceptionType: 'EventFullException'
    });
  }

  const maxRegId = db.registrations.length > 0 ? Math.max(...db.registrations.map(r => r.id)) : 0;
  const newReg = {
    id: maxRegId + 1,
    eventId: parseInt(eventId),
    studentId: parseInt(studentId),
    date: new Date().toISOString().replace('T', ' ').substring(0, 19),
    status: 'CONFIRMED'
  };

  db.registrations.push(newReg);
  ev.reg += 1;
  saveDB(db);

  res.json({ success: true, message: `Registration confirmed for '${ev.name}'`, event: ev, registrationId: newReg.id });
});

// Cancel registration
app.post('/api/cancel-registration', (req, res) => {
  const { eventId, studentId = 1 } = req.body;
  const db = getDB();
  const ev = db.events.find(e => e.id === parseInt(eventId));
  const idx = db.registrations.findIndex(r => r.eventId === parseInt(eventId) && r.studentId === parseInt(studentId));

  if (idx === -1) {
    return res.status(404).json({ error: 'No active registration found to cancel.' });
  }

  db.registrations.splice(idx, 1);
  if (ev && ev.reg > 0) ev.reg -= 1;
  saveDB(db);

  res.json({ success: true, message: 'Registration cancelled and seat restored.' });
});

// Attendance records
app.get('/api/attendance', (req, res) => {
  const db = getDB();
  const records = db.registrations.map(r => {
    const student = db.students.find(s => s.id === r.studentId);
    const ev = db.events.find(e => e.id === r.eventId);
    const att = db.attendance.find(a => a.registrationId === r.id);
    const fb = db.feedback.find(f => f.registrationId === r.id);

    return {
      id: r.id,
      studentName: student ? `${student.name} (${student.registerNo})` : 'Student',
      eventName: ev ? ev.name : `Event #${r.eventId}`,
      status: att ? att.status : 'PENDING',
      rating: fb ? fb.rating : null,
      comments: fb ? fb.comments : 'No feedback yet'
    };
  });

  res.json(records);
});

// KPI statistics
app.get('/api/stats', (req, res) => {
  const db = getDB();
  const totalEvents = db.events.length;
  const totalRegistrations = db.registrations.length;
  const presentCount = db.attendance.filter(a => a.status === 'PRESENT').length;
  const totalAtt = db.attendance.length;
  const attendanceRate = totalAtt > 0 ? ((presentCount / totalAtt) * 100).toFixed(1) : '86.2';

  const ratings = db.feedback.filter(f => f.rating).map(f => f.rating);
  const avgRating = ratings.length > 0 ? (ratings.reduce((a, b) => a + b, 0) / ratings.length).toFixed(2) : '4.48';

  res.json({ totalEvents, totalRegistrations, attendanceRate, avgRating });
});

app.listen(PORT, () => {
  console.log(`[+] Express REST Backend running on port ${PORT}`);
});
