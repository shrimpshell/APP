package com.example.hsinhwang.shrimpshell.Classes;

import java.io.Serializable;

public class Employees implements Serializable {
    private int employeeId;
    private String name, employeeCode, email, gender, phone, address, password;

    public Employees(String name, String employeeCode, String email, String password, String gender, String phone, String address) {
        this.name = name;
        this.employeeCode = employeeCode;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
