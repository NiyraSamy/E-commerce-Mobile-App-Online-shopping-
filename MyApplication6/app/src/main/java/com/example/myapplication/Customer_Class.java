package com.example.myapplication;

public class Customer_Class {
    private int customer_id;
    private String customer_name,customer_mail, customer_password, customer_gender, customer_Jop,customer_BirthDate;

    public Customer_Class() {
    }

    public Customer_Class(String customer_name, String customer_mail, String customer_password, String customer_gender, String customer_Jop, String customer_BirthDate) {
        this.customer_name = customer_name;
        this.customer_mail=customer_mail;
        this.customer_password = customer_password;
        this.customer_gender = customer_gender;
        this.customer_Jop = customer_Jop;
        this.customer_BirthDate = customer_BirthDate;
    }

    public int getId() {
        return customer_id;
    }

    public String getMail() {
        return customer_mail;
    }

    public void setMail(String customer_mail) {
        this.customer_mail = customer_mail;
    }

    public void setId(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustName() {
        return customer_name;
    }

    public void setCustName(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getPassword() {
        return customer_password;
    }

    public void setPassword(String customer_password) {
        this.customer_password = customer_password;
    }

    public String getGender() {
        return customer_gender;
    }

    public void setGender(String customer_gender) {
        this.customer_gender = customer_gender;
    }

    public String getCustJop() {
        return customer_Jop;
    }

    public void setCustJop(String customer_Jop) {
        this.customer_Jop = customer_Jop;
    }

    public String getCustBirthDate() {
        return customer_BirthDate;
    }

    public void setCustBirthDate(String customer_BirthDate) {
        this.customer_BirthDate = customer_BirthDate;
    }
}
