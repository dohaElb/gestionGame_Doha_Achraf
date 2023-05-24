module com.example.gestiongames {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires poi;
    requires poi.ooxml;


    opens com.example.gestiongames to javafx.fxml;
    opens com.example.gestiongames.entities to javafx.base;
    exports com.example.gestiongames;
}