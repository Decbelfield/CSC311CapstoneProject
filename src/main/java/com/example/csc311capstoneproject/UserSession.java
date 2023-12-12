package com.example.csc311capstoneproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import static com.google.cloud.sql.jdbc.internal.ConnectionProperty.PASSWORD;
import static db.ConnectionDatabase.USERNAME;
/**
 * Class representing a user session in the application.
 * This class manages the current user's session data including login credentials and privileges.
 */
public class UserSession {

    private static UserSession instance;

    private String userName;

    private String password;
    private String privileges;
    private static final String USER_DATA_FILE = "user_data.txt";

    private UserSession(String userName, String password, String privileges) {
        this.userName = userName;
        this.password = password;
        this.privileges = privileges;
        Preferences userPreferences = Preferences.userRoot();
        userPreferences.put("USERNAME",userName);
        userPreferences.put("PASSWORD",password);
        userPreferences.put("PRIVILEGES",privileges);
    }

    /**
     * Private constructor for UserSession.
     * It stores the user's session data and preferences.
     *
     * @param userName The username of the user.
     * @param password The password of the user.
     * @param privileges The privileges of the user.
     */

    public static UserSession getInstance(String userName,String password, String privileges) {
        if(instance == null) {
            instance = new UserSession(userName, password, privileges);
        }
        return instance;
    }/**
     * Overloaded method to get the singleton instance of UserSession without privileges.
     * If the instance is null, it creates a new instance with default privileges.
     *
     * @param userName The username of the user.
     * @param password The password of the user.
     * @return The singleton instance of UserSession.
     */

    public static UserSession getInstace(String userName,String password) {
        if(instance == null) {
            instance = new UserSession(userName, password, "NONE");
        }
        return instance;
    }

    /**
     * Validates if the provided username and password are correct.
     * Reads user data from a file and checks if the credentials match.
     *
     * @param UserNameField The username entered by the user.
     * @param PasswordField The password entered by the user.
     * @return true if the user is valid, false otherwise.
     */
    public static boolean isUserValid(String UserNameField, String PasswordField) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                String storedUsername = userData[0].split(":")[1].trim();
                String storedPassword = userData[1].split(":")[1].trim();

                // Check if entered username and password match stored values
                if (UserNameField.equals(storedUsername) && PasswordField.equals(storedPassword)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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

    /**
     * Saves the user's data into the database.
     * Inserts the username and password into the users table.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public static void saveUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection( USERNAME)) {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);


                ((PreparedStatement) statement).executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace(); //handle database errors
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Clears the current user session data.
     */
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
