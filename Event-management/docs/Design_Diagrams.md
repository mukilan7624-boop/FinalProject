# System Design Diagrams

## College Event Management and Participation Analysis System
**Student Register No.:** 31  
**Class:** 25IT3A&B | **Course:** 25ITT33 Software Engineering & 25ITT32 Java Programming  
**Department:** Information Technology, Velalar College of Engineering and Technology  

---

## 1. Use Case Diagram

The Use Case diagram illustrates the functional interactions between the primary actors (**Administrator**, **Student**, and **Data Analyst / Python Engine**) and the system boundaries.

```mermaid
flowchart LR
    subgraph Actors
        Admin["Administrator / Organizer"]
        Student["Student (Participant - Reg 31)"]
        Analyst["Python Analytics Engine"]
    end

    subgraph College Event Management System
        UC1(["Create / Update / Delete Event"])
        UC2(["View Available Events & Details"])
        UC3(["Register for Event"])
        UC4(["Validate Capacity & Prevent Duplicates"])
        UC5(["Mark Attendance (Present / Absent)"])
        UC6(["Submit Event Feedback & Rating"])
        UC7(["Generate Participation Reports"])
        UC8(["Extract Data & Visualize Analytics"])
    end

    Admin --> UC1
    Admin --> UC2
    Admin --> UC5
    Admin --> UC7

    Student --> UC2
    Student --> UC3
    Student --> UC6

    UC3 -.->|<<include>>| UC4
    UC6 -.->|<<extend>>| UC5

    Analyst --> UC8
    UC8 -.->|<<reads>>| UC7
```
*Vector diagram file:* [`docs/diagrams/use_case_diagram.svg`](file:///c:/Users/madha/OneDrive/Desktop/Event%20management/docs/diagrams/use_case_diagram.svg)

---

## 2. Overall System Architecture Diagram

```mermaid
flowchart TD
    subgraph Tier 1: Presentation Layer
        AdminUI["Administrator Console"]
        StudentUI["Student Portal & CLI"]
        WebUI["Web Dashboard (Localhost:8080)"]
    end

    subgraph Tier 2: Application & Business Logic
        ES["EventService (Validation Engine)"]
        RS["ReportService (Java 8 Streams)"]
        DAO["DAO Persistence Layer (EventDAO, RegDAO, etc.)"]
    end

    subgraph Tier 3: Database & Persistence Layer
        DB[("Relational Database\n(SQLite / MySQL)\nNormalized to BCNF")]
    end

    subgraph Tier 4: Data Science Analytics Pipeline
        PY["Python Extraction Engine (read_sql)"]
        EDA["Pandas Wrangling & Cleaning"]
        VIS["Matplotlib & Seaborn Visualizations"]
    end

    AdminUI --> ES
    StudentUI --> ES
    WebUI --> ES
    ES --> DAO
    RS --> DAO
    DAO --> DB
    DB --> PY
    PY --> EDA
    EDA --> VIS
```
*Vector diagram file:* [`docs/diagrams/architecture_diagram.svg`](file:///c:/Users/madha/OneDrive/Desktop/Event%20management/docs/diagrams/architecture_diagram.svg)

---

## 3. UML Class Diagram

The Class Diagram reflects the Object-Oriented design implemented in the Java source code (`com.eventmanagement.*`), demonstrating encapsulation, inheritance, interfaces, associations, and custom exceptions.

```mermaid
classDiagram
    direction TB

    class Event {
        -int eventId
        -String eventName
        -String category
        -String department
        -String eventDate
        -String venue
        -int maxCapacity
        -int currentRegistrations
        +hasAvailableSeats() boolean
        +incrementRegistration() void
        +decrementRegistration() void
        +getUtilizationRate() double
    }

    class Student {
        -int studentId
        -String registerNo
        -String name
        -String department
        -String email
        -String contactNumber
    }

    class Registration {
        -int registrationId
        -int eventId
        -int studentId
        -String registrationDate
        -String status
    }

    class Attendance {
        -int attendanceId
        -int registrationId
        -String status
        -String markedAt
        +isPresent() boolean
    }

    class Feedback {
        -int feedbackId
        -int registrationId
        -int rating
        -String comments
        -String submittedAt
    }

    class EventService {
        -EventDAO eventDAO
        -RegistrationDAO regDAO
        +registerStudent(int eventId, int studentId) Registration
        +cancelRegistration(int regId) boolean
        +markAttendance(int regId, String status) boolean
        +submitFeedback(int regId, int rating, String comments) boolean
    }

    class DuplicateRegistrationException {
        +DuplicateRegistrationException(String msg)
    }

    class EventFullException {
        +EventFullException(String msg)
    }

    Event "1" o-- "0..*" Registration : receives
    Student "1" o-- "0..*" Registration : makes
    Registration "1" *-- "0..1" Attendance : tracks
    Registration "1" *-- "0..1" Feedback : collects
    EventService ..> Event : manages
    EventService ..> Registration : processes
    EventService ..> DuplicateRegistrationException : throws
    EventService ..> EventFullException : throws
```
*Vector diagram file:* [`docs/diagrams/class_diagram.svg`](file:///c:/Users/madha/OneDrive/Desktop/Event%20management/docs/diagrams/class_diagram.svg)

---

## 4. UML Component Diagram (Component-Level Design)

The Component Diagram details the internal building blocks, interfaces (`IEventService`, `IPersistenceDAO`), and physical dependencies between the presentation, service, persistence, and analytical components.

```mermaid
flowchart TD
    subgraph UIComponent [Presentation Component]
        MainApp["Main.java (Console UI)"]
        WebDash["Web Dashboard (HTML5/JS)"]
        TableRunner["Terminal Table Viewer"]
    end

    subgraph ServiceComponent [Business Service Component]
        ES["EventService.java\n(Capacity & Duplicate Logic)"]
        RS["ReportService.java\n(Java Streams Aggregator)"]
    end

    subgraph DAOComponent [Data Access Persistence Component]
        DAO_Layer["DAO Classes (EventDAO, RegDAO, etc.)"]
        DBC["DBConnection & DataStore"]
    end

    subgraph DatabaseComponent [Database Storage Component]
        SQL_DB[("SQLite 3 / MySQL Database\n(Normalized to BCNF)")]
    end

    subgraph AnalyticsComponent [Python Data Science Component]
        PY_Engine["event_analysis.py\n(Pandas / NumPy / Seaborn)"]
    end

    UIComponent -->|IEventService| ServiceComponent
    ServiceComponent -->|IPersistenceDAO| DAOComponent
    DAOComponent -->|JDBC SQL / PreparedStatement| DatabaseComponent
    DatabaseComponent -->|SQL Retrieval: read_sql()| AnalyticsComponent
```
*Vector diagram file:* [`docs/diagrams/component_diagram.svg`](file:///c:/Users/madha/OneDrive/Desktop/Event%20management/docs/diagrams/component_diagram.svg)

---

## 5. Sequence Diagram: Student Event Registration

```mermaid
sequenceDiagram
    autonumber
    actor Student
    participant Main as Main (CLI / Web)
    participant Service as EventService
    participant RegDAO as RegistrationDAO
    participant EventDAO as EventDAO
    participant DB as Relational Database

    Student->>Main: Request Registration(eventId, studentId)
    Main->>Service: registerStudent(eventId, studentId)
    
    Service->>EventDAO: getEventById(eventId)
    EventDAO->>DB: SELECT * FROM events WHERE event_id = ?
    DB-->>EventDAO: Event details
    EventDAO-->>Service: Event object

    alt Event has no available seats
        Service-->>Main: throw EventFullException("Capacity reached")
        Main-->>Student: Display "Event Full! Registration rejected."
    else Event has available seats
        Service->>RegDAO: isStudentRegistered(eventId, studentId)
        RegDAO->>DB: SELECT COUNT(*) FROM registrations WHERE event_id=? AND student_id=?
        DB-->>RegDAO: count
        RegDAO-->>Service: boolean exists
        
        alt Student already registered
            Service-->>Main: throw DuplicateRegistrationException("Already registered")
            Main-->>Student: Display "Duplicate Registration Prohibited!"
        else Valid New Registration
            Service->>RegDAO: insertRegistration(eventId, studentId)
            RegDAO->>DB: INSERT INTO registrations VALUES (...)
            DB-->>RegDAO: Success (registration_id)
            Service->>EventDAO: incrementRegistrationCount(eventId)
            EventDAO->>DB: UPDATE events SET current_registrations = current_registrations + 1
            DB-->>EventDAO: OK
            Service-->>Main: Registration Object
            Main-->>Student: Display "Registration Successful! Status: CONFIRMED"
        end
    end
```

---

## 6. Entity-Relationship (ER) Diagram

```mermaid
erDiagram
    STUDENT ||--o{ REGISTRATION : places
    EVENT ||--o{ REGISTRATION : contains
    REGISTRATION ||--o| ATTENDANCE : has
    REGISTRATION ||--o| FEEDBACK : submits

    STUDENT {
        int student_id PK
        string register_no UK
        string name
        string department
        string email UK
        string contact_number
    }

    EVENT {
        int event_id PK
        string event_name
        string category
        string department
        string event_date
        string venue
        int max_capacity
        int current_registrations
    }

    REGISTRATION {
        int registration_id PK
        int event_id FK
        int student_id FK
        datetime registration_date
        string status
    }

    ATTENDANCE {
        int attendance_id PK
        int registration_id FK,UK
        string status
        datetime marked_at
    }

    FEEDBACK {
        int feedback_id PK
        int registration_id FK,UK
        int rating
        string comments
        datetime submitted_at
    }
```
*Vector diagram file:* [`docs/diagrams/er_diagram.svg`](file:///c:/Users/madha/OneDrive/Desktop/Event%20management/docs/diagrams/er_diagram.svg)
