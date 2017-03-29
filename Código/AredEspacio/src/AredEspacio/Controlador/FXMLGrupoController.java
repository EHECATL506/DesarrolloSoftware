package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.Grupo;
import Modelo.Horario;
import Modelo.Maestro;
import Modelo.Mensaje;
import Modelo.Validar;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLGrupoController extends MainController implements Initializable {

    @FXML
    private TextField tfSalon;

    @FXML
    private TextField txNivel;
    @FXML
    private TextField tfTipoDeDanza;

    @FXML
    private TableView tMaestro;

    @FXML
    private TableColumn cNoDeColaborador;

    @FXML
    private TableColumn cNombre;

    @FXML
    private TableColumn cApellidos;

    @FXML
    private TableView tHorario;

    @FXML
    private TableColumn cDia;

    @FXML
    private TableColumn cInicio;

    @FXML
    private TableColumn cFin;

    @FXML
    private ComboBox cbDia;

    @FXML
    private Spinner<Integer> sHorasInicio;

    @FXML
    private Spinner<Integer> spMinutosInicio;

    @FXML
    private Spinner<Integer> spHorasFin;

    @FXML
    private Spinner<Integer> spMinutosFin;

    @FXML
    private Button bAgregarHorario;

    @FXML
    private Button bEliminarHorario;

    @FXML
    private Button bRegistrarGrupo;
    private ArrayList<Horario> horarios;

    @FXML
    void eliminarDelHorario() {
        try {
            Horario horario = (Horario) this.tHorario.getSelectionModel().getSelectedItem();
            this.horarios.remove(horario);
            ObservableList listaHorario = FXCollections.observableArrayList(this.horarios);
            this.tHorario.setItems(listaHorario);
        } catch (Exception e) {
        }
    }

    @FXML
    void agregarAlHorario(ActionEvent event) {
        if (!(this.cbDia.getValue() == null)) {
            String dia = this.cbDia.getValue().toString();
            String inicio = this.sHorasInicio.getValue() + ":" + this.spMinutosInicio.getValue();
            String fin = this.spHorasFin.getValue() + ":" + this.spMinutosFin.getValue();
            Horario horario = new Horario();
            horario.setDia(dia);
            horario.setHora(inicio+"-"+fin);
            this.horarios.add(horario);
            ObservableList listaHorario = FXCollections.observableArrayList(this.horarios);
            this.tHorario.setItems(listaHorario);
        } else {
            Mensaje.advertencia("Seleccione un Dia");
        }
    }

    @FXML
    void registrarGrupo(ActionEvent event) {
        if (this.horarios.isEmpty()) {
            Mensaje.advertencia("Agregue almenos un dia con su hora de inicio y fin al horario");
        } else {
            boolean salon = Validar.texto(this.tfSalon);
            boolean danza = Validar.texto(this.tfTipoDeDanza);
            boolean nivel = Validar.texto(this.txNivel);
            if (salon && danza && nivel) {
                try {
                    Maestro maestro = (Maestro) this.tMaestro.getSelectionModel().getSelectedItem();
                    if (maestro == null) {
                        Mensaje.advertencia("Seleccione un maestro");
                    } else {
                        Grupo grupo = new Grupo();
                        grupo.setSalon(this.tfSalon.getText());
                        grupo.setTipoDeDanza(this.tfTipoDeDanza.getText());
                        grupo.setNivel(this.txNivel.getText());
                        grupo.setIdMaestro(maestro);
                        GregorianCalendar gc = new GregorianCalendar();
                        grupo.setInicioDeGrupo(new Date(gc.getTimeInMillis()));
                        grupo.crear(this.horarios);
                        Mensaje.informacion("Exito! al actualizar el grupo");
                        this.escena.cargarEscena(EscenaPrincipal.EscenaBuscarGrupo);
                    }
                } catch (Exception e) {
                    Mensaje.advertencia("No hay conexión con la base de Datos");
                }
            } else {
                Mensaje.advertencia("Corrija los campos en rojo");
            }
        }
    }

    private void inicializarSpinner(Spinner spinner, int maximo, int aumento) {
        SpinnerValueFactory<Integer> valueFactory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maximo, 0, aumento);
        spinner.setValueFactory(valueFactory);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.cNoDeColaborador.setCellValueFactory(
                new PropertyValueFactory<>("NoDeColaborador")
        );
        this.cNombre.setCellValueFactory(
                new PropertyValueFactory<>("Nombre")
        );
        this.cApellidos.setCellValueFactory(
                new PropertyValueFactory<>("Apellidos")
        );

        this.horarios = new ArrayList<>();

        ObservableList lista = FXCollections.observableArrayList(
                Maestro.obtenerMaestroPorNombre("")
        );
        this.tMaestro.setItems(lista);
        this.cbDia.getItems().addAll("Lunes", "Martes", "Miércoles",
                "Jueves", "Viernes", "Sábado", "Domingo"
        );
        this.inicializarSpinner(this.sHorasInicio, 24, 1);
        this.inicializarSpinner(this.spHorasFin, 24, 1);
        this.inicializarSpinner(this.spMinutosInicio, 55, 5);
        this.inicializarSpinner(this.spMinutosFin, 55, 5);

        this.cDia.setCellValueFactory(
                new PropertyValueFactory<>("Dia")
        );

        this.cInicio.setCellValueFactory(
                new PropertyValueFactory<>("Inicio")
        );

        this.cFin.setCellValueFactory(
                new PropertyValueFactory<>("Fin")
        );
    }
}
