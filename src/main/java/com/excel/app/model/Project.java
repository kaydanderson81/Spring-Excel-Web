package com.excel.app.model;

import java.util.List;

public class Project {

    private String projectId;
    private String projectName;
    private String startDate;
    private String endDate;
    private double monthFrom;
    private double monthTo;
    private double projectMonths;

    private List<Employee> employees;

    public Project() {

    }

    public Project(String projectId, String projectName, String startDate, String endDate, double monthFrom, double monthTo, double projectMonths, List<Employee> employees) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthFrom = monthFrom;
        this.monthTo = monthTo;
        this.projectMonths = projectMonths;
        this.employees = employees;
    }

    public Project(String projectId, String projectName, String startDate, String endDate, double projectMonths, List<Employee> employees) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectMonths = projectMonths;
        this.employees = employees;
    }

    public Project(String projectId, String projectName, String startDate, String endDate, double monthFrom, double monthTo, double projectMonths) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthFrom = monthFrom;
        this.monthTo = monthTo;
        this.projectMonths = projectMonths;
    }


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public double getMonthFrom() {
        return monthFrom;
    }

    public void setMonthFrom(double monthFrom) {
        this.monthFrom = monthFrom;
    }

    public double getMonthTo() {
        return monthTo;
    }

    public void setMonthTo(double monthTo) {
        this.monthTo = monthTo;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getProjectMonths() {
        return projectMonths;
    }

    public void setProjectMonths(double projectMonths) {
        this.projectMonths = projectMonths;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", monthFrom='" + monthFrom + '\'' +
                ", monthTo='" + monthTo + '\'' +
                ", projectMonths=" + projectMonths +
                ", employee=" + employees +
                '}';
    }

    public boolean isEmpty() {
        return projectId != null && projectName != null && startDate != null && endDate != null && monthFrom > 0 && monthTo > 0 && projectMonths > 0;
    }
}
