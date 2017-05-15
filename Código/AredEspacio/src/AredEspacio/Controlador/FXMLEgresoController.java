package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import ControladorBD.EgresoJpaController;
import Modelo.Egreso;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import Modelo.Validar;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
/**
 * FXML Controller class
 *
 * @author EHECA
 */
public class FXMLEgresoController extends MainController implements Initializable {
    @FXML
    private TextField campomonto;
    @FXML
    private Button botonoregistra;
    @FXML
    private Button botoncancelar;
    @FXML
    private TextArea campomotivo;
   
    @FXML
    void registraEgresos(ActionEvent event) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
        EgresoJpaController erjpa = new EgresoJpaController(emf);
        Calendar calendario=GregorianCalendar.getInstance();
        java.util.Date fecha=new java.util.Date();
        fecha.setTime(calendario.getTimeInMillis());
        java.sql.Date fechaSql=new java.sql.Date(fecha.getTime());
        int monto=Integer.parseInt(this.campomonto.getText());
        try{
            Egreso egreso= new Egreso();
            egreso.setMonto(monto);
            egreso.setMotivo(this.campomotivo.getText());
            egreso.setFecha(fechaSql);
            this.validarInformacion();
           erjpa.create(egreso);
        }
        catch(Exception e){
            e.printStackTrace();
            this.alertaSinConexion();
    }
        
    }
        
    @FXML
    void cancelar(ActionEvent event) {
         escena.cargarEscena(EscenaPrincipal.EscenaInicio);
    }
  
     private void alertaSinConexion() {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setHeaderText("No se puede conectar con la Base de Datos");
        alerta.setContentText("Revisa que el servidor de Base de Datos este funcionando");
        alerta.showAndWait();
    }
     
     public void alertaDeValidacion() {  
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText("Corrija los campos marcados en rojo");
        alerta.setContentText("Como se deben llenar los campos:");
        TextArea textArea = new TextArea();
        textArea.setText("• No puede haber campos vacíos\n"
                + "• Se deben seleccionar un numero valido de monto para registrar el egreso\n"
                + "• Debe tener al menos una palabra clave para el registro de egresos\n");
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);
        alerta.getDialogPane().setExpandableContent(expContent);
        alerta.showAndWait();
    }
     
     private boolean validarInformacion() {
       boolean bMonto = this.validarAlfanumerico(this.campomonto);
        boolean bMotivo = this.validarCampo(this.campomotivo);
        if(bMonto&& bMotivo){
            return true;
        }
       else{
            this.alertaDeValidacion();
            return false;
     }
     }
     
     
     private boolean validarAlfanumerico(TextField campo) {
        if (Pattern.compile("[a-zA-Z0-9ñÑáÁéÉíÍóÓúÚ\\s\\.\\#\\:\\-\\_\\@]{1,45}").matcher(campo.getText()).matches()) {
            campo.setStyle(null);
            return true;
        } else {
            campo.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            return false;
        }
    }
     
       private boolean validarCampo(TextArea campo) {
        if (Pattern.compile("[a-zA-ZñÑáÁéÉíÍóÓúÚ\\s\\.\\#\\:]{1,45}").matcher(campo.getText()).matches()) {
            campo.setStyle(null);
            return true;
        } else {
            campo.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            return false;
        }
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}
  