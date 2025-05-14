package de.seminar.objects;

public class Credentials {
    String username;
    String password;

    public Credentials() {}  // No-arg constructor

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
