package com.example.myapplication;

public class HelperClass2 {


    String email, password;

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

    public HelperClass2(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public HelperClass2() {
    }
}
