package com.example.gestiongames.dao;
import com.example.gestiongames.entities.Game;

import java.text.ParseException;
import java.util.List;

public interface DaoGame {

    void insert(Game game );

    void update(Game game);

    void deleteById(Integer id);

    Game findById(Integer id);

    List<Game> findAll();


    //gestion fichiers
/*
    public void exporterVersTxt(String path); // done
    public void importerDepuisTxt(String path);// done
    public void exporterVersJson(String path); // done
    public void importerDepuisJson(String path); // probleme

 */
public void exporterVersExcel(String path);
    public void importerDepuisExcel(String path) ;



}
