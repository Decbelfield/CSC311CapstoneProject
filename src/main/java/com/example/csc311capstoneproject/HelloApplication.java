package com.example.csc311capstoneproject;

import db.ConnectionDatabase;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * Main application class for the CSC311 Database Project.
 * This class initializes the application and manages the primary stage and scene transitions.
 */
public class HelloApplication extends Application {
    private static Scene scene;
    private static ConnectionDatabase db;
    private Stage primaryStage;
    /**
     * Main entry point for the application.
     * Initializes the database connection and launches the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        ConnectionDatabase ConnectionDatabase = new ConnectionDatabase();
        launch(args);

    }
    /**
     * Starts the primary stage of the application, sets basic properties and shows the initial scene.
     *
     * @param primaryStage the primary stage for this application
     */
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);

        primaryStage.setTitle("FSC CSC311 _ Database Project");
        showScene1();
    }
    /**
     * Loads and displays the initial splash screen scene.
     */
    private void showScene1() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
            Scene scene = new Scene(root, 900, 600);

            primaryStage.setScene(scene);
            primaryStage.show();
            changeScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Transitions the current scene to the login scene with a fade-out effect.
     */
    public void changeScene() {
        try {
            Parent newRoot = FXMLLoader.load(getClass().getResource("LogIn.fxml").toURI().toURL());
            Scene currentScene = primaryStage.getScene();
            Parent currentRoot = currentScene.getRoot();

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), currentRoot);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setOnFinished(e -> {
                Scene newScene = new Scene(newRoot, 900, 600);
                primaryStage.setScene(newScene);
                primaryStage.show();
            });
            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}