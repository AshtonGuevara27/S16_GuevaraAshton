package vallegrande.edu.pe.sistemabibliotecademo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class LibroDAO {

    // 1. CONSULTAR (Listar todos los registros)
    public ObservableList<Libro> listar() {
        ObservableList<Libro> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM libros";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("id_libro"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("editorial"),
                        rs.getString("categoria"),
                        rs.getInt("anio_publicacion"),
                        rs.getInt("cantidad"),
                        rs.getString("estado")
                );
                lista.add(libro);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar libros: " + e.getMessage());
        }
        return lista;
    }

    // 2. REGISTRAR (Insertar nuevo registro)
    public boolean registrar(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, editorial, categoria, anio_publicacion, cantidad, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getEditorial());
            ps.setString(4, libro.getCategoria());
            ps.setInt(5, libro.getAnioPublicacion());
            ps.setInt(6, libro.getCantidad());
            ps.setString(7, libro.getEstado());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar libro: " + e.getMessage());
            return false;
        }
    }

    // 3. ACTUALIZAR (Modificar un registro existente)
    public boolean actualizar(Libro libro) {
        String sql = "UPDATE libros SET titulo=?, autor=?, editorial=?, categoria=?, anio_publicacion=?, cantidad=?, estado=? WHERE id_libro=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getEditorial());
            ps.setString(4, libro.getCategoria());
            ps.setInt(5, libro.getAnioPublicacion());
            ps.setInt(6, libro.getCantidad());
            ps.setString(7, libro.getEstado());
            ps.setInt(8, libro.getIdLibro());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar libro: " + e.getMessage());
            return false;
        }
    }

    // 4. ELIMINAR (Borrar un registro por su ID)
    public boolean eliminar(int idLibro) {
        String sql = "DELETE FROM libros WHERE id_libro=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idLibro);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar libro: " + e.getMessage());
            return false;
        }
    }
}