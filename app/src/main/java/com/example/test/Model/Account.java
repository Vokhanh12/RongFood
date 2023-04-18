package com.example.test.Model;

public class Account {

    private String _email;
    private String _password;

    public Account(String Email,String Password){
        this._email=Email;
        this._password=Password;
    }

    public String getPassword() {

        return _password;
    }

    public String getEmail() {
        return _email;
    }


    public void setPassword(String password) {
        this._password = password;
    }

    public void setEmail(String Email) {this._email = Email;}
}
