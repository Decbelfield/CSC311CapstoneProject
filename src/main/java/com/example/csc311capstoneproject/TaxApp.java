package com.example.csc311capstoneproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Main application class for the Tax Application.
 * This class extends Application and is responsible for initializing and displaying the primary stage and scene.
 */
public class TaxApp extends Application {
    /**
     * Starts the primary stage of the application.
     * This method is called at application launch and is responsible for setting up the initial user interface.
     *
     * @param stage The primary stage for this application, onto which the application scene is set.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaxApp.class.getResource("Dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * The main method to launch the application.
     * This is the entry point of the JavaFX application.
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch();
    }
    // ... Any additional methods or inner classes ...
}