package GUI.Controllers;

import Modelo.Enumeradores.TipoMenu;
import GUI.EscenaPrincipal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class FXMLMarcoController extends MainController implements Initializable {

    @FXML
    MenuBar menu;
    @FXML
    Menu alumno;
    @FXML
    Menu maestro;
    @FXML
    MenuItem itemRegistrarAlumno;
    
    @FXML
    MenuItem itemRegistrarMaestro;
    @FXML
    MenuItem itemVerMaestro;
    @FXML
    MenuItem itemModificarMaestro;
    @FXML
    MenuItem itemDeshabilitarMaestro;

    @FXML
    public void registrarAlumno() {
        this.escena.cargarEscena(EscenaPrincipal.EscenaMaestro);
    }

    //Metodos del Menu del Maestro
    @FXML
    public void registrarMaestro() {
        this.escena.cargarEscena(EscenaPrincipal.EscenaMaestro);
    }

    @FXML
    public void consultarMaestro() {
        this.escena.cargarEscenaConTipoDeMenu(EscenaPrincipal.EscenaBusquedaMaestro, TipoMenu.CONSULTAR);
    }

    @FXML
    public void modificarMaestro() {
        this.escena.cargarEscenaConTipoDeMenu(EscenaPrincipal.EscenaBusquedaMaestro, TipoMenu.MODIFICAR);
    }

    @FXML
    public void deshabilitarMaestro() {
        this.escena.cargarEscenaConTipoDeMenu(EscenaPrincipal.EscenaBusquedaMaestro, TipoMenu.DESHABILITAR);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }

}
