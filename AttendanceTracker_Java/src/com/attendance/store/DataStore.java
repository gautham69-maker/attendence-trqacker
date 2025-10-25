package com.attendance.store;
import com.attendance.model.*;
import java.io.*;import java.nio.file.*;import java.time.*;import java.util.*;
public class DataStore {
    private static final Path DATA=Paths.get("data");
    private static final Path ST=DATA.resolve("students.csv");
    private static final Path AT=DATA.resolve("attendance.csv");
    public void ensureFiles(){try{if(!Files.exists(DATA))Files.createDirectories(DATA);if(!Files.exists(ST))Files.writeString(ST,"id,name,section\n");if(!Files.exists(AT))Files.writeString(AT,"date,studentId,present\n");}catch(IOException e){throw new RuntimeException(e);}}
    public List<Student> loadStudents(){List<Student>l=new ArrayList<>();try(BufferedReader br=Files.newBufferedReader(ST)){String line;boolean h=true;while((line=br.readLine())!=null){if(h){h=false;continue;}if(line.isBlank())continue;String[]t=line.split(",");l.add(new Student(Integer.parseInt(t[0]),t[1],t[2]));}}catch(Exception ignored){}return l;}
    public List<AttendanceEntry> loadAttendance(){List<AttendanceEntry>l=new ArrayList<>();try(BufferedReader br=Files.newBufferedReader(AT)){String line;boolean h=true;while((line=br.readLine())!=null){if(h){h=false;continue;}if(line.isBlank())continue;String[]t=line.split(",");l.add(new AttendanceEntry(LocalDate.parse(t[0]),Integer.parseInt(t[1]),Boolean.parseBoolean(t[2])));}}catch(Exception ignored){}return l;}
    public void saveStudents(List<Student>s){try(PrintWriter out=new PrintWriter(Files.newBufferedWriter(ST))){out.println("id,name,section");for(Student st:s)out.println(st.id()+","+st.name()+","+st.section());}catch(Exception ignored){}}
    public void saveAttendance(List<AttendanceEntry>a){try(PrintWriter out=new PrintWriter(Files.newBufferedWriter(AT))){out.println("date,studentId,present");for(AttendanceEntry e:a)out.println(e.date()+","+e.studentId()+","+e.present());}catch(Exception ignored){}}
    public String exportMonthlyReport(List<AttendanceEntry> e,List<Student> s,YearMonth ym){Path f=DATA.resolve("attendance-"+ym+".csv");try(PrintWriter out=new PrintWriter(Files.newBufferedWriter(f))){out.println("date,studentId,name,section,present");for(var x:e){if(YearMonth.from(x.date()).equals(ym)){Student st=s.stream().filter(y->y.id()==x.studentId()).findFirst().orElse(null);out.println(x.date()+","+x.studentId()+","+(st!=null?st.name():"?")+","+(st!=null?st.section():"?")+","+x.present());}}}catch(Exception ex){throw new RuntimeException(ex);}return f.toString();}
}
