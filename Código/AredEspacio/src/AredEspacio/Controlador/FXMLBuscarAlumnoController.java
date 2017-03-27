/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.Alumno;
import Modelo.Mensaje;
import Modelo.TipoDeMenu;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Jonathan
 */
public class FXMLBuscarAlumnoController extends MainController implements Initializable {

    @FXML
    private TextField tFBusqueda;
    @FXML
    private TableView<Alumno> tVResultados;
    @FXML
    private ComboBox<?> cBCriterio;
    @FXML
    private TableColumn<?, ?> tCNombre;
    @FXML
    private TableColumn<?, ?> tCApellidos;
    @FXML
    private TableColumn<?, ?> tCMatricula;
    @FXML
    private TableColumn<?, ?> tCStatus;
    private List<Alumno> alumnos;
    
    //clicBuscar
    @FXML
    void buscar(ActionEvent event) {
        String busqueda = tFBusqueda.getText();
        
        if (cBCriterio.getValue() == "Apellidos") {
            alumnos = Alumno.obtenerCoincidenciasPorApellidos(busqueda);
            ObservableList lista = FXCollections.observableArrayList(alumnos);
            tVResultados.setItems(lista);
        }
        else if (cBCriterio.getValue() == "Matricula") {
            alumnos = Alumno.obtenerCoincidenciasPorMatricula(busqueda);
            ObservableList lista = FXCollections.observableArrayList(alumnos);
            tVResultados.setItems(lista);
        } else if (cBCriterio.getValue() == null) 
            Mensaje.advertencia("No se ha seleccionado el criterio");
    }
    
    //clicConsultar
    @FXML
    void consultar(ActionEvent event) {
        Alumno row = tVResultados.getSelectionModel().getSelectedItem();
        if (row != null )
            escena.cargarEscenaConParametros(EscenaPrincipal.EscenaRegistrarAlumno, row, TipoDeMenu.CONSULTAR);
        else
            Mensaje.advertencia("No se ha seleccionado el alumno");
    }
   //clicDarDeBaja
    @FXML
    void eliminar(ActionEvent event) {
        Alumno row = tVResultados.getSelectionModel().getSelectedItem();
        if (row != null )
            escena.cargarEscenaConParametros(EscenaPrincipal.EscenaEliminarAlumno, row, TipoDeMenu.ELIMINAR);
        else
            Mensaje.advertencia("No se ha seleccionado el alumno");
    }
    //clicModificar
    @FXML
    void modificar(ActionEvent event) {
        Alumno row = tVResultados.getSelectionModel().getSelectedItem();
        if (row != null )
            escena.cargarEscenaConParametros(EscenaPrincipal.EscenaRegistrarAlumno, row, TipoDeMenu.MODIFICAR);
        else
            Mensaje.advertencia("No se ha seleccionado el alumno");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList criterio = FXCollections.observableArrayList("Matricula","Apellidos");
        cBCriterio.setItems(criterio);
        tCMatricula.setCellValueFactory(new PropertyValueFactory<>("Matricula"));
        tCNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));        
        tCApellidos.setCellValueFactory(new PropertyValueFactory<>("Apellidos"));        
        tCStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
    }
}
