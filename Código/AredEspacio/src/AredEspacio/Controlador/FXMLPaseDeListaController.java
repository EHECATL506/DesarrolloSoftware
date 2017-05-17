package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.Asistencia;
import Modelo.Clase;
import Modelo.Grupo;
import Modelo.Mensaje;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class FXMLPaseDeListaController extends MainController implements Initializable {

    @FXML
    private TableView tGrupos;

    @FXML
    private TableColumn cID;

    @FXML
    private TableColumn cSalon;

    @FXML
    private TableColumn cDanza;

    @FXML
    private TableColumn cNivel;

    @FXML
    private TableColumn cMaestro;

    @FXML
    private VBox vNombre;

    private Map<CheckBox, Clase> checkList;

    @FXML
    void desmarcarTodos(ActionEvent event) {
        this.checkList.forEach(
                (check, clase) -> check.setSelected(false)
        );
    }

    @FXML
    void marcarTodos(ActionEvent event) {
        this.checkList.forEach(
                (check, clase) -> check.setSelected(true)
        );
    }

    @FXML
    void registrarAsistencia(ActionEvent event) {
        Date fecha = new Date(new GregorianCalendar().getTimeInMillis());
        try {
            
            if(!this.checkList.isEmpty()){
            
            this.checkList.forEach((check, clase)
                    -> {
                Asistencia asistencia = new Asistencia();
                asistencia.setIdClase(clase);
                asistencia.setAsistio(check.isSelected());
                asistencia.setFecha(fecha);
                asistencia.crear();
            });
            Mensaje.informacion("Exito!! al registrar la asistencia del grupo");
            this.escena.cargarEscena(EscenaPrincipal.EscenaPaseDeLista);
            }else{
                Mensaje.advertencia("Seleccione un grupo y asigné la asistencia");
            }
        } catch (Exception e) {
            Mensaje.advertencia("No hay conexión, Intentelo mas tarde");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.cID.setCellValueFactory(
                new PropertyValueFactory<>("IdGrupo")
        );
        this.cSalon.setCellValueFactory(
                new PropertyValueFactory<>("Salon")
        );
        this.cDanza.setCellValueFactory(
                new PropertyValueFactory<>("TipoDeDanza")
        );

        this.cNivel.setCellValueFactory(
                new PropertyValueFactory<>("Nivel")
        );

        this.cMaestro.setCellValueFactory(
                new PropertyValueFactory<>("Maestro")
        );
        this.checkList = new HashMap<>();

        ArrayList<Grupo> grupos = new ArrayList<>();
        Grupo.listarGrupos().forEach((grupo) -> {
            if (grupo.getFinDeGrupo() == null) {
                grupos.add(grupo);
            }
        });

        ObservableList lista = FXCollections.observableArrayList(grupos);
        this.tGrupos.setItems(lista);

        Node tituloNombre = this.vNombre.getChildren().get(0);
        this.tGrupos.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    Grupo grupo = (Grupo) newSelection;
                    this.vNombre.getChildren().clear();
                    this.vNombre.getChildren().add(tituloNombre);
                    this.checkList.clear();
                    //---->Clase
                    Clase.obtenerClasesDelGrupo(grupo.getIdGrupo()).forEach(
                            (clase) -> {
                                String nombre = "        "
                                + clase.getNombre() + " " + clase.getApellidos();
                                CheckBox check = new CheckBox(nombre);
                                this.checkList.put(check, clase);
                                this.vNombre.getChildren().add(check);
                            }
                    );
                });

    }
}
