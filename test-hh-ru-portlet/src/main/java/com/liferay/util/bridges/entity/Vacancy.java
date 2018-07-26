package com.liferay.util.bridges.entity;

import java.sql.Date;

public class Vacancy {

    private String name;
    private String nameOrganization;
    private Date date;
    private String salary;
    private int salaryInt;
    private String area;
    private String profArea;

    public Vacancy(String name, String nameOrganization, Date date, String salary, int salaryInt, String area,
                   String profArea) {
        this.name = name;
        this.nameOrganization = nameOrganization;
        this.date = date;
        this.salary = salary;
        this.salaryInt = salaryInt;
        this.area = area;
        this.profArea = profArea;
    }

    public Vacancy() {
    }

    public int getSalaryInt() {
        return salaryInt;
    }

    public void setSalaryInt(int salaryInt) {
        this.salaryInt = salaryInt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameOrganization() {
        return nameOrganization;
    }

    public void setNameOrganization(String nameOrganization) {
        this.nameOrganization = nameOrganization;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProfArea() {
        return profArea;
    }

    public void setProfArea(String profArea) {
        this.profArea = profArea;
    }
}

