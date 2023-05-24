package com.example.gestiongames;

import com.example.gestiongames.dao.impl.GameDaoImpl;
import com.example.gestiongames.entities.Game;
import com.example.gestiongames.service.GameService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class Ajouter {

    @FXML
    private TextField nameField;
    @FXML
    private TextField developerField;
    @FXML
    private TextField platformField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField releaseYearField;

    @FXML
    public void initialize2() {
            String nom = nameField.getText();
            String nomDeveloppeur = developerField.getText();
            String platforme = platformField.getText();
            String genre = genreField.getText();
            int anneeDeSortie = Integer.parseInt(releaseYearField.getText());


            GameService gameservice1 = new GameService();

            // Ajout d'un jeu
            Game newGame = new Game();

            newGame.setNom(nom);
            newGame.setNomDeveloppeur(nomDeveloppeur);
            newGame.setAnneeDeSortie(anneeDeSortie);
            newGame.setPlateforme(platforme);
            newGame.setGenre(genre);

            gameservice1.save(newGame);
            System.out.println("bien ajoute !!");



            // Effacer les champs du formulaire après l'ajout
            nameField.clear();
            developerField.clear();
            platformField.clear();
            genreField.clear();
            releaseYearField.clear();


        // ...
    }


}
