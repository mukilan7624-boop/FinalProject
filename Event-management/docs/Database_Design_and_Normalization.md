# Database Design, Relational Model, and Normalization Document

## College Event Management and Participation Analysis System
**Academic Year:** 2026 - 2027 (ODD)  
**Student Register No.:** 31  
**Class:** 25IT3A&B  
**Course Code:** 25ITT31 - Database Management Systems  
**Department:** Information Technology, Velalar College of Engineering and Technology  

---

## 1. Entity-Relationship (ER) Design

### 1.1 Entities and Attributes
1. **STUDENT**:
   - `student_id` (Primary Key, Integer, Auto Increment)
   - `register_no` (Unique, VarChar(20) - e.g., '25IT031')
   - `name` (VarChar(100))
   - `department` (VarChar(50))
   - `email` (Unique, VarChar(100))
   - `contact_number` (VarChar(15))

2. **EVENT**:
   - `event_id` (Primary Key, Integer, Auto Increment)
   - `event_name` (VarChar(150))
   - `category` (VarChar(50) - 'Symposium', 'Workshop', 'Seminar', 'Cultural')
   - `department` (VarChar(50))
   - `event_date` (Date)
   - `venue` (VarChar(100))
   - `max_capacity` (Integer, Check: `max_capacity > 0`)
   - `current_registrations` (Integer, Default 0)

3. **REGISTRATION**:
   - `registration_id` (Primary Key, Integer, Auto Increment)
   - `event_id` (Foreign Key referencing `EVENT(event_id)`)
   - `student_id` (Foreign Key referencing `STUDENT(student_id)`)
   - `registration_date` (Timestamp, Default CURRENT_TIMESTAMP)
   - `status` (VarChar(20) - 'CONFIRMED', 'CANCELLED')
   - *Composite Unique Constraint:* `UNIQUE(event_id, student_id)`

4. **ATTENDANCE**:
   - `attendance_id` (Primary Key, Integer, Auto Increment)
   - `registration_id` (Foreign Key referencing `REGISTRATION(registration_id)`, Unique)
   - `status` (VarChar(20) - 'PRESENT', 'ABSENT')
   - `marked_at` (Timestamp, Default CURRENT_TIMESTAMP)

5. **FEEDBACK**:
   - `feedback_id` (Primary Key, Integer, Auto Increment)
   - `registration_id` (Foreign Key referencing `REGISTRATION(registration_id)`, Unique)
   - `rating` (Integer, Check: `rating BETWEEN 1 AND 5`)
   - `comments` (Text)
   - `submitted_at` (Timestamp, Default CURRENT_TIMESTAMP)

### 1.2 Relationship Cardinalities
- **STUDENT to REGISTRATION**: $1 : N$ (One student can register for multiple events).
- **EVENT to REGISTRATION**: $1 : N$ (One event can have multiple student registrations up to `max_capacity`).
- **REGISTRATION to ATTENDANCE**: $1 : 1$ (Each registration has at most one attendance record).
- **REGISTRATION to FEEDBACK**: $1 : 1$ (Each registration has at most one feedback submission).

---

## 2. Relational Model Schema

```text
STUDENT (
    student_id: INT [PK],
    register_no: VARCHAR(20) [UQ],
    name: VARCHAR(100),
    department: VARCHAR(50),
    email: VARCHAR(100) [UQ],
    contact_number: VARCHAR(15)
)

EVENT (
    event_id: INT [PK],
    event_name: VARCHAR(150),
    category: VARCHAR(50),
    department: VARCHAR(50),
    event_date: DATE,
    venue: VARCHAR(100),
    max_capacity: INT,
    current_registrations: INT
)

REGISTRATION (
    registration_id: INT [PK],
    event_id: INT [FK -> EVENT.event_id],
    student_id: INT [FK -> STUDENT.student_id],
    registration_date: TIMESTAMP,
    status: VARCHAR(20),
    CONSTRAINT uq_event_student UNIQUE (event_id, student_id)
)

ATTENDANCE (
    attendance_id: INT [PK],
    registration_id: INT [FK -> REGISTRATION.registration_id, UQ],
    status: VARCHAR(20),
    marked_at: TIMESTAMP
)

FEEDBACK (
    feedback_id: INT [PK],
    registration_id: INT [FK -> REGISTRATION.registration_id, UQ],
    rating: INT,
    comments: TEXT,
    submitted_at: TIMESTAMP
)
```

---

## 3. Database Normalization Documentation

To ensure zero redundancy, prevent insertion, update, and deletion anomalies, the relational schema has been decomposed and rigorously normalized from an unnormalized state up to **Third Normal Form (3NF) and Boyce-Codd Normal Form (BCNF)**.

### 3.1 Unnormalized Form (UNF)
In an unnormalized flat record, all event, participant, attendance, and feedback details would be stored in a single table:
$$\text{UNF}(\underline{\text{event\_id}}, \text{event\_name}, \text{category}, \text{venue}, \text{max\_capacity}, \{\text{student\_id}, \text{register\_no}, \text{student\_name}, \text{dept}, \text{attendance\_status}, \text{rating}, \text{comments}\})$$
*Anomalies in UNF:* Multi-valued repeating groups for students registered under each event.

---

### 3.2 First Normal Form (1NF)
**Rule:** All attributes must be atomic (no repeating groups, no arrays/lists), and a primary key must uniquely identify each tuple.

**Action:** Remove repeating groups by introducing a composite key:
$$\text{EVENT\_REGISTRATION\_FLAT}(\underline{\text{event\_id}}, \underline{\text{student\_id}}, \text{event\_name}, \text{category}, \text{venue}, \text{max\_capacity}, \text{register\_no}, \text{student\_name}, \text{dept}, \text{attendance\_status}, \text{rating}, \text{comments})$$
*Condition Met:* All values are atomic.  
*Issue Remaining:* Partial Functional Dependencies exist because attributes depend on only part of the composite primary key $(\text{event\_id}, \text{student\_id})$.

---

### 3.3 Second Normal Form (2NF)
**Rule:** The relation must be in 1NF, and **no non-prime attribute may be partially dependent on any candidate key** (Elimination of Partial Dependencies).

**Identified Functional Dependencies (FDs):**
1. $\text{event\_id} \rightarrow \text{event\_name}, \text{category}, \text{department}, \text{event\_date}, \text{venue}, \text{max\_capacity}$ *(Dependent only on `event_id`)*
2. $\text{student\_id} \rightarrow \text{register\_no}, \text{name}, \text{department}, \text{email}, \text{contact\_number}$ *(Dependent only on `student_id`)*
3. $(\text{event\_id}, \text{student\_id}) \rightarrow \text{registration\_date}, \text{status}$
4. $\text{registration\_id} \rightarrow \text{attendance\_status}, \text{rating}, \text{comments}$

**Decomposition into 2NF:**
- **EVENT** ($\underline{\text{event\_id}}$, event\_name, category, department, event\_date, venue, max\_capacity, current\_registrations)
- **STUDENT** ($\underline{\text{student\_id}}$, register\_no, name, department, email, contact\_number)
- **REGISTRATION** ($\underline{\text{registration\_id}}$, event\_id, student\_id, registration\_date, status)
- **EVENT\_ACTIVITY** ($\underline{\text{registration\_id}}$, attendance\_status, rating, comments)

*Condition Met:* All partial dependencies removed.

---

### 3.4 Third Normal Form (3NF)
**Rule:** The relation must be in 2NF, and **no non-prime attribute may be transitively dependent on the primary key** ($X \rightarrow Y$ and $Y \rightarrow Z \implies X \rightarrow Z$ must be eliminated).

**Transitive Dependency Analysis in EVENT_ACTIVITY:**
- In `EVENT_ACTIVITY`:
  - Attendance can exist without Feedback (student attends, but doesn't fill feedback).
  - Feedback should be isolated and optional with rating validation ($1 \le \text{rating} \le 5$).
  - Separating `ATTENDANCE` and `FEEDBACK` removes transitive dependencies and handles sparse nulls cleanly.

**Final 3NF Decomposition:**
- `EVENT` ($\underline{\text{event\_id}}$, event\_name, category, department, event\_date, venue, max\_capacity, current\_registrations)
- `STUDENT` ($\underline{\text{student\_id}}$, register\_no, name, department, email, contact\_number)
- `REGISTRATION` ($\underline{\text{registration\_id}}$, event\_id, student\_id, registration\_date, status)
- `ATTENDANCE` ($\underline{\text{attendance\_id}}$, registration\_id, status, marked\_at)
- `FEEDBACK` ($\underline{\text{feedback\_id}}$, registration\_id, rating, comments, submitted_at)

---

### 3.5 Boyce-Codd Normal Form (BCNF)
**Rule:** For every non-trivial functional dependency $X \rightarrow Y$, $X$ must be a super key.

**Evaluation:**
- In `EVENT`: The determinant $\text{event\_id}$ is a super key.
- In `STUDENT`: Both $\text{student\_id}$, $\text{register\_no}$, and $\text{email}$ are candidate keys (determinants are super keys).
- In `REGISTRATION`: $\text{registration\_id}$ is a candidate key; composite $(\text{event\_id}, \text{student\_id})$ is also unique (candidate key).
- In `ATTENDANCE`: $\text{attendance\_id}$ and $\text{registration\_id}$ are super keys.
- In `FEEDBACK`: $\text{feedback\_id}$ and $\text{registration\_id}$ are super keys.

**Conclusion:** The database schema is strictly in **BCNF**, completely free of update, insertion, or deletion anomalies.

---

## 4. Integrity Constraints & Business Triggers
- **Domain Constraints:** `rating BETWEEN 1 AND 5`, `max_capacity > 0`.
- **Referential Integrity:** `ON DELETE CASCADE` or `ON DELETE RESTRICT` to prevent orphan registrations.
- **Database Trigger (Enforce Capacity Limit):**
  A trigger automatically increments the event's `current_registrations` upon a new registration and prevents insertion if `current_registrations >= max_capacity`.
