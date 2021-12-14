package com.example.demoservlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserProfile {
    private final String login, pass, email;

    public UserProfile(String login, String pass, String email) {
        this.login = login;
        this.pass = pass;
        this.email = email;
    }

    public UserProfile(String login) {
        this.login = login;
        this.pass = login;
        this.email = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }

    public String getFullPath() throws IOException {
        String userHomeDirectory = "/home/" + getLogin();

        if( !Files.isDirectory(Paths.get(userHomeDirectory)) )
            Files.createDirectories(Paths.get(userHomeDirectory));

        return userHomeDirectory + "/";
    }
}
