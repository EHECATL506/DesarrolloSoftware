package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.Maestro;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 * FXML Controller class
 *
 * @author EHECA
 */
public class FXMLEgresoController extends MainController implements Initializable {
    @FXML
    private TableColumn columnaid;
    @FXML
    private TextField campomaestro;
    @FXML
    private TextField campomonto;
    @FXML
    private TableColumn columnanombre;
    @FXML
    private Button botonoregistra;
    @FXML
    private Button botoncancelar;
    @FXML
    private TableView tablamaestros;
    @FXML
    private TableColumn columnaapellido;
    @FXML
    private TextArea campomotivo;
   
    @FXML
    void registraEgresos(ActionEvent event) {
        /*
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
        EgresosJpaController erjpa = new EgresosJpaController(emf);
        Calendar calendario=GregorianCalendar.getInstance();
        java.util.Date fecha=new java.util.Date();
        fecha.setTime(calendario.getTimeInMillis());
        java.sql.Date fechaSql=new java.sql.Date(fecha.getTime());
        int monto=Integer.parseInt(this.campomonto.getText());
        int idmaestro=Integer.parseInt(this.campomaestro.getText());
        try{
        Maestro maestro = (Maestro) this.tablamaestros.getSelectionModel().getSelectedItem();
            Egresos egreso= new Egresos();
            egreso.setMonto(monto);
            egreso.setMotivo(this.campomotivo.getText());
            egreso.setFechaInicio(fechaSql);
            egreso.setIdMaestro(idmaestro);
           erjpa.create(egreso);
        }
        catch(Exception e){
            e.printStackTrace();
        }*/
    }

    @FXML
    void cancelar(ActionEvent event) {
         escena.cargarEscena(EscenaPrincipal.EscenaInicio);
    }
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      Maestro megreso = (Maestro) this.tablamaestros.getSelectionModel().getSelectedItem();
       this.columnaid.setCellValueFactory(
                new PropertyValueFactory<>("id"))
               ;
       this.columnanombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );
        this.columnaapellido.setCellValueFactory(
                new PropertyValueFactory<>("apellidos")
        );
        ObservableList lista = FXCollections.observableArrayList(
                Maestro.obtenerMaestroPorNombre("")
        );
        this.tablamaestros.setItems(lista);
    }    
}
  