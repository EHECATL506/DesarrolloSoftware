/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import ControladorBD.AlumnoJpaController;
import Modelo.Alumno;
import Modelo.Foto;
import Modelo.Mensaje;
import Modelo.Validar;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * FXML Controller class
 *
 * @author Jonathan
 */
public class FXMLEliminarAlumnoController extends MainController implements Initializable {
    @FXML
    private DatePicker dPRegistro;
    @FXML
    private TextField tFMovil;
    @FXML
    private Label lTitulo;
    @FXML
    private TextField tFMatricula;
    @FXML
    private TextField tFTelefono;
    @FXML
    private TextArea tAMotivo;
    @FXML
    private TextField tFCorreo;
    @FXML
    private ImageView iVFoto;
    @FXML
    private TextField tFApellidos;
    @FXML
    private TextField tFNombre;
    @FXML
    private Label lMensaje;
    @FXML
    private Button bEliminar;
    @FXML
    private Button bCancelar;
    
    private Alumno alumno;
    public void desplegarAlumno()
    {
        tFNombre.setText(alumno.getNombre());
        tFApellidos.setText(alumno.getApellidos());
        GregorianCalendar gregorian = new GregorianCalendar();
        gregorian.setTime(alumno.getFechaRegistro());
        dPRegistro.setValue(gregorian.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        tFCorreo.setText(alumno.getCorreo());
        tFTelefono.setText(alumno.getTelefono());
        tFMovil.setText(alumno.getMovil());
        tFMatricula.setText(alumno.getMatricula());
        
        if (alumno.getFoto() != null) {
            Foto foto = new Foto();
            iVFoto.setImage(foto.obtenerImagen(alumno.getFoto()));
        }
    }
    @FXML
    void cancelar(ActionEvent event) {
        escena.cargarEscena(EscenaPrincipal.EscenaInicio);
    }
    
    @FXML
    void darBaja(ActionEvent event) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
        AlumnoJpaController jpa = new AlumnoJpaController(emf);
        if ( Validar.area(tAMotivo) ) {
            alumno.setMotivo(tAMotivo.getText());
            alumno.setFechaBaja(new Date());
            if (alumno.getStatus().equals("Alta")) alumno.setStatus("Baja");
            else if (alumno.getStatus().equals("Baja")) {
                alumno.setFechaRegistro(new Date());
                alumno.setStatus("Alta");
            }
            jpa.edit(alumno);
            Mensaje.informacion("El alumno ha sido dado de " + alumno.getStatus()); 
            escena.cargarEscena(EscenaPrincipal.EscenaInicio);
        }
        else Mensaje.advertencia("No se ha capturado el motivo");
               
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            if (this.parametros != null ) {
                alumno = (Alumno)this.parametros;
                if (alumno.getStatus().equals("Baja")) {
                    bEliminar.setText("Dar de Alta");
                    lTitulo.setText("Dar de Alta Alumno");
                    lMensaje.setText("El alumno será habilitado, por favor capture el motivo de la alta");
                } 
                if (alumno.getStatus().equals("Alta")) {
                    bEliminar.setText("Dar de Baja");
                    lTitulo.setText("Dar de Baja Alumno");
                    lMensaje.setText("El alumno será deshabilitado, por favor capture el motivo de la baja");
                }
                desplegarAlumno();
            }
        });
    }    
    
}
