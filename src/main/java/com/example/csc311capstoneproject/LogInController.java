package com.example.csc311capstoneproject;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
/**
 * Controller for the login screen of the application.
 * This class handles user interactions on the login screen, including logging in and signing up.
 */
public class LogInController{

    @FXML
    private TextField UserNameField;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private Button LogInButton;
    @FXML
    private Button SignUpButton;
    @FXML
    private Text UserNameError;
    @FXML
    private Text PasswordError;

    private PauseTransition userNameErrorTimer;
    private PauseTransition passwordErrorTimer;
    /**
     * Initializes the controller class. This method is automatically invoked
     * after the FXML file has been loaded. It sets up timers for hiding error messages.
     */
    public void initialize() {
        // Create PauseTransitions for usernameError and passwordError
        userNameErrorTimer = createTimer(UserNameError);
        passwordErrorTimer = createTimer(PasswordError);
    }

    /**
     * Creates a PauseTransition timer to hide error messages after a specified duration.
     *
     * @param textNode The Text node whose opacity is to be changed.
     * @return A configured PauseTransition object.
     */
    private PauseTransition createTimer(Text textNode) {
        PauseTransition timer = new PauseTransition(Duration.seconds(10));
        timer.setOnFinished(event -> textNode.setOpacity(0.0));
        return timer;
    }
    /**
     * Handles the login action when the login button is pressed.
     * Checks for valid input and navigates to the dashboard scene upon successful login.
     *
     * @param actionEvent The event that triggered the method call.
     */
    @FXML
    public void loginAction(ActionEvent actionEvent) {
        String username = UserNameField.getText();
        String password = PasswordField.getText();


        if (username.isEmpty()) {
            // Username is empty, set the error message opacity to 1 for 10 seconds
            UserNameError.setOpacity(1.0);
            userNameErrorTimer.play();
            return; // Stop execution if the username is empty
        }

        if (password.isEmpty()) {
            // Password is empty, set the error message opacity to 1 for 10 seconds
            PasswordError.setOpacity(1.0);
            passwordErrorTimer.play();
            return; // Stop execution if the password is empty
        }
        if(password.isEmpty() && username.isEmpty()){
            UserNameError.setOpacity(1.0);
            PasswordError.setOpacity(1.0);
            userNameErrorTimer.play();
            return; // Stop execution if the username is empty
        }

        // Perform login logic here if needed
         // If login is successful, save user information to the database
        UserSession.saveUser(username, password);
        // If login is successful, navigate to another scene
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Scene scene = new Scene(root, 900, 600);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Handles the sign-up action when the sign-up button is pressed.
     * Navigates to the Terms and Conditions scene for new user registration.
     *
     * @param actionEvent The event that triggered the method call.
     */
    @FXML
    public void signUp(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TermsAndConditions.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 650, 600);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception here (e.g., show an error message)
        }
    }

}
