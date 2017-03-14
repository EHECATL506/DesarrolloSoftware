package GUI.Controllers;

import Modelo.ControlDeImagenes;
import Modelo.Enumeradores.TipoMenu;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXMLMaestroController extends MainController implements Initializable {

    @FXML
    private Label titulo;
    @FXML
    private TextField campoNombre;
    @FXML
    private TextField campoApellido;
    @FXML
    private DatePicker fechaNacimiento;
    @FXML
    private RadioButton generoMasculino;
    @FXML
    private RadioButton generoFemenino;
    @FXML
    private TextField campoCorreo;
    @FXML
    private TextField campoTelefono;
    @FXML
    private TextField campoMovil;
    @FXML
    private TextField campoDomicilio;
    @FXML
    private TextField campoCodigoPostal;
    @FXML
    private TextField campoCiudad;
    @FXML
    private TextField campoEstado;
    @FXML
    private Button botonOpcion;
    @FXML
    private Button agregarFoto;
    @FXML
    private Label labelDomicilio;
    @FXML
    private Label labelCiudad;
    @FXML
    private TextArea motivo;
    @FXML
    private ImageView foto = null;
    private String rutaFoto;

    @FXML
    private void buscarFoto() {
        this.rutaFoto = new ControlDeImagenes().buscarImagen();
        if (this.rutaFoto == null) {
            this.foto.setImage(new Image("Recursos/Imagenes/ErrorDeImagen.png"));
        } else {
            this.foto.setImage(
                    new Image("file:" + this.rutaFoto)
            );
        }
    }

    @FXML
    private void realizarFuncion() {
        switch (this.botonOpcion.getText()) {
            case "Registrar":
                if (this.validarInformacion()) {

                }
                break;
            case "Consultar":
                break;
            case "Modificar":
                break;
            case "Deshabilitar":
                break;
        }
    }

    private void cargarInformacionDeMaestro() {
        //TODO
    }

    private boolean esVacio(TextField campo) {
        //TODO agregar dato es vacio
        return campo.getText().equals("");
    }

    private boolean esNumerico(TextField campo) {
        try {
            //TODO agregar valor no es numerico
            Integer.parseInt(campo.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean seSeleccionoGenero() {
        //TODO agregar no se selecciono genero
        return this.generoFemenino.isSelected() || this.generoMasculino.isSelected();
    }

    private boolean validarInformacion() {
        if (esVacio(this.campoApellido)
                || esVacio(this.campoCiudad)
                || esVacio(this.campoCodigoPostal)
                || esVacio(this.campoCorreo)
                || esVacio(this.campoDomicilio)
                || esVacio(this.campoEstado)
                || esVacio(this.campoMovil)
                || esVacio(this.campoNombre)
                || esVacio(this.campoTelefono)
                || this.rutaFoto == null
                || !this.seSeleccionoGenero()
                || this.fechaNacimiento.getValue() == null) {
            return false;
        } else {
            return this.esNumerico(this.campoMovil)
                    && this.esNumerico(this.campoTelefono)
                    && this.esNumerico(this.campoCodigoPostal);
        }
    }

    private void cargarRegistrar() {
        this.titulo.setText("Registrar Maestro");
        this.titulo.setLayoutX(300);
        this.botonOpcion.setText("Registrar");
        this.botonOpcion.setLayoutX(615);
        this.foto.setImage(new Image("Recursos/Imagenes/ImagenPorDefecto.png"));
    }

    private void cargarConsultar() {
        this.titulo.setText("Consultar Información de Maestro");
        this.titulo.setLayoutX(240);
        this.botonOpcion.setVisible(false);
        this.cargarInformacionDeMaestro();
    }

    private void cargarModificar() {
        this.titulo.setText("Modificar Maestro");
        this.titulo.setLayoutX(315);
        this.botonOpcion.setText("Modificar");
        this.botonOpcion.setLayoutX(605);
        this.cargarInformacionDeMaestro();
    }

    private void cargarDeshabilitar() {
        this.titulo.setText("Deshabilitar Maestro");
        this.titulo.setLayoutX(315);
        this.motivo.setVisible(true);
        this.labelDomicilio.setText("Motivo:");
        this.labelCiudad.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.labelCiudad.setText("Ciudad:");
        this.labelDomicilio.setText("Domicilio:");
        this.motivo.setVisible(false);
        Platform.runLater(() -> {
            if (this.parametros == null) {
                this.cargarRegistrar();
            } else {
                if (TipoMenu.CONSULTAR == this.tipoMenu) {
                    this.cargarConsultar();
                } else {
                    if (TipoMenu.MODIFICAR == this.tipoMenu) {
                        this.cargarModificar();
                    } else {
                        if (TipoMenu.DESHABILITAR == this.tipoMenu) {
                            this.cargarDeshabilitar();
                        }
                    }
                }
            }
        });
    }
}
