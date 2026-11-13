package vallegrande.edu.pe.sistemabibliotecademo.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3307/bd_biblioteca?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root123";

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("¡Conexión exitosa con MySQL!");
        } catch (SQLException e) {
            System.err.println("Error de conexión con MySQL: " + e.getMessage());
        }
        return con;
    }

    // Método main temporal para probar la conexión y tomar la Evidencia 2
    public static void main(String[] args) {
        getConnection();
    }
}