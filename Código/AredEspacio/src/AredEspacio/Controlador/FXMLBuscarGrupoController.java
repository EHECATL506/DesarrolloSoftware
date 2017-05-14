package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.Grupo;
import Modelo.Mensaje;
import Modelo.TipoDeMenu;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLBuscarGrupoController extends MainController implements Initializable {

    @FXML
    private Button modificar;

    @FXML
    private Button deshabilitar;

    @FXML
    private TableView tGrupo;

    @FXML
    private TableColumn cId;

    @FXML
    private TableColumn cSalon;

    @FXML
    private TableColumn cTipoDeDanza;
    
    @FXML
    private TableColumn cNivel;

    @FXML
    private TableColumn cMaestro;

    @FXML
    void cargarModificar(ActionEvent event) {
        Grupo grupo = (Grupo) this.tGrupo.getSelectionModel().getSelectedItem();
        if (grupo == null) {
            Mensaje.advertencia("Seleccione un Grupo");
        } else {
            this.escena.cargarEscenaConParametros(EscenaPrincipal.EscenaModificarGrupo, grupo, null);
        }
    }

    @FXML
    void deshabilitarGrupo() {
        Grupo grupo = (Grupo) this.tGrupo.getSelectionModel().getSelectedItem();
        if (grupo == null) {
            Mensaje.advertencia("Seleccione un Grupo");
        } else {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Deshabilitar Grupo");
            alerta.setHeaderText(null);
            alerta.setContentText("¿Esta seguro de Eliminar el Grupo con ID: "+grupo.getIdGrupo()+"?");
            Optional<ButtonType> resultado = alerta.showAndWait();
            if (resultado.get() == ButtonType.OK) {
                grupo.setFinDeGrupo(new Date(new GregorianCalendar().getTimeInMillis()));
                try {
                    grupo.actualizar(new ArrayList(), new ArrayList());
                    this.escena.cargarEscenaConParametros(EscenaPrincipal.EscenaBuscarGrupo
                            ,null, TipoDeMenu.DESHABILITAR);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Mensaje.advertencia("No hay conexión, Intentelo mas tarde");
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            if (this.tipoMenu == TipoDeMenu.MODIFICAR) {
                this.deshabilitar.setVisible(false);
            } else {
                this.modificar.setVisible(false);
            }
        });

        this.cId.setCellValueFactory(
                new PropertyValueFactory<>("IdGrupo")
        );
        this.cSalon.setCellValueFactory(
                new PropertyValueFactory<>("Salon")
        );
        this.cTipoDeDanza.setCellValueFactory(
                new PropertyValueFactory<>("TipoDeDanza")
        );
        this.cNivel.setCellValueFactory(
                new PropertyValueFactory<>("Nivel")
        );
        this.cMaestro.setCellValueFactory(
                new PropertyValueFactory<>("Maestro")
        );

        List<Grupo> grupos = new ArrayList<>();
        for (Grupo grupo : Grupo.listarGrupos()) {
            if (grupo.getFinDeGrupo() == null) {
                grupos.add(grupo);
            }
        }

        ObservableList lista = FXCollections.observableArrayList(
                grupos
        );

        this.tGrupo.setItems(lista);
    }
}
