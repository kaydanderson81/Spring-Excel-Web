package com.excel.app.model;


import java.util.List;

public class Employee {

    private String name;
    private String contractFrom;
    private String contractTo;
    private List<Project> projects;

    public Employee() {

    }

    public Employee(String name, String contractFrom, String contractTo, List<Project> projects) {
        this.name = name;
        this.projects = projects;
        this.contractFrom = contractFrom;
        this.contractTo = contractTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContractFrom() {
        return contractFrom;
    }

    public void setContractFrom(String contractFrom) {
        this.contractFrom = contractFrom;
    }

    public String getContractTo() {
        return contractTo;
    }

    public void setContractTo(String contractTo) {
        this.contractTo = contractTo;
    }
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", projects=" + projects +
                ", contractFrom='" + contractFrom + '\'' +
                ", contractTo='" + contractTo + '\'' +
                '}';
    }
}
