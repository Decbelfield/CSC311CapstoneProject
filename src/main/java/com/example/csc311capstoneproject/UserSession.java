package com.example.csc311capstoneproject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.prefs.Preferences;

import static com.google.cloud.sql.jdbc.internal.ConnectionProperty.PASSWORD;
import static db.ConnectionDatabase.USERNAME;

public class UserSession {

    private static UserSession instance;

    private String userName;

    private String password;
    private String privileges;

    private UserSession(String userName, String password, String privileges) {
        this.userName = userName;
        this.password = password;
        this.privileges = privileges;
        Preferences userPreferences = Preferences.userRoot();
        userPreferences.put("USERNAME",userName);
        userPreferences.put("PASSWORD",password);
        userPreferences.put("PRIVILEGES",privileges);
    }



    public static UserSession getInstace(String userName,String password, String privileges) {
        if(instance == null) {
            instance = new UserSession(userName, password, privileges);
        }
        return instance;
    }

    public static UserSession getInstace(String userName,String password) {
        if(instance == null) {
            instance = new UserSession(userName, password, "NONE");
        }
        return instance;
    }
    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPrivileges() {
        return this.privileges;
    }
    public static void saveUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection( USERNAME, PASSWORD)) {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);


                ((PreparedStatement) statement).executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace(); //handle database errors
                throw new RuntimeException(e);
            }
        }
    }
    public void cleanUserSession() {
        this.userName = "";// or null
        this.password = "";
        this.privileges = "";// or null
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userName='" + this.userName + '\'' +
                ", privileges=" + this.privileges +
                '}';
    }
}
