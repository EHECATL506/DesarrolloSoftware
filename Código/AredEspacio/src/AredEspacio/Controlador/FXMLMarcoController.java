package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.TipoDeMenu;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class FXMLMarcoController extends MainController implements Initializable {

    @FXML
    public void modificarGrupo() {
        this.escena.cargarEscena(EscenaPrincipal.EscenaBuscarGrupo);
    }

    @FXML
    public void registrarGrupo() {
        this.escena.cargarEscena(EscenaPrincipal.EscenaGrupo);
    }

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
