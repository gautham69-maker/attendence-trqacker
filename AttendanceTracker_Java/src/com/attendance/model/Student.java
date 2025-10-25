package com.attendance.model;
public record Student(int id, String name, String section) {
    public String toString() { return id + ": " + name + " (" + section + ")"; }
}