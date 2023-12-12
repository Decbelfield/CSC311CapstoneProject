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
/**
 * Controller for the main application window.
 * This class handles user interactions and data manipulation for the application's GUI.
 */
public class HelloController {

    @FXML
    private TextField name, DOB, email, password, phone, address, income, imageURL, Social;
    @FXML
    private ImageView img_view;
    @FXML
    private MenuBar menuBar;
    @FXML
    private TableView<Account> tv;
    @FXML
    private TableColumn<Account, Integer> tv_id, tv_income;
    @FXML
    private TableColumn<Account, String> tv_name, tv_DOB, tv_email, tv_password, tv_phone, tv_address, tv_Social;
    @FXML
    private Button deleteButton, clearButton, editButton, addBtn;
    @FXML
    private TextField AddStatus, DeleteStatus, EditStatus;
    @FXML
    private ComboBox<String> MajorComboBox;

    private final ConnectionDatabase cnUtil = new ConnectionDatabase();
    private final ObservableList<Account> data = cnUtil.getData();
    /**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            tv_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tv_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            tv_DOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
            tv_email.setCellValueFactory(new PropertyValueFactory<>("email"));
            tv_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            tv_address.setCellValueFactory(new PropertyValueFactory<>("address"));
            tv_income.setCellValueFactory(new PropertyValueFactory<>("income"));
            tv_Social.setCellValueFactory(new PropertyValueFactory<>("social"));

            tv.setItems(data);

            editButton.disableProperty().bind(tv.getSelectionModel().selectedItemProperty().isNull());
            deleteButton.disableProperty().bind(tv.getSelectionModel().selectedItemProperty().isNull());
            addBtn.disableProperty().bind(Bindings.createBooleanBinding(() ->
                            name.getText().isEmpty() || DOB.getText().isEmpty() ||
                                    email.getText().isEmpty() ||address.getText().isEmpty() ||
                                    income.getText().isEmpty() || MajorComboBox.getValue() == null ||
                                    Social.getText().isEmpty() ||
                                    password.getText().isEmpty(), name.textProperty(), DOB.textProperty(),
                    email.textProperty(), MajorComboBox.valueProperty(), password.textProperty()));

            MajorComboBox.setItems(FXCollections.observableArrayList("Business", "CSC", "CPIS"));

        } catch (Exception e) {
            MyLogger.makeLog("Exception occurred during initialization: " + e.getMessage());
        }
    }
    /**
     * Adds a new record to the TableView and the database.
     */
    @FXML
    public void addNewRecord() {
        if (validateFields()) {
            try {
                Account account = new Account(
                        name.getText(),
                        DOB.getText(),
                        email.getText(),
                        password.getText(),
                        phone.getText(),
                        address.getText(),
                        0, // Assuming ID is auto-generated or retrieved from the database
                        Integer.parseInt(income.getText()),
                        imageURL.getText(),
                        Social.getText()
                );
                cnUtil.insertUser(account);
                int generatedId = cnUtil.retrieveId(account);
                account.setId(generatedId);
                data.add(account);
                clearForm();
                AddStatus.setText("Record added successfully.");
            } catch (NumberFormatException e) {
                AddStatus.setText("Error: Income must be a valid number.");
                MyLogger.makeLog("NumberFormatException: " + e.getMessage());
            }
        } else {
            AddStatus.setText("Please fill in all fields correctly.");
        }

        AddStatus.setOpacity(1.0);
        DeleteStatus.setOpacity(0.0);
        EditStatus.setOpacity(0.0);
    }

    /**
     * Validates the input fields before adding or editing a record.
     *
     * @return true if all fields are correctly filled, false otherwise
     */
    private boolean validateFields() {
        // Validation logic based on Account fields
        return !name.getText().trim().isEmpty() &&
                !DOB.getText().trim().isEmpty() &&
                !email.getText().trim().isEmpty() &&
                !phone.getText().trim().isEmpty() &&
                !address.getText().trim().isEmpty() &&
                !income.getText().trim().isEmpty() &&
                !Social.getText().trim().isEmpty();
    }
    /**
     * Clears all input fields in the form.
     */
    @FXML
    public void clearForm() {
        name.clear();
        DOB.clear();
        email.clear();
        password.clear();
        phone.clear();
        address.clear();
        income.clear();
        imageURL.clear();
        Social.clear();
    }
    /**
     * Logs out the current user and loads the login screen.
     *
     * @param actionEvent The event that triggered the method call
     */
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
    /**
     * Closes the application.
     */
    @FXML
    protected void closeApplication() {
        Platform.exit();
    }
    /**
     * Displays the About dialog with information about the application.
     */
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
    /**
     * Edits the selected record in the TableView and updates it in the database.
     */
    @FXML
    protected void editRecord() {
        // Get the selected account from the table
        Account selectedAccount = tv.getSelectionModel().getSelectedItem();

        if (selectedAccount != null) {
            int index = data.indexOf(selectedAccount);

            // Update the account properties from the form fields
            selectedAccount.setName(name.getText());
            selectedAccount.setDOB(DOB.getText());
            selectedAccount.setEmail(email.getText());
            selectedAccount.setPassword(password.getText());
            selectedAccount.setPhone(phone.getText());
            selectedAccount.setAddress(address.getText());
            selectedAccount.setIncome(Integer.parseInt(income.getText()));
            selectedAccount.setImageURL(imageURL.getText());
            selectedAccount.setSocial(Social.getText());

            // Update the account in the database and in the ObservableList
            cnUtil.editUser(selectedAccount.getId(), selectedAccount);
            data.set(index, selectedAccount);
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
    /**
     * Deletes the selected record from the TableView and the database.
     */
    @FXML
    protected void deleteRecord() {
        // Get the selected person from the table
        Account p = tv.getSelectionModel().getSelectedItem();
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
    /**
     * Opens a file chooser to select an image and displays it in the ImageView.
     */
    @FXML
    protected void showImage() {
        // Show a file chooser dialog to select an image
        File file = (new FileChooser()).showOpenDialog(img_view.getScene().getWindow());
        if (file != null) {
            img_view.setImage(new Image(file.toURI().toString()));
        }
    }
    /**
     * Adds a record to the TableView.
     */
    @FXML
    protected void addRecord() {
        // Show a dialog to add a new user
        showSomeone();
    }
    /**
     * Handles selection of an item in the TableView and updates the form fields accordingly.
     *
     * @param mouseEvent The mouse event that triggered the method call
     */
    @FXML
    protected void selectedItemTV(MouseEvent mouseEvent) {
        Account selectedAccount = tv.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            name.setText(selectedAccount.getName());
            DOB.setText(selectedAccount.getDOB());
            email.setText(selectedAccount.getEmail());
            // ... [Set other fields]
        }
    }
    /**
     * Changes the application theme to light.
     *
     * @param actionEvent The event that triggered the method call
     */
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
    /**
     * Changes the application theme to dark.
     *
     * @param actionEvent The event that triggered the method call
     */
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
    /**
     * Shows a dialog to add a new user with specified fields.
     */
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
        return FXMLLoader.load(getClass().getResource("LogIn.fxml"));
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