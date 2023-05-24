package com.example.gestiongames;

import com.example.gestiongames.entities.Game;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class GameTableUtil {

        public static TableColumn<Game, Integer> getIdColumn() {
            TableColumn<Game, Integer> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            return idColumn;
        }

        public static TableColumn<Game, String> getNomColumn() {
            TableColumn<Game, String> nomColumn = new TableColumn<>("Nom");
            nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
            return nomColumn;
        }

        public static TableColumn<Game, String> getNomDeveloppeurColumn() {
            TableColumn<Game, String> nomDeveloppeurColumn = new TableColumn<>("Nom Développeur");
            nomDeveloppeurColumn.setCellValueFactory(new PropertyValueFactory<>("nomDeveloppeur"));
            return nomDeveloppeurColumn;
        }

        public static TableColumn<Game, Integer> getAnneeDeSortieColumn() {
            TableColumn<Game, Integer> anneeDeSortieColumn = new TableColumn<>("Année de sortie");
            anneeDeSortieColumn.setCellValueFactory(new PropertyValueFactory<>("anneeDeSortie"));
            return anneeDeSortieColumn;
        }

        public static TableColumn<Game, String> getPlateformeColumn() {
            TableColumn<Game, String> plateformeColumn = new TableColumn<>("Plateforme");
            plateformeColumn.setCellValueFactory(new PropertyValueFactory<>("plateforme"));
            return plateformeColumn;
        }

        public static TableColumn<Game, String> getGenreColumn() {
            TableColumn<Game, String> genreColumn = new TableColumn<>("Genre");
            genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
            return genreColumn;
        }
    }

