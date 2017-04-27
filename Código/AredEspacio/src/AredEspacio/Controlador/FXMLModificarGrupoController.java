package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.Danza;
import Modelo.Grupo;
import Modelo.Horario;
import Modelo.Maestro;
import Modelo.Mensaje;
import Modelo.Validar;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLModificarGrupoController extends MainController implements Initializable {

    @FXML
    private TextField tfSalon;

    @FXML
    private ComboBox txNivel;

    @FXML
    private ComboBox tfTipoDeDanza;

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
    private Button bActualizar;

    @FXML
    private Button bEliminarHorario;

    @FXML
    private Button bActualizarGrupo;

    @FXML
    private Label lMaestro;

    @FXML
    private Button bActualizarMaestro;

    private ArrayList<Horario> horarios;
    private ArrayList<Horario> horariosEliminados;

    @FXML
    void agregarAlHorario(ActionEvent event) {
        if (!(this.cbDia.getValue() == null)) {
            boolean valido = false;
            String dia = this.cbDia.getValue().toString();
            int horasInicio = Integer.parseInt(this.sHorasInicio.getValue().toString());
            int minutosInicio = Integer.parseInt(this.spMinutosInicio.getValue().toString());
            int horasFin = Integer.parseInt(this.spHorasFin.getValue().toString());
            int minutosFin = Integer.parseInt(this.spMinutosFin.getValue().toString());
            String horas = horasInicio + ":" + minutosInicio + "-" + horasFin + ":" + minutosFin;
            valido = this.validarDia(dia)
                    && this.validarHora(horasInicio, minutosInicio, horasFin, minutosFin);
            if (valido) {
                Horario horario = new Horario(dia, horas);
                this.horarios.add(horario);
                ObservableList listaHorario = FXCollections.observableArrayList(this.horarios);
                this.tHorario.setItems(listaHorario);
            }
        } else {
            Mensaje.advertencia("Seleccione un Dia");
        }
    }

    private boolean validarDia(String dia) {
        for (Horario horario : this.horarios) {
            if (horario.getDia().equals(dia)) {
                Mensaje.advertencia("No puede tener dos clases el mismo dia");
                return false;
            }
        }
        return true;
    }

    private boolean validarHora(int horasInicio, int minutosInicio, int horasFin, int minutosFin) {
        if (horasInicio > horasFin) {
            Mensaje.advertencia("La hora de inicio debe ser menor que la de fin");
            return false;
        }
        int minutos = (horasFin - horasInicio) * 60 + minutosFin - minutosInicio;

        if (minutos > 120) {
            Mensaje.advertencia("Las clases no pueden durar mas de 2 horas");
            return false;
        }

        if (minutos < 30) {
            Mensaje.advertencia("Las clases deben durar mas de 30 minutos");
            return false;
        }

        return true;
    }

    @FXML
    void actualizarMaestro() {
        this.tMaestro.setVisible(true);
        this.bActualizarMaestro.setVisible(false);
        this.lMaestro.setLayoutX(460);
        this.lMaestro.setLayoutY(102);
        this.lMaestro.setText("Seleccione el Maestro");

        List<Maestro> maestros = new ArrayList<>();
        for (Maestro maestro : Maestro.obtenerMaestroPorNombre("")) {
            if (!maestro.getDeshabilitado()) {
                maestros.add(maestro);
            }
        }
        ObservableList listaHorario
                = FXCollections.observableArrayList(maestros);
        this.tMaestro.setItems(listaHorario);
    }

    @FXML
    void eliminarDelHorario() {
        try {
            Horario horario = (Horario) this.tHorario.getSelectionModel().getSelectedItem();
            this.horariosEliminados.add(horario);
            this.horarios.remove(horario);
            ObservableList listaHorario = FXCollections.observableArrayList(this.horarios);
            this.tHorario.setItems(listaHorario);
        } catch (Exception e) {
        }
    }
      /*
    private boolean validarDiaID(String dia, Integer id) {
        for (Horario horario : this.horarios) {
            if (!horario.getIdHorario().equals(id)) {
                if (horario.getDia().equals(dia)) {
                    Mensaje.advertencia("No puede tener dos clases el mismo dia");
                    return false;
                }
            }
        }
        return true;
    }*/

    @FXML
    void actualizarHorario(ActionEvent event) {
        /*Horario horario = (Horario) this.tHorario.getSelectionModel().getSelectedItem();
        if (horario != null) {
            if (!(this.cbDia.getValue() == null)) {
                boolean valido = false;

                String dia = this.cbDia.getValue().toString();
                int horasInicio = Integer.parseInt(this.sHorasInicio.getValue().toString());
                int minutosInicio = Integer.parseInt(this.spMinutosInicio.getValue().toString());
                int horasFin = Integer.parseInt(this.spHorasFin.getValue().toString());
                int minutosFin = Integer.parseInt(this.spMinutosFin.getValue().toString());
                String horas = horasInicio + ":" + minutosInicio + "-" + horasFin + ":" + minutosFin;

                if (horario.getIdHorario() != null) {
                    valido = this.validarDiaID(dia, horario.getIdHorario())
                            && this.validarHora(horasInicio, minutosInicio, horasFin, minutosFin);
                    if (valido) {
                        this.horarios.remove(horario);
                        horario.setDia(dia);
                        horario.setHora(horas);
                        this.horarios.add(horario);
                        ObservableList listaHorario = FXCollections.observableArrayList(this.horarios);
                        this.tHorario.setItems(listaHorario);
                    }
                }

            } else {
                Mensaje.advertencia("Seleccione un Dia");
            }
        } else {
            Mensaje.advertencia("Seleccione un horario");
        }*/
    }

    @FXML
    void actualizarGrupo(ActionEvent event) {
        if (this.horarios.isEmpty()) {
            Mensaje.advertencia("Agregue almenos un dia con su hora de inicio y fin al horario");
        } else {
            boolean danza = Validar.combo(this.tfTipoDeDanza);
            boolean nivel = Validar.combo(this.txNivel);
            if (danza && nivel) {
                try {
                    Grupo grupo = (Grupo) this.parametros;
                    grupo.setSalon(this.tfSalon.getText());
                    Danza newDanza = Danza.buscarPorTipoDanza(this.tfTipoDeDanza.getValue().toString());
                    grupo.setIdDanza(newDanza);
                    grupo.setNivel(this.txNivel.getValue().toString());
                    GregorianCalendar gc = new GregorianCalendar();
                    grupo.setInicioDeGrupo(new Date(gc.getTimeInMillis()));
                    if (this.bActualizarMaestro.isVisible()) {
                        grupo.actualizar(this.horarios, this.horariosEliminados);
                        Mensaje.informacion("Exito! al actualizar el grupo");
                        this.escena.cargarEscena(EscenaPrincipal.EscenaBuscarGrupo);
                    } else {
                        Maestro maestro = (Maestro) this.tMaestro.getSelectionModel().getSelectedItem();
                        if (maestro == null) {
                            Mensaje.advertencia("Seleccione un maestro");
                        } else {
                            grupo.setIdMaestro(maestro);
                            grupo.actualizar(this.horarios, this.horariosEliminados);
                            Mensaje.informacion("Exito! al registrar el grupo");
                            this.escena.cargarEscena(EscenaPrincipal.EscenaBuscarGrupo);
                        }
                    }
                } catch (Exception e) {
                    Mensaje.advertencia("No hay conexión con la base de Datos");
                }
            } else {
                Mensaje.advertencia("Corrija los campos en rojo");
            }
        }
    }

    private void inicializarSpinner(Spinner spinner, int minimo, int maximo, int aumento) {
        SpinnerValueFactory<Integer> valueFactory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(minimo, maximo, 0, aumento);
        spinner.setValueFactory(valueFactory);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bActualizar.setVisible(false);
        this.cNoDeColaborador.setCellValueFactory(
                new PropertyValueFactory<>("NoDeColaborador")
        );
        this.cNombre.setCellValueFactory(
                new PropertyValueFactory<>("Nombre")
        );
        this.cApellidos.setCellValueFactory(
                new PropertyValueFactory<>("Apellidos")
        );

        this.cbDia.getItems().addAll("Lunes", "Martes", "Miércoles",
                "Jueves", "Viernes", "Sábado", "Domingo"
        );

        this.inicializarSpinner(this.sHorasInicio, 6, 22, 1);
        this.inicializarSpinner(this.spHorasFin, 6, 22, 1);
        this.inicializarSpinner(this.spMinutosInicio, 0, 55, 5);
        this.inicializarSpinner(this.spMinutosFin, 0, 55, 5);

        this.cDia.setCellValueFactory(
                new PropertyValueFactory<>("Dia")
        );

        this.cInicio.setCellValueFactory(
                new PropertyValueFactory<>("Inicio")
        );

        this.cFin.setCellValueFactory(
                new PropertyValueFactory<>("Fin")
        );

        this.txNivel.getItems().addAll("Basico", "Intermedio", "Avanzado");
        for (Danza danza : Danza.obtenerTodas()) {
            this.tfTipoDeDanza.getItems().add(danza.getTipoDanza());
        }

        Platform.runLater(() -> {
            Grupo grupo = (Grupo) this.parametros;
            this.horarios = new ArrayList(grupo.getHorarioList());
            this.horariosEliminados = new ArrayList<>();
            this.tfSalon.setText(grupo.getSalon());
            this.tfTipoDeDanza.getSelectionModel().select(grupo.getTipoDeDanza());
            this.txNivel.getSelectionModel().select(grupo.getNivel());
            this.lMaestro.setText("Maestro: " + grupo.getMaestro());
            ObservableList listaHorario = FXCollections.observableArrayList(this.horarios);
            this.tHorario.setItems(listaHorario);
            this.lMaestro.setLayoutX(340);
            this.lMaestro.setLayoutY(130);
        });

        this.tHorario.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    try {
                        Horario horario = (Horario) this.tHorario.getSelectionModel().getSelectedItem();
                        this.cbDia.setValue(horario.getDia());
                        String[] inicio = horario.getInicio().split(":");
                        String[] fin = horario.getFin().split(":");
                        this.sHorasInicio.getValueFactory().setValue(Integer.parseInt(inicio[0]));
                        this.spMinutosInicio.getValueFactory().setValue(Integer.parseInt(inicio[1]));
                        this.spHorasFin.getValueFactory().setValue(Integer.parseInt(fin[0]));
                        this.spMinutosFin.getValueFactory().setValue(Integer.parseInt(fin[1]));
                    } catch (Exception e) {
                        this.tHorario.setItems(FXCollections.observableArrayList(this.horarios));
                    }
                });
    }
}
