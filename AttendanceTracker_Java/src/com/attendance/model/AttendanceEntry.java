package com.attendance.model;
import java.time.LocalDate;
public record AttendanceEntry(LocalDate date, int studentId, boolean present) {}
