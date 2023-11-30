package com.example.csc311capstoneproject;

public class Account {
    String name;
    String DOB;
    String email;
    String password;
    String phone;
    int id;
    int income;
    public Account() {
        this.name = "";
        this.DOB = "";
        this.email = "";
        this.password = "";
        this.phone = "";
        this.id = 0;
        this.income = 0;
    }
    public Account(String name, String DOB, String email, String password, String phone, int id, int income) {
        this.name = name;
        this.DOB = DOB;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.id = id;
        this.income = income;
    }
}
