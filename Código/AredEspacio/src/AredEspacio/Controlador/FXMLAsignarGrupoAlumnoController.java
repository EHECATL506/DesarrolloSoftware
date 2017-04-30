package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import ControladorBD.AlumnoJpaController;
import ControladorBD.ClaseJpaController;
import ControladorBD.DanzaJpaController;
import ControladorBD.GrupoJpaController;
import ControladorBD.HorarioJpaController;
import Modelo.Alumno;
import Modelo.Clase;
import Modelo.Danza;
import Modelo.FilaClase;
import Modelo.FilaHorario;
import Modelo.Grupo;
import Modelo.Horario;
import Modelo.Mensaje;
import Modelo.TipoDeMenu;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FXMLAsignarGrupoAlumnoController extends MainController implements Initializable {

    @FXML
    private ComboBox<String> cBDanza;
    @FXML
    private TextField tFDescuento;
    @FXML
    private TableColumn<FilaHorario, String> tCHora;
    @FXML
    private TextField tFResta;
    @FXML
    private TextField tFCantidad;
    @FXML
    private TableView<FilaHorario> tVHorario;

    @FXML
    private TextField tFTotal;
    @FXML
    private TableColumn<FilaHorario, String> tCDia;
    @FXML
    private TableColumn<FilaHorario, String> tCClase;
    @FXML
    private TextField tFNivel;
    @FXML
    private TextField tFPago;

    //columnas de grupo
    @FXML
    private TableView<Grupo> tVGrupo;
    @FXML
    private TableColumn<?, ?> tCSalon;
    @FXML
    private TableColumn<?, ?> tCNivel;
    @FXML
    private TableColumn<?, ?> tCMaestro;
    @FXML
    private Button clicInscribir;

    @FXML
    private TableView<FilaClase> tVClase;
    @FXML
    private TableColumn<FilaClase, String> tCAlumno;
    @FXML
    private TableColumn<FilaClase, String> tCGrupo;
    @FXML
    private TableColumn<FilaClase, String> tCFechaIngreso;

    @FXML
    private Button clicAgregar;

    List<Danza> danzas;

    //ObservableList<FilaHorario> lista;
    private ClaseJpaController jpaClase;
    private DanzaJpaController jpaDanza;
    private GrupoJpaController jpaGrupo;
    private HorarioJpaController jpaHorario;

    private Alumno alumno;

    public void desplegarGrupos(Danza danza) {
        List<Grupo> grupos = jpaGrupo.obtenerPorDanza(danza);
        ObservableList oLGrupos = FXCollections.observableArrayList(grupos);
        tVGrupo.setItems(oLGrupos);

        //desplegarHorario al seleccionar el grupo
        tVGrupo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                //obtener la fila del grupo seleccionado
                Grupo grupo = tVGrupo.getSelectionModel().getSelectedItem();

                //obtener lista de los horarios
                List<Horario> horario = jpaHorario.obtenerPorGrupo(grupo);
                ObservableList oLHorario = FXCollections.observableArrayList(horario);

                tVHorario.setItems(oLHorario);
            }
        });
        Clase clase = new Clase();
        clase.setIdAlumno(alumno);
        //clicAgregarClase
        clicAgregar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Grupo grupo = tVGrupo.getSelectionModel().getSelectedItem();
                if (grupo != null) {
                    clase.setIdGrupo(grupo);
                    clase.setFechaRegistro(new Date());

                    jpaClase.create(clase);
                    //creaste una clase 

                    ObservableList<FilaClase> oLlista = FXCollections.observableArrayList();
                    FilaClase fClase = new FilaClase(alumno.getNombre() + alumno.getApellidos(),
                            grupo.getSalon(), clase.getFechaRegistro().toString());
                    oLlista.add(fClase);
                    tVClase.setItems(oLlista);

                    //desplegar clase
                    Mensaje.informacion("EL ALUMNO HA SIDO AÑADIDO AL GRUPO: " + grupo.getSalon());
                } else {
                    Mensaje.advertencia("No se ha seleccionado un grupo");
                }

            }
        });

        //inscribirClase
        clicInscribir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FilaClase clase = tVClase.getSelectionModel().getSelectedItem();
                Dialog dIncripcion = new Dialog();
                dIncripcion.setTitle("Pago Incripción");
                dIncripcion.setHeaderText("Horario que tiene asignado el maestro: ");
                dIncripcion.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
                TextField tFCantidad = new TextField();
                TextField tFDescuento = new TextField();
                TextField tFPaga = new TextField();
                TextField tFTotal = new TextField();
                
                HBox panel1 = new HBox(15);
                panel1.getChildren().addAll(
                        new Label("   Cantidad a Pagar:"), tFCantidad,
                        new Label("    Descuento:"), tFDescuento );
                
                HBox panel2 = new HBox(15);
                panel2.getChildren().addAll(
                        new Label("Cantidad que Paga:"), tFPaga,
                        new Label("Total a Pagar:"), tFTotal
                        
                );
                
                VBox panel3 = new VBox(10);
                panel3.getChildren().addAll(panel1, panel2);

                                
                
                dIncripcion.getDialogPane().setContent(panel3);
                dIncripcion.show();
            }
        });

    }

    @FXML
    void bAgregar(ActionEvent event) {
        String danzaSeleccionada = cBDanza.getValue();
        Danza danza = null;

        for (Danza d : danzas) {
            if (d.getTipoDanza().equals(danzaSeleccionada)) {
                danza = d;
            }
        }
        desplegarGrupos(danza);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
            jpaClase = new ClaseJpaController(emf);
            jpaGrupo = new GrupoJpaController(emf);
            jpaDanza = new DanzaJpaController(emf);
            jpaHorario = new HorarioJpaController(emf);
            alumno = (Alumno) this.parametros;
            //desplegarDanzas
            danzas = jpaDanza.findDanzaEntities();
            ObservableList oLDanzas = FXCollections.observableArrayList();
            for (Danza o : danzas) {
                String v = o.getTipoDanza();
                oLDanzas.add(v);
            }
            cBDanza.setItems(oLDanzas);

            tCSalon.setCellValueFactory(new PropertyValueFactory<>("Salon"));
            tCNivel.setCellValueFactory(new PropertyValueFactory<>("Nivel"));
            tCMaestro.setCellValueFactory(new PropertyValueFactory<>("Maestro"));

            tCDia.setCellValueFactory(new PropertyValueFactory<>("Dia"));
            tCHora.setCellValueFactory(new PropertyValueFactory<>("Hora"));

            tCAlumno.setCellValueFactory(new PropertyValueFactory<>("Alumno"));
            tCGrupo.setCellValueFactory(new PropertyValueFactory<>("Grupo"));
            tCFechaIngreso.setCellValueFactory(new PropertyValueFactory<>("FechaIngreso"));
        });
    }
}
