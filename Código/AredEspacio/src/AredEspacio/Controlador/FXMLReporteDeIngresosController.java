package AredEspacio.Controlador;

import Modelo.Pago;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLReporteDeIngresosController extends MainController implements Initializable {

    @FXML
    private TableView<Pago> tEgresos;
    
    @FXML
    private TableColumn cTipo;

    @FXML
    private TableColumn cMonto;

    @FXML
    private TableColumn cGrupo;

    @FXML
    private TableColumn cAlumno;

    @FXML
    private TableColumn cPromocion;
    
    @FXML
    private TableColumn cFecha;

    @FXML
    private ComboBox<String> comboMes;

    @FXML
    private ComboBox<String> comboAño;

    @FXML
    private Label lTotal;

    private int mes = 0;
    private int año = 0;
    private List<Pago> pagos;

    private List<String> obtenerAños() {
        List<String> años = new ArrayList<>();
        años.add("Todos");
        for (Pago pago : this.pagos) {
            Calendar fecha = new GregorianCalendar();
            fecha.setTimeInMillis(pago.getFechaPago().getTime());
            String anio = String.valueOf(fecha.get(Calendar.YEAR));
            if (!años.contains(anio)) {
                años.add(anio);
            }
        }
        return años;
    }

    public void actualizarEgresos() {
        List<Pago> auxiliar = new ArrayList<>();
        for (Pago pago : this.pagos) {
            Calendar fecha = new GregorianCalendar();
            fecha.setTimeInMillis(pago.getFechaPago().getTime());
            int añoAux = fecha.get(Calendar.YEAR);
            int mesAux = fecha.get(Calendar.MONTH)+1;
            if (this.año == 0) {
                if (this.mes == 0) {
                    auxiliar.add(pago);
                }
                if (this.mes == mesAux) {
                    auxiliar.add(pago);
                }
            }
            if (this.año == añoAux) {
                if (this.mes == 0) {
                    auxiliar.add(pago);
                }
                if (this.mes == mesAux) {
                    auxiliar.add(pago);
                }
            }
        }
        int total = 0;
        for(Pago pago: auxiliar){
            //Verificar abono o precio del grupo
            total += pago.getAbono();
            //total += pago.getIdClase().getIdGrupo().getCosto();
        }
        this.lTotal.setText("Total: "+total);
        this.tEgresos.setItems(FXCollections.observableArrayList(auxiliar));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.pagos = Pago.obtenerTodosLosPagos();
       
        this.cTipo.setCellValueFactory(
                new PropertyValueFactory<>("TipoDePago")
        );
        this.cMonto.setCellValueFactory(
                new PropertyValueFactory<>("AbonoString")
        );
        
        this.cGrupo.setCellValueFactory(
                new PropertyValueFactory<>("Grupo")
        );
        
        this.cAlumno.setCellValueFactory(
                new PropertyValueFactory<>("Alumno")
        );
        
        this.cPromocion.setCellValueFactory(
                new PropertyValueFactory<>("Promocion")
        );
        
        this.cFecha.setCellValueFactory(
                new PropertyValueFactory<>("Fecha")
        );

        this.comboMes.getItems().addAll(
                "Todos", "Enero", "Febrero", "Marzo",
                "Abril", "Mayo", "Junio", "Julio",
                "Agosto", "Septiembre", "Octubre",
                "Noviembre", "Diciembre");
        this.comboMes.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue object, String anterior, String actual) {
                switch (actual) {
                    case "Enero":
                        mes = 1;
                        break;
                    case "Febrero":
                        mes = 2;
                        break;
                    case "Marzo":
                        mes = 3;
                        break;
                    case "Abril":
                        mes = 4;
                        break;
                    case "Mayo":
                        mes = 5;
                        break;
                    case "Junio":
                        mes = 6;
                        break;
                    case "Julio":
                        mes = 7;
                        break;
                    case "Agosto":
                        mes = 8;
                        break;
                    case "Septiembre":
                        mes = 9;
                        break;
                    case "Octubre":
                        mes = 10;
                        break;
                    case "Noviembre":
                        mes = 11;
                        break;
                    case "Diciembre":
                        mes = 12;
                        break;
                    default:
                        mes = 0;
                }
                actualizarEgresos();
            }
        });
        
        for (String año : this.obtenerAños()) {
            this.comboAño.getItems().add(año);
        }
        
        this.comboAño.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue object, String anterior, String actual) {
                try {
                    año = Integer.parseInt(actual);
                } catch (Exception e) {
                    año = 0;
                }
                actualizarEgresos();
            }
        });
        this.comboAño.getSelectionModel().select(0);
        this.comboMes.getSelectionModel().select(0);
    }
}
