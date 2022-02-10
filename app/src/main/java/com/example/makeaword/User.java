package com.example.makeaword;

public class User {
    private String email, log, pas;
    public User(){}

    public User(String email, String log, String pas) {
        this.email = email;
        this.log = log;
        this.pas = pas;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getPas() {
        return pas;
    }

    public void setPas(String pas) {
        this.pas = pas;
    }
}
