module vallegrande.edu.pe.sistemabibliotecademo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens vallegrande.edu.pe.sistemabibliotecademo to javafx.fxml;
    opens vallegrande.edu.pe.sistemabibliotecademo.controller to javafx.fxml;
    opens vallegrande.edu.pe.sistemabibliotecademo.model to javafx.base;

    exports vallegrande.edu.pe.sistemabibliotecademo;
}