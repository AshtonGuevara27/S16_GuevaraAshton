package vallegrande.edu.pe.sistemabibliotecademo.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import vallegrande.edu.pe.sistemabibliotecademo.model.Libro;
import vallegrande.edu.pe.sistemabibliotecademo.model.LibroDAO;

public class LibroController {

    @FXML private TextField txtId;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtEditorial;
    @FXML private TextField txtCategoria;
    @FXML private TextField txtAnio;
    @FXML private TextField txtCantidad;
    @FXML private ComboBox<String> cbEstado;

    @FXML private TableView<Libro> tablaLibros;
    @FXML private TableColumn<Libro, Integer> colId;
    @FXML private TableColumn<Libro, String> colTitulo;
    @FXML private TableColumn<Libro, String> colAutor;
    @FXML private TableColumn<Libro, String> colEditorial;
    @FXML private TableColumn<Libro, String> colCategoria;
    @FXML private TableColumn<Libro, Integer> colAnio;
    @FXML private TableColumn<Libro, Integer> colCantidad;
    @FXML private TableColumn<Libro, String> colEstado;

    private final LibroDAO libroDAO = new LibroDAO();

    @FXML
    public void initialize() {
        cbEstado.setItems(FXCollections.observableArrayList("Disponible", "Prestado", "En reparación", "Agotado"));

        colId.setCellValueFactory(new PropertyValueFactory<>("idLibro"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colEditorial.setCellValueFactory(new PropertyValueFactory<>("editorial"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colAnio.setCellValueFactory(new PropertyValueFactory<>("anioPublicacion"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        cargarTabla();
    }

    private void cargarTabla() {
        tablaLibros.setItems(libroDAO.listar());
    }

    @FXML
    public void registrarLibro() {
        if (!validarCampos()) return;

        Libro libro = new Libro(
                0,
                txtTitulo.getText(),
                txtAutor.getText(),
                txtEditorial.getText(),
                txtCategoria.getText(),
                Integer.parseInt(txtAnio.getText()),
                Integer.parseInt(txtCantidad.getText()),
                cbEstado.getValue()
        );

        if (libroDAO.registrar(libro)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Libro registrado correctamente.");
            cargarTabla();
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar el libro.");
        }
    }

    @FXML
    public void actualizarLibro() {
        if (txtId.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione un libro de la tabla para actualizar.");
            return;
        }
        if (!validarCampos()) return;

        Libro libro = new Libro(
                Integer.parseInt(txtId.getText()),
                txtTitulo.getText(),
                txtAutor.getText(),
                txtEditorial.getText(),
                txtCategoria.getText(),
                Integer.parseInt(txtAnio.getText()),
                Integer.parseInt(txtCantidad.getText()),
                cbEstado.getValue()
        );

        if (libroDAO.actualizar(libro)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Libro actualizado correctamente.");
            cargarTabla();
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el libro.");
        }
    }

    @FXML
    public void eliminarLibro() {
        if (txtId.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione un libro de la tabla para eliminar.");
            return;
        }

        int id = Integer.parseInt(txtId.getText());
        if (libroDAO.eliminar(id)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Libro eliminado correctamente.");
            cargarTabla();
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el libro.");
        }
    }

    @FXML
    public void seleccionarLibro() {
        Libro seleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            txtId.setText(String.valueOf(seleccionado.getIdLibro()));
            txtTitulo.setText(seleccionado.getTitulo());
            txtAutor.setText(seleccionado.getAutor());
            txtEditorial.setText(seleccionado.getEditorial());
            txtCategoria.setText(seleccionado.getCategoria());
            txtAnio.setText(String.valueOf(seleccionado.getAnioPublicacion()));
            txtCantidad.setText(String.valueOf(seleccionado.getCantidad()));
            cbEstado.setValue(seleccionado.getEstado());
        }
    }

    @FXML
    public void limpiarCampos() {
        txtId.clear();
        txtTitulo.clear();
        txtAutor.clear();
        txtEditorial.clear();
        txtCategoria.clear();
        txtAnio.clear();
        txtCantidad.clear();
        cbEstado.setValue(null);
        tablaLibros.getSelectionModel().clearSelection();
    }

    private boolean validarCampos() {
        if (txtTitulo.getText().isEmpty() || txtAutor.getText().isEmpty() || txtEditorial.getText().isEmpty() ||
                txtCategoria.getText().isEmpty() || txtAnio.getText().isEmpty() || txtCantidad.getText().isEmpty() ||
                cbEstado.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, complete todos los campos.");
            return false;
        }
        try {
            Integer.parseInt(txtAnio.getText());
            Integer.parseInt(txtCantidad.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Dato inválido", "El Año y la Cantidad deben ser números enteros.");
            return false;
        }
        return true;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}