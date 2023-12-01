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

public class HelloApplication extends Application {
    private static Scene scene;
    private static ConnectionDatabase db;
    private Stage primaryStage;

    public static void main(String[] args) {
        ConnectionDatabase ConnectionDatabase = new ConnectionDatabase();
        launch(args);

    }

    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);

        primaryStage.setTitle("FSC CSC311 _ Database Project");
        showScene1();
    }

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