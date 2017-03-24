package GUI.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FXMLGrupoController implements Initializable {

    @FXML
    private TableView tabla;
    @FXML
    private TableColumn cNoDeColaborador;
    @FXML
    private TableColumn cNombre;
    @FXML
    private TableColumn cApellidos;
    @FXML
    private TextField tfSalon;
    @FXML
    private TextField tfTipoDeDanza;
    @FXML
    private TextField tfMaestro;
    @FXML
    private Button bBuscar;
    @FXML
    private Button bRegistrar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }        

    @FXML
    private void buscarMaestro(ActionEvent event) {
    }

    @FXML
    private void registrarGrupo(ActionEvent event) {
    }
}
