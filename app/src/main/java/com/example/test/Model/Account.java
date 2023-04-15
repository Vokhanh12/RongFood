package com.example.test.Model;

public class Account {

    private int id;
    private String email;
    private String password;

    public Account(String Email,String Password){
        this.email=Email;
        this.password=Password;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {

        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String Email) {this.email = Email;}
}
