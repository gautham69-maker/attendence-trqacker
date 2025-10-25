package com.attendance.service;
import com.attendance.model.*;
import com.attendance.store.DataStore;
import com.attendance.util.AttendanceSummary;
import java.time.*;
import java.util.*;
public class AttendanceService {
    private final DataStore store;
    private final List<Student> students=new ArrayList<>();
    private final List<AttendanceEntry> entries=new ArrayList<>();
    public AttendanceService(DataStore s){store=s;}
    public void loadAll(){students.addAll(store.loadStudents());entries.addAll(store.loadAttendance());}
    public void saveAll(){store.saveStudents(students);store.saveAttendance(entries);}
    public Student addStudent(String n,String sec){int id=students.stream().mapToInt(Student::id).max().orElse(1000)+1;Student s=new Student(id,n,sec);students.add(s);saveAll();return s;}
    public List<Student> getAllStudents(){return students;}
    public void setAttendance(LocalDate d,int id,boolean p){entries.removeIf(e->e.studentId()==id&&e.date().equals(d));entries.add(new AttendanceEntry(d,id,p));}
    public Map<Integer,Boolean> getAttendanceByDate(LocalDate d){Map<Integer,Boolean>m=new HashMap<>();for(var e:entries)if(e.date().equals(d))m.put(e.studentId(),e.present());return m;}
    public AttendanceSummary getMonthlySummary(int id,YearMonth ym){int t=0,p=0;for(var e:entries){if(e.studentId()==id&&YearMonth.from(e.date()).equals(ym)){t++;if(e.present())p++;}}return new AttendanceSummary(t,p);}
    public String exportMonthlyReport(YearMonth ym){return store.exportMonthlyReport(entries,students,ym);}
}
