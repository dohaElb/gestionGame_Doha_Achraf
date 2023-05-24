package com.example.gestiongames;

import com.example.gestiongames.entities.Game;
import com.example.gestiongames.service.GameService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Affichage {

    @FXML
    private TableView<Game> gameTable;

    @FXML
    private TableColumn<Game, Integer> idColumn;

    @FXML
    private TableColumn<Game, String> nomColumn;

    @FXML
    private TableColumn<Game, String> nomDeveloppeurColumn;

    @FXML
    private TableColumn<Game, Integer> anneeDeSortieColumn;

    @FXML
    private TableColumn<Game, String> plateformeColumn;

    @FXML
    private TableColumn<Game, String> genreColumn;

    @FXML
    private TableColumn<Game, Void> supprimerColumn;

    @FXML
    private TableColumn<Game, Void> modifierColumn;

    @FXML
    private Button ajouter;

    private GameService gameService;
    @FXML
    private ObservableList<Game> gameObservableList;

    public Affichage() {
        // Initialize the GameService
        gameService = new GameService();
    }

    @FXML
    public void initialize() {
        // Configure table columns to display Game properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        nomDeveloppeurColumn.setCellValueFactory(new PropertyValueFactory<>("nomDeveloppeur"));
        anneeDeSortieColumn.setCellValueFactory(new PropertyValueFactory<>("anneeDeSortie"));
        plateformeColumn.setCellValueFactory(new PropertyValueFactory<>("plateforme"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        // Configure the "Supprimer" column
        supprimerColumn.setCellFactory(column -> new TableCell<Game, Void>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    Game game = getTableRow().getItem();
                    if (game != null) {
                        // Handle the delete action for the selected game
                        gameService.remove(game.getId());
                        gameObservableList.remove(game);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        // Configure the "Modifier" column
        modifierColumn.setCellFactory(column -> new TableCell<Game, Void>() {
            private final Button editButton = new Button("Modifier");

            {
                editButton.setOnAction(event -> {
                    Game game = getTableRow().getItem();
                    if (game != null) {
                        openModifierForm(game);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });

        // Load game data into the table
        List<Game> gameList = gameService.findAll();
        gameObservableList = FXCollections.observableArrayList(gameList);
        gameTable.setItems(gameObservableList);
    }

    @FXML
    public void exporter() {
        gameService.exporterVersExcel("src/main/resources/fileExcel.xlsx");
    }

    @FXML
    public void importer() {
        gameService.importerDepuisExcel("src/main/resources/fileExcel2.xlsx");
    }

    @FXML
    private void handleAjouterButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ajouter.fxml"));
            Parent afficherPage = loader.load();

            // Création de la scène pour afficher le fichier FXML chargé
            Scene afficherScene = new Scene(afficherPage,350,700);

            // Récupération de la fenêtre principale et mise à jour de sa scène
            Stage primaryStage = (Stage) ajouter.getScene().getWindow();
            primaryStage.setScene(afficherScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openModifierForm(Game game) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifier.fxml"));
            Parent modifierPage = loader.load();
            ModifierController modifierController = loader.getController();

            // Pass the selected game to the Modifier controller
            modifierController.setGameToUpdate(game);

            // Création de la scène pour afficher le fichier FXML chargé
            Scene modifierScene = new Scene(modifierPage,300,500);

            // Create a new stage for the modifier form
            Stage modifierStage = new Stage();
            modifierStage.setScene(modifierScene);
            modifierStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
