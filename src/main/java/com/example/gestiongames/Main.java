package com.example.gestiongames;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Load the login page
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent loginRoot = loginLoader.load();
        Login loginController = loginLoader.getController();
        // Create the scene for the login page
        Scene loginScene = new Scene(loginRoot);
        // Load the table view
        FXMLLoader tableLoader = new FXMLLoader(getClass().getResource("affichage .fxml"));
        Parent tableRoot = tableLoader.load();
        Affichage affichage = tableLoader.getController();
        // Create the scene for the table view
        Scene tableScene = new Scene(tableRoot,520,360);
        // Set the login success handler to switch to the table view upon successful login
        loginController.setOnLoginSuccess(() -> {
            primaryStage.setScene(tableScene);
            affichage.initialize();
        });
        // Set the login scene as the primary stage scene
        primaryStage.setScene(loginScene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
