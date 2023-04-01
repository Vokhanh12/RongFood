package com.example.test.Model;

public class Account {

    private int id;
    private String username;
    private String password;

    public Account(String Username,String Password){
        this.username=Username;
        this.password=Password;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {

        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        password = password;
    }

    public void setUsername(String username) {
        username = username;
    }
}
