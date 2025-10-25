# attendence-trqacker
# Attendance Tracker – Java Console Project

A simple Java-only mini project that tracks student attendance using CSV files. There is no database or GUI. This project is perfect for college submissions or for demonstrating Java fundamentals.

## Features
- Add students (auto-increment ID)  
- List all students  
- Mark daily attendance (P/A)  
- View attendance by date  
- Generate monthly attendance reports (with % attendance)  
- Export monthly report to CSV  
- Pure Java SE; no external libraries  

## Project Structure
```
AttendanceTracker/
│
├── src/
│   └── com/attendance/
│       ├── App.java                    # Main console app
│       ├── model/
│       │   ├── Student.java
│       │   └── AttendanceEntry.java
│       ├── service/
│       │   └── AttendanceService.java
│       ├── store/
│       │   └── DataStore.java
│       └── util/
│           └── AttendanceSummary.java
│
├── data/                               # Auto-created data folder
│   ├── students.csv                    # Student records
│   ├── attendance.csv                  # Attendance data
│   └── attendance-YYYY-MM.csv          # Exported reports
│
└── README.md
```

## How to Compile & Run
### Windows (PowerShell / CMD)
```powershell
cd AttendanceTracker
mkdir out
javac -d out src/com/attendance/**/*.java
java -cp out com.attendance.App
```
If your shell doesn’t support `**` wildcard, run:
```powershell
for /R %f in (src\*.java) do @echo %f >> sources.txt
javac -d out @sources.txt
java -cp out com.attendance.App
```

### Linux / macOS
```bash
cd AttendanceTracker
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out com.attendance.App
```

## CSV File Formats
**students.csv**
```
id,name,section
1001,John Doe,CSE-A
1002,Jane Smith,CSE-A
```
**attendance.csv**
```
date,studentId,present
2025-10-25,1001,true
2025-10-25,1002,false
```
**attendance-YYYY-MM.csv**
```
date,studentId,studentName,section,present
2025-10-25,1001,John Doe,CSE-A,true
```

## Example Console Flow
```
==== Attendance Tracker (Java Console) ====
Menu:
 1) Add student
 2) List students
 3) Mark attendance for a date
 4) View attendance by date
 5) Student monthly report
 6) Search students by name
 7) Export attendance report (CSV)
 0) Exit
Choose: 1
Student name: Gautham
Section (e.g., CSE-A): AIML-A
Added: 1001: Gautham (AIML-A)
```

## Notes
- Works fully offline.
- Data is stored in simple .csv files inside data/.
- You can delete the data/ folder to reset all data.
- You can extend it easily with a Swing GUI or MySQL later.

# Developer
**Created by:** Rachamallu Gautham Reddy  
**Language:** Java SE 17+  
**Type:** Console-based Mini Project  
**Usage:** Academic / College Submission Ready
