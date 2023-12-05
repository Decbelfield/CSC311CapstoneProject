package com.example.csc311capstoneproject;

import db.ConnectionDatabase;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class HelloController {

    @FXML
    private TextField first_name, last_name, department, email, imageURL;
    @FXML
    private ImageView img_view;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TableView<Account> tv;
    @FXML
    private TableColumn<Account, Integer> tv_id;
    @FXML
    private TableColumn<Account, String> tv_fn, tv_ln, tv_department, tv_email;
    @FXML
    private Button deleteButton, clearButton, editButton, addBtn;
    @FXML
    private TextField AddStatus, DeleteStatus, EditStatus;
    @FXML
    private ComboBox<String> MajorComboBox;

    private final ConnectionDatabase cnUtil = new ConnectionDatabase();
    private final ObservableList<Account> data = cnUtil.getData();

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            tv_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tv_fn.setCellValueFactory(new PropertyValueFactory<>("name"));
            tv_ln.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            tv_department.setCellValueFactory(new PropertyValueFactory<>("department"));
            tv_email.setCellValueFactory(new PropertyValueFactory<>("email"));
            tv.setItems(data);

            editButton.disableProperty().bind(tv.getSelectionModel().selectedItemProperty().isNull());
            deleteButton.disableProperty().bind(tv.getSelectionModel().selectedItemProperty().isNull());
            addBtn.disableProperty().bind(Bindings.createBooleanBinding(() ->
                            first_name.getText().isEmpty() || last_name.getText().isEmpty() ||
                                    department.getText().isEmpty() || MajorComboBox.getValue() == null ||
                                    email.getText().isEmpty(), first_name.textProperty(), last_name.textProperty(),
                    department.textProperty(), MajorComboBox.valueProperty(), email.textProperty()));

            MajorComboBox.setItems(FXCollections.observableArrayList("Business", "CSC", "CPIS"));

        } catch (Exception e) {
            MyLogger.makeLog("Exception occurred during initialization: " + e.getMessage());
        }
    }

    @FXML
    public void addNewRecord() {
        if (validateFields()) {
            String selectedMajor = MajorComboBox.getValue();
            if (selectedMajor != null) {
                Account account = new Account(
                        first_name.getText(),
                        last_name.getText(),
                        department.getText(),
                        selectedMajor,
                        email.getText(),
                        imageURL.getText()
                );
                cnUtil.insertUser(account);
                int generatedId = cnUtil.retrieveId(account);
                account.setId(generatedId);
                data.add(account);
                clearForm();
                AddStatus.setText("Record added successfully.");
                AddStatus.setOpacity(1.0);
                DeleteStatus.setOpacity(0.0);
                EditStatus.setOpacity(0.0);
            } else {
                AddStatus.setText("Please select a major.");
                AddStatus.setOpacity(1.0);
                DeleteStatus.setOpacity(0.0);
                EditStatus.setOpacity(0.0);
            }
        } else {
            AddStatus.setText("Please fill in all fields correctly.");
            AddStatus.setOpacity(1.0);
            DeleteStatus.setOpacity(0.0);
            EditStatus.setOpacity(0.0);
        }
    }

    private boolean validateFields() {
        return !first_name.getText().trim().isEmpty() &&
                !last_name.getText().trim().isEmpty() &&
                !department.getText().trim().isEmpty() &&
                MajorComboBox.getValue() != null &&
                !email.getText().trim().isEmpty();
    }

    @FXML
    public void clearForm() {
        first_name.clear();
        last_name.clear();
        department.clear();
        MajorComboBox.getSelectionModel().clearSelection();
        email.clear();
        imageURL.clear();
    }

    @FXML
    protected void logOut(ActionEvent actionEvent) {
        try {
            // Load the login.fxml file
            Parent root = loadLoginFXML();
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").getFile());
            Stage window = (Stage) menuBar.getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            MyLogger.makeLog("Error occurred while logging out: " + e.getMessage());
        }
    }

    @FXML
    protected void closeApplication() {
        Platform.exit();
    }

    @FXML
    protected void displayAbout() {
        try {
            // Load the about.fxml file for displaying information about the application
            Parent root = FXMLLoader.load(getClass().getResource("/view/about.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 600, 500);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            MyLogger.makeLog("Exception occurred while displaying about: " + e.getMessage());
        }
    }

    @FXML
    protected void editRecord() {
        // Get the selected person from the table
        Person selectedPerson = tv.getSelectionModel().getSelectedItem();

        if (selectedPerson != null) {
            int index = data.indexOf(selectedPerson);

            // Ensure MajorComboBox has a selected value
            if (MajorComboBox.getValue() == null) {
                EditStatus.setText("Please select a major.");
                EditStatus.setOpacity(1.0);
                return;
            }

            // Create a new Person object with the actual ID and updated values
            Person updatedPerson = new Person(selectedPerson.getId(), first_name.getText(), last_name.getText(),
                    department.getText(), MajorComboBox.getValue().name(), email.getText(),
                    imageURL.getText());

            // Update the person in the database and in the ObservableList
            cnUtil.editUser(selectedPerson.getId(), updatedPerson);
            data.set(index, updatedPerson);
            tv.refresh();

            // Update status messages
            EditStatus.setText("Record updated successfully.");
            EditStatus.setOpacity(1.0);
            DeleteStatus.setOpacity(0.0);
            AddStatus.setOpacity(0.0);
        } else {
            EditStatus.setText("No record selected for editing.");
            EditStatus.setOpacity(1.0);
        }
    }

    @FXML
    protected void deleteRecord() {
        // Get the selected person from the table
        Person p = tv.getSelectionModel().getSelectedItem();
        int index = data.indexOf(p);

        // Delete the person from the database
        cnUtil.deleteRecord(p);

        // Remove the person from the data list
        data.remove(index);
        tv.getSelectionModel().select(index);
        DeleteStatus.setOpacity(1.0);
        EditStatus.setOpacity(0.0);
        AddStatus.setOpacity(0.0);
    }

    @FXML
    protected void showImage() {
        // Show a file chooser dialog to select an image
        File file = (new FileChooser()).showOpenDialog(img_view.getScene().getWindow());
        if (file != null) {
            img_view.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    protected void addRecord() {
        // Show a dialog to add a new user
        showSomeone();
    }

    @FXML
    protected void selectedItemTV(MouseEvent mouseEvent) {
        Person p = tv.getSelectionModel().getSelectedItem();
        first_name.setText(p.getFirstName());
        last_name.setText(p.getLastName());
        department.setText(p.getDepartment());
        MajorComboBox.setValue(Major.valueOf(p.getMajor())); // Set ComboBox value
        email.setText(p.getEmail());
        imageURL.setText(p.getImageURL());
    }

    public void lightTheme(ActionEvent actionEvent) {
        try {
            // Change the application theme to light
            Scene scene = menuBar.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.getScene().getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            System.out.println("light " + scene.getStylesheets());

        } catch (Exception e) {
            MyLogger.makeLog("Exception occurred while changing to light theme: " + e.getMessage());
        }
    }

    public void darkTheme(ActionEvent actionEvent) {
        try {
            // Change the application theme to dark
            Stage stage = (Stage) menuBar.getScene().getWindow();
            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/css/darkTheme.css").toExternalForm());
        } catch (Exception e) {
            MyLogger.makeLog("Exception occurred while changing to dark theme: " + e.getMessage());
        }
    }

    public void showSomeone() {
        // Show a dialog to add a new user with name, last name, email, and major selection
        Dialog<Results> dialog = new Dialog<>();
        dialog.setTitle("New User");
        dialog.setHeaderText("Please specify…");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField textField1 = new TextField("Name");
        TextField textField2 = new TextField("Last Name");
        TextField textField3 = new TextField("Email ");
        ObservableList<Major> options = FXCollections.observableArrayList(Major.values());
        ComboBox<Major> comboBox = new ComboBox<>(options);
        comboBox.getSelectionModel().selectFirst();
        dialogPane.setContent(new VBox(8, textField1, textField2, textField3, comboBox));
        Platform.runLater(textField1::requestFocus);
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new Results(textField1.getText(), textField2.getText(), comboBox.getValue());
            }
            return null;
        });
        Optional<Results> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((Results results) -> {
            MyLogger.makeLog(
                    results.fname + " " + results.lname + " " + results.major);
        });
    }

    private Parent loadLoginFXML() throws IOException {
        return FXMLLoader.load(getClass().getResource("/view/login.fxml"));
    }

    private static enum Major { Business, CSC, CPIS }

    private static class Results {

        String fname;
        String lname;
        Major major;

        public Results(String name, String date, Major venue) {
            this.fname = name;
            this.lname = date;
            this.major = venue;
        }
    }
}
