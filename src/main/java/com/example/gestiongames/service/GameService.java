package com.example.gestiongames.service;
import com.example.gestiongames.dao.DaoGame;
import com.example.gestiongames.dao.impl.GameDaoImpl;
import com.example.gestiongames.entities.Game;
import java.text.ParseException;
import java.util.List;

public class GameService {

    private DaoGame daoGame = new GameDaoImpl();

    public List<Game> findAll() {
        return daoGame.findAll();
    }


    public void save(Game game) {

        daoGame.insert(game);

    }
    public void update(Game game) {

        daoGame.update(game);

    }
    public void remove(int id ) {

        daoGame.deleteById(id);
    }

    public Game findById(int id )
    {
        return daoGame.findById(id);
    }


/*partie fichiers

    public void exporterVersTxt(String path){
        daoGame.exporterVersTxt(path);

    }

    public void importerDepuisTxt(String path){
        daoGame.importerDepuisTxt(path);


    }

    public void importerDepuisJson(String path){
        daoGame.importerDepuisJson(path);

    }


    public void exporterVersJson(String path){
        daoGame.exporterVersJson(path);

    }
    */
public void exporterVersExcel(String path){
    daoGame.exporterVersExcel(path);

}
    public void importerDepuisExcel(String path) {
        daoGame.importerDepuisExcel(path);

    }


}
