package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.Clase;
import Modelo.Grupo;
import Modelo.Mensaje;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLCambioDeGrupoController extends MainController implements Initializable {

    @FXML
    private TableView tGruposOrigen;

    @FXML
    private TableColumn cIdOrigen;

    @FXML
    private TableColumn cSalonOrigen;

    @FXML
    private TableColumn cDanzaOrigen;

    @FXML
    private TableColumn cMaestroOrigen;

    @FXML
    private TableView tAlumnosOrigen;

    @FXML
    private TableColumn cMatriculaOrigen;

    @FXML
    private TableColumn cNombreOrigen;

    @FXML
    private TableColumn cApellidoOrigen;

    @FXML
    private TableView tGruposDestino;

    @FXML
    private TableColumn cIdDestino;

    @FXML
    private TableColumn cSalonDestino;

    @FXML
    private TableColumn cDanzaDestino;

    @FXML
    private TableColumn cMaestroDestino;

    @FXML
    private TableView tAlumnosDestino;

    @FXML
    private TableColumn cMatriculaDestino;

    @FXML
    private TableColumn cNombreDestino;

    @FXML
    private TableColumn cApellidoDestino;

    @FXML
    void cambiarAlumnos(ActionEvent event) {
        if (this.alumnosDestino.isEmpty()) {
            Mensaje.informacion("Agregue minimo un alumno al grupo destino");
        } else {
            Grupo grupo = (Grupo) this.tGruposDestino.getSelectionModel().getSelectedItem();
            if (grupo == null) {
                Mensaje.informacion("Seleccione el grupo destino");
            } else {
                try {
                    for (Clase clase : this.alumnosDestino) {
                        clase.cambiarDeGrupo(grupo);
                    }
                    Mensaje.informacion("Exito! al cambiar de Grupo");
                    this.escena.cargarEscena(EscenaPrincipal.EscenaCambiarDeGrupo);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Mensaje.advertencia("No hay conexión con la Base de Datos");
                }
            }
        }
    }

    @FXML
    void removerlumno(ActionEvent event) {
        Clase clase = (Clase) this.tAlumnosOrigen.getSelectionModel().getSelectedItem();
        if (clase != null) {
            this.alumnosDestino.remove(clase);
            ObservableList clasesDestino = FXCollections.observableArrayList(this.alumnosDestino);
            this.tAlumnosDestino.setItems(clasesDestino);
        }
    }

    private ArrayList<Clase> alumnosDestino;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.alumnosDestino = new ArrayList();
//Inicializar Grupos origen y Destino       
        this.cIdOrigen.setCellValueFactory(new PropertyValueFactory<>("IdGrupo"));
        this.cSalonOrigen.setCellValueFactory(new PropertyValueFactory<>("Salon"));
        this.cDanzaOrigen.setCellValueFactory(new PropertyValueFactory<>("TipoDeDanza"));
        this.cMaestroOrigen.setCellValueFactory(new PropertyValueFactory<>("Maestro"));

        this.cIdDestino.setCellValueFactory(new PropertyValueFactory<>("IdGrupo"));
        this.cSalonDestino.setCellValueFactory(new PropertyValueFactory<>("Salon"));
        this.cDanzaDestino.setCellValueFactory(new PropertyValueFactory<>("TipoDeDanza"));
        this.cMaestroDestino.setCellValueFactory(new PropertyValueFactory<>("Maestro"));

        ObservableList lista = FXCollections.observableArrayList(Grupo.listarGrupos());
        this.tGruposOrigen.setItems(lista);
        this.tGruposDestino.setItems(lista);
//Inicializar 

        this.cMatriculaOrigen.setCellValueFactory(new PropertyValueFactory<>("Matricula"));
        this.cNombreOrigen.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        this.cApellidoOrigen.setCellValueFactory(new PropertyValueFactory<>("Apellidos"));

        this.cMatriculaDestino.setCellValueFactory(new PropertyValueFactory<>("Matricula"));
        this.cNombreDestino.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        this.cApellidoDestino.setCellValueFactory(new PropertyValueFactory<>("Apellidos"));

        this.tGruposOrigen.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    Grupo grupo = (Grupo) this.tGruposOrigen.getSelectionModel().getSelectedItem();
                    ObservableList clases = FXCollections.observableArrayList(
                            Clase.obtenerClasesDelGrupo(grupo.getIdGrupo())
                    );
                    this.tAlumnosOrigen.setItems(clases);
                });

        this.tAlumnosOrigen.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    Clase clase = (Clase) this.tAlumnosOrigen.getSelectionModel().getSelectedItem();
                    if (!this.alumnosDestino.contains(clase) && clase != null) {
                        this.alumnosDestino.add(clase);
                        ObservableList clasesDestino = FXCollections.observableArrayList(this.alumnosDestino);
                        this.tAlumnosDestino.setItems(clasesDestino);
                    }
                });
    }
}
