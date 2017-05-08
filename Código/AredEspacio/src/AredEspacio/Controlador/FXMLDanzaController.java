package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.Danza;
import Modelo.Mensaje;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLDanzaController extends MainController implements Initializable {

    @FXML
    private TableView tDanza;

    @FXML
    private TableColumn cID;

    @FXML
    private TableColumn cDanza;

    @FXML
    private Button bModificar;

    @FXML
    private Button bEliminar;

    @FXML
    void eliminarDanza(ActionEvent event) {
        Danza danza = (Danza) this.tDanza.getSelectionModel().getSelectedItem();
        if (danza == null) {
            Mensaje.advertencia("Seleccione una Danza");
        } else {
            Alert alerta = new Alert(AlertType.CONFIRMATION);
            alerta.setTitle("Eliminar Danza");
            alerta.setHeaderText("¿Esta seguro de Eliminar la Danza?");
            alerta.setContentText("Danza: " + danza.getIdDanza() + "-" + danza.getTipoDanza());
            Optional<ButtonType> resultado = alerta.showAndWait();
            if (resultado.get() == ButtonType.OK) {
                try {
                    danza.eliminar();
                    this.escena.cargarEscena(EscenaPrincipal.EscenaListaDeDanza);
                } catch (Exception ex) {
                    Mensaje.advertencia("No hay conexión,Intentelo mas tarde");
                }
            }
        }
    }

    @FXML
    void modificarDanza(ActionEvent event) {
        Danza danza = (Danza) this.tDanza.getSelectionModel().getSelectedItem();
        if (danza == null) {
            Mensaje.advertencia("Seleccione un Danza");
        } else {
            TextInputDialog dialogo = new TextInputDialog(danza.getTipoDanza());
            dialogo.setTitle("Modificar Danza");
            dialogo.setHeaderText("Ingrese el nuevo nombre de la danza");
            dialogo.setContentText("Danza: ");
            Optional<String> resultado = dialogo.showAndWait();
            resultado.ifPresent((nombreDanza) -> {
                danza.setTipoDanza(nombreDanza);
                try {
                    danza.actualizar();
                    this.escena.cargarEscena(EscenaPrincipal.EscenaListaDeDanza);
                } catch (Exception ex) {
                    Mensaje.advertencia("No hay conexión,Intentelo mas tarde");
                }
            });
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.cID.setCellValueFactory(
                new PropertyValueFactory<>("IdDanza")
        );
        this.cDanza.setCellValueFactory(
                new PropertyValueFactory<>("TipoDanza")
        );

        this.tDanza.setItems(FXCollections.observableArrayList(
                Danza.obtenerTodas()
        ));
    }
}
