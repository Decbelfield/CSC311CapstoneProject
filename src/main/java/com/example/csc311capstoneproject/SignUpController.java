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
/**
 * Controller for the sign-up screen of the application.
 * This class handles user interactions for the registration process, including field validation and data submission.
 */
public class SignUpController {
    @FXML
    private TextField fNameField, LNameField, emailField, dobField, zipField;
    @FXML
    private Label fNameErr, LNameErr, emailErr, dobErr, zipErr;
    @FXML
    private Label fNameCheck, LNameCheck, emailCheck, dobCheck, zipCheck, RegistrationComplete;

@FXML
private Button createBtn;
    private boolean flag;
    /**
     * Initializes the controller class. Sets up field validation for the sign-up form.
     */
    @FXML
    public void initialize() {
        createBtn.setDisable(true);
        dataVerification(fNameField, fNameErr, fNameCheck, "[A-Za-z]{2,25}");
        dataVerification(LNameField, LNameErr, LNameCheck, "[A-Za-z]{2,25}");
        dataVerification(emailField, emailErr, emailCheck, "[a-z]+@farmingdale.edu");
        dataVerification(dobField, dobErr, dobCheck, "\\d{2}/\\d{2}/\\d{4}");
        dataVerification(zipField, zipErr, zipCheck, "\\d{5}");

    }
    /**
     * Validates a TextField's input against a regular expression and updates UI elements accordingly.
     *
     * @param txtField The TextField to be validated.
     * @param error The Label to display an error message.
     * @param check The Label to indicate successful validation.
     * @param regEx The regular expression used for validation.
     */
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
    /**
     * Validates whether all data fields are correctly filled and enables the create account button if so.
     */
@FXML
    private void validateData() {
        boolean isDataValid = fNameCheck.isVisible() && LNameCheck.isVisible() &&
                emailCheck.isVisible() && dobCheck.isVisible() &&
                zipCheck.isVisible();

        createBtn.setDisable(!isDataValid); // Enable the create button if data is valid
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


    /**
     * Handles the action to go back to the login screen.
     *
     * @param actionEvent The event that triggered the method call.
     */
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
    /**
     * Handles the creation of a new user account. Saves the user data to the database
     * and optionally navigates to another scene or shows a confirmation message.
     *
     * @param actionEvent The event that triggered the method call.
     */
    @FXML
    private void handleCreateAccount(ActionEvent actionEvent) {
        if (!createBtn.isDisabled()) {
            saveUserDataToDatabase();
            // Optionally, navigate to another scene or show a confirmation message
        }
    }

}

