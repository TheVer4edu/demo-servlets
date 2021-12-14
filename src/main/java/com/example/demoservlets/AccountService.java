package com.example.demoservlets;

import com.example.demoservlets.orm.User;
import com.example.demoservlets.orm.UsersDAO;
import com.example.demoservlets.orm.UsersDAOImplementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AccountService {
    //private final DatabaseConnectivity dbc;
    private static UsersDAO dao = new UsersDAOImplementation();
    //private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;

    public AccountService() {
/*        this.dbc = new DatabaseConnectivity();
        this.loginToProfile = new HashMap<>();*/
        this.sessionIdToProfile = new HashMap<>();
/*
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
        }*/
    }

    public void addNewUser(UserProfile userProfile) {
        dao.add(new User(userProfile.getLogin(), userProfile.getPass(), userProfile.getEmail()));

/*        loginToProfile.put(userProfile.getLogin().toLowerCase(Locale.ROOT), userProfile);
        try {
            dbc.getStatement().executeUpdate(
                    String.format(
                            "INSERT INTO `users` (`login`, `email`, `password`) VALUES ('%s','%s','%s');",
                            userProfile.getLogin(),
                            userProfile.getEmail(),
                            userProfile.getPass()));
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    public UserProfile getUserByLogin(String login) {
        String finalLogin = login.toLowerCase(Locale.ROOT);
        UserProfile result = null;
        try {
            User user =  dao.getAll().stream().filter(x -> x.getLogin().toLowerCase(Locale.ROOT).equals(finalLogin)).findFirst().get();
            result = new UserProfile(user.getLogin(), user.getPassword(), user.getEmail());
        }
        catch (Exception e) {}
        return result;

        //  return loginToProfile.get(login.toLowerCase(Locale.ROOT));
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
