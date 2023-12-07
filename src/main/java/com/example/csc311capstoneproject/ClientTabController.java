package com.example.csc311capstoneproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ClientTabController {

    private AnchorPane currentForm;
    @FXML
    private AnchorPane w2Form, _1040Form, _1099Form;

    @FXML
    private Label fn1040Field, Ln1040Field, ssn1040Field, addr1040Field, city1040Field, state1040Field, zip1040Field;


    public void setCurrentForm(AnchorPane anchorPane) {
           currentForm.setDisable(true);
           currentForm.setVisible(false);

           currentForm = anchorPane;
           currentForm.setDisable(false);
           currentForm.setVisible(true);
    }

    public void load1040(Client c){
        currentForm = _1040Form;

        fn1040Field.setText(c.getFirstName() + "    " + c.getMiddleInitial());
        Ln1040Field.setText(c.getLastName());
        ssn1040Field.setText(c.getSsn());
        addr1040Field.setText(c.getAddress());
        city1040Field.setText(c.getCity());
        state1040Field.setText(c.getState());
        zip1040Field.setText(c.getZip());
    }

    public void swapTo1040() {
        setCurrentForm(_1040Form);
    }

    public void swapToW2() {
        setCurrentForm(w2Form);
    }

    public void swapTo1099(){
        setCurrentForm(_1099Form);
    }

}
