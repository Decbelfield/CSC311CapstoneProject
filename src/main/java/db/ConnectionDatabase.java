package db;

import com.example.csc311capstoneproject.Account;
import com.example.csc311capstoneproject.MyLogger;
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
                String social = resultSet.getString("social");
                data.add(new Account(name, DOB, email, password, phone, address, id, income, imageURL, social));
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
                        "imageURL VARCHAR(200))" +
                        "social VARCHAR(200))";
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
                    String accountName = resultSet.getString("name");
                    String DOB = resultSet.getString("DOB");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String phone = resultSet.getString("phone");
                    String address = resultSet.getString("address");
                    int id = resultSet.getInt("id");
                    int income = resultSet.getInt("income");
                    String imageURL = resultSet.getString("imageURL");
                    String social = resultSet.getString("social");


                    Account account = new Account(accountName, DOB, email, password, phone, address, id, income, imageURL,social);
                    data.add(account);
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
                String accountName = resultSet.getString("name");
                String DOB = resultSet.getString("DOB");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");
                int id = resultSet.getInt("id");
                int income = resultSet.getInt("income");
                String imageURL = resultSet.getString("imageURL");
                String social = resultSet.getString("social");


                Account account = new Account(accountName, DOB, email, password, phone, address, id, income, imageURL,social);
                data.add(account);
            }

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
            String sql = "INSERT INTO users (name, DOB, email, password, phone, address, income, imageURL) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, account.getName());
            preparedStatement.setString(2, account.getDOB());
            preparedStatement.setString(3, account.getEmail());
            preparedStatement.setString(4, account.getPassword());
            preparedStatement.setString(5, account.getPhone());
            preparedStatement.setString(6, account.getAddress());
            preparedStatement.setInt(7, account.getIncome());
            preparedStatement.setString(8, account.getImageURL());
            preparedStatement.setString(9, account.getSocial());


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

    public void editUser(int id, Account account) {
        connectToDatabase();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "UPDATE users SET name=?, DOB=?, email=?, password=?, phone=?, address=?, income=?, imageURL=? WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, account.getName());
            preparedStatement.setString(2, account.getDOB());
            preparedStatement.setString(3, account.getEmail());
            preparedStatement.setString(4, account.getPassword());
            preparedStatement.setString(5, account.getPhone());
            preparedStatement.setString(6, account.getAddress());
            preparedStatement.setInt(7, account.getIncome());
            preparedStatement.setString(8, account.getImageURL());
            preparedStatement.setInt(9, id);
            preparedStatement.setString(10, account.getSocial());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRecord(Account account) {
        int id = account.getId();
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
            e.printStackTrace();
        }
    }

    //Method to retrieve id from database where it is auto-incremented.
    public int retrieveId(Account account) {
        connectToDatabase();
        int id = -1; // Initialize with a default value

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT id FROM users WHERE email=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, account.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        lg.makeLog(String.valueOf(id));
        return id;
    }
}


