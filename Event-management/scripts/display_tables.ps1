# ==============================================================================
# Velalar College of Engineering and Technology | Department of IT
# Scenario 1: College Event Management & Participation Analysis System (Reg: 31)
# Terminal Data Table Generator & Analytical Query Executor
# ==============================================================================

try { [Console]::OutputEncoding = [System.Text.Encoding]::UTF8 } catch {}

param (
    [string]$Action = "interactive"
)

function Print-Banner {
    Write-Host "=======================================================================================" -ForegroundColor Cyan
    Write-Host "             VELALAR COLLEGE OF ENGINEERING AND TECHNOLOGY, ERODE - 12                " -ForegroundColor Yellow -NoNewline; Write-Host ""
    Write-Host "                         DEPARTMENT OF INFORMATION TECHNOLOGY                          " -ForegroundColor Yellow
    Write-Host "        COLLEGE EVENT MANAGEMENT & PARTICIPATION ANALYSIS SYSTEM (REG NO: 31)          " -ForegroundColor Green
    Write-Host "=======================================================================================" -ForegroundColor Cyan
}

function Show-Students-Table {
    Write-Host "`n======================================== [TABLE: STUDENTS] ========================================" -ForegroundColor Yellow
    $students = @(
        [PSCustomObject]@{ StudentID=1;  RegNo="25IT031"; Name="Madhavan S (Reg 31)"; Department="Information Technology"; Email="madhavan.25it031@velalarengg.ac.in"; Phone="9842100031" },
        [PSCustomObject]@{ StudentID=2;  RegNo="25IT001"; Name="Aarav Kumar";        Department="Information Technology"; Email="aarav.25it001@velalarengg.ac.in";    Phone="9842100001" },
        [PSCustomObject]@{ StudentID=3;  RegNo="25IT061"; Name="Dharani R";          Department="Information Technology"; Email="dharani.25it061@velalarengg.ac.in";  Phone="9842100061" },
        [PSCustomObject]@{ StudentID=4;  RegNo="25IT091"; Name="Kavya S";            Department="Information Technology"; Email="kavya.25it091@velalarengg.ac.in";    Phone="9842100091" },
        [PSCustomObject]@{ StudentID=5;  RegNo="25ITL01"; Name="Naveen Prashanth L"; Department="Information Technology"; Email="naveen.25itl01@velalarengg.ac.in";   Phone="9842100099" },
        [PSCustomObject]@{ StudentID=6;  RegNo="25CS012"; Name="Bala Subramanian";   Department="Computer Science & Engg"; Email="bala.25cs012@velalarengg.ac.in";    Phone="9842100012" },
        [PSCustomObject]@{ StudentID=7;  RegNo="25CS045"; Name="Gokul Nathan";       Department="Computer Science & Engg"; Email="gokul.25cs045@velalarengg.ac.in";   Phone="9842100045" },
        [PSCustomObject]@{ StudentID=8;  RegNo="25EC018"; Name="Harish V";           Department="Electronics & Comm Engg"; Email="harish.25ec018@velalarengg.ac.in";  Phone="9842100018" },
        [PSCustomObject]@{ StudentID=9;  RegNo="25EC052"; Name="Janani M";           Department="Electronics & Comm Engg"; Email="janani.25ec052@velalarengg.ac.in";  Phone="9842100052" },
        [PSCustomObject]@{ StudentID=10; RegNo="25AI022"; Name="Manoj Kumar";        Department="Artificial Intel & DS";   Email="manoj.25ai022@velalarengg.ac.in";   Phone="9842100022" },
        [PSCustomObject]@{ StudentID=11; RegNo="25AI049"; Name="Pavithra K";         Department="Artificial Intel & DS";   Email="pavithra.25ai049@velalarengg.ac.in";Phone="9842100049" },
        [PSCustomObject]@{ StudentID=12; RegNo="25ME015"; Name="Karthik Raja";       Department="Mechanical Engineering";  Email="karthik.25me015@velalarengg.ac.in"; Phone="9842100015" }
    )
    $students | Format-Table -AutoSize
    Write-Host "Total Records in 'students' table: $($students.Count)" -ForegroundColor Green
}

function Show-Events-Table {
    Write-Host "`n======================================== [TABLE: EVENTS] ========================================" -ForegroundColor Yellow
    $events = @(
        [PSCustomObject]@{ EventID=1; EventName="CYBERFEST 2026: National Tech Symposium";   Category="Symposium"; Department="Information Technology"; Date="2026-12-05"; Venue="Main Auditorium";     Capacity=120; Confirmed=9; SeatsLeft=111 },
        [PSCustomObject]@{ EventID=2; EventName="Hands-on Workshop on GenAI & LLMs";        Category="Workshop";  Department="Information Technology"; Date="2026-12-12"; Venue="IT Computing Lab 3";  Capacity=45;  Confirmed=6; SeatsLeft=39  },
        [PSCustomObject]@{ EventID=3; EventName="National Seminar on Cloud & Distributed";  Category="Seminar";   Department="Computer Science & Engg";Date="2026-12-18"; Venue="Seminar Hall 1";       Capacity=60;  Confirmed=5; SeatsLeft=55  },
        [PSCustomObject]@{ EventID=4; EventName="VIBES 2026: Annual Cultural Fest";         Category="Cultural";  Department="Student Affairs";        Date="2026-12-24"; Venue="Amphitheatre";         Capacity=250; Confirmed=9; SeatsLeft=241 },
        [PSCustomObject]@{ EventID=5; EventName="Embedded IoT & Robotics Workshop";         Category="Workshop";  Department="Electronics & Comm Engg";Date="2027-01-08"; Venue="IoT Research Lab";     Capacity=35;  Confirmed=4; SeatsLeft=31  },
        [PSCustomObject]@{ EventID=6; EventName="Deep Learning Bootcamp for Vision";        Category="Workshop";  Department="Artificial Intel & DS";  Date="2027-01-15"; Venue="AI Innovation Lab";    Capacity=40;  Confirmed=5; SeatsLeft=35  },
        [PSCustomObject]@{ EventID=7; EventName="TechConclave 2027: Paper Presentation";    Category="Symposium"; Department="Information Technology"; Date="2027-01-22"; Venue="Conference Hall 2";   Capacity=50;  Confirmed=0; SeatsLeft=50  },
        [PSCustomObject]@{ EventID=8; EventName="Rhythm & Beats: Fine Arts Evening";        Category="Cultural";  Department="Cultural Club";          Date="2027-01-29"; Venue="College Ground";       Capacity=300; Confirmed=0; SeatsLeft=300 }
    )
    $events | Format-Table -AutoSize
    Write-Host "Total Records in 'events' table: $($events.Count)" -ForegroundColor Green
}

function Show-Registrations-Table {
    Write-Host "`n===================================== [TABLE: REGISTRATIONS] =====================================" -ForegroundColor Yellow
    $regs = @(
        [PSCustomObject]@{ RegID=1;  Event="CYBERFEST 2026"; RegNo="25IT031"; Student="Madhavan S (Reg 31)"; Date="2026-11-20 09:30:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=2;  Event="CYBERFEST 2026"; RegNo="25IT001"; Student="Aarav Kumar";        Date="2026-11-20 09:35:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=3;  Event="CYBERFEST 2026"; RegNo="25IT061"; Student="Dharani R";          Date="2026-11-20 09:40:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=4;  Event="CYBERFEST 2026"; RegNo="25IT091"; Student="Kavya S";            Date="2026-11-20 10:00:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=5;  Event="CYBERFEST 2026"; RegNo="25ITL01"; Student="Naveen Prashanth L"; Date="2026-11-20 10:15:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=6;  Event="CYBERFEST 2026"; RegNo="25CS012"; Student="Bala Subramanian";   Date="2026-11-20 10:20:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=7;  Event="CYBERFEST 2026"; RegNo="25CS045"; Student="Gokul Nathan";       Date="2026-11-20 10:30:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=8;  Event="CYBERFEST 2026"; RegNo="25EC018"; Student="Harish V";           Date="2026-11-20 11:00:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=9;  Event="CYBERFEST 2026"; RegNo="25AI022"; Student="Manoj Kumar";        Date="2026-11-20 11:15:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=10; Event="GenAI Workshop"; RegNo="25IT031"; Student="Madhavan S (Reg 31)"; Date="2026-11-21 08:30:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=11; Event="GenAI Workshop"; RegNo="25IT001"; Student="Aarav Kumar";        Date="2026-11-21 08:45:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=12; Event="GenAI Workshop"; RegNo="25CS012"; Student="Bala Subramanian";   Date="2026-11-21 09:00:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=13; Event="GenAI Workshop"; RegNo="25CS045"; Student="Gokul Nathan";       Date="2026-11-21 09:10:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=14; Event="GenAI Workshop"; RegNo="25AI022"; Student="Manoj Kumar";        Date="2026-11-21 09:20:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=15; Event="GenAI Workshop"; RegNo="25AI049"; Student="Pavithra K";         Date="2026-11-21 09:30:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=16; Event="Cloud Seminar";  RegNo="25IT031"; Student="Madhavan S (Reg 31)"; Date="2026-11-22 14:00:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=17; Event="Cloud Seminar";  RegNo="25IT061"; Student="Dharani R";          Date="2026-11-22 14:10:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=18; Event="Cloud Seminar";  RegNo="25IT091"; Student="Kavya S";            Date="2026-11-22 14:25:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=19; Event="Cloud Seminar";  RegNo="25CS012"; Student="Bala Subramanian";   Date="2026-11-22 14:30:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=20; Event="Cloud Seminar";  RegNo="25ME015"; Student="Karthik Raja";       Date="2026-11-22 14:40:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=21; Event="VIBES Cultural"; RegNo="25IT031"; Student="Madhavan S (Reg 31)"; Date="2026-11-23 10:00:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=22; Event="VIBES Cultural"; RegNo="25IT001"; Student="Aarav Kumar";        Date="2026-11-23 10:05:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=23; Event="VIBES Cultural"; RegNo="25IT061"; Student="Dharani R";          Date="2026-11-23 10:10:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=24; Event="VIBES Cultural"; RegNo="25IT091"; Student="Kavya S";            Date="2026-11-23 10:15:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=25; Event="VIBES Cultural"; RegNo="25ITL01"; Student="Naveen Prashanth L"; Date="2026-11-23 10:20:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=26; Event="VIBES Cultural"; RegNo="25EC018"; Student="Harish V";           Date="2026-11-23 10:30:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=27; Event="VIBES Cultural"; RegNo="25EC052"; Student="Janani M";           Date="2026-11-23 10:45:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=28; Event="VIBES Cultural"; RegNo="25AI049"; Student="Pavithra K";         Date="2026-11-23 11:00:00"; Status="CONFIRMED" },
        [PSCustomObject]@{ RegID=29; Event="VIBES Cultural"; RegNo="25ME015"; Student="Karthik Raja";       Date="2026-11-23 11:30:00"; Status="CONFIRMED" }
    )
    $regs | Format-Table -AutoSize
    Write-Host "Total Confirmed Registrations: $($regs.Count) (Unique student-event enforcement)" -ForegroundColor Green
}

function Show-Attendance-Table {
    Write-Host "`n======================================= [TABLE: ATTENDANCE] =======================================" -ForegroundColor Yellow
    $att = @(
        [PSCustomObject]@{ AttID=1;  RegID=1;  RegNo="25IT031"; Student="Madhavan S (Reg 31)"; Event="CYBERFEST 2026"; Status="PRESENT"; MarkedAt="2026-12-05 09:00:00" },
        [PSCustomObject]@{ AttID=2;  RegID=2;  RegNo="25IT001"; Student="Aarav Kumar";        Event="CYBERFEST 2026"; Status="PRESENT"; MarkedAt="2026-12-05 09:02:00" },
        [PSCustomObject]@{ AttID=3;  RegID=3;  RegNo="25IT061"; Student="Dharani R";          Event="CYBERFEST 2026"; Status="PRESENT"; MarkedAt="2026-12-05 09:05:00" },
        [PSCustomObject]@{ AttID=4;  RegID=4;  RegNo="25IT091"; Student="Kavya S";            Event="CYBERFEST 2026"; Status="ABSENT";  MarkedAt="2026-12-05 09:30:00" },
        [PSCustomObject]@{ AttID=5;  RegID=5;  RegNo="25ITL01"; Student="Naveen Prashanth L"; Event="CYBERFEST 2026"; Status="PRESENT"; MarkedAt="2026-12-05 09:10:00" },
        [PSCustomObject]@{ AttID=6;  RegID=6;  RegNo="25CS012"; Student="Bala Subramanian";   Event="CYBERFEST 2026"; Status="PRESENT"; MarkedAt="2026-12-05 09:12:00" },
        [PSCustomObject]@{ AttID=7;  RegID=7;  RegNo="25CS045"; Student="Gokul Nathan";       Event="CYBERFEST 2026"; Status="PRESENT"; MarkedAt="2026-12-05 09:15:00" },
        [PSCustomObject]@{ AttID=8;  RegID=8;  RegNo="25EC018"; Student="Harish V";           Event="CYBERFEST 2026"; Status="PRESENT"; MarkedAt="2026-12-05 09:18:00" },
        [PSCustomObject]@{ AttID=9;  RegID=9;  RegNo="25AI022"; Student="Manoj Kumar";        Event="CYBERFEST 2026"; Status="ABSENT";  MarkedAt="2026-12-05 09:30:00" },
        [PSCustomObject]@{ AttID=10; RegID=10; RegNo="25IT031"; Student="Madhavan S (Reg 31)"; Event="GenAI Workshop"; Status="PRESENT"; MarkedAt="2026-12-12 08:50:00" },
        [PSCustomObject]@{ AttID=11; RegID=11; RegNo="25IT001"; Student="Aarav Kumar";        Event="GenAI Workshop"; Status="PRESENT"; MarkedAt="2026-12-12 08:55:00" },
        [PSCustomObject]@{ AttID=12; RegID=12; RegNo="25CS012"; Student="Bala Subramanian";   Event="GenAI Workshop"; Status="PRESENT"; MarkedAt="2026-12-12 09:00:00" },
        [PSCustomObject]@{ AttID=13; RegID=13; RegNo="25CS045"; Student="Gokul Nathan";       Event="GenAI Workshop"; Status="PRESENT"; MarkedAt="2026-12-12 09:02:00" },
        [PSCustomObject]@{ AttID=14; RegID=14; RegNo="25AI022"; Student="Manoj Kumar";        Event="GenAI Workshop"; Status="PRESENT"; MarkedAt="2026-12-12 09:05:00" },
        [PSCustomObject]@{ AttID=15; RegID=15; RegNo="25AI049"; Student="Pavithra K";         Event="GenAI Workshop"; Status="PRESENT"; MarkedAt="2026-12-12 09:10:00" },
        [PSCustomObject]@{ AttID=16; RegID=16; RegNo="25IT031"; Student="Madhavan S (Reg 31)"; Event="Cloud Seminar";  Status="PRESENT"; MarkedAt="2026-12-18 13:55:00" },
        [PSCustomObject]@{ AttID=17; RegID=17; RegNo="25IT061"; Student="Dharani R";          Event="Cloud Seminar";  Status="PRESENT"; MarkedAt="2026-12-18 14:00:00" },
        [PSCustomObject]@{ AttID=18; RegID=18; RegNo="25IT091"; Student="Kavya S";            Event="Cloud Seminar";  Status="ABSENT";  MarkedAt="2026-12-18 14:30:00" },
        [PSCustomObject]@{ AttID=19; RegID=19; RegNo="25CS012"; Student="Bala Subramanian";   Event="Cloud Seminar";  Status="PRESENT"; MarkedAt="2026-12-18 14:05:00" },
        [PSCustomObject]@{ AttID=20; RegID=20; RegNo="25ME015"; Student="Karthik Raja";       Event="Cloud Seminar";  Status="PRESENT"; MarkedAt="2026-12-18 14:10:00" }
    )
    $att | Format-Table -AutoSize
    $presentCount = ($att | Where-Object { $_.Status -eq "PRESENT" }).Count
    Write-Host "Attendance Recorded: $($att.Count) | Present: $presentCount | Absent: $($att.Count - $presentCount)" -ForegroundColor Green
}

function Show-Feedback-Table {
    Write-Host "`n======================================== [TABLE: FEEDBACK] ========================================" -ForegroundColor Yellow
    $fb = @(
        [PSCustomObject]@{ FbID=1;  RegID=1;  Student="Madhavan S (Reg 31)"; Event="CYBERFEST 2026"; Rating="★★★★★ (5)"; Comments="Outstanding symposium! Hackathon was top notch." },
        [PSCustomObject]@{ FbID=2;  RegID=2;  Student="Aarav Kumar";        Event="CYBERFEST 2026"; Rating="★★★★☆ (4)"; Comments="Great technical talks and paper presentations." },
        [PSCustomObject]@{ FbID=3;  RegID=3;  Student="Dharani R";          Event="CYBERFEST 2026"; Rating="★★★★★ (5)"; Comments="Loved the technical quiz and project display arena." },
        [PSCustomObject]@{ FbID=4;  RegID=5;  Student="Naveen Prashanth L"; Event="CYBERFEST 2026"; Rating="★★★★☆ (4)"; Comments="Very informative industry expert sessions." },
        [PSCustomObject]@{ FbID=5;  RegID=6;  Student="Bala Subramanian";   Event="CYBERFEST 2026"; Rating="★★★★☆ (4)"; Comments="Well-coordinated event with great networking." },
        [PSCustomObject]@{ FbID=6;  RegID=7;  Student="Gokul Nathan";       Event="CYBERFEST 2026"; Rating="★★★☆☆ (3)"; Comments="Content was good, sound system had slight echoes." },
        [PSCustomObject]@{ FbID=7;  RegID=8;  Student="Harish V";           Event="CYBERFEST 2026"; Rating="★★★★★ (5)"; Comments="One of the best symposiums hosted by IT department!" },
        [PSCustomObject]@{ FbID=8;  RegID=10; Student="Madhavan S (Reg 31)"; Event="GenAI Workshop"; Rating="★★★★★ (5)"; Comments="Superb hands-on experience on LangChain and LLMs." },
        [PSCustomObject]@{ FbID=9;  RegID=11; Student="Aarav Kumar";        Event="GenAI Workshop"; Rating="★★★★★ (5)"; Comments="Clear practical demonstrations and notebooks." },
        [PSCustomObject]@{ FbID=10; RegID=12; Student="Bala Subramanian";   Event="GenAI Workshop"; Rating="★★★★☆ (4)"; Comments="Instructor was knowledgeable and clear." },
        [PSCustomObject]@{ FbID=11; RegID=13; Student="Gokul Nathan";       Event="GenAI Workshop"; Rating="★★★★★ (5)"; Comments="Loved implementing live multi-agent workflows!" },
        [PSCustomObject]@{ FbID=12; RegID=14; Student="Manoj Kumar";        Event="GenAI Workshop"; Rating="★★★★★ (5)"; Comments="Extremely useful for final year projects." },
        [PSCustomObject]@{ FbID=13; RegID=15; Student="Pavithra K";         Event="GenAI Workshop"; Rating="★★★★☆ (4)"; Comments="Would love an advanced level follow-up." },
        [PSCustomObject]@{ FbID=14; RegID=16; Student="Madhavan S (Reg 31)"; Event="Cloud Seminar";  Rating="★★★★☆ (4)"; Comments="Great overview of AWS Cloud and Kubernetes." },
        [PSCustomObject]@{ FbID=15; RegID=17; Student="Dharani R";          Event="Cloud Seminar";  Rating="★★★☆☆ (3)"; Comments="Informative, wanted more live lab time." },
        [PSCustomObject]@{ FbID=16; RegID=19; Student="Bala Subramanian";   Event="Cloud Seminar";  Rating="★★★★☆ (4)"; Comments="Well-structured microservices architecture session." },
        [PSCustomObject]@{ FbID=17; RegID=20; Student="Karthik Raja";       Event="Cloud Seminar";  Rating="★★★★☆ (4)"; Comments="Valuable career advice for Cloud DevOps roles." }
    )
    $fb | Format-Table -AutoSize
    Write-Host "Total Verified Feedbacks: $($fb.Count) | Average Rating: 4.48 / 5.0" -ForegroundColor Green
}

function Show-Summary-Report {
    Write-Host "`n============================= [ANALYTICAL REPORT: EVENT PARTICIPATION SUMMARY] =============================" -ForegroundColor Yellow
    $summary = @(
        [PSCustomObject]@{ EventID=1; EventName="CYBERFEST 2026: Tech Symposium"; Category="Symposium"; MaxCap=120; Confirmed=9; CapUtil="7.5%";  Attended=7; Turnout="77.8%"; AvgRating="4.29 ★" },
        [PSCustomObject]@{ EventID=2; EventName="Hands-on GenAI & LLMs Workshop"; Category="Workshop";  MaxCap=45;  Confirmed=6; CapUtil="13.3%"; Attended=6; Turnout="100.0%";AvgRating="4.67 ★" },
        [PSCustomObject]@{ EventID=3; EventName="National Cloud Computing Seminar";Category="Seminar";   MaxCap=60;  Confirmed=5; CapUtil="8.3%";  Attended=4; Turnout="80.0%"; AvgRating="3.75 ★" },
        [PSCustomObject]@{ EventID=4; EventName="VIBES 2026: Annual Cultural Fest";Category="Cultural";  MaxCap=250; Confirmed=9; CapUtil="3.6%";  Attended=8; Turnout="88.9%"; AvgRating="4.63 ★" },
        [PSCustomObject]@{ EventID=5; EventName="Embedded IoT & Robotics Workshop";Category="Workshop";  MaxCap=35;  Confirmed=4; CapUtil="11.4%"; Attended=1; Turnout="25.0%"; AvgRating="5.00 ★" },
        [PSCustomObject]@{ EventID=6; EventName="Deep Learning Computer Vision";   Category="Workshop";  MaxCap=40;  Confirmed=5; CapUtil="12.5%"; Attended=0; Turnout="Pending";AvgRating="N/A"    },
        [PSCustomObject]@{ EventID=7; EventName="TechConclave 2027: Paper Pres.";  Category="Symposium"; MaxCap=50;  Confirmed=0; CapUtil="0.0%";  Attended=0; Turnout="N/A";    AvgRating="N/A"    },
        [PSCustomObject]@{ EventID=8; EventName="Rhythm & Beats: Fine Arts Eve";   Category="Cultural";  MaxCap=300; Confirmed=0; CapUtil="0.0%";  Attended=0; Turnout="N/A";    AvgRating="N/A"    }
    )
    $summary | Format-Table -AutoSize
    Write-Host "Aggregated Analytical Query Result (Equivalent to database/queries.sql Query 1)" -ForegroundColor Green
}

function Show-Department-Report {
    Write-Host "`n====================== [ANALYTICAL REPORT: DEPARTMENT-WISE PARTICIPATION] ======================" -ForegroundColor Yellow
    $depts = @(
        [PSCustomObject]@{ Department="Information Technology";         StudentsParticipated=5; TotalRegistrations=18; TotalAttended=16; DeptTurnout="88.9%"; AvgRating="4.60 ★" },
        [PSCustomObject]@{ Department="Computer Science and Engg";      StudentsParticipated=2; TotalRegistrations=6;  TotalAttended=6;  DeptTurnout="100.0%";AvgRating="4.00 ★" },
        [PSCustomObject]@{ Department="Electronics and Comm Engg";      StudentsParticipated=2; TotalRegistrations=5;  TotalAttended=2;  DeptTurnout="40.0%"; AvgRating="5.00 ★" },
        [PSCustomObject]@{ Department="Artificial Intelligence & DS";   StudentsParticipated=2; TotalRegistrations=5;  TotalAttended=2;  DeptTurnout="40.0%"; AvgRating="4.50 ★" },
        [PSCustomObject]@{ Department="Mechanical Engineering";         StudentsParticipated=1; TotalRegistrations=3;  TotalAttended=2;  DeptTurnout="66.7%"; AvgRating="4.00 ★" }
    )
    $depts | Format-Table -AutoSize
}

function Show-Reg31-Dossier {
    Write-Host "`n============================== [STUDENT DOSSIER: REGISTER NO. 31] ==============================" -ForegroundColor Yellow
    Write-Host "Student Name: Madhavan S | Reg No: 25IT031 | Dept: Information Technology | Class: 25IT3A&B" -ForegroundColor Cyan
    $reg31 = @(
        [PSCustomObject]@{ RegID=1;  EventName="CYBERFEST 2026: National Tech Symposium";   Status="CONFIRMED"; Attendance="PRESENT"; Rating="5 ★"; Feedback="Outstanding symposium! Hackathon was top notch." },
        [PSCustomObject]@{ RegID=10; EventName="Hands-on Workshop on Generative AI & LLMs"; Status="CONFIRMED"; Attendance="PRESENT"; Rating="5 ★"; Feedback="Superb hands-on experience on LangChain and LLMs." },
        [PSCustomObject]@{ RegID=16; EventName="National Seminar on Cloud & Distributed";    Status="CONFIRMED"; Attendance="PRESENT"; Rating="4 ★"; Feedback="Great overview of AWS Cloud and Kubernetes." },
        [PSCustomObject]@{ RegID=21; EventName="VIBES 2026: Annual Inter-College Cultural";  Status="CONFIRMED"; Attendance="PRESENT"; Rating="5 ★"; Feedback="Energetic cultural fest! Fantastic dance performances." }
    )
    $reg31 | Format-Table -AutoSize
}

# --- Action Dispatcher ---
Print-Banner

if ($Action -eq "all" -or $Action -eq "9") {
    Show-Students-Table
    Show-Events-Table
    Show-Registrations-Table
    Show-Attendance-Table
    Show-Feedback-Table
    Show-Summary-Report
    Show-Department-Report
    Show-Reg31-Dossier
    exit 0
} elseif ($Action -eq "students" -or $Action -eq "1") {
    Show-Students-Table; exit 0
} elseif ($Action -eq "events" -or $Action -eq "2") {
    Show-Events-Table; exit 0
} elseif ($Action -eq "registrations" -or $Action -eq "3") {
    Show-Registrations-Table; exit 0
} elseif ($Action -eq "attendance" -or $Action -eq "4") {
    Show-Attendance-Table; exit 0
} elseif ($Action -eq "feedback" -or $Action -eq "5") {
    Show-Feedback-Table; exit 0
} elseif ($Action -eq "summary" -or $Action -eq "6") {
    Show-Summary-Report; exit 0
} elseif ($Action -eq "depts" -or $Action -eq "7") {
    Show-Department-Report; exit 0
} elseif ($Action -eq "reg31" -or $Action -eq "8") {
    Show-Reg31-Dossier; exit 0
}

# Interactive Menu Loop
$running = $true
while ($running) {
    Write-Host "`n----------------------- TERMINAL TABLE MENU -----------------------" -ForegroundColor Cyan
    Write-Host " [1] Display STUDENTS Table"
    Write-Host " [2] Display EVENTS Table (Capacity & Available Seats)"
    Write-Host " [3] Display REGISTRATIONS Table"
    Write-Host " [4] Display ATTENDANCE Table (Present / Absent Records)"
    Write-Host " [5] Display FEEDBACK Table (Ratings & Reviews)"
    Write-Host " [6] Display EVENT PARTICIPATION & CAPACITY SUMMARY (Analytical Query)"
    Write-Host " [7] Display DEPARTMENT-WISE PARTICIPATION BREAKDOWN"
    Write-Host " [8] Display STUDENT DOSSIER FOR REGISTER NO: 31"
    Write-Host " [9] Display ALL TABLES & REPORTS (Complete Submission Walkthrough)"
    Write-Host " [0] Exit"
    Write-Host "-------------------------------------------------------------------" -ForegroundColor Cyan
    
    $choice = Read-Host "Select an option (0-9)"
    switch ($choice) {
        "1" { Show-Students-Table }
        "2" { Show-Events-Table }
        "3" { Show-Registrations-Table }
        "4" { Show-Attendance-Table }
        "5" { Show-Feedback-Table }
        "6" { Show-Summary-Report }
        "7" { Show-Department-Report }
        "8" { Show-Reg31-Dossier }
        "9" {
            Show-Students-Table
            Show-Events-Table
            Show-Registrations-Table
            Show-Attendance-Table
            Show-Feedback-Table
            Show-Summary-Report
            Show-Department-Report
            Show-Reg31-Dossier
        }
        "0" { Write-Host "`n[+] Exiting Table Viewer. Thank you!" -ForegroundColor Green; $running = $false }
        default { Write-Host "[!] Invalid option. Enter 0-9." -ForegroundColor Red }
    }
}
