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



    // This is the point where i havent altered much


    public void queryUserByLastName(String name) {
        connectToDatabase();
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE name = ?")) {

            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
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
    public void listAllUsers() {
        connectToDatabase();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM users ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String DOB = resultSet.getString("DOB");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String address = resultSet.getString("address");
                int id = resultSet.getInt("id");
                int income = resultSet.getInt("income");

                lg.makeLog(" Name: " + name + ",Date of Birth: " +  DOB + ",Email: "
                        + DOB + ", Password: " + password + ", Address: " + address +", ID: " + id + ", Income:" + income);}

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUser(Account account) {
        connectToDatabase();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO users (first_name, last_name, department, major, email, imageURL) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, account.name());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setString(3, person.getDepartment());
            preparedStatement.setString(4, person.getMajor());
            preparedStatement.setString(5, person.getEmail());
            preparedStatement.setString(6, person.getImageURL());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                lg.makeLog("A new user was inserted successfully.");
            }
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editUser(int id, Person p) {
        connectToDatabase();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "UPDATE users SET first_name=?, last_name=?, department=?, major=?, email=?, imageURL=? WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, p.getFirstName());
            preparedStatement.setString(2, p.getLastName());
            preparedStatement.setString(3, p.getDepartment());
            preparedStatement.setString(4, p.getMajor());
            preparedStatement.setString(5, p.getEmail());
            preparedStatement.setString(6, p.getImageURL());
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteRecord(Person person) {
        int id = person.getId();
        connectToDatabase();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM users WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Method to retrieve id from database where it is auto-incremented.
    public int retrieveId(Person p) {
        connectToDatabase();
        int id;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT id FROM users WHERE email=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, p.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("id");
            preparedStatement.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        lg.makeLog(String.valueOf(id));
        return id;
    }
}

}
