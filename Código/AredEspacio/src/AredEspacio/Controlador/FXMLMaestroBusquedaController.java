package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.Maestro;
import Modelo.TipoDeMenu;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TableColumn columnaApellidos;
    @FXML
    private TableColumn columnaEstado;
    @FXML
    private Button consultar;
    @FXML
    private Button modificar;
    @FXML
    private Button deshabilitar;

    @FXML
    public void buscarMaestro() {
        List<Maestro> maestros = new ArrayList<>();
        if (this.tipoDeBusqueda.getValue() != null) {
            if (this.tipoDeBusqueda.getValue().equals("Nombre")) {
                maestros = Maestro.obtenerMaestroPorNombre(this.datoDeBusqueda.getText());
            } else {
                if (this.tipoDeBusqueda.getValue().equals("Apellido")) {
                    maestros = Maestro.obtenerMaestroPorApellido(this.datoDeBusqueda.getText());
                } else {
                    maestros = Maestro.obtenerMaestroPorNoDeColaborador(this.datoDeBusqueda.getText());
                }
            }
            ObservableList lista = FXCollections.observableArrayList(maestros);
            this.tabla.setItems(lista);
            if (maestros.size() > 0) {
                this.consultar.setDisable(false);
                this.modificar.setDisable(false);
                this.deshabilitar.setDisable(false);
            } else {
                this.consultar.setDisable(true);
                this.modificar.setDisable(true);
                this.deshabilitar.setDisable(true);
            }
        }
    }

    @FXML
    public void cargarConsultar() {
        this.tipoMenu = TipoDeMenu.CONSULTAR;
        this.cargarSiguienteEscena();
    }

    @FXML
    public void cargarModificar() {
        this.tipoMenu = TipoDeMenu.MODIFICAR;
        this.cargarSiguienteEscena();
    }

    @FXML
    public void cargarDeshabilitar() {
        this.tipoMenu = TipoDeMenu.DESHABILITAR;
        this.cargarSiguienteEscena();
    }

    public void cargarSiguienteEscena() {
        try {
            Maestro maestro = (Maestro) this.tabla.getSelectionModel().getSelectedItem();
            this.escena.cargarEscenaConParametros(EscenaPrincipal.EscenaMaestro,
                    maestro, this.tipoMenu);
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setHeaderText(null);
            alerta.setTitle("Información");
            alerta.setContentText("Seleccione un Maestro");
            alerta.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.tabla.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    Maestro maestro = (Maestro) this.tabla.getSelectionModel().getSelectedItem();
                    if(maestro.getDeshabilitado()){
                        this.deshabilitar.setText("Habilitar");
                        //this.deshabilitar.setPrefSize(78, 25);
                    }else{
                        this.deshabilitar.setText("Deshabilitar");
                        //this.deshabilitar.setPrefSize(78, 25);
                    }
                });

        this.columnaNoDeColaborador.setCellValueFactory(
                new PropertyValueFactory<>("NoDeColaborador")
        );
        this.columnaNombre.setCellValueFactory(
                new PropertyValueFactory<>("Nombre")
        );
        this.columnaApellidos.setCellValueFactory(
                new PropertyValueFactory<>("Apellidos")
        );

        this.columnaEstado.setCellValueFactory(
                new PropertyValueFactory<>("EstadoDeMaestro")
        );

        ObservableList<String> opciones = FXCollections.observableArrayList(
                "No. de Colaborador", "Nombre", "Apellido"
        );
        this.consultar.setDisable(true);
        this.modificar.setDisable(true);
        this.deshabilitar.setDisable(true);
        this.tipoDeBusqueda.setItems(opciones);
    }
}
