package com.thedancercodes.retrofiti_v2.models;

/**
 * Created by TheDancerCodes on 03/10/2018.
 */

// Use this class for logging in
public class Login {
    private String email;
    private String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
