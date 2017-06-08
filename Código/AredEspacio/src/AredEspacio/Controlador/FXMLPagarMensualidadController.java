package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.Alumno;
import Modelo.Clase;
import Modelo.Grupo;
import Modelo.Mensaje;
import Modelo.Pago;
import Modelo.Promocion;
import Modelo.Validar;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLPagarMensualidadController extends MainController implements Initializable {

    @FXML
    private TableView<Alumno> tablaAlumnos;

    @FXML
    private TableColumn cDatosAlumno;

    @FXML
    private TableView<Grupo> tablaGrupos;

    @FXML
    private TableColumn cDatosGrupo;

    @FXML
    private TextField total;

    @FXML
    private Button botonPago;

    @FXML
    private ComboBox<Promocion> promociones;

    private int cantidad = 0;
    private int totalConDescuento = 0;
    private Alumno alumnoAux = null;
    private Grupo grupoAux = null;
    private Promocion promocionAux = null;

    @FXML
    public void pagarMensualidad() {
        if (Validar.cantidadSinPunto(this.total)) {
            if (Integer.parseInt(this.total.getText()) > 0) {
                Pago pago = new Pago();
                pago.setIdPromocion(promocionAux);
                pago.setFechaPago(new Date(new GregorianCalendar().getTimeInMillis()));
                if (promocionAux != null) {
                    pago.setDescuento(promocionAux.getDescuento() * 100);
                }
                pago.setAbono(Integer.parseInt(this.total.getText()));
                pago.setTipoDePago("Mensualidad");
                Clase claseAux = null;
                for (Clase clase : this.alumnoAux.getClaseList()) {
                    if (clase.getIdGrupo().getIdGrupo() == this.grupoAux.getIdGrupo()) {
                        claseAux = clase;
                    }
                }
                pago.setStatus("pagado");
                pago.setIdClase(claseAux);
                pago.crear();
                Mensaje.informacion("!Exito al registrar el pago de la mensualidad¡");
                this.escena.cargarEscena(EscenaPrincipal.EscenaPagarMensualidad);
            } else {
                Mensaje.advertencia("El pago debe ser mayor a 0");
            }
        } else {
            Mensaje.advertencia("Corrija el campo de total");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cDatosAlumno.setCellValueFactory(new PropertyValueFactory<>("DatosAlumno"));
        this.tablaAlumnos.setItems(FXCollections.observableArrayList(
                Alumno.listaDeAlumnos()
        ));
        this.cDatosGrupo.setCellValueFactory(new PropertyValueFactory<>("DatosGrupo"));
        this.tablaAlumnos.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    this.alumnoAux = newSelection;
                    List<Grupo> grupos = new ArrayList<>();
                    for (Clase clase : newSelection.getClaseList()) {
                        grupos.add(clase.getIdGrupo());
                    }
                    this.tablaGrupos.setItems(
                            FXCollections.observableArrayList(grupos)
                    );
                }
        );

        this.tablaGrupos.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    this.grupoAux = newSelection;
                    this.botonPago.setDisable(false);
                    this.total.setText(newSelection.getCosto() + "");
                    this.cantidad = newSelection.getCosto();
                    this.promociones.getSelectionModel().select(0);
                }
        );

        List<Promocion> listaDePromociones = new ArrayList<>();
        Promocion aux = new Promocion();
        aux.setDescuento(0);
        aux.setDescripcion("Sin promoción");
        listaDePromociones.add(aux);
        for (Promocion promocion : Promocion.listaDePromociones()) {
            if (promocion.getTipo().equals("Mensualidad")) {
                listaDePromociones.add(promocion);
            }
        }
        this.promociones.setItems(
                FXCollections.observableArrayList(listaDePromociones)
        );

        this.promociones.valueProperty().addListener(
                (ObservableValue<? extends Promocion> observable,
                        Promocion oldValue, Promocion newValue) -> {
                    if (newValue.getDescuento() != 0 && this.cantidad != 0) {
                        int monto = this.cantidad;
                        float porcentaje = monto * newValue.getDescuento();
                        monto -= porcentaje;
                        this.totalConDescuento = monto;
                        this.total.setText(monto + "");
                        this.promocionAux = newValue;
                    } else {
                        this.promocionAux = null;
                        this.totalConDescuento = 0;
                        this.total.setText(this.cantidad + "");
                    }
                });

    }
}
