# Software Testing Document

## College Event Management and Participation Analysis System
**Academic Year:** 2026 - 2027 (ODD)  
**Student Register No.:** 31  
**Class:** 25IT3A&B  
**Course Code:** 25ITT33 - Software Engineering  
**Department:** Information Technology, Velalar College of Engineering and Technology  

---

## 1. Testing Strategy & Objectives

The testing phase validates the functional correctness, robust business constraint validation (capacity limits, duplicate prevention), data integrity, and error-handling capabilities of the College Event Management System.

### 1.1 Testing Techniques Employed
1. **Unit Testing:** Testing individual classes, methods, and DAOs (`EventDAO`, `RegistrationDAO`, `EventService`).
2. **Integration Testing:** Testing interactions between the Service layer, JDBC drivers, and SQL database transactions.
3. **Black-Box Testing (Equivalence Partitioning & Boundary Value Analysis):**
   - *Boundary Value Analysis (BVA):* Testing event capacity at boundaries ($0$, $\text{max\_capacity} - 1$, $\text{max\_capacity}$, $\text{max\_capacity} + 1$).
   - *Equivalence Partitioning (EP):* Testing feedback ratings with valid partitions ($1 \le \text{rating} \le 5$) and invalid partitions ($\text{rating} < 1$, $\text{rating} > 5$).
4. **Negative & Exception Testing:** Verifying handling of `DuplicateRegistrationException`, `EventFullException`, and SQL injection sanitization.

---

## 2. Comprehensive Test Cases Table

| Test Case ID | Test Scenario | Technique | Input Data | Expected Result | Actual Result | Status |
|:---|:---|:---|:---|:---|:---|:---:|
| **TC-01** | Admin creates a new valid workshop event | Functional / Unit | Event: "AI & ML Workshop", Cap: 50, Date: 2026-12-10 | Event saved to database with unique Event ID | Event successfully created and verified in DB | **PASS** |
| **TC-02** | Admin creates event with invalid capacity (zero/negative) | Boundary Value Analysis | Event: "Hackathon", Cap: 0 or -5 | Rejection with `IllegalArgumentException` / Validation error | System prompts: "Capacity must be greater than 0" | **PASS** |
| **TC-03** | Valid student registration within capacity | Functional / Integration | Event ID: 1, Student ID: 31 ("Reg No: 31") | Registration confirmed with unique `registration_id` | Status: `CONFIRMED`, current registration count increments | **PASS** |
| **TC-04** | Duplicate registration attempt for same event & student | Negative Testing / Integrity | Event ID: 1, Student ID: 31 (re-attempted) | Rejection with `DuplicateRegistrationException` | System prevents insert: "Student already registered for this event!" | **PASS** |
| **TC-05** | Registration when event capacity is exactly full | Boundary Value Analysis | Event ID: 2 (Capacity: 30, Registered: 30), Student ID: 4 | Rejection with `EventFullException` | Registration denied: "Event has reached maximum capacity!" | **PASS** |
| **TC-06** | Student updates / cancels registration | State Transition | Registration ID: 10, Action: CANCEL | Registration status changed to `CANCELLED`, seat freed | Status updated, `current_registrations` decremented | **PASS** |
| **TC-07** | Mark attendance for registered student | Functional / Unit | Registration ID: 1, Status: `PRESENT` | Attendance logged with timestamp | Record inserted in `ATTENDANCE` table | **PASS** |
| **TC-08** | Submit feedback within valid rating range (1-5) | Equivalence Partitioning | Registration ID: 1, Rating: 5, "Excellent seminar" | Feedback recorded successfully | Feedback saved with 5-star rating | **PASS** |
| **TC-09** | Submit feedback with out-of-range rating | Boundary Value Analysis | Registration ID: 1, Rating: 6 or 0 | Rejection: Rating must be between 1 and 5 | Error: "Rating must be an integer between 1 and 5" | **PASS** |
| **TC-10** | Submit feedback for absent / unregistered student | Integrity / Business Rule | Registration ID: 999 or marked `ABSENT` | Rejection: Only attended students can submit feedback | Feedback rejected: "Student did not attend this event" | **PASS** |
| **TC-11** | SQL Injection vulnerability test on search | Security / Black-Box | Search Query: `' OR '1'='1` | Query safely parameterized using `PreparedStatement` | Safe literal search executed; no data leakage | **PASS** |
| **TC-12** | Python pipeline database connectivity & retrieval | Integration / Data Science | Execute `event_analysis.py` with DB connection | Returns populated Pandas DataFrames for all 4 tables | 100% records extracted without data truncation | **PASS** |
| **TC-13** | Python preprocessing handles missing values | Data Science Cleaning | Unfilled feedback comments / null ratings | Imputed or filtered without script crash | Nulls converted to "No comments provided", clean EDA | **PASS** |
| **TC-14** | Visualization generation creates valid output files | Output Verification | Script generates 6 visualization charts | 6 high-res chart files generated in `/visualizations` | All 6 charts exported cleanly | **PASS** |

---

## 3. Test Execution Summary

- **Total Test Cases Executed:** 14
- **Passed:** 14
- **Failed:** 0
- **Pass Rate:** 100%
- **Conclusion:** The application meets all software engineering requirements, enforces database integrity, handles invalid edge cases gracefully, and provides reliable analytics.
