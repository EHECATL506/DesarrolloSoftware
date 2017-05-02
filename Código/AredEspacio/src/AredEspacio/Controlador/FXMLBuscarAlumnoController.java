/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import ControladorBD.AlumnoJpaController;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
    @FXML
    private Button bClicAccion;
    
    private AlumnoJpaController jpaAlumno;
/*
    //buscarPorApellidos
    public List<Alumno> buscarPorApellidos(String busqueda) {
        List<Alumno> alumnos = jpaAlumno.obtenerPorApellidos(busqueda);
        if (alumnos.size() > 0) {
            bConsultar.setDisable(false);
            bModificar.setDisable(false);
            bEliminar.setDisable(false);
            bInscribir.setDisable(false);
        }
        return alumnos;
    }
    
    //buscarPorMatricula
    public List<Alumno> buscarPorMatricula (String busqueda) {
        List<Alumno> alumnos = jpaAlumno.obtenerPorMatricula(busqueda);
        if (alumnos.size() > 0) {
            bConsultar.setDisable(false);
            bModificar.setDisable(false);
            bEliminar.setDisable(false);
            bInscribir.setDisable(false);
        }
        return alumnos;
    }
  */  
    //desplegarAlumnos
    public void desplegarAlumnos () {
        List<Alumno> alumnos = jpaAlumno.findAlumnoEntities();
        if (alumnos.size() > 0) {
            ObservableList lista = FXCollections.observableArrayList(alumnos);
            tVResultados.setItems(lista);
            bClicAccion.setDisable(false);
            bClicAccion.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (getTipoDeMenu().equals(TipoDeMenu.INCRIBIR)) {
                       
                        inscribir();
                    }
                    if (getTipoDeMenu().equals(TipoDeMenu.CONSULTAR)) {
                        bClicAccion.setText("Consultar");
                        consultar();
                    }
                    if (getTipoDeMenu().equals(TipoDeMenu.MODIFICAR)) {
                        bClicAccion.setText("Modificar");
                        modificar();
                    }
                    else if (getTipoDeMenu().equals(TipoDeMenu.ELIMINAR)) {
                        bClicAccion.setText("Eliminar");
                        eliminar();
                    }                
                }            
            });
        }
    }
    //clicBuscar
    @FXML
    void buscar(ActionEvent event) {
        String busqueda = tFBusqueda.getText();
        /*
        if (cBCriterio.getValue() == "Apellidos") {
            List<Alumno> alumnos = buscarPorApellidos(busqueda);
            ObservableList lista = FXCollections.observableArrayList(alumnos);
            tVResultados.setItems(lista);
        } else if (cBCriterio.getValue() == "Matricula") {
            List<Alumno> alumnos = buscarPorMatricula(busqueda);
            ObservableList lista = FXCollections.observableArrayList(alumnos);
            tVResultados.setItems(lista);
        } else if (cBCriterio.getValue() == null) {
            Mensaje.advertencia("No se ha seleccionado el criterio");
        }
*/
    }
    
    //clicIncribir
    private void inscribir() {
        bClicAccion.setText("Incribir");
        Alumno row = tVResultados.getSelectionModel().getSelectedItem();
        if (row != null) {
            escena.cargarEscenaConParametros(EscenaPrincipal.EscenaIncribirAlumno, row, TipoDeMenu.INCRIBIR);
        } else {
            Mensaje.advertencia("No se ha seleccionado el alumno");
        }
    }

    //clicConsultar
    private void consultar() {
        Alumno row = tVResultados.getSelectionModel().getSelectedItem();
        if (row != null) {
            escena.cargarEscenaConParametros(EscenaPrincipal.EscenaRegistrarAlumno, row, TipoDeMenu.CONSULTAR);
        } else {
            Mensaje.advertencia("No se ha seleccionado el alumno");
        }
    }

    //clicDarDeBaja
    private void eliminar() {
        Alumno row = tVResultados.getSelectionModel().getSelectedItem();
        if (row != null) {
            escena.cargarEscenaConParametros(EscenaPrincipal.EscenaEliminarAlumno, row, TipoDeMenu.ELIMINAR);
        } else {
            Mensaje.advertencia("No se ha seleccionado el alumno");
        }
    }

    //clicModificar
    private void modificar() {
        Alumno row = tVResultados.getSelectionModel().getSelectedItem();
        if (row != null) {
            escena.cargarEscenaConParametros(EscenaPrincipal.EscenaRegistrarAlumno, row, TipoDeMenu.MODIFICAR);
        } else {
            Mensaje.advertencia("No se ha seleccionado el alumno");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList criterio = FXCollections.observableArrayList("Matricula", "Apellidos");
        cBCriterio.setItems(criterio);
        tCMatricula.setCellValueFactory(new PropertyValueFactory<>("Matricula"));
        tCNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        tCApellidos.setCellValueFactory(new PropertyValueFactory<>("Apellidos"));
        tCStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
        jpaAlumno = new AlumnoJpaController(emf);
        desplegarAlumnos();

    }
}
