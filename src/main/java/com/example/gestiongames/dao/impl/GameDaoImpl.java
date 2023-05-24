package com.example.gestiongames.dao.impl;
import com.example.gestiongames.dao.DaoGame;
import com.example.gestiongames.entities.Game;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;




public class GameDaoImpl implements DaoGame {

    private Connection conn = DB.getConnection();

    @Override
    public void insert(Game game) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO game (nom,nomDeveloppeur,anneeDeSortie,plateforme,genre) VALUES (?,?,?,?,?)");
            ps.setString(1, game.getNom());
            ps.setString(2, game.getNomDeveloppeur());
            ps.setInt(3, game.getAnneeDeSortie());
            ps.setString(4, game.getPlateforme());
            ps.setString(5, game.getGenre());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("");

        } finally {
            DB.closeStatement(ps);
        }
    }


    @Override
    public void update(Game game) {
        PreparedStatement ps = null;

        try {
            String query = "UPDATE game SET nom=?, nomDeveloppeur=?, anneeDeSortie=?, plateforme=?, genre=? WHERE id=?";

            ps = conn.prepareStatement(query);
            ps.setString(1, game.getNom());
            ps.setString(2, game.getNomDeveloppeur());
            ps.setInt(3, game.getAnneeDeSortie());
            ps.setString(4, game.getPlateforme());
            ps.setString(5, game.getGenre());
            ps.setInt(6, game.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Problème de mise à jour d'un jeu : " + e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM game WHERE id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Bien supprime !!!");
        } catch (SQLException e) {
            System.err.println("problème de suppression d'un jeu");
            ;
        } finally {
            DB.closeStatement(ps);
        }

    }

    @Override
    public Game findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM game WHERE id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Game game = new Game();

                game.setId(rs.getInt("id"));
                game.setNom(rs.getString("nom"));
                game.setNomDeveloppeur(rs.getString("nomDeveloppeur"));
                game.setAnneeDeSortie(rs.getInt("anneeDeSortie"));
                game.setPlateforme(rs.getNString("plateforme"));
                game.setGenre(rs.getString("genre"));

                return game;
            }

            return null;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver un jeu");
            ;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }

    }

    @Override
    public List<Game> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM game");
            rs = ps.executeQuery();

            List<Game> listjeu = new ArrayList<>();

            while (rs.next()) {
                Game game = new Game();

                game.setId(rs.getInt("id"));
                game.setNom(rs.getString("nom"));
                game.setNomDeveloppeur(rs.getString("nomDeveloppeur"));
                game.setAnneeDeSortie(rs.getInt("anneeDeSortie"));
                game.setPlateforme(rs.getNString("plateforme"));
                game.setGenre(rs.getString("genre"));

                listjeu.add(game);
            }

            return listjeu;
        } catch (SQLException e) {
            System.err.println("problème de requête pour sélectionner un jeu");
            ;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }

    }

    // *********************Les fichiers ************************************************************************

    @Override
    public void exporterVersExcel(String path) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Games");

            List<Game> games = findAll();

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Nom");
            headerRow.createCell(2).setCellValue("Développeur");
            headerRow.createCell(3).setCellValue("Année de sortie");
            headerRow.createCell(4).setCellValue("Plateforme");
            headerRow.createCell(5).setCellValue("Genre");

            int rowNum = 1;
            for (Game game : games) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(game.getId());
                row.createCell(1).setCellValue(game.getNom());
                row.createCell(2).setCellValue(game.getNomDeveloppeur());
                row.createCell(3).setCellValue(game.getAnneeDeSortie());
                row.createCell(4).setCellValue(game.getPlateforme());
                row.createCell(5).setCellValue(game.getGenre());
            }

            try (FileOutputStream outputStream = new FileOutputStream(path)) {
                workbook.write(outputStream);
            }

            System.out.println("Exportation vers le fichier Excel réussie !");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'exportation vers le fichier Excel : " + e.getMessage());
        }
    }

    @Override
    public void importerDepuisExcel(String path) {
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(path))) {
            Sheet sheet = workbook.getSheetAt(0);

            // Parcourir chaque ligne à partir de la deuxième ligne (la première ligne contient les en-têtes)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                int id = (int) row.getCell(0).getNumericCellValue();
                String nom = row.getCell(1).getStringCellValue();
                String developpeur = row.getCell(2).getStringCellValue();
                int anneeDeSortie = (int) row.getCell(3).getNumericCellValue();
                String plateforme = row.getCell(4).getStringCellValue();
                String genre = row.getCell(5).getStringCellValue();

                // Créer un objet Game avec les données extraites
                Game game = new Game();
                game.setId(id);
                game.setNom(nom);
                game.setNomDeveloppeur(developpeur);
                game.setAnneeDeSortie(anneeDeSortie);
                game.setPlateforme(plateforme);
                game.setGenre(genre);


                // Insérer ou traiter l'objet Game selon vos besoins
                insert(game);

            }

            System.out.println("Importation depuis le fichier Excel réussie !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


/*


    @Override
    public void exporterVersTxt(String path) {

        Statement statement = null;
        ResultSet resultat = null;

        try (FileWriter writer = new FileWriter(path)) {

            statement = conn.createStatement();
            String requeteSQL = "SELECT * FROM game";
            resultat = statement.executeQuery(requeteSQL);

            while (resultat.next()) {
                // Récupérer les valeurs des colonnes souhaitées
                String valeur1 = resultat.getString("nom"); // Remplacez "nom" par le nom de votre colonne
                String valeur2 = resultat.getString("nomDeveloppeur"); // Remplacez "nomDeveloppeur" par le nom de votre colonne
                int valeur3 = resultat.getInt("anneeDeSortie"); // Remplacez "anneeDeSortie" par le nom de votre colonne
                String valeur4 = resultat.getString("plateforme"); // Remplacez "plateforme" par le nom de votre colonne
                String valeur5 = resultat.getString("genre"); // Remplacez "genre" par le nom de votre colonne

                // Écrire les valeurs dans le fichier
                writer.write(valeur1 + "\t" + valeur2 + "\t" + valeur3 + "\t" + valeur4 + "\t" + valeur5 + "\n");
            }

            System.out.println("Exportation vers le fichier réussie !");
        } catch (IOException | SQLException e) {
            System.out.println("Erreur lors de l'exportation vers le fichier : " + e.getMessage());
        } finally {
            // Fermer les ressources JDBC
            DB.closeResultSet(resultat);
            DB.closeStatement(statement);
        }
    }

    @Override
    public void importerDepuisTxt(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\t");
                String nom = data[0];
                String nomDeveloppeur = data[1];
                int anneeDeSortie = Integer.parseInt(data[2]);
                String plateforme = data[3];
                String genre = data[4];

                // Créer un objet Game avec les données extraites
                Game game = new Game();
                game.setNom(nom);
                game.setNomDeveloppeur(nomDeveloppeur);
                game.setAnneeDeSortie(anneeDeSortie);
                game.setPlateforme(plateforme);
                game.setGenre(genre);


                // Insérer l'objet Game dans la base de données ou effectuer toute autre opération souhaitée
                insert(game);

            }

            System.out.println("Importation depuis le fichier réussie !");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'importation depuis le fichier : " + e.getMessage());
        }
    }


    @Override
    public void exporterVersJson(String path) {
        try (FileWriter fileWriter = new FileWriter(path)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Créer une liste de jeux à exporter
            List<Game> games = findAll(); // Remplacez getAllGames() par la méthode appropriée pour récupérer les jeux

            // Convertir la liste en format JSON
            String json = gson.toJson(games);

            // Écrire le JSON dans le fichier
            fileWriter.write(json);

            System.out.println("Exportation vers le fichier JSON réussie !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    @Override
    public void importerDepuisJson(String path) {
        JSONParser parser = new JSONParser();
        List<Game> games = new ArrayList<>();

        try {
            Object obj = parser.parse(new FileReader(path));

            // Check if the root object is an array
            if (obj instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) obj;

                for (Object gameObj : jsonArray) {
                    JSONObject jsonObject = (JSONObject) gameObj;

                    String nom = (String) jsonObject.get("nom");
                    String nomDeveloppeur = (String) jsonObject.get("nomDeveloppeur");
                    int anneeDeSortie = ((Long) jsonObject.get("anneeDeSortie")).intValue();
                    String plateforme = (String) jsonObject.get("plateforme");
                    String genre = (String) jsonObject.get("genre");

                    Game game = new Game();

                    game.setNom(nom);
                    game.setNomDeveloppeur(nomDeveloppeur);
                    game.setAnneeDeSortie(anneeDeSortie);
                    game.setPlateforme(plateforme);
                    game.setGenre(genre);

                    System.out.println(game);
                    insert(game);

                }

                System.out.println("Bien ajoute !! ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




    @Override
    public void importerDepuisJson(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Game> games = objectMapper.readValue(new File(path), new TypeReference<List<Game>>() {});
            for (Game game : games) {
                insert(game);
                System.out.println("done !!! ");
            }
            System.out.println("Importation depuis le fichier JSON réussie !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

}








