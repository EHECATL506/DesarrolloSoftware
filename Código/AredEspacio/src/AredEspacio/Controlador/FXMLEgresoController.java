package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.Egreso;
import Modelo.Mensaje;
import Modelo.Validar;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLEgresoController extends MainController implements Initializable {
    @FXML
    private TextField campomonto;
    @FXML
    private TextArea campomotivo;
   
    @FXML
    void registraEgresos(ActionEvent event) {
       boolean monto = Validar.cantidadSinPunto(this.campomonto);
       boolean motivo = Validar.area(this.campomotivo);
       if(monto && motivo){
           Egreso egreso = new Egreso();
           egreso.setFecha(new Date(new GregorianCalendar().getTimeInMillis()));
           egreso.setMonto(Integer.parseInt(this.campomonto.getText()));
           egreso.setMotivo(this.campomotivo.getText());
           try{
               egreso.crear();
               Mensaje.informacion("Se ha creado con exito el Egreso!!");
               this.escena.cargarEscena(EscenaPrincipal.EscenaRegistrarEgreso);
           }catch(Exception e){
               Mensaje.advertencia("No hay conexión, intentelo mas tarde");
           }
       }else{
           Mensaje.advertencia("Corrija los campos en rojo");
       }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
}
  