package com.example.csc311capstoneproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ClientTabController {

    private Client client;
    private FormW2 w2;
    private AnchorPane currentForm;
    @FXML
    private AnchorPane w2Form, _1040Form, _1099Form;
    @FXML
    private Label fn1040Field, Ln1040Field, ssn1040Field, addr1040Field, city1040Field, state1040Field, zip1040Field;
    @FXML
    private TextField bW2, _1W2, _2W2, _3W2, _4W2, _5W2, _6W2, _15W2, _16W2, _17W2, _18W2, _19W2;
    @FXML
    private TextArea _cW2, _eW2;
    @FXML
    private Label ssnW2Field;

    public void setCurrentForm(AnchorPane anchorPane) {
           currentForm.setDisable(true);
           currentForm.setVisible(false);

           currentForm = anchorPane;
           currentForm.setDisable(false);
           currentForm.setVisible(true);
    }

    public void save() {


        w2.setAll(bW2.getText(), _cW2.getText(), _eW2.getText(),
                    _1W2.getText(), _2W2.getText(), _3W2.getText(),
                    _4W2.getText(), _5W2.getText(), _6W2.getText(),
                    _15W2.getText(), _16W2.getText(), _17W2.getText(),
                    _18W2.getText(), _19W2.getText());


        System.out.println(_cW2.getText());
    }


    public void load1040(Client c){

        if(c.hasW2())
            w2 = c.getW2();
        else
            w2 = new FormW2(c);

        client = c;
        currentForm = _1040Form;

        fn1040Field.setText(c.getFirstName() + "    " + c.getMiddleInitial());
        Ln1040Field.setText(c.getLastName());
        ssn1040Field.setText(c.getSsn());
        addr1040Field.setText(c.getAddress());
        city1040Field.setText(c.getCity());
        state1040Field.setText(c.getState());
        zip1040Field.setText(c.getZip());
    }

    public void loadW2() {
        ssnW2Field.setText(client.getSsn());
        bW2.setText(w2.getBoxB());_cW2.setText(w2.getBoxC());_eW2.setText(w2.getBoxE());
        _1W2.setText(w2.getBox1());_2W2.setText(w2.getBox2());_3W2.setText(w2.getBox3());
        _4W2.setText(w2.getBox4());_5W2.setText(w2.getBox5());_6W2.setText(w2.getBox6());
        _15W2.setText(w2.getBox15());_16W2.setText(w2.getBox16());_17W2.setText(w2.getBox17());
        _18W2.setText(w2.getBox18());_19W2.setText(w2.getBox19());
    }

    public void swapTo1040() {
        setCurrentForm(_1040Form);
    }

    public void swapToW2() {
        setCurrentForm(w2Form);
        loadW2();
    }

    public void swapTo1099(){
        setCurrentForm(_1099Form);
    }

    public void calculateTaxes() {
        w2.setBox1(_1W2.getText());
        w2.calculateWithholdings();
        w2.calculateIncomeTax();

        _3W2.setText(_1W2.getText());
        _5W2.setText(_1W2.getText());
        _16W2.setText(_1W2.getText());
        _18W2.setText(_1W2.getText());

        _2W2.setText(w2.getBox2());
        _4W2.setText(w2.getBox4());
        _6W2.setText(w2.getBox6());
        _16W2.setText(w2.getBox1());

        _17W2.setText(w2.getBox17());
        _19W2.setText(w2.getBox19());
    }


}
