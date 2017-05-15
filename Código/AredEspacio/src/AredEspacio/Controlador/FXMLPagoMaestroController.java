package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.Clase;
import Modelo.DatoPago;
import Modelo.Egreso;
import Modelo.Grupo;
import Modelo.Maestro;
import Modelo.Mensaje;
import Modelo.Pago;
import Modelo.TipoDeMenu;
import Modelo.Validar;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLPagoMaestroController extends MainController implements Initializable {

    @FXML
    private TableView<DatoPago> tDatosDePago;

    @FXML
    private TableColumn cGrupo;

    @FXML
    private TableColumn cPagos;

    @FXML
    private TableColumn cTotal;

    @FXML
    private Label lNombre;

    @FXML
    private Label lFecha;

    @FXML
    private Label lUltimaFecha;

    @FXML
    private TextField cantidad;

    @FXML
    void pagarMaesto(ActionEvent event) {
        boolean bCantidad = Validar.cantidadSinPunto(cantidad);
        if (bCantidad) {
            try {
                int cantidadTotal = Integer.parseInt(this.cantidad.getText());
                if (cantidadTotal > 0) {
                    Egreso egreso = new Egreso();
                    egreso.setFecha(new Date(new GregorianCalendar().getTimeInMillis()));
                    egreso.setIdMaestro(this.maestro.getId());
                    egreso.setMonto(cantidadTotal);
                    egreso.setMotivo("Pago a Maestro");
                    egreso.crear();
                    this.maestro.setFechaDeDeshabilitacion(new Date(new GregorianCalendar().getTimeInMillis()));
                    this.maestro.actualizar();
                    Mensaje.informacion("Se ha registrado el pago con Exito!!");
                    this.escena.cargarEscenaConParametros(EscenaPrincipal.EscenaPagoMaestro,
                            this.maestro, null);
                }else{
                   Mensaje.advertencia("Para registrar el pago debe ser mayor a 0");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Mensaje.advertencia("No hay conexión, Intentelo mas tarde");
            }
        } else {
            Mensaje.advertencia("Ingrese una cantidad correcta");
        }
    }

    private Maestro maestro;

    private String dateToString(long date) {
        GregorianCalendar fecha = new GregorianCalendar();
        if (date > -1) {
            fecha.setTimeInMillis(date);
        }
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int año = fecha.get(Calendar.YEAR);
        return String.format(" %1$02d/%2$02d/%3$04d", dia, mes, año);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            maestro = (Maestro) this.parametros;
            if (maestro.getDeshabilitado()) {
                Mensaje.advertencia("No se puede pagar al Maestro\nporque esta deshabilitado");
                this.escena.cargarEscenaConParametros(EscenaPrincipal.EscenaBuscarMaestro,
                        null, TipoDeMenu.PAGO);
            } else {
                List<Clase> clases = new ArrayList<>();
                for (Grupo grupo : maestro.getGrupoList()) {
                    for (Clase clase : Clase.obtenerClasesDelGrupo(grupo.getIdGrupo())) {
                        clases.add(clase);
                    }
                }
                List<Pago> pagos = new ArrayList<>();
                Date fecha = maestro.getFechaDeDeshabilitacion();
                for (Clase clase : clases) {
                    for (Pago pago : Pago.obtenerPagosPorIdDeClase(clase.getIdClase(), fecha)) {
                        pagos.add(pago);
                    }
                }
                List<DatoPago> datos = new ArrayList<>();
                int totalFinal = 0;
                for (Grupo grupo : maestro.getGrupoList()) {
                    String datosGrupo = "";
                    datosGrupo += "Salon: " + grupo.getSalon();
                    datosGrupo += "\nDanza: " + grupo.getTipoDeDanza();
                    datosGrupo += "\nNivel: " + grupo.getNivel();
                    int noPagos = 0;
                    int total = 0;
                    for (Pago pago : pagos) {
                        if (pago.getIdClase().getIdGrupo().getIdGrupo() == grupo.getIdGrupo()) {
                            noPagos++;
                            total += pago.getAbono();
                        }
                    }
                    totalFinal += total;
                    datos.add(new DatoPago(datosGrupo, String.valueOf(noPagos), String.valueOf(total)));
                }
                datos.add(new DatoPago(" ", "Total", String.valueOf(totalFinal)));
                this.tDatosDePago.setItems(FXCollections.observableArrayList(datos));
                this.cantidad.setText(String.valueOf(totalFinal));
                this.lFecha.setText("Fecha de Hoy: " + this.dateToString(-1));
                this.lUltimaFecha.setText("Ultima fecha de Pago: " + this.dateToString(this.maestro.getFechaDeDeshabilitacion().getTime()));
                String nombre = "Nombre del Maestro: " + this.maestro.getNombre() + " " + this.maestro.getApellidos();
                this.lNombre.setText(nombre);
            }
        });
        this.cGrupo.setCellValueFactory(
                new PropertyValueFactory<>("Grupo")
        );
        this.cPagos.setCellValueFactory(
                new PropertyValueFactory<>("Pagos")
        );
        this.cTotal.setCellValueFactory(
                new PropertyValueFactory<>("Total")
        );
    }
}
