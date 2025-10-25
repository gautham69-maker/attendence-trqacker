package com.attendance;
import com.attendance.model.Student;
import com.attendance.service.AttendanceService;
import com.attendance.store.DataStore;
import com.attendance.util.AttendanceSummary;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class App {
    private final Scanner in = new Scanner(System.in);
    private final DataStore store = new DataStore();
    private final AttendanceService service = new AttendanceService(store);
    public static void main(String[] args) { new App().run(); }
    void run() {
        System.out.println("\n==== Attendance Tracker (Java Console) ====");
        store.ensureFiles();
        service.loadAll();
        while (true) {
            System.out.println("\nMenu:\n 1) Add student\n 2) List students\n 3) Mark attendance\n 4) View attendance by date\n 5) Student monthly report\n 6) Search students\n 7) Export report\n 0) Exit");
            System.out.print("Choose: ");
            String c = in.nextLine().trim();
            switch (c) {
                case "1" -> addStudent();
                case "2" -> listStudents();
                case "3" -> markAttendance();
                case "4" -> viewByDate();
                case "5" -> studentMonthlyReport();
                case "6" -> searchStudents();
                case "7" -> exportReport();
                case "0" -> { System.out.println("Bye!"); return; }
                default -> System.out.println("Invalid.");
            }
        }
    }
    private void addStudent() {
        System.out.print("Student name: ");
        String name = in.nextLine().trim();
        System.out.print("Section: ");
        String sec = in.nextLine().trim();
        Student s = service.addStudent(name, sec);
        System.out.println("Added: " + s);
    }
    private void listStudents() {
        var list = service.getAllStudents();
        if (list.isEmpty()) { System.out.println("No students."); return; }
        for (Student s : list) System.out.println(s);
    }
    private void markAttendance() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        LocalDate d = LocalDate.parse(in.nextLine().trim());
        var list = service.getAllStudents();
        for (Student s : list) {
            System.out.print(s.name() + " (p/a): ");
            String a = in.nextLine().trim().toLowerCase();
            service.setAttendance(d, s.id(), a.startsWith("p"));
        }
        service.saveAll();
        System.out.println("Saved.");
    }
    private void viewByDate() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        LocalDate d = LocalDate.parse(in.nextLine().trim());
        var map = service.getAttendanceByDate(d);
        for (Student s : service.getAllStudents())
            System.out.println(s.name() + " -> " + (map.getOrDefault(s.id(), false) ? "Present" : "Absent"));
    }
    private void studentMonthlyReport() {
        System.out.print("Student ID: ");
        int id = Integer.parseInt(in.nextLine().trim());
        System.out.print("Month (YYYY-MM): ");
        YearMonth ym = YearMonth.parse(in.nextLine().trim());
        AttendanceSummary sum = service.getMonthlySummary(id, ym);
        System.out.println("Total: " + sum.totalDays() + " Present: " + sum.presentDays() + " Absent: " + sum.absentDays());
    }
    private void searchStudents() {
        System.out.print("Search: ");
        String q = in.nextLine().trim().toLowerCase();
        for (Student s : service.getAllStudents()) if (s.name().toLowerCase().contains(q)) System.out.println(s);
    }
    private void exportReport() {
        System.out.print("Month (YYYY-MM): ");
        YearMonth ym = YearMonth.parse(in.nextLine().trim());
        System.out.println("Exported: " + service.exportMonthlyReport(ym));
    }
}
