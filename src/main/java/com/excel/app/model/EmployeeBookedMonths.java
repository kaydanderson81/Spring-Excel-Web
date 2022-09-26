package com.excel.app.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class EmployeeBookedMonths {
    private String employee;
    private List<List<LocalDate>> currentMonths;
    private List<LocalDate> overlapMonths;

    public EmployeeBookedMonths(String employee, List<List<LocalDate>> currentMonths, List<LocalDate> overlapMonths) {
        this.employee = employee;
        this.currentMonths = currentMonths;
        this.overlapMonths = overlapMonths;
    }

    public EmployeeBookedMonths() {}


    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public List<List<LocalDate>> getCurrentMonths() {
        return currentMonths;
    }

    public void setCurrentMonths(List<List<LocalDate>> currentMonths) {
        this.currentMonths = currentMonths;
    }

    public List<LocalDate> getOverlapMonths() {
        return overlapMonths;
    }

    public void setOverlapMonths(List<LocalDate> overlapMonths) {
        this.overlapMonths = overlapMonths;
    }

    @Override
    public String toString() {
        return "EmployeeBookedMonths{" +
                "employee='" + employee + '\'' +
                ", currentMonths=" + currentMonths +
                ", overlapMonths=" + overlapMonths +
                '}';
    }
}
