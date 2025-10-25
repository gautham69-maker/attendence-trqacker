package com.attendance.util;
public class AttendanceSummary {
    private final int total, present;
    public AttendanceSummary(int t, int p){ total=t; present=p; }
    public int totalDays(){ return total; }
    public int presentDays(){ return present; }
    public int absentDays(){ return Math.max(0,total-present); }
}
