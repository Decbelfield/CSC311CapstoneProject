package com.example.csc311capstoneproject;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddFormController {

    // Reference to the main TaxAppController
    private TaxAppController taxAppController;
    // FXML annotated fields corresponding to the GUI elements in the form
    @FXML
    private TextField firstNameField, MiddleInitialField, LastNameField, ssnField, telephoneField, emailField, addressField, cityField, stateField, zipField;
    /**
     * Sets the reference to the original TaxAppController.
     * This method is used for dependency injection to allow interaction with the main application controller.
     *
     * @param taxAppController the TaxAppController instance to be used by this controller
     */
    public void setOriginalController(TaxAppController taxAppController) {
        this.taxAppController = taxAppController;
    }
    /**
     * Handles the action of adding a new client.
     * This method is triggered when the user submits the form to add a new client.
     * It creates a new Client object with the provided data, adds it to the main application controller,
     * and then closes the form window.
     */
    public void AddClient() {
        // Implementation of adding a new client
        Client c = new Client(
                firstNameField.getText(), MiddleInitialField.getText(),
                LastNameField.getText(), ssnField.getText(),
                telephoneField.getText(), emailField.getText(),
                addressField.getText(), cityField.getText(),
                stateField.getText(), zipField.getText());

        taxAppController.addData(c);

        Stage stage = (Stage) firstNameField.getScene().getWindow();
        stage.close();
    }
}
