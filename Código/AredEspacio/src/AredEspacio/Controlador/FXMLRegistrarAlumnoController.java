/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AredEspacio.Controlador;
import AredEspacio.EscenaPrincipal;
import ControladorBD.AlumnoJpaController;
import Modelo.Alumno;
import Modelo.Clase;
import Modelo.Foto;
import Modelo.Horario;
import Modelo.Mensaje;
import Modelo.Validar;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Jonathan
 */


public class FXMLRegistrarAlumnoController extends MainController implements  Initializable {
    public class FilaHorario {
        private final SimpleStringProperty clase;
        private final SimpleStringProperty maestro;
        private final SimpleStringProperty dia;
        private final SimpleStringProperty hora;

        public FilaHorario(String clase, String maestro, String dia, String hora) {
            this.clase = new SimpleStringProperty(clase);
            this.maestro = new SimpleStringProperty(maestro);
            this.dia = new SimpleStringProperty(dia);
            this.hora = new SimpleStringProperty(hora);
        }
        public String getClase () {
            return clase.get();
        }
        public String getMaestro () {
            return maestro.get();
        }
        public String getDia () {
            return dia.get();
        }
        public String getHora () {
            return hora.get();
        }
    }
    @FXML
    private DatePicker dPNacimiento;
    @FXML
    private TextField tFMovil;
    @FXML
    private TextField tFCiudad;
    @FXML
    private TextField tFTelefono;
    @FXML
    private TextField tFCodigoPostal;
    @FXML
    private TextField tFCorreo;
    @FXML
    private ImageView iVFoto;
    @FXML
    private ComboBox<String> cBGenero;
    @FXML
    private TextField tFDomicilio;
    @FXML
    private ComboBox<String> cBEstado;
    @FXML
    private TextField tFNombre;
    @FXML
    private TextField tFApellidos;
    @FXML
    private Label lTitulo;
    @FXML
    private Label lMensaje;
    @FXML
    private Button bGuardar;
    @FXML
    private Button bFoto;
    @FXML
    private Button bCancelar;
    @FXML
    private Label lRegistro;
    
    
    @FXML
    private TableView<FilaHorario> tVHorario;
    @FXML
    private TableColumn<FilaHorario, String> tCDia;
    @FXML
    private TableColumn<FilaHorario, String> tCHora;
    @FXML
    private TableColumn<FilaHorario, String> tCClase;    
    @FXML
    private TableColumn<FilaHorario, String> tCMaestro;
    
    private Alumno alumno;
    private String rutaFoto;
    private String accion;
    
    //desplegarAlumno
    public void desplegarAlumno()
    {
        if (accion.equals("CONSULTAR")) {
            lRegistro.setText("REGISTRADO: "+alumno.getFechaRegistro().toLocaleString());
            tFNombre.setEditable(false);
            tFApellidos.setEditable(false);
            
            dPNacimiento.setEditable(false);
            cBGenero.setDisable(true);
            tFCorreo.setEditable(false);
            tFTelefono.setEditable(false);
            tFMovil.setEditable(false);
            tFDomicilio.setEditable(false);
            tFCiudad.setEditable(false);
            tFCodigoPostal.setEditable(false);
            cBEstado.setDisable(true);
        }
        tFNombre.setText(alumno.getNombre());
        tFApellidos.setText(alumno.getApellidos());
        GregorianCalendar gregorian = new GregorianCalendar();
        gregorian.setTime(alumno.getFechaNacimiento());
        dPNacimiento.setValue(gregorian.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        cBGenero.setValue(alumno.getGenero());
        tFCorreo.setText(alumno.getCorreo());
        tFTelefono.setText(alumno.getTelefono());
        tFMovil.setText(alumno.getMovil());
        tFDomicilio.setText(alumno.getDomicilo());
        tFCiudad.setText(alumno.getCiudad());
        tFCodigoPostal.setText(alumno.getCp());
        cBEstado.setValue(alumno.getEstado());
        
        if (alumno.getFoto() != null) {
            Foto foto = new Foto();
            iVFoto.setImage(foto.obtenerImagen(alumno.getFoto()));
        }
        
    }
    //limpiarCampos
    public void limpiarCampos() {
        tFNombre.setText("");
        tFApellidos.setText("");
        dPNacimiento.setValue(null);
        cBGenero.setValue(null);
        tFCorreo.setText("");
        tFTelefono.setText("");
        tFMovil.setText("");
        tFDomicilio.setText("");
        tFCiudad.setText("");
        tFCodigoPostal.setText("");
        cBEstado.setValue(null);
        iVFoto.setImage(null);
    }
    //consultarAlumno
    public void consultarAlumno() {
        //System.out.println("CONSULTAR");
        ObservableList<FilaHorario> lista = FXCollections.observableArrayList();
        for (Clase grupo : alumno.getClaseList()) {
            
            String clase = grupo.getIdGrupo().getTipoDeDanza();
            String maestro =  (grupo.getIdGrupo().getIdMaestro().getNombre() + " ") +
                    grupo.getIdGrupo().getIdMaestro().getApellidos();
            
            for (Horario horario : grupo.getIdGrupo().getHorarioList()) {
                String dia = horario.getDia();
                String hora = horario.getHora();
                lista.add(new FilaHorario(clase, maestro, dia, hora));
            }
            
            tVHorario.setItems(lista);
        }
       
    }
    
    //modificarAlumno
    public void modificarAlumno() throws Exception  {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
        AlumnoJpaController jpa = new AlumnoJpaController(emf);
        jpa.edit(alumno);
        Mensaje.informacion("El alumno ha sido actualizado");
        escena.cargarEscena(EscenaPrincipal.EscenaBuscarAlumno);
    }
    //guardarAlumno
    public void guardarAlumno() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
        AlumnoJpaController jpa = new AlumnoJpaController(emf);
        
        if (rutaFoto != null) {
            Foto foto = new Foto();
            alumno.setFoto(foto.agregarImagen(rutaFoto));
        }
        //alumno.setIdAlumno(2);
        //generarMatricula
        alumno.setFechaRegistro(new Date());
        alumno.setMatricula(String.format("MA-%1$05d", jpa.getAlumnoCount()));
        alumno.setStatus("Alta");
        
        jpa.create(alumno);
        Mensaje.informacion("El alumno ha sido registrado, su matricula es: " + alumno.getMatricula());
        limpiarCampos();
    }
    
    //clicGuardar
    @FXML
    public void guardar(ActionEvent event) throws Exception {
        if (validarCampos() == 1) {
            if (accion.equals("MODIFICAR")) modificarAlumno();
            //else if (accion.equals("CONSULTAR")) consultarAlumno();
            else if (accion.equals("REGISTRAR")) guardarAlumno();              
        } else Mensaje.advertencia("Los campos han sido invalidos");
    }
    
    //clicAgregarFoto
    @FXML
    public void agregarFoto(ActionEvent event) {
        Foto fotoAlumno = new Foto();
        rutaFoto = fotoAlumno.buscarImagen();
        alumno.setFoto(fotoAlumno.agregarImagen(rutaFoto));
        iVFoto.setImage(fotoAlumno.obtenerImagen(alumno.getFoto()));
    }
    
    //clicCancelar
    @FXML
    void cancelar(ActionEvent event) {
        escena.cargarEscena(EscenaPrincipal.EscenaBuscarAlumno);
    }
    
    //validarCampos
    public int validarCampos() throws ParseException {
        int b = 2048;
        if (Validar.texto(tFNombre)) {b >>=1; alumno.setNombre(tFNombre.getText());}
        if (Validar.texto(tFApellidos)) {b >>=1;alumno.setApellidos(tFApellidos.getText());}
        if (Validar.correo(tFCorreo)) {b >>=1;alumno.setCorreo(tFCorreo.getText());}
        if (Validar.fecha(dPNacimiento)) {b >>=1;alumno.setFechaNacimiento(new SimpleDateFormat("yyyy-MM-dd").parse(dPNacimiento.getValue().toString()));}
        if (Validar.combo(cBGenero)) {b >>=1;alumno.setGenero(cBGenero.getValue());}
        if (Validar.telefono(tFTelefono)) {b >>=1;alumno.setTelefono(tFTelefono.getText());}
        if (Validar.celular(tFMovil)) {b >>=1;alumno.setMovil(tFMovil.getText());}
        if (Validar.texto(tFDomicilio)) {b >>=1;alumno.setDomicilo(tFDomicilio.getText());}
        if (Validar.codigoPostal(tFCodigoPostal)) {b >>=1;alumno.setCp(tFCodigoPostal.getText());}
        if (Validar.texto(tFCiudad)) {b >>=1;alumno.setCiudad(tFCiudad.getText());}
        if (Validar.combo(cBEstado)) {b >>=1;alumno.setEstado(cBEstado.getValue());}
        //System.out.println(b);
        return b;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            accion = this.tipoMenu.toString();
            tVHorario.setVisible(false);
            if (accion.equals("REGISTRAR")) {
                alumno = new Alumno();
                bGuardar.setText("Guardar");
                lTitulo.setText("Registrar Alumno");
                lMensaje.setText("Ingresa los datos personales del nuevo alumno");
                bFoto.setText("Agregar foto del alumno");
            } else if (accion.equals("CONSULTAR")) {
                tVHorario.setVisible(true); 
                bGuardar.setVisible(false);
                lTitulo.setText("Consultar Alumno");
                lMensaje.setText("Datos personales del alumno y horarios de sus clases");
                bFoto.setVisible(false);
                bCancelar.setText("Aceptar");
                tCClase.setCellValueFactory(new PropertyValueFactory<>("Clase"));
                tCMaestro.setCellValueFactory(new PropertyValueFactory<>("Maestro"));        
                tCDia.setCellValueFactory(new PropertyValueFactory<>("Dia"));        
                tCHora.setCellValueFactory(new PropertyValueFactory<>("Hora"));
                alumno = (Alumno)this.parametros;
                desplegarAlumno();
                consultarAlumno();
            } else if (accion.equals("MODIFICAR")) {
                bGuardar.setText("Actualizar");
                lTitulo.setText("Modificar Alumno");
                lMensaje.setText("Modifica los nuevos datos personales del alumno");
                bFoto.setText("Actualizar foto del alumno");
                alumno = (Alumno)this.parametros;
                desplegarAlumno();
            }   
        });
        ObservableList genero = FXCollections.observableArrayList("Masculino","Femenino");
        ObservableList estados = FXCollections.observableArrayList("Aguascalientes","Baja California","Baja California Sur","Campeche","Chiapas","Chihuahua","Coahuila","Colima",
            "Distrito Federal","Durango","Estado de México","Guanajuato","Guerrero","HidalgoJalisco","Michoacán","Morelos",
            "Nayarit","Nuevo León","Oaxaca","Puebla","Querétaro","Quintana Roo","San Luis Potosí","Sinaloa","Sonora","Tabasco",
            "Tamaulipas","Tlaxcala","Veracruz","Yucatán","Zacatecas");
        cBGenero.setItems(genero);
        cBEstado.setItems(estados);
    }
}
