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
import Modelo.FilaHorario;
import Modelo.Horario;
import Modelo.Mensaje;
import Modelo.TipoDeMenu;
import Modelo.Validar;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Jonathan
 */


public class FXMLRegistrarAlumnoController extends MainController implements  Initializable {
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
    @FXML
    private TableColumn<FilaHorario, String> tCNivel;
    
    private String rutaFoto;
    private String accion;
    private AlumnoJpaController jpaAlumno;
    
    //desplegarAlumno
    public void desplegarAlumno(Alumno alumno)
    {
        if (accion.equals("CONSULTAR")) {
            lRegistro.setText("REGISTRADO: "+alumno.getFechaRegistro().toLocaleString());
            tFNombre.setEditable(false);
            tFApellidos.setEditable(false);
            
            dPNacimiento.setDisable(true);
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
            try {
                ByteArrayInputStream in = new ByteArrayInputStream(alumno.getFoto());
                BufferedImage bufferedImage;
                bufferedImage = ImageIO.read(in);
                iVFoto.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
             } catch (Exception ex) {
                return;
            }
        }
        
    }
    //limpiarCampos
    private void limpiarCampos() {
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
        rutaFoto = null;
        
    }
    //consultarHorario
    public void consultarHorario(Alumno alumno) {
       ObservableList<FilaHorario> lista = FXCollections.observableArrayList();
        for (Clase grupo : alumno.getClaseList()) {
            String clase = grupo.getIdGrupo().getTipoDeDanza();
            String maestro =  (grupo.getIdGrupo().getIdMaestro().getNombre() + " ") +
                    grupo.getIdGrupo().getIdMaestro().getApellidos();
            String nivel = grupo.getIdGrupo().getNivel();
            String dia="";
            String hora="";
            for (Horario horario : grupo.getIdGrupo().getHorarioList()) {
                dia += horario.getDia()+"\n";
                hora += horario.getHora()+"\n";
            } 
            lista.add(new FilaHorario(clase, maestro, dia, hora, nivel));
           
        }
         tVHorario.setItems(lista);
    }
    //modificarAlumno
    public void modificarAlumno(Alumno alumno) throws Exception  {
        if (rutaFoto != null) AgregarFoto(alumno);
        jpaAlumno.edit(alumno);
        Mensaje.informacion("El alumno ha sido actualizado");
        //---->
        escena.cargarEscenaConParametros(EscenaPrincipal.EscenaBuscarAlumno,
                null, TipoDeMenu.MODIFICAR);
    }
    //guardarAlumno
    public void guardarAlumno(Alumno alumno) throws Exception {        
        if (rutaFoto != null) AgregarFoto(alumno);
        //generarMatricula
        alumno.setFechaRegistro(new Date());
        alumno.setMatricula(String.format("MA-%1$05d", jpaAlumno.getAlumnoCount()));
        alumno.setStatus("Alta");
        
        jpaAlumno.create(alumno);
        Mensaje.informacion("El alumno ha sido registrado, su matricula es: " + alumno.getMatricula());
        limpiarCampos();
    }
    //clicGuardar
    @FXML
    public void clicGuardar(ActionEvent event) throws Exception {
        Alumno alumno = new Alumno();
        if (accion.equals("MODIFICAR")) {
            if (validarAlumno((Alumno)this.parametros) == 1) modificarAlumno((Alumno)this.parametros);
            else Mensaje.advertencia("Los campos han sido invalidos");
        }
        else if (accion.equals("REGISTRAR")) {
            if (validarAlumno(alumno) == 1) guardarAlumno(alumno);
            else Mensaje.advertencia("Los campos han sido invalidos"); 
        }  
    }
    
    //setAgregarFoto
    public void AgregarFoto(Alumno alumno ) throws Exception {
        File ruta = new File(rutaFoto);
        FileInputStream  file = new FileInputStream(ruta);
        BufferedImage bufferedImage = ImageIO.read(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", out);
        out.flush();
        alumno.setFoto(out.toByteArray());
    }
    
    //clicAgregarFoto
    @FXML
    public void clicAgregarFoto(ActionEvent event) {
        FileChooser explorador = new FileChooser();
        explorador.setTitle("Buscar Foto");
        explorador.getExtensionFilters().add(new FileChooser.ExtensionFilter("Foto", "*.jpg","*.png"));
        
        try {
            rutaFoto = explorador.showOpenDialog(new Stage()).getPath();
            File ruta = new File(rutaFoto);
            FileInputStream foto;
            foto = new FileInputStream(ruta);
            BufferedImage bufferedImage = ImageIO.read(foto);
            if (bufferedImage != null) iVFoto.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        } catch (Exception ex) {
            return;
        }
    }
    
    //clicCancelar
    @FXML
    void clicCancelar(ActionEvent event) {
        escena.cargarEscena(EscenaPrincipal.EscenaInicio );
    }
    
    //validarAlumno
    public int validarAlumno(Alumno alumno) throws ParseException {
        int valido = 2048;
        if (Validar.texto(tFNombre)) {valido >>=1; alumno.setNombre(tFNombre.getText());}
        if (Validar.texto(tFApellidos)) {valido >>=1;alumno.setApellidos(tFApellidos.getText());}
        if (Validar.correo(tFCorreo)) {valido >>=1;alumno.setCorreo(tFCorreo.getText());}
        if (Validar.fecha(dPNacimiento)) {valido >>=1;alumno.setFechaNacimiento(new SimpleDateFormat("yyyy-MM-dd").parse(dPNacimiento.getValue().toString()));}
        if (Validar.combo(cBGenero)) {valido >>=1;alumno.setGenero(cBGenero.getValue());}
        if (Validar.telefono(tFTelefono)) {valido >>=1;alumno.setTelefono(tFTelefono.getText());}
        if (Validar.celular(tFMovil)) {valido >>=1;alumno.setMovil(tFMovil.getText());}
        if (Validar.texto(tFDomicilio)) {valido >>=1;alumno.setDomicilo(tFDomicilio.getText());}
        if (Validar.codigoPostal(tFCodigoPostal)) {valido >>=1;alumno.setCp(tFCodigoPostal.getText());}
        if (Validar.texto(tFCiudad)) {valido >>=1;alumno.setCiudad(tFCiudad.getText());}
        if (Validar.combo(cBEstado)) {valido >>=1;alumno.setEstado(cBEstado.getValue());}
        return valido;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            accion = this.tipoMenu.toString();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
            jpaAlumno = new AlumnoJpaController(emf);
            
            tVHorario.setVisible(false);
            switch (accion) {
                case "REGISTRAR":
                    bGuardar.setText("Guardar");
                    lTitulo.setText("Registrar Alumno");
                    lMensaje.setText("Ingresa los datos personales del nuevo alumno");
                    bFoto.setText("Agregar foto del alumno");
                    break;
                case "CONSULTAR":
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
                    tCNivel.setCellValueFactory(new PropertyValueFactory<>("Nivel"));
                    desplegarAlumno((Alumno)this.parametros);
                    consultarHorario((Alumno)this.parametros);
                    break;
                case "MODIFICAR":
                    bGuardar.setText("Actualizar");
                    lTitulo.setText("Modificar Alumno");
                    lMensaje.setText("Modifica los nuevos datos personales del alumno");   
                    bFoto.setText("Actualizar foto del alumno");
                    desplegarAlumno((Alumno)this.parametros);
                    break;
                default:
                    break;
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