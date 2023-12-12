package com.example.csc311capstoneproject;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
/**
 * Controller for the Terms and Conditions screen of the application.
 * This class handles the display and interaction with the terms and conditions, including agreement or rejection.
 */
public class TermConditionController {
    @FXML
    private TextArea TermsText;
    @FXML
    private Button AgreedTerms;
    @FXML
    private Button RejectedTerms;
    @FXML
    private AnchorPane ButtonPane;
    /**
     * Initializes the controller class.
     * Sets up listeners for the terms text area to monitor when the user has scrolled to the bottom.
     */
    public void initialize() {
        TermsText.textProperty().addListener((obs, oldText, newText) -> {
            checkScrollAtBottom();
        });
        TermsText.scrollTopProperty().addListener((obs, oldScrollTop, newScrollTop) -> {
            checkScrollAtBottom();
        });
        // Initially disable the ButtonPane
        ButtonPane.setDisable(true);
    }
    /**
     * Checks if the scroll is at the bottom of the terms text area.
     * Enables the button pane when the user has scrolled to the bottom of the terms.
     */
    private void checkScrollAtBottom() {
        // Get the total number of lines in the TextArea
        int totalLines = TermsText.getParagraphs().size();
        // Estimate the height of the text
        double estimatedTextHeight = totalLines * TermsText.getFont().getSize();
        // Check if the scroll is at the bottom
        boolean atBottom = TermsText.getScrollTop() + TermsText.getHeight() >= estimatedTextHeight;

        // Enable the ButtonPane if at bottom
        ButtonPane.setDisable(!atBottom);
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
     * Handles the action to proceed to the sign-up screen upon agreeing to the terms and conditions.
     *
     * @param actionEvent The event that triggered the method call.
     */
    @FXML
    public void signUp(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
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
