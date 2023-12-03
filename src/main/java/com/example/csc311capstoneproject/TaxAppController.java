package com.example.csc311capstoneproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TaxAppController implements Initializable {

    @FXML
    private TableView<Client> tableView;
    @FXML
    private TableColumn<Client, String> fullNameCol, ssnCol, telephoneCol, emailCol, addressCol, cityCol, stateCol, zipCol;
    @FXML
    private TableColumn<Client, Boolean> markedCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("beep"));
        markedCol.setCellValueFactory(new PropertyValueFactory<>("marked"));
        //markedCol.setCellValueFactory(CheckBoxTableCell.forTableColumn(index ->));

    }
}