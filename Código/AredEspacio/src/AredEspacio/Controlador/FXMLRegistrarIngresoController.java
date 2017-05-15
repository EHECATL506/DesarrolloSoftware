package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.Pago;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author EHECA
 */
public class FXMLRegistrarIngresoController extends MainController implements Initializable {
    @FXML
    private TableColumn<?, ?> columnadescuento;
    @FXML
    private TableColumn<?, ?> columnatipopago;
    @FXML
    private TextField campototal;
    @FXML
    private TableColumn<?, ?> columnapromocion;
    @FXML
    private TableColumn<?, ?> columnadescripcion;
    @FXML
    private TableView<?> tablaingresos;
    @FXML
    private Button botonatras;
    @FXML
    private ComboBox<?> combomes;
    @FXML
    private TableColumn<?, ?> columnapago;
    @FXML
    private TableColumn<?, ?> columnafecha;
    
    @FXML
    public void buscarIngresos() {
        ObservableList meses = FXCollections.observableArrayList("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio",
            "Agosto","Septiembre","Octubre","Noviembre","Diciembre");
        combomes.setItems(meses);
        List<Pago> pagos = new ArrayList<>();
        if (this.combomes.getValue() != null) {
             if (this.combomes.getValue().equals("Mayo")) {
                 pagos=Pago.listarMeses(5);
                 
             }else
                if (this.combomes.getValue().equals("Febrero")) {
                     pagos=Pago.listarMeses(2);
                 }
             /*
           switch(combomes){
               case "Enero":  
                pagos = Pago.listarMeses(1);
                break;
                case"Febrero":
                    pagos= Pago.listarMeses(2);
               break;
                case "Marzo" :  
                pagos = Pago.listarMeses(3);
                break;
                case"Abril":
                    pagos= Pago.listarMeses(4);
               break;
                case "Mayo" :  
                pagos = Pago.listarMeses(5);
                break;
                case"Junio":
                    pagos= Pago.listarMeses(6);
               break;
                case "Julio" :  
                pagos = Pago.listarMeses(7);
                break;
                case"Agosto":
                    pagos= Pago.listarMeses(8);
               break;
                case "Septiembre" :  
                pagos = Pago.listarMeses(9);
                break;
                case"Octubre":
                    pagos= Pago.listarMeses(10);
               break;
                case "Noviembre" :  
                pagos = Pago.listarMeses(11);
                break;
                case"Diciembre":
                    pagos= Pago.listarMeses(12);
               break;
            }
*/
            ObservableList lista = FXCollections.observableArrayList(pagos);
            this.tablaingresos.setItems(lista);
        }
    }
    
    
    @FXML
    void regresar(ActionEvent event) {
        escena.cargarEscena(EscenaPrincipal.EscenaInicio);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Pago ingreso = (Pago) this.tablaingresos.getSelectionModel().getSelectedItem();
        this.columnapago.setCellValueFactory(
                new PropertyValueFactory<>("idPago")
        );
        this.columnatipopago.setCellValueFactory(
                new PropertyValueFactory<>("tipoDePago")
        );
        this.columnafecha.setCellValueFactory(
                new PropertyValueFactory<>("fechaPago")
        );
         this.columnapromocion.setCellValueFactory(
                new PropertyValueFactory<>("idPromocion")
        );  
        this.columnadescripcion.setCellValueFactory(
                new PropertyValueFactory<>("id")        
        );
        this.columnadescuento.setCellValueFactory(
                new PropertyValueFactory<>("descuento")
        );  
         ObservableList lista = FXCollections.observableArrayList(
                Pago.listar()
        );
         
         ObservableList meses = FXCollections.observableArrayList("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio",
            "Agosto","Septiembre","Octubre","Noviembre","Diciembre");
        combomes.setItems(meses);
}
    }