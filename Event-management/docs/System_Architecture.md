# Overall System Architecture

## College Event Management and Participation Analysis System
**Student Register No.:** 31  
**Department:** Information Technology, Velalar College of Engineering and Technology  
**Academic Year:** 2026 - 2027 (ODD) | **Class:** 25IT3A&B  

---

## 1. Architectural Overview

The system is designed following a **3-Tier Layered Architecture** with distinct decoupling between:
1. **Presentation & Application Layer (Java CLI / GUI)**
2. **Business Logic & Persistence Layer (Java DAO & Services)**
3. **Database Layer (Relational Storage - SQLite / MySQL)**
4. **Analytical & Visualization Pipeline (Python Data Science Engine)**

```
+---------------------------------------------------------------------------------+
|                                USER INTERFACE LAYER                             |
|  +----------------------------------+    +-----------------------------------+  |
|  |     Administrator Console        |    |          Student Portal           |  |
|  | (Event CRUD, Attendance Marking) |    |  (Registration, Status, Feedback) |  |
|  +-----------------+----------------+    +-----------------+-----------------+  |
+--------------------|---------------------------------------|--------------------+
                     |                                       |
                     v                                       v
+---------------------------------------------------------------------------------+
|                            APPLICATION & SERVICE LAYER                          |
|  +---------------------------------------------------------------------------+  |
|  | EventService                                                              |  |
|  |  - validateRegistration() [Checks capacity & prevents duplicate entry]    |  |
|  |  - markAttendance()                                                       |  |
|  |  - processFeedback()                                                      |  |
|  +---------------------------------------------------------------------------+  |
|  | ReportService                                                             |  |
|  |  - computeParticipationMetrics() (Java 8 Streams & Lambdas)               |  |
|  |  - generateEventSummary()                                                 |  |
|  +---------------------------------------------------------------------------+  |
+------------------------------------+--------------------------------------------+
                                     |
                                     v
+---------------------------------------------------------------------------------+
|                            DATA ACCESS OBJECT (DAO) LAYER                       |
|  +-------------------+  +--------------------+  +----------------------------+  |
|  |   EventDAO.java   |  |   StudentDAO.java  |  |   RegistrationDAO.java     |  |
|  +-------------------+  +--------------------+  +----------------------------+  |
|  |                   DBConnection (JDBC Connection Pool Manager)             |  |
|  +---------------------------------+-----------------------------------------+  |
+------------------------------------|--------------------------------------------+
                                     | (SQL Transactions: ACID)
                                     v
+---------------------------------------------------------------------------------+
|                                DATABASE STORAGE LAYER                           |
|             SQLite 3 (`event_management.db`) / MySQL Database                   |
|  [Events] <1:N> [Registrations] <N:1> [Students]                                |
|  [Registrations] <1:1> [Attendance] <1:1> [Feedback]                            |
+------------------------------------+--------------------------------------------+
                                     |
                                     | (SQL Query & Dataset Extraction)
                                     v
+---------------------------------------------------------------------------------+
|                       PYTHON DATA SCIENCE & ANALYTICS PIPELINE                  |
|  +---------------------------------------------------------------------------+  |
|  | Data Extraction: sqlite3 / mysql.connector -> Pandas DataFrame            |  |
|  +---------------------------------------------------------------------------+  |
|  | Data Preprocessing: Handling missing values, type casting, feature eng    |  |
|  +---------------------------------------------------------------------------+  |
|  | Exploratory Data Analysis: NumPy aggregations, groupby statistics        |  |
|  +---------------------------------------------------------------------------+  |
|  | Visualization: Matplotlib & Seaborn plots                                 |  |
|  |  - Category Distribution, Turnout Rates, Feedback Distributions          |  |
|  +---------------------------------------------------------------------------+  |
+---------------------------------------------------------------------------------+
```

---

## 2. Component Descriptions

### 2.1 Java Application Subsystem
- **`com.eventmanagement.model`**: POJO classes representing entities (`Event`, `Student`, `Registration`, `Attendance`, `Feedback`).
- **`com.eventmanagement.dao`**: Implements the Data Access Object pattern to decouple database interactions from application logic using standard JDBC `PreparedStatement` interfaces to prevent SQL injection.
- **`com.eventmanagement.service`**: Encapsulates core business rules:
  - *Duplicate Prevention*: Rejects registration if student ID already registered for event ID.
  - *Capacity Enforcement*: Rejects registration if `current_registrations >= max_capacity`.
  - *Attendance-Feedback Integrity*: Feedback is accepted only for verified attendees.
- **`com.eventmanagement.exception`**: Custom checked exceptions:
  - `DuplicateRegistrationException`
  - `EventFullException`
  - `EventNotFoundException`

### 2.2 Relational Database Subsystem
- Normalization up to **3NF / BCNF** eliminates update anomalies, deletion anomalies, and insertion anomalies.
- Foreign keys maintain referential integrity with cascading updates.
- Indexed columns (`event_id`, `student_id`) ensure $O(\log n)$ lookup times during seat validation.

### 2.3 Python Data Analytics Subsystem
- **Pandas**: Used for tabular dataset wrangling, merging relational tables (`events`, `registrations`, `attendance`, `feedback`), and computing aggregate indicators:
  $$\text{Turnout Rate (\%)} = \left(\frac{\text{Attended Students}}{\text{Registered Students}}\right) \times 100$$
  $$\text{Capacity Utilization (\%)} = \left(\frac{\text{Registered Students}}{\text{Max Capacity}}\right) \times 100$$
- **Matplotlib & Seaborn**: Generates publication-quality charts depicting participation trends, department engagement, and feedback satisfaction scores.
