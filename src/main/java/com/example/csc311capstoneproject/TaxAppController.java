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
    public TableView<Client> tableView;
    @FXML
    private TableColumn<Client, String> fullNameCol, ssnCol, telephoneCol, emailCol, addressCol, cityCol, stateCol, zipCol;
    @FXML
    private TableColumn<Client, Boolean> markedCol;
    public ObservableList<Client> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data.add(new Client("Jakob", "O", "Guzman", "162-26-7783", "(631)827-5759", "guzmjo@farmingdale.edu", "1919 Joshuas Path", "Central Islip", "NY", "11722"));
        data.add(new Client("Andre ", "S", "Barnes", "970-68-9723", "(194)293-9666", "andreBarnes@gmail.com", "8611 Marsh Dr. ", "Patchogue", "NY", "11772"));
        data.add(new Client("Karl", "T", "Huff", "439-17-5312", "(203)543-5334", "huffkr@farmingdale.edu", "702 Peachtree St.", "Troy", "NY", "12180"));
        data.add(new Client("Edwin", "R", "Madrid", "026-90-5247", "(317)684-1735", "madridew@farmingdale.edu", "8 Cleveland St.", "Bronx", "NY", "10462"));
        data.add(new Client("Christopher", "A", "Donaldson", "064-34-6157", "(755)852-4648", "donaldsonco@farmingdale.edu", "7155 Hawthorne St. ", "South Ozone Park", "NY", "11420"));
        data.add(new Client("Ronald", "A", "Booth", "874-99-2742", "(749)015-8331", "boothro@farmingdale.edu", "994 Race Ave", "Westbury", "NY", "11590"));
        data.add(new Client("Leo ", "A", "Noble", "728-94-6797", "(368)473-3755", "leono@farmingdale.edu", "59 Richardson Rd", "Astoria", "NY", "11103"));



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
                checkBox.selectedProperty().addListener(((observableValue, aBoolean, t1) ->{
                    if (t1) {
                        tableView.getSelectionModel().getSelectedItem().setMarked(true);
                    }

                    else {
                        tableView.getSelectionModel().getSelectedItem().setMarked(false);
                    }

                    System.out.println(tableView.getSelectionModel().getSelectedItem().isMarked());
                }));

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

    public void addClientForm() {
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

    public void removeClient() {
        data.removeIf(Client::isMarked);
        tableView.setItems(data);

    }

    public void markAll(){
        for(Client c: data) {
            c.setMarked(true);
        }
    }

    public void unmarkAll() {
        for(Client c: data) {
            c.setMarked(false);
        }
    }

    public void help() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Help.fxml"));
        Parent root = loader.load();

        Stage helpStage = new Stage();
        helpStage.setTitle("Tax Home Help");
        helpStage.initModality(Modality.APPLICATION_MODAL);
        helpStage.setScene(new Scene(root));
        helpStage.show();
    }

}