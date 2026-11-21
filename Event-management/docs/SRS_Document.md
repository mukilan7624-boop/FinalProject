# Software Requirements Specification (SRS)

## College Event Management and Participation Analysis System
**Academic Year:** 2026 - 2027 (ODD)  
**Institution:** Velalar College of Engineering and Technology, Erode - 12  
**Department:** Information Technology  
**Class:** 25IT3A&B  
**Course Codes:** 
- 25ITT33: Software Engineering
- 25ITT32: Java Programming
- 25ITT31: Database Management Systems
- 25ITC31: Foundations of Data Science  
**Assigned Scenario:** Scenario 1 (College Event Management and Participation Analysis System)  
**Register No.:** 31  

---

## 1. Introduction

### 1.1 Purpose
This Software Requirements Specification (SRS) document defines the software and functional requirements for the **College Event Management and Participation Analysis System**. The primary objective is to replace the traditional, error-prone manual record-keeping of college events with an automated, database-backed software solution coupled with Python-based data science analytics.

### 1.2 Scope of the System
The system automates the complete lifecycle of campus events organized across departments:
- **Event Administration:** Creating, updating, viewing, and archiving events (Technical Symposiums, Workshops, Seminars, Cultural Events).
- **Student Registration:** Allowing authenticated students to view active events and register.
- **Integrity & Capacity Enforcement:** Ensuring zero duplicate registrations and strictly rejecting registrations when venue capacity is reached.
- **Attendance & Feedback Tracking:** Recording actual student attendance and gathering multi-point qualitative feedback.
- **Analytical Reporting:** Real-time summary generation using Java streams and comprehensive exploratory data analysis (EDA) using Python (Pandas, NumPy, Matplotlib, Seaborn).

### 1.3 Definitions, Acronyms, and Abbreviations
- **SRS:** Software Requirements Specification
- **DBMS:** Database Management System
- **JDBC:** Java Database Connectivity
- **DAO:** Data Access Object
- **CRUD:** Create, Read, Update, Delete
- **EDA:** Exploratory Data Analysis
- **CSV:** Comma Separated Values
- **GUI / CLI:** Graphical User Interface / Command Line Interface
- **VCET:** Velalar College of Engineering and Technology

### 1.4 References
- IEEE Std 830-1998: IEEE Recommended Practice for Software Requirements Specifications.
- VCET IT Department Academic Regulations 2026-2027.
- Oracle Java SE Documentation (JDK 17/21).
- Python 3.10+ / Pandas / Seaborn Documentation.

---

## 2. Overall Description

### 2.1 Product Perspective
The College Event Management System is an integrated multi-tier software system:
1. **Data Layer:** Relational database (SQLite/MySQL) storing events, students, registrations, attendance, and feedback records normalized to 3NF/BCNF.
2. **Business & Application Layer (Java):** An Object-Oriented application handling input validation, business logic, constraints enforcement, and database transactions via JDBC.
3. **Analytics & Visualization Layer (Python):** An automated analytics pipeline retrieving structured SQL records, conducting data preprocessing, cleaning, and rendering visual reports.

### 2.2 User Classes and Characteristics
- **System Administrator / Event Organizer:**
  - Creates, updates, and deletes event schedules.
  - Monitors event capacity and attendance.
  - Generates institutional participation reports.
- **Student / Participant (Reg No. 31, etc.):**
  - Browses upcoming technical symposiums, workshops, seminars, and cultural events.
  - Registers for events with real-time seat availability validation.
  - Submits ratings (1-5 stars) and feedback comments post-event.
- **Faculty Coordinator / Head of Department:**
  - Evaluates student engagement trends, department-wise turnout, and event satisfaction metrics.

### 2.3 Operating Environment
- **Operating System:** Windows 10/11, Linux (Ubuntu/Debian), macOS.
- **Java Runtime:** Java SE Development Kit (JDK 8 / 11 / 17 / 21).
- **Python Environment:** Python 3.8+ with `pandas`, `numpy`, `matplotlib`, and `seaborn`.
- **Database Engine:** SQLite 3 (embedded) / MySQL 8.0+.

### 2.4 Design and Implementation Constraints
- Strong validation to prevent duplicate registrations (`UNIQUE(event_id, student_id)` constraint and DAO checks).
- Strict atomic transaction management during seat allocation.
- Separation of concerns using Model-View-Controller (MVC) and Data Access Object (DAO) architectural patterns.
- Clear separation between the operational transaction system (Java) and analytical reporting system (Python).

---

## 3. Specific Requirements

### 3.1 Functional Requirements

#### FR-1: Event Lifecycle Management (Administrator)
- **FR-1.1:** The admin shall be able to create new events with attributes: `event_id`, `event_name`, `category` (Symposium, Workshop, Seminar, Cultural), `department`, `event_date`, `venue`, `max_capacity`, and `organizer_contact`.
- **FR-1.2:** The admin shall be able to update event details (e.g., date, venue, capacity).
- **FR-1.3:** The admin shall be able to view active, upcoming, and completed events.
- **FR-1.4:** The admin shall be able to cancel or delete an event, with proper cascading or soft-delete safeguards.

#### FR-2: Student Registration
- **FR-2.1:** Students shall browse available events with remaining seat count.
- **FR-2.2:** Students shall register by providing their Student ID / Register Number, Name, Department, and Email.
- **FR-2.3:** The system **MUST** prevent duplicate registrations for the same event by the same student.
- **FR-2.4:** The system **MUST** verify remaining capacity before confirming registration. If full, a descriptive error (`EventFullException`) must be returned.

#### FR-3: Attendance & Feedback Recording
- **FR-3.1:** The organizer shall mark student attendance status (`PRESENT` / `ABSENT`).
- **FR-3.2:** Attended students shall submit feedback with a rating scale of 1 to 5 and textual feedback comments.
- **FR-3.3:** The system shall restrict feedback submission only to students marked as `PRESENT`.

#### FR-4: Operational Reporting (Java)
- **FR-4.1:** Generate summary reports of registrations per event.
- **FR-4.2:** Calculate real-time attendance percentage and average feedback rating per event.

#### FR-5: Data Science Analytics & Visualizations (Python)
- **FR-5.1:** Connect to the relational database and load datasets into Pandas DataFrames.
- **FR-5.2:** Perform data preprocessing: clean null values, normalize data types, derive calculated metrics (e.g., Attendance Rate = Present / Registered * 100).
- **FR-5.3:** Generate visualizations using Matplotlib & Seaborn:
  - Event category distribution (Bar / Pie chart).
  - Registration count vs. Maximum capacity comparison.
  - Department-wise student participation.
  - Event-wise attendance rates.
  - Feedback rating distribution (Histogram / Box plot).
  - Multi-variable correlation heatmap.

---

### 3.2 Non-Functional Requirements

| ID | Requirement Area | Specification |
|---|---|---|
| **NFR-1** | **Performance** | Event queries and registration transactions must complete within under 200ms. |
| **NFR-2** | **Data Integrity** | ACID compliance guaranteed via relational foreign keys and database transactions. |
| **NFR-3** | **Reliability** | Exception handling for SQL failures, duplicate primary keys, and capacity limits. |
| **NFR-4** | **Usability** | Clear menu-driven console prompts and formatted tabular display of records. |
| **NFR-5** | **Maintainability**| Standard Java packages (`com.eventmanagement.*`) and modular Python functions. |
| **NFR-6** | **Portability** | Standalone execution on Windows, Linux, and macOS without proprietary third-party dependencies. |

---

## 4. System Models and Use Cases

### 4.1 Actors
1. **Administrator / Faculty Organizer:** Manages events, records attendance, reviews reports.
2. **Student:** Views events, registers, submits feedback.
3. **Data Analyst / Python Engine:** Queries database, performs wrangling and produces visual plots.

### 4.2 Use Case Descriptions
- **UC-01:** Admin creates and publishes event.
- **UC-02:** Student registers for event (Includes: *Check Seat Availability*, *Verify Duplicate*).
- **UC-03:** Admin marks attendance.
- **UC-04:** Student submits feedback (Precondition: Attendance marked `PRESENT`).
- **UC-05:** Python analytics script generates visual insights.

---
**Prepared By:** Register No. 31 (Class 25IT3A&B)  
**Department of Information Technology**, Velalar College of Engineering and Technology.
