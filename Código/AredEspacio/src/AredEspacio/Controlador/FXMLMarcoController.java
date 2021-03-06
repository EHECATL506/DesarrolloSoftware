package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.TipoDeMenu;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class FXMLMarcoController extends MainController implements Initializable {
    
    @FXML
    public void pagarMensualidad(){
        this.escena.cargarEscena(EscenaPrincipal.EscenaPagarMensualidad);
    }
    
    @FXML
    public void pagarMaestro() {
        this.escena.cargarEscenaConParametros(EscenaPrincipal.EscenaBuscarMaestro,
                null, TipoDeMenu.PAGO);
    }
   
    @FXML
    public void verNotificaciones() {
        this.escena.cargarEscena(EscenaPrincipal.EscenaNotificaciones);
    }
    
    @FXML
    public void verPromociones() {
        this.escena.cargarEscena(EscenaPrincipal.EscenaPromocion);
    }

    @FXML
    public void paseDeAsistencia() {
        this.escena.cargarEscena(EscenaPrincipal.EscenaPaseDeLista);
    }

    @FXML
    public void cambiarAlumnoDeGrupo() {
        this.escena.cargarEscena(EscenaPrincipal.EscenaCambiarDeGrupo);
    }

    @FXML
    public void modificarGrupo() {
        this.escena.cargarEscenaConParametros(EscenaPrincipal.EscenaBuscarGrupo,
                null, TipoDeMenu.MODIFICAR);
    }

    @FXML
    public void deshabilitarGrupo() {
        this.escena.cargarEscenaConParametros(EscenaPrincipal.EscenaBuscarGrupo,
                null, TipoDeMenu.DESHABILITAR);
    }

    @FXML
    public void listaDeDanzas() {
        this.escena.cargarEscena(EscenaPrincipal.EscenaListaDeDanza);
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
    public void consultarMaestro() {
        this.escena.cargarEscenaConParametros(
                EscenaPrincipal.EscenaBuscarMaestro, null, TipoDeMenu.CONSULTAR);
    }

    @FXML
    public void modificarMaestro() {
        this.escena.cargarEscenaConParametros(
                EscenaPrincipal.EscenaBuscarMaestro, null, TipoDeMenu.MODIFICAR);
    }

    @FXML
    public void deshabilitarMaestro() {
        this.escena.cargarEscenaConParametros(
                EscenaPrincipal.EscenaBuscarMaestro, null, TipoDeMenu.DESHABILITAR);
    }

    //Administrar Alumno
    @FXML
    public void registrarAlumno() {
        escena.cargarEscenaConParametros(EscenaPrincipal.EscenaRegistrarAlumno, null, TipoDeMenu.REGISTRAR);
    }

    @FXML
    public void consultarAlumno() {
        escena.cargarEscenaConParametros(EscenaPrincipal.EscenaBuscarAlumno, null, TipoDeMenu.CONSULTAR);
    }

    @FXML
    public void modificarAlumno() {
        escena.cargarEscenaConParametros(EscenaPrincipal.EscenaBuscarAlumno, null, TipoDeMenu.MODIFICAR);
    }

    @FXML
    public void eliminarAlumno() {
        escena.cargarEscenaConParametros(EscenaPrincipal.EscenaBuscarAlumno, null, TipoDeMenu.ELIMINAR);
    }

    //Pagos e Incripciones
    @FXML
    public void incribirAlumno() {
        escena.cargarEscenaConParametros(EscenaPrincipal.EscenaBuscarAlumno, null, TipoDeMenu.INCRIBIR);
    }

    public void registrarEgreso() {
        this.escena.cargarEscena(EscenaPrincipal.EscenaRegistrarEgreso);
    }

    @FXML
    public void consulatrReporteDeEgresos() {
        this.escena.cargarEscena(EscenaPrincipal.EscenaReporteDeEgresos);
    }
    
    @FXML
    public void consultarReporteDeIngresos(){
        this.escena.cargarEscena(EscenaPrincipal.EscenaReporteDeIngresos);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
             
    }
}
