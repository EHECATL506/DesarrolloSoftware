package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import ControladorBD.AlumnoJpaController;
import ControladorBD.ClaseJpaController;
import ControladorBD.DanzaJpaController;
import ControladorBD.GrupoJpaController;
import ControladorBD.HorarioJpaController;
import ControladorBD.PagoJpaController;
import Modelo.Alumno;
import Modelo.Clase;
import Modelo.Danza;
import Modelo.FilaClase;
import Modelo.FilaHorario;
import Modelo.Grupo;
import Modelo.Horario;
import Modelo.Mensaje;
import Modelo.Pago;
import Modelo.TipoDeMenu;
import Modelo.Validar;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
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

public class FXMLIncribirAlumnoController extends MainController implements Initializable {

    @FXML
    private ComboBox<String> cBDanza;
    @FXML
    private TableColumn<FilaHorario, String> tCHora;
    @FXML
    private TableView<FilaHorario> tVHorario;
    @FXML
    private TableColumn<FilaHorario, String> tCDia;

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
    private PagoJpaController jpaPago;

    private TextField tFPaga;
    private TextField tFTotal;
    private TextField tFResto;
    private TextField tFDescuento;
    private TextField tFCantidad;
    private ComboBox cBPromocion;

    public void desplegarGrupos(Danza danza, Alumno alumno) {
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

                    //desplegar clase
                    ObservableList<FilaClase> oLlista = FXCollections.observableArrayList();
                    FilaClase fClase = new FilaClase(alumno.getNombre() + alumno.getApellidos(),
                            grupo.getSalon(), clase.getFechaRegistro().toString());
                    oLlista.add(fClase);
                    tVClase.setItems(oLlista);

                    Mensaje.informacion("EL ALUMNO HA SIDO AÑADIDO AL GRUPO: " + grupo.getSalon());
                } else {
                    Mensaje.advertencia("No se ha seleccionado un grupo");
                }
            }
        });

        //clicPagarIncripcion
        clicInscribir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FilaClase filaClase = tVClase.getSelectionModel().getSelectedItem();
                if (filaClase == null) {
                    return;
                }
                Dialog dIncripcion = new Dialog();
                dIncripcion.setTitle("Pago Incripción");
                dIncripcion.setHeaderText("Capture el precio del la clase: "
                        + clase.getIdGrupo().getTipoDeDanza()
                        + " " + clase.getIdGrupo().getNivel()
                );
                dIncripcion.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

                //comboDescuentos ><'
                cBPromocion = new ComboBox();
                ObservableList oLPromociones = FXCollections.observableArrayList("5%", "10%", "15%", "20%", "25%", "50%");
                cBPromocion.setItems(oLPromociones);

                //spinerPaga
                tFCantidad = new TextField();
                tFPaga = new TextField();
                tFTotal = new TextField();
                tFResto = new TextField();
                tFDescuento = new TextField();

                Button clicPago = new Button("Registrar Pago");
                Button clicImprimir = new Button("Imprimir Pago");

                HBox panel1 = new HBox(8);
                panel1.getChildren().addAll(
                        new Label("   Cantidad a Pagar:"), tFCantidad,
                        new Label("    Promoción:"), cBPromocion);

                HBox panel2 = new HBox(8);

                panel2.getChildren().addAll(
                        new Label("Cantidad que Paga:"), tFPaga,
                        new Label("Total a Pagar:"), tFTotal
                );

                HBox panel3 = new HBox(8);
                panel3.getChildren().addAll(
                        new Label("     Resta por Pagar:"), tFResto,
                        new Label("    Descuento:"), tFDescuento
                );

                //FilaBotones
                HBox panel4 = new HBox();
                panel4.getChildren().addAll(clicImprimir, clicPago);

                VBox panel = new VBox(10);
                panel.getChildren().addAll(panel1, panel2, panel3, panel4);
                dIncripcion.getDialogPane().setContent(panel);
                dIncripcion.show();

                //clicPagarIncripcion
                clicPago.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        Pago pago = new Pago();
                        if (validarPago(pago) == 1) {
                            guardarPago(pago, clase);
                        } else {
                            Mensaje.advertencia("Los campos han sido invalidos");
                        }
                    }
                });
            }
        });
    }

    //validarPago
    private int validarPago(Pago pago) {
        int valido = 8;
        if (Validar.cantidad(tFPaga)) {
            valido >>= 1;
        }
        if (Validar.cantidad(tFCantidad)) {
            valido >>= 1;
        }
        if (Validar.combo(cBPromocion)) {
            valido >>= 1;
        }
        return valido;
    }

    //guardarPago
    private void guardarPago(Pago pago, Clase clase) {

        double cantidad = Double.valueOf(tFCantidad.getText());
        double paga = Double.valueOf(tFPaga.getText());
        double descuento = 0;
        double total;
        double resta;

        switch ((String) cBPromocion.getValue()) {
            case "5%":
                descuento = 0.05 * cantidad;
                total = cantidad - descuento;
                resta = total - paga;
                tFDescuento.setText(String.valueOf(descuento));
                tFResto.setText(String.valueOf(resta));
                tFTotal.setText(String.valueOf(total));
                break;
            case "10%":
                descuento = 0.10 * cantidad;
                total = cantidad - descuento;
                resta = total - paga;
                tFDescuento.setText(String.valueOf(descuento));
                tFResto.setText(String.valueOf(resta));
                tFTotal.setText(String.valueOf(total));
                break;
            case "15%":
                descuento = 0.15 * cantidad;
                total = cantidad - descuento;
                resta = total - paga;
                tFDescuento.setText(String.valueOf(descuento));
                tFResto.setText(String.valueOf(resta));
                tFTotal.setText(String.valueOf(total));
                break;
            case "20%":
                descuento = 0.20 * cantidad;
                total = cantidad - descuento;
                resta = total - paga;
                tFDescuento.setText(String.valueOf(descuento));
                tFResto.setText(String.valueOf(resta));
                tFTotal.setText(String.valueOf(total));
                break;
            case "25%":
                descuento = 0.25 * cantidad;
                total = cantidad - descuento;
                resta = total - paga;
                tFDescuento.setText(String.valueOf(descuento));
                tFResto.setText(String.valueOf(resta));
                tFTotal.setText(String.valueOf(total));
                break;
            case "50%":
                descuento = 0.50 * cantidad;
                total = cantidad - descuento;
                resta = total - paga;
                tFDescuento.setText(String.valueOf(descuento));
                tFResto.setText(String.valueOf(resta));
                tFTotal.setText(String.valueOf(total));
                break;
        }

        if (Mensaje.confirmacion("¿DESEA REGISTRAR EL PAGO POR LA CANTIDAD DE: " + tFTotal.getText())) {
            pago.setFolio("fo-" + jpaPago.getPagoCount());

            pago.setAbono((float) paga);
            pago.setDescuento((float) descuento);
            pago.setFechaPago(new Date());
            pago.setTipoDePago("INCRIPCIÓN");
            pago.setStatus("CORRIENTE");
            pago.setIdClase(clase);

            jpaPago.create(pago);
            Mensaje.informacion("EL ALUMNO HA PAGADO SU INSCRIPCIÓN");
        }
    }

    @FXML
    void clicBuscar(ActionEvent event) {
        String danzaSeleccionada = cBDanza.getValue();
        Danza danza = null;

        for (Danza d : danzas) {
            if (d.getTipoDanza().equals(danzaSeleccionada)) {
                danza = d;
            }
        }
        desplegarGrupos(danza, (Alumno) this.parametros);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //      Platform.runLater(() -> {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
        jpaClase = new ClaseJpaController(emf);
        jpaGrupo = new GrupoJpaController(emf);
        jpaDanza = new DanzaJpaController(emf);
        jpaHorario = new HorarioJpaController(emf);
        jpaPago = new PagoJpaController(emf);

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
//        });
    }
}
