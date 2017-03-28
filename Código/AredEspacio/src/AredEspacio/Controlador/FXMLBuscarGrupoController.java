package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.Grupo;
import Modelo.Mensaje;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLBuscarGrupoController extends MainController implements Initializable {

    @FXML
    private TableView tGrupo;

    @FXML
    private TableColumn cId;

    @FXML
    private TableColumn cSalon;

    @FXML
    private TableColumn cTipoDeDanza;

    @FXML
    private TableColumn cMaestro;

    @FXML
    void cargarModificar(ActionEvent event) {
        Grupo grupo = (Grupo) this.tGrupo.getSelectionModel().getSelectedItem();
        if(grupo==null){
            Mensaje.advertencia("Seleccione un Grupo");
        }else{
            this.escena.cargarEscenaConParametros(EscenaPrincipal.EscenaModificarGrupo, grupo, null);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cId.setCellValueFactory(
                new PropertyValueFactory<>("IdGrupo")
        );
        this.cSalon.setCellValueFactory(
                new PropertyValueFactory<>("Salon")
        );
        this.cTipoDeDanza.setCellValueFactory(
                new PropertyValueFactory<>("TipoDeDanza")
        );
        this.cMaestro.setCellValueFactory(
                new PropertyValueFactory<>("Maestro")
        );
        ObservableList lista = FXCollections.observableArrayList(
                Grupo.listarGrupos()
        );
        this.tGrupo.setItems(lista);
    }
}
