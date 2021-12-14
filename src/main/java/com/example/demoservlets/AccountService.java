package com.example.demoservlets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AccountService {
    private final DatabaseConnectivity dbc;
    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;

    public AccountService() {
        this.dbc = new DatabaseConnectivity();
        this.loginToProfile = new HashMap<>();
        this.sessionIdToProfile = new HashMap<>();

        try {
            ResultSet rs = dbc.getStatement().executeQuery("SELECT * FROM `users`");
            while (rs.next()) {
                loginToProfile.put(
                        rs.getString(1),
                        new UserProfile(rs.getString(1),
                                rs.getString(3),
                                rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getLogin().toLowerCase(Locale.ROOT), userProfile);
        try {
            dbc.getStatement().executeUpdate(
                    String.format(
                            "INSERT INTO `users` (`login`, `email`, `password`) VALUES ('%s','%s','%s');",
                            userProfile.getLogin(),
                            userProfile.getEmail(),
                            userProfile.getPass()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login.toLowerCase(Locale.ROOT));
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
