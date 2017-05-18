package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import ControladorBD.AlumnoJpaController;
import ControladorBD.ClaseJpaController;
import ControladorBD.DanzaJpaController;
import ControladorBD.GrupoJpaController;
import ControladorBD.HorarioJpaController;
import ControladorBD.PagoJpaController;
import ControladorBD.PromocionJpaController;
import Modelo.Alumno;
import Modelo.Clase;
import Modelo.Danza;
import Modelo.FilaClase;
import Modelo.FilaHorario;
import Modelo.Grupo;
import Modelo.Horario;
import Modelo.Mensaje;
import Modelo.Pago;
import Modelo.Promocion;
import Modelo.TipoDeMenu;
import Modelo.Validar;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
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
    private TableColumn<?, ?> tCCosto;
    
    @FXML
    private Button clicInscribir;

    @FXML
    private TableView<FilaClase> tVClase;
    @FXML
    private TableColumn<FilaClase, String> tCProximoPago;
    @FXML
    private TableColumn<FilaClase, String> tCGrupo;
    @FXML
    private TableColumn<FilaClase, String> tCFechaIngreso;
    @FXML
    private TableColumn<FilaClase, String> tCDanza;
    @FXML
    private Button clicAgregar;
    @FXML
    private Button clicBuscar;

    //ObservableList<FilaHorario> lista;
    private ClaseJpaController jpaClase;
    private DanzaJpaController jpaDanza;
    private GrupoJpaController jpaGrupo;
    private HorarioJpaController jpaHorario;
    private PagoJpaController jpaPago;
    private PromocionJpaController jpaPromocion;

    private TextField tFPaga;
    private TextField tFTotal;
    private TextField tFResto;
    private TextField tFDescuento;
    private TextField tFCantidad;
    private ComboBox<String> cBPromocion;

    private List<Clase> clases;
    public void desplegarClases(Alumno alumno) {
        ObservableList<FilaClase> oLlista = FXCollections.observableArrayList();
        clases = jpaClase.obtenerPorAlumno(alumno);
        for (Clase c : clases) {
            
            
            
            String grupo = c.getIdGrupo().toString();
            String fechaIngreso = new SimpleDateFormat("EEE, d MMM yyyy").format(c.getFechaRegistro());
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(c.getFechaRegistro());
            calendar.add(Calendar.DAY_OF_YEAR, 90);
            
            String danza = c.getIdGrupo().getTipoDeDanza();
            String proximoPago =  new SimpleDateFormat("EEE, d MMM yyyy").format(calendar.getTime());
            List<Pago> pagos = c.getPagoList();
            

            
            oLlista.add(new FilaClase(grupo, fechaIngreso, proximoPago, danza, "" , c));
        }       

        tVClase.setItems(oLlista);
        
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
                //####aqui estoy ahora
                dIncripcion.setHeaderText("Capture el precio del la clase: "
                        + filaClase.getClase().getIdGrupo().getTipoDeDanza()
                        + " " + filaClase.getClase().getIdGrupo().getNivel()
                );
                dIncripcion.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

                //comboDescuentos ><'
                cBPromocion = new ComboBox();
                List<Promocion> promociones = jpaPromocion.findPromocionEntities();
                ObservableList oLPromociones = FXCollections.observableArrayList();
                for (Promocion p : promociones) {
                    String v = p.getDescripcion() + " ( " + p.getPorcentajeDeDescuento() + " )";
                    oLPromociones.add(v);
                }
                cBPromocion.setItems(oLPromociones);

                //spinerPaga
                //obtener el costo de la clase
                tFCantidad = new TextField("$" + String.valueOf(filaClase.getClase().getIdGrupo().getCosto()) + ".00");
                tFCantidad.setDisable(true);
                tFPaga = new TextField();
                tFTotal = new TextField();
                tFTotal.setDisable(true);
                tFResto = new TextField();
                tFResto.setDisable(true);
                tFDescuento = new TextField();
                tFDescuento.setDisable(true);

                Button clicPago = new Button("Calcular Pago");
                Button clicImprimir = new Button("Imprimir Pago");

                HBox panel1 = new HBox(8);
                panel1.getChildren().addAll(
                        new Label("   Cantidad a Pagar:"), tFCantidad,
                        new Label("    Promoción:"), cBPromocion);

                /*
                HBox panel2 = new HBox(8);

                panel2.getChildren().addAll(
                        new Label("Cantidad que Paga:"), tFPaga,
                        new Label("         Cambio:"), tFResto
                );
                */
                HBox panel3 = new HBox(8);
                panel3.getChildren().addAll(
                        new Label("        Total a Pagar:"), tFTotal,
                        new Label("    Descuento:"), tFDescuento
                );

                //FilaBotones
                HBox panel4 = new HBox();
                panel4.getChildren().addAll(clicImprimir, clicPago);

                VBox panel = new VBox(10);
                panel.getChildren().addAll(panel1, panel3, panel4);
                dIncripcion.getDialogPane().setContent(panel);
                dIncripcion.show();
                
                clicImprimir.setOnAction(new EventHandler<ActionEvent>() {
                     @Override
                    public void handle(ActionEvent event) {
                        
                    }
                });

                //clicPagarIncripcion
                clicPago.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String promocionSeleccionada = cBPromocion.getValue();
                        Promocion promocion = null;

                        //busca la promocion seleccionada
                        for (Promocion p : promociones) {
                            String v = p.getDescripcion() + " ( " + p.getPorcentajeDeDescuento() + " )";
                            if (v.equals(promocionSeleccionada)) {
                                promocion = p;
                            }
                        }
                        if (validarPago() == 1) {
                            guardarPago(new Pago(), filaClase.getClase(), promocion);
                        } else {
                            Mensaje.advertencia("Los campos han sido invalidos");
                        }
                    }
                });
            }
        });
        
        
        
    }
 
    public void desplegarGrupos(Danza danza, Alumno alumno) {        
        List<Grupo> grupos = jpaGrupo.obtenerPorDanza(danza);
        ObservableList oLGrupos = FXCollections.observableArrayList(grupos);
        tVGrupo.setItems(oLGrupos);
        
        //desplegarHo   rario al seleccionar el grupo
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
                    //clase.setIdClase(alumno.getIdAlumno()+grupo.getIdGrupo());
                    clase.setIdGrupo(grupo);
                    clase.setFechaRegistro(new Date());

                    //validar que el alumno no se vuelva a inscribir a la misma clase
                    for (Clase c : clases) {
                        if (c.getIdAlumno().equals(clase.getIdAlumno())) {
                            if (c.getIdGrupo().equals(clase.getIdGrupo())) {
                                Mensaje.advertencia("EL ALUMNO YA HA SIDO REGISTRA EN ESTE GRUPO");
                                return;
                            } 
                        }
                                             
                    } 
                    jpaClase.create(clase);
                    desplegarClases(alumno);
                    
                    Mensaje.informacion("El alumno ha sido inscrito en el salón: " + grupo.getSalon());
                } else {
                    Mensaje.advertencia("No se ha seleccionado un grupo");
                }
            }
        });

        
        desplegarClases(alumno);
    }

    //validarPago
    private int validarPago() {
        int valido = 4;
        /*if (Validar.cantidad(tFPaga)) {

            valido >>= 1;
        }*/
        if (Validar.cantidad(tFCantidad)) {
            valido >>= 1;
        }
        if (Validar.combo(cBPromocion)) {
            valido >>= 1;
        }
        return valido;
    }

    //guardarPago
    private void guardarPago(Pago pago, Clase clase, Promocion promocion) {
        

        
        float cantidad = Float.valueOf(tFCantidad.getText().replaceAll("\\$", ""));
        float paga = 0;
        float descuento = 0;
        float total;
        double resta;
        
        
        descuento = promocion.getDescuento() * cantidad;
        total = (float) (cantidad - descuento);
        resta = total - paga;

        tFDescuento.setText("$" + String.valueOf(descuento));
        tFResto.setText("$" + String.valueOf(Math.abs(resta)));
        tFTotal.setText("$" + String.valueOf(total));

        
        if (Mensaje.confirmacion("¿Desea registrar la cantidad de: " + tFTotal.getText())) {
            
            pago.setFolio("fo-" + jpaPago.getPagoCount());
            pago.setDescuento(descuento);
            pago.setFechaPago(new Date());
            pago.setTipoDePago("INCRIPCIÓN");
            pago.setStatus("CORRIENTE");
            pago.setAbono(total);
            pago.setIdPromocion(promocion);
            pago.setIdClase(clase);
            
            clase.setFechaRegistro(new Date());
            try {
                jpaClase.edit(clase);
            } catch (Exception ex) {
                Logger.getLogger(FXMLIncribirAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            jpaPago.create(pago);
            Mensaje.informacion("El alumno ha pagado su inscripción");
            escena.cargarEscena(EscenaPrincipal.EscenaInicio );
        }
    }

    /*
    @FXML
    void clicBuscar(ActionEvent event) {
        
    }
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
        jpaClase = new ClaseJpaController(emf);
        jpaGrupo = new GrupoJpaController(emf);
        jpaDanza = new DanzaJpaController(emf);
        jpaHorario = new HorarioJpaController(emf);
        jpaPago = new PagoJpaController(emf);
        jpaPromocion = new PromocionJpaController(emf);

        //desplegarDanzas
        List<Danza> danzas;
        danzas = jpaDanza.findDanzaEntities();
        ObservableList oLDanzas = FXCollections.observableArrayList();
        for (Danza o : danzas) {
            String v = o.getTipoDanza();
            oLDanzas.add(v);
        }
        cBDanza.setItems(oLDanzas);
        //clicBuscar
        Alumno alumno = (Alumno) this.parametros;
        desplegarClases(alumno);
        clicBuscar.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String danzaSeleccionada = cBDanza.getValue();
                Danza danza = null;

                for (Danza d : danzas) {
                    if (d.getTipoDanza().equals(danzaSeleccionada)) {
                        danza = d;
                    }
                }
                desplegarGrupos(danza, alumno);
            }
        });
        tCSalon.setCellValueFactory(new PropertyValueFactory<>("Salon"));
        tCNivel.setCellValueFactory(new PropertyValueFactory<>("Nivel"));
        tCMaestro.setCellValueFactory(new PropertyValueFactory<>("Maestro"));
        tCCosto.setCellValueFactory(new PropertyValueFactory<>("Costo"));

        tCDia.setCellValueFactory(new PropertyValueFactory<>("Dia"));
        tCHora.setCellValueFactory(new PropertyValueFactory<>("Hora"));

        tCDanza.setCellValueFactory(new PropertyValueFactory<>("Danza"));
        tCGrupo.setCellValueFactory(new PropertyValueFactory<>("Grupo"));
        tCFechaIngreso.setCellValueFactory(new PropertyValueFactory<>("FechaIngreso"));
        tCProximoPago.setCellValueFactory(new PropertyValueFactory<>("ProximoPago"));

        });
    }
}