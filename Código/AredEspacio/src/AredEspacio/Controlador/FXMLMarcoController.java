package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.TipoDeMenu;
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
    MenuItem itemRegistrarMaestro;
    @FXML
    MenuItem itemBuscarMaestro;
    @FXML
    MenuItem itemRegistrarAlumno;
    @FXML
    MenuItem itemBuscarAlumno;

    @FXML
    public void registrarMaestro() {
        this.escena.cargarEscena(EscenaPrincipal.EscenaMaestro);
    }

    @FXML
    public void buscarMaestro() {
        this.escena.cargarEscenaConParametros(EscenaPrincipal.EscenaBuscarMaestro, null, null);
    }

    @FXML
    public void registrarAlumno() {
        escena.cargarEscenaConParametros(EscenaPrincipal.EscenaRegistrarAlumno, null, TipoDeMenu.REGISTRAR);
    }

    @FXML
    public void buscarAlumno() {
        escena.cargarEscena(EscenaPrincipal.EscenaBuscarAlumno);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
