package com.example.test.Model;

public class Account {

    private int id;
    private String Username;
    private String Password;

    public Account(){

    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return Password;
    }

    public String getUsername() {
        return Username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
