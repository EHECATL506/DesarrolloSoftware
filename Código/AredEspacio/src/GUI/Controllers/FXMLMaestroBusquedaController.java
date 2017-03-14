package GUI.Controllers;

import GUI.EscenaPrincipal;
import JPA.ClasesEntidad.Maestro;
import Modelo.Enumeradores.TipoMenu;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class FXMLMaestroBusquedaController extends MainController implements Initializable {

    @FXML
    private Label titulo;
    @FXML
    private ComboBox tipoDeBusqueda;
    @FXML
    private TextField datoDeBusqueda;
    @FXML
    private Button buscar;
    @FXML
    private TableView tabla;
    @FXML
    private TableColumn columnaNoDeColaborador;
    @FXML
    private TableColumn columnaNombre;
    @FXML
    private TableColumn columnaTelefono;
    @FXML
    private TableColumn columnaCorreo;
    @FXML
    private Button opcion;

    @FXML
    public void buscarMaestro() {
        List<Maestro> maestros = new ArrayList<>();
        if (this.tipoDeBusqueda.getValue() != null) {
            if (this.tipoDeBusqueda.getValue().equals("Nombre")) {
                maestros = Maestro.obtenerCoincidenciasPorNombre(this.datoDeBusqueda.getText());
            } else {
                maestros = Maestro.obtenerCoincidenciasPorNoDeColaborador(this.datoDeBusqueda.getText());
            }
            ObservableList lista = FXCollections.observableArrayList(maestros);
            this.tabla.setItems(lista);
            if (maestros.size() > 0) {
                this.opcion.setDisable(false);
            }else{
                this.opcion.setDisable(true);
            }
        }
    }

    @FXML
    public void cargarSiguienteEscena() {
      this.escena.cargarEscenaConTipoDeMenu(EscenaPrincipal.EscenaMaestro, this.tipoMenu);
    }

    public void cargarConsulta() {
        this.titulo.setText("Consultar Información de Maestro");
        this.titulo.setLayoutX(176);
        this.opcion.setText("Consultar");
        this.opcion.setLayoutX(680);
    }

    public void cargarModificar() {
        this.titulo.setText("Modificar Maestro");
        this.titulo.setLayoutX(260);
        this.opcion.setText("Modificar");
        this.opcion.setLayoutX(680);
    }

    public void cargarDeshabilitar() {
        this.titulo.setText("Deshabilitar Maestro");
        this.titulo.setLayoutX(245);
        this.opcion.setText("Deshabilitar");
        this.opcion.setLayoutX(670);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.columnaCorreo.setCellValueFactory(
                new PropertyValueFactory<>("CorreoElectronico")
        );
        this.columnaNoDeColaborador.setCellValueFactory(
                new PropertyValueFactory<>("NoDeColaborador")
        );
        this.columnaNombre.setCellValueFactory(
                new PropertyValueFactory<>("Nombre")
        );
        this.columnaTelefono.setCellValueFactory(
                new PropertyValueFactory<>("Telefono")
        );

        ObservableList<String> opciones = FXCollections.observableArrayList(
                "No. de Colaborador", "Nombre"
        );
        this.opcion.setDisable(true);
        this.tipoDeBusqueda.setItems(opciones);
        Platform.runLater(() -> {
            if (this.tipoMenu == TipoMenu.CONSULTAR) {
                this.cargarConsulta();
            } else {
                if (this.tipoMenu == TipoMenu.MODIFICAR) {
                    this.cargarModificar();
                } else {
                    this.cargarDeshabilitar();
                }
            }
        });
    }
}
