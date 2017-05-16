package AredEspacio.Controlador;

import Modelo.Clase;
import Modelo.Horario;
import Modelo.Notificacion;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLNotificacionesController extends MainController implements Initializable {

    @FXML
    private TableView<Notificacion> tNotificacionesPago;
    @FXML
    private TableColumn cDescripcionPago;
    
    @FXML
    private TableView<Notificacion> tNotificacionesClase;
    @FXML
    private TableColumn cDescripcionClase;

    private List<Notificacion> notificacionesPago;
    private List<Notificacion> notificacionesClase;

    private int getDayFromDate(long date) {
        Calendar fecha = new GregorianCalendar();
        if (date > 0) {
            fecha.setTimeInMillis(date);
        }
        return fecha.get(Calendar.DAY_OF_MONTH);
    }

    private int getNameDayFromDate(long date) {
        Calendar fecha = new GregorianCalendar();
        if (date > 0) {
            fecha.setTimeInMillis(date);
        }
        return fecha.get(Calendar.DAY_OF_WEEK);
    }

    private int nameDayToIntValue(String name) {
        switch (name) {
            case "Domingo":
                return 1;
            case "Lunes":
                return 2;
            case "Martes":
                return 3;
            case "Miércoles":
                return 4;
            case "Jueves":
                return 5;
            case "Viernes":
                return 6;
            case "Sábado":
                return 7;
            default:
                return -1;
        }
    }

    public void obtenerNotificaciones() {
        int DIA_DEL_MES = this.getDayFromDate(0);
        int DIA_DE_LA_SEMANA = this.getNameDayFromDate(0);

        for (Clase clase : Clase.listaDeClases()) {
            int dia = this.getDayFromDate(clase.getFechaRegistro().getTime());
            if (dia >= (DIA_DEL_MES - 3) && dia <= DIA_DEL_MES) {
                this.notificacionesPago.add(this.claseToNotificacion(clase, dia));
            }
        }

        for (Horario horario : Horario.listaDeHorarios()) {
            if (DIA_DE_LA_SEMANA == this.nameDayToIntValue(horario.getDia())) {
                this.notificacionesClase.add(this.horarioToNotificacion(horario));
            }
        }
    }

    private Notificacion horarioToNotificacion(Horario horario) {
        String descripcion = "Grupo: " + horario.getIdGrupo().getTipoDeDanza();
        descripcion += "  Nivel: " + horario.getIdGrupo().getNivel();
        descripcion += "  Salon: " + horario.getIdGrupo().getSalon();
        descripcion += "\nMaestro: " + horario.getIdGrupo().getMaestro();
        descripcion += "\nHora: " + horario.getHora();
        return new Notificacion(descripcion);
    }

    private String getMonth() {
        Calendar fecha = new GregorianCalendar();
        switch (fecha.get(Calendar.MONTH)) {
            case 0:
                return "enero";
            case 1:
                return "febrero";
            case 2:
                return "marzo";
            case 3:
                return "abril";
            case 4:
                return "mayo";
            case 5:
                return "junio";
            case 6:
                return "julio";
            case 7:
                return "agosto";
            case 8:
                return "septiembre";
            case 9:
                return "octubre";
            case 10:
                return "noviembre";
            case 11:
                return "diciembre";
            default:
                return "";
        }
    }

    private Notificacion claseToNotificacion(Clase clase, int day) {
        String descripcion = "Nombre: " + clase.getNombre() + " " + clase.getApellidos();
        descripcion += "\nGrupo: " + clase.getIdGrupo().getTipoDeDanza()
                + "  Nivel: " + clase.getIdGrupo().getNivel() + "  Salon: " + clase.getIdGrupo().getSalon();
        descripcion += "\nUltimo dia para recibir el pago: " + day + " de " + this.getMonth() + "\n  ";
        return new Notificacion(descripcion);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.notificacionesPago = new ArrayList<>();
        this.notificacionesClase = new ArrayList<>();
        this.obtenerNotificaciones();
        
        this.cDescripcionPago.setCellValueFactory(
                new PropertyValueFactory<>("Descripcion")
        );
        this.tNotificacionesPago.setItems(FXCollections.observableArrayList(this.notificacionesPago));
        
        
        this.cDescripcionClase.setCellValueFactory(
                new PropertyValueFactory<>("Descripcion")
        );
        this.tNotificacionesClase.setItems(FXCollections.observableArrayList(this.notificacionesClase));
    }
}
