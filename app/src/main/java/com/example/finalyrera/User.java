package com.example.finalyrera;

public class User {

    private String email; // Field for storing the user's email

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {
    }

    // Parameterized constructor
    public User(String email) {
        this.email = email;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }
}
