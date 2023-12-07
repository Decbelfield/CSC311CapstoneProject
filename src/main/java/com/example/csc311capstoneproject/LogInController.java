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

    public void initialize() {
        // Create PauseTransitions for usernameError and passwordError
        userNameErrorTimer = createTimer(UserNameError);
        passwordErrorTimer = createTimer(PasswordError);
    }

    private PauseTransition createTimer(Text textNode) {
        PauseTransition timer = new PauseTransition(Duration.seconds(10));
        timer.setOnFinished(event -> textNode.setOpacity(0.0));
        return timer;
    }

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
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(root, 900, 600);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void signUp(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
            Scene scene = new Scene(root, 900, 600);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
