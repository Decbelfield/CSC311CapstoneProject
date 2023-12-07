package com.example.csc311capstoneproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ClientTabController {

    @FXML
    private Label fn1040Field, Ln1040Field, ssn1040Field, addr1040Field, city1040Field, state1040Field, zip1040Field;
    @FXML
    private AnchorPane mainAnchorPane, secondaryAnchorPane;
    @FXML
    private StackPane stackPane;

    public void load1040(Client c){
        fn1040Field.setText(c.getFirstName() + "    " + c.getMiddleInitial());
        Ln1040Field.setText(c.getLastName());
        ssn1040Field.setText(c.getSsn());
        addr1040Field.setText(c.getAddress());
        city1040Field.setText(c.getCity());
        state1040Field.setText(c.getState());
        zip1040Field.setText(c.getZip());
    }

    public void swapTo1040() {
        swapAnchorPanes(stackPane, secondaryAnchorPane, mainAnchorPane);
    }

    public void loadW2() {
        swapAnchorPanes(stackPane, mainAnchorPane, secondaryAnchorPane);
    }

    public static void swapAnchorPanes(StackPane stackPane, AnchorPane anchorPane1, AnchorPane anchorPane2) {
        // Ensure both AnchorPanes are present in the StackPane
        if (stackPane.getChildren().contains(anchorPane1) && stackPane.getChildren().contains(anchorPane2)) {
            int index1 = stackPane.getChildren().indexOf(anchorPane1);
            int index2 = stackPane.getChildren().indexOf(anchorPane2);

            // Swap the AnchorPanes in the StackPane
            stackPane.getChildren().set(index1, anchorPane2);
            stackPane.getChildren().set(index2, anchorPane1);
        }
    }

}
