package AredEspacio.Controlador;

import Modelo.Egreso;
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

public class FXMLReporteDeEgresosController extends MainController implements Initializable {

    @FXML
    private TableView<Egreso> tEgresos;

    @FXML
    private TableColumn cMonto;

    @FXML
    private TableColumn cMotivo;

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
    private List<Egreso> egresos;

    private List<String> obtenerAños() {
        List<String> años = new ArrayList<>();
        años.add("Todos");
        for (Egreso egreso : this.egresos) {
            Calendar fecha = new GregorianCalendar();
            fecha.setTimeInMillis(egreso.getFecha().getTime());
            String anio = String.valueOf(fecha.get(Calendar.YEAR));
            if (!años.contains(anio)) {
                años.add(anio);
            }
        }
        return años;
    }

    public void actualizarEgresos() {
        List<Egreso> auxiliar = new ArrayList<>();
        for (Egreso egreso : this.egresos) {
            Calendar fecha = new GregorianCalendar();
            fecha.setTimeInMillis(egreso.getFecha().getTime());
            int añoAux = fecha.get(Calendar.YEAR);
            int mesAux = fecha.get(Calendar.MONTH)+1;
            if (this.año == 0) {
                if (this.mes == 0) {
                    auxiliar.add(egreso);
                }
                if (this.mes == mesAux) {
                    auxiliar.add(egreso);
                }
            }
            if (this.año == añoAux) {
                if (this.mes == 0) {
                    auxiliar.add(egreso);
                }
                if (this.mes == mesAux) {
                    auxiliar.add(egreso);
                }
            }
        }
        int total = 0;
        for(Egreso egreso: auxiliar){
            total += egreso.getMonto();
        }
        this.lTotal.setText("Total: "+total);
        this.tEgresos.setItems(FXCollections.observableArrayList(auxiliar));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.egresos = Egreso.obtenerTodosLosEgresos();
        this.cMonto.setCellValueFactory(
                new PropertyValueFactory<>("MontoCompleto")
        );
        this.cFecha.setCellValueFactory(
                new PropertyValueFactory<>("FechaCompleta")
        );
        this.cMotivo.setCellValueFactory(
                new PropertyValueFactory<>("MotivoCompleto")
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
