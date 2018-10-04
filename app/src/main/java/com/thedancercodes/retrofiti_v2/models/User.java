package com.thedancercodes.retrofiti_v2.models;

/**
 * Created by TheDancerCodes on 03/10/2018.
 */

// User model is what we are getting back from the server
public class User {

    private int id;
    private String email;
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
