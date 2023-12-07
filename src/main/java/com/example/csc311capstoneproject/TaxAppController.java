package com.example.csc311capstoneproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TaxAppController implements Initializable {

    @FXML
    private TabPane Tpane;
    @FXML
    public  TableView<Client> tableView;
    @FXML
    private  TableColumn<Client, String> fullNameCol, ssnCol, telephoneCol, emailCol, addressCol, cityCol, stateCol, zipCol;
    @FXML
    private TableColumn<Client, Boolean> markedCol;
    public  ObservableList<Client> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data.add(new Client("Jakob", "O", "Guzman", "000-00-0000", "(631)827-5759", "guzmjo@farmingdale.edu", "1919 Joshuas Path","Central Islip", "NY", "11722"));
        data.add(new Client("Andre ", "S", "Barnes", "000-00-0000", "(194)293-9666", "andreBarnes@gmail.com", "8 St John St", "Sayville", "NY", "11716"));
        data.add(new Client("Karl", "T", "Huff", "000-00-0000", "(203)543-5334", "huffkr@farmingdale.edu", "480 Nostrand Ave","Central Islip", "NY", "11722"));
        data.add(new Client("Edwin", "R", "Madrid", "000-00-0000", "(317)684-1735", "madridew@farmingdale.edu", "108 Applegate Dr","Central Islip", "NY", "11722"));
        data.add(new Client("Christopher", "A", "Donaldson", "000-00-0000", "(755)852-4648", "donaldsonco@farmingdale.edu", "278 Ocean Ave","Central Islip", "NY", "11722"));

        markedCol.setCellValueFactory(new PropertyValueFactory<>("marked"));
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        ssnCol.setCellValueFactory(new PropertyValueFactory<>("Ssn"));
        telephoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        zipCol.setCellValueFactory(new PropertyValueFactory<>("zip"));

        tableView.setItems(data);

        if(!data.isEmpty()) {
            markedCol.setCellValueFactory(new PropertyValueFactory<>("marked"));
            markedCol.setCellFactory(tc -> {
                CheckBox checkBox = new CheckBox();

                TableCell<Client, Boolean> cell = new TableCell<>();
                cell.setGraphic(checkBox);
                cell.setOnMouseClicked(event -> {

                    if (event.getClickCount() == 2 && !cell.isEmpty()) {
                        Client selectedClient = cell.getTableView().getItems().get(cell.getIndex());
                        openNewTab(selectedClient);
                    }
                });

                return cell;
            });
        }
    }

    public void addClientForm(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddClientForm.fxml"));
            Parent addClientRoot = loader.load();

            AddFormController addFormController = loader.getController();
            addFormController.setOriginalController(this);

            Stage addClientStage = new Stage();
            addClientStage.setTitle("Add Client Form");
            addClientStage.initModality(Modality.APPLICATION_MODAL);
            addClientStage.setScene(new Scene(addClientRoot));
            addClientStage.show();



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addData(Client client) {
        data.add(client);
    }

    private void openNewTab(Client c) {
        Task<Tab> task = new Task<Tab>() {
            @Override
            protected Tab call() throws Exception {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientTab.fxml"));
                    Node tabContent = loader.load();

                    ClientTabController clientTabController = loader.getController();
                    clientTabController.load1040(c);

                    Tab newTab = new Tab(c.getFullName(), tabContent);
                    newTab.setClosable(true);

                    return newTab;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

        task.setOnSucceeded(event -> {
            Tab newTab = task.getValue();
            if (newTab != null) {


                Tpane.getTabs().add(newTab);
                Tpane.getSelectionModel().select(newTab);
            }
        });

        new Thread(task).start();
    }

}