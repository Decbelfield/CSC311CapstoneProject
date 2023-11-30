package com.example.csc311capstoneproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ConnectionDatabase {

    final static String DB_NAME = "CSC311_BD_TEMP";
    MyLogger lg = new MyLogger();
    final static String SQL_SERVER_URL = "jdbc:mysql://csc311server2001.mariadb.database.azure.com";
    final static String DB_URL = "jdbc:mysql://csc311server2001.mariadb.database.azure.com/" + DB_NAME;
    final static String USERNAME = "decbelfield@csc311server2001";
    final static String PASSWORD = "DataBased01";

    private final ObservableList<Account> data = FXCollections.observableArrayList();

    public ObservableList<Account> getData() {
        connectToDatabase();
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users ");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (!resultSet.isBeforeFirst()) {
                lg.makeLog("No data");
            }

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String DOB = resultSet.getString("DOB");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                int id = resultSet.getInt("id");
                int income = resultSet.getInt("income");
                String imageURL = resultSet.getString("imageURL");
                data.add(new Account(name, DOB, email, password, phone, address, id, income, imageURL));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public boolean connectToDatabase() {
        boolean hasRegisteredUsers = false;

        try (Connection conn = DriverManager.getConnection(SQL_SERVER_URL, USERNAME, PASSWORD);
             Statement statement = conn.createStatement()) {

            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME + "");

            try (Connection dbConn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                 Statement dbStatement = dbConn.createStatement()) {

                String sql = "CREATE TABLE IF NOT EXISTS users (" +
                        "id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                        "name VARCHAR(200) NOT NULL," +
                        "DOB VARCHAR(200)," +
                        "email VARCHAR(200) NOT NULL UNIQUE," +
                        "password VARCHAR(200)," +
                        "phone VARCHAR(200)," +
                        "address VARCHAR(200)," +
                        "income INT(10)," +
                        "imageURL VARCHAR(200))";
                dbStatement.executeUpdate(sql);
            }

            try (Connection dbConn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                 Statement dbStatement = dbConn.createStatement();
                 ResultSet resultSet = dbStatement.executeQuery("SELECT COUNT(*) FROM users")) {

                if (resultSet.next()) {
                    int numUsers = resultSet.getInt(1);
                    hasRegisteredUsers = numUsers > 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasRegisteredUsers;
    }

    public void queryUserByName(String name) {
        connectToDatabase();
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE last_name = ?")) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String firstName = resultSet.getString("name");
                    String DOB = resultSet.getString("DOB");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String address = resultSet.getString("address");
                    int id = resultSet.getInt("id");
                    int income = resultSet.getInt("income");


                    lg.makeLog(" Name: " + name + ",Date of Birth: " +  DOB + ",Email: "
                            + DOB + ", Password: " + password + ", Address: " + address +", ID: " + id + ", Income:" + income);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
