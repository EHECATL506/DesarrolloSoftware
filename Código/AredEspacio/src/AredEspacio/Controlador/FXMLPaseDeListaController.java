package AredEspacio.Controlador;

import Modelo.Clase;
import Modelo.Grupo;
import Modelo.TipoDeMenu;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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

    private ArrayList<CheckBox> checkList;

    @FXML
    void desmarcarTodos(ActionEvent event) {
        this.checkList.forEach(
                (check) -> check.setSelected(false)
        );
    }

    @FXML
    void marcarTodos(ActionEvent event) {
        this.checkList.forEach(
                (check) -> check.setSelected(true)
        );
    }

    @FXML
    void registrarAsistencia(ActionEvent event) {

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
        this.checkList = new ArrayList<>();

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
                    Clase.buscarClasesPorIdDeGrupo(grupo.getIdGrupo()).forEach(
                            (clase) -> {
                                String nombre = "        "
                                + clase.getNombre() + " " + clase.getApellidos();
                                CheckBox check = new CheckBox(nombre);
                                check.setId(""+clase.getIdClase());
                                
                                this.checkList.add(check);
                                this.vNombre.getChildren().add(check);
                            }
                    );
                });

    }
}
