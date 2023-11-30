package com.example.csc311capstoneproject;

public class Account {
    String name;
    String DOB;
    String email;
    String password;
    String phone;
    String address;
    int id;
    int income;
    String imageURL;
    // Constructor for all the variables needed for the Account
    public Account() {
        this.name = "";
        this.DOB = "";
        this.email = "";
        this.password = "";
        this.phone = "";
        this.address = "";
        this.id = 0;
        this.income = 0;
        this.imageURL = "";

    }
    public Account(String name, String DOB, String email, String password, String phone,String address, int id, int income, String imageURL) {
        this.name = name;
        this.DOB = DOB;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.id = id;
        this.income = income;
        this.imageURL = imageURL;
    }

    //Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
