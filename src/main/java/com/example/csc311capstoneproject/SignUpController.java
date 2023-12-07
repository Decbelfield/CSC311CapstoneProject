package com.example.csc311capstoneproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpController {
    @FXML
    private TextField fNameField, LNameField, emailField, dobField, zipField;
    @FXML
    private Label fNameErr, LNameErr, emailErr, dobErr, zipErr;
    @FXML
    private Label fNameCheck, LNameCheck, emailCheck, dobCheck, zipCheck, RegistrationComplete;
    @FXML
    private SplitPane TermsAndConditionsPane;
    @FXML
    private TextArea TermsText;
    @FXML
    private Button AgreedTerms, createBtn, ReturnToLogIn;

    private boolean flag;

    @FXML
    public void initialize() {
        dataVerification(fNameField, fNameErr, fNameCheck, "[A-Za-z]{2,25}");
        dataVerification(LNameField, LNameErr, LNameCheck, "[A-Za-z]{2,25}");
        dataVerification(emailField, emailErr, emailCheck, "[a-z]+@farmingdale.edu");
        dataVerification(dobField, dobErr, dobCheck, "\\d{2}/\\d{2}/\\d{4}");
        dataVerification(zipField, zipErr, zipCheck, "\\d{5}");

        RegistrationScroll(); // Call this method to set up the listener for the Terms and Conditions
    }

    private void dataVerification(TextField txtField, Label error, Label check, String regEx) {
        txtField.setOnKeyPressed(event -> {
            if (!event.getCode().isNavigationKey()) {
                txtField.setStyle("-fx-border-color: none;");
                flag = false;
            }
        });

        txtField.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue) {
                if (txtField.getText().matches(regEx)) {
                    txtField.setEditable(false);
                    txtField.setBorder(null);
                    error.setVisible(false);
                    check.setVisible(true);
                    validateData();
                } else {
                    txtField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                    error.setVisible(true);
                    flag = true;
                }
            }
        });
    }

    @FXML
    private void validateData() {
        boolean isDataValid = fNameCheck.isVisible() && LNameCheck.isVisible() &&
                emailCheck.isVisible() && dobCheck.isVisible() &&
                zipCheck.isVisible();
        createBtn.setDisable(!isDataValid);
    }

    @FXML
    private void swapScene(ActionEvent actionEvent) throws IOException {
        if (AgreedTerms.isDisabled()) {
            // If terms not agreed, do not proceed
            return;
        }

        // Assuming all data is valid and terms agreed, save user information to the database
        saveUserDataToDatabase();

        Parent root = FXMLLoader.load(getClass().getResource("NextScene.fxml")); // Replace with your next scene file
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    private void saveUserDataToDatabase() {
        String SQL_SERVER_URL = "jdbc:mysql://capstoneproj.mariadb.database.azure.com";
        String username = "usfour@capstoneproj";
        String password = "four_1234";

        try (Connection connection = DriverManager.getConnection(SQL_SERVER_URL, username, password);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO users (firstName, lastName, email, dob, zip) VALUES (?, ?, ?, ?, ?)")) {

            statement.setString(1, fNameField.getText());
            statement.setString(2, LNameField.getText());
            statement.setString(3, emailField.getText());
            statement.setString(4, dobField.getText());
            statement.setString(5, zipField.getText());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }
    }

    @FXML
    private void RegistrationScroll() {
        TermsText.caretPositionProperty().addListener((observable, oldValue, newValue) -> {
            boolean atBottom = newValue.intValue() == TermsText.getLength();
            AgreedTerms.setDisable(!atBottom);
        });
    }

    @FXML
    public void goBack(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

