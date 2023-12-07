package com.example.csc311capstoneproject;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddFormController {

    private TaxAppController taxAppController;

    @FXML
    private TextField firstNameField, MiddleInitialField, LastNameField, ssnField, telephoneField, emailField, addressField, cityField, stateField, zipField;

    public void setOriginalController(TaxAppController taxAppController) {
        this.taxAppController = taxAppController;
    }
    public void AddClient() {
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
