package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import ControladorBD.EgresoJpaController;
import Modelo.Egreso;
import Modelo.Maestro;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 * FXML Controller class
 *
 * @author EHECA
 */
public class FXMLReportarEgresosController extends MainController implements Initializable {
    @FXML
    private Button botonguardar;
    @FXML
    private TableColumn<?, ?> columnanombre;
    @FXML
    private Button botomcosultar;
    @FXML
    private Button botoncancelar;
    @FXML
    private TableColumn<?, ?> columnamotivo;
    @FXML
    private TableColumn<?, ?> columnaidegreso;
    @FXML
    private TableColumn<?, ?> columnamonto;
    @FXML
    private TableView<?> tablaegresos;
    @FXML
    private TableColumn<?, ?> columnaapellido;
    @FXML
    private TableColumn<?, ?> columnafecha;
    @FXML
    private TableColumn<?, ?> columanidmaestro;
    Calendar calendario=GregorianCalendar.getInstance();
    java.util.Date fecha=new java.util.Date();
    java.sql.Date fechaSql=new java.sql.Date(fecha.getTime());
    private String strNombreDelPDF;
   //private  Font fuenteNegra10 = new Font(Font.TIMES_ROMAN, 10, Font.BOLD, Color.BLACK);
    //private  Font fuente8 = new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.BLACK);
    //private  Font fuenteAzul25 = new Font(Font.TIMES_ROMAN, 25, Font.BOLD, Color.BLUE);
    Color grisClaro = new Color( 230,230,230);  
    Color azulClaro = new Color( 124,195,255 );
    Document document;
    PdfWriter writer;
    String strRotuloPDF;
     
    @FXML
    void exportar(ActionEvent event) {
         strRotuloPDF =" Reporte de Egresos generados por el programa Ared Espacio";
         strNombreDelPDF = "Reporte de egresos.pdf";
        try{   
            document = new Document( PageSize.LETTER.rotate() );  
            writer = PdfWriter.getInstance(document,new FileOutputStream(strNombreDelPDF));
            document.open();
             document.add(new Paragraph("Reporte generado a partir de la clase egreso  "+ " generado en:"+fechaSql));
            agregarContenido(document);
            document.close();
            System.out.println("Se ha generado el PDF: "+ strNombreDelPDF);
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
     
    private void agregarContenido(Document document) throws DocumentException{
        Paragraph ParrafoHoja = new Paragraph();
        agregarLineasEnBlanco(ParrafoHoja, 1);
        ParrafoHoja.add(new Paragraph(strRotuloPDF.toUpperCase ()));
        agregarLineasEnBlanco(ParrafoHoja, 1);
        agregarTabla(ParrafoHoja);
        agregarLineasEnBlanco(ParrafoHoja, 2);
        /*try
        {
            Image im = Image.getInstance("logo_mysql.gif");
            im.setAlignment(Image.ALIGN_CENTER | Image.TEXTWRAP );
            im.setWidthPercentage (50);
          ParrafoHoja.add(im);
        }
        catch(Exception e)
        {
            e.printStackTrace ();
        }
         */
        document.add(ParrafoHoja);
    }

    private void agregarTabla(Paragraph parrafo) throws BadElementException {
        float anchosFilas[] = {0.5f,0.5f,0.5f,0.5f};
        PdfPTable tabla = new PdfPTable(anchosFilas);
        String rotulosColumnas[] = {"Idpago","Monto","Motivo","FechaInicio"};
        tabla.setWidthPercentage(70);
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell cell = new PdfPCell(new Paragraph("Egreso"));
        cell.setColspan(4);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);     
        tabla.addCell(cell);
        try {
          EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
             {
                for(int i=0; i<rotulosColumnas.length; i++){
                    cell = new PdfPCell(new Paragraph(rotulosColumnas[i]));
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tabla.addCell(cell);
                }
                  EgresoJpaController erjpa = new EgresoJpaController(emf);
                  Egreso egreso= new Egreso();
                    cell = new PdfPCell(new Paragraph(String.valueOf(egreso.getIdPago())));
                    tabla.addCell(cell);
                    cell = new PdfPCell(new Paragraph(String.valueOf(egreso.getMonto())));
                    tabla.addCell(cell);
                    cell = new PdfPCell(new Paragraph(String.valueOf(egreso.getMotivo())));
                    tabla.addCell(cell);
                    cell = new PdfPCell(new Paragraph(String.valueOf(egreso.getFecha())));
                    tabla.addCell(cell);
          /*        cell = new PdfPCell(new Paragraph(rs.getString("id"),fuente8));
                    tabla.addCell(cell);
                    cell = new PdfPCell(new Paragraph(String.valueOf(rs.getInt("nombre")),fuente8) );
                    tabla.addCell(cell);
                    cell = new PdfPCell(new Paragraph(rs.getString("apellidos"),fuente8));
                    tabla.addCell(cell); */
             }
        }catch(Exception e) 
        {
            System.out.println("Excepcion al consultar la base de datos y en tu vida!!!");
            e.printStackTrace();
        }
        parrafo.add(tabla);
    } 
 
    private static void agregarLineasEnBlanco(Paragraph parrafo, int nLineas) {
        for (int i = 0; i < nLineas; i++) 
            parrafo.add(new Paragraph(" "));
    }
 
    public void abrirPDF(){
        try{
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + strNombreDelPDF);
        }catch (IOException e) 
        {
            e.printStackTrace();
        }   
    }   

    @FXML
    void atras(ActionEvent event) {
         escena.cargarEscena(EscenaPrincipal.EscenaInicio);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Egreso egreso = (Egreso) this.tablaegresos.getSelectionModel().getSelectedItem();
        Maestro megreso = (Maestro) this.tablaegresos.getSelectionModel().getSelectedItem();
        this.columnaidegreso.setCellValueFactory(
                new PropertyValueFactory<>("Idpago")
        );
        this.columnamonto.setCellValueFactory(
                new PropertyValueFactory<>("Monto")
        );
        this.columnamotivo.setCellValueFactory(
                new PropertyValueFactory<>("Motivo")
        );
         this.columnafecha.setCellValueFactory(
                new PropertyValueFactory<>("FechaInicio")
        );  
        this.columanidmaestro.setCellValueFactory(
                new PropertyValueFactory<>("id")        
        );
        this.columnanombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );
        this.columnaapellido.setCellValueFactory(
                new PropertyValueFactory<>("apellidos")
        );     
         ObservableList lista = FXCollections.observableArrayList(
              //  Egreso.listar()//, Maestro.listar()              
        );
       //  ObservableList listas = FXCollections.observableArrayList(
         //     Maestro.listar()              
        //);
        this.tablaegresos.setItems(lista);
        //this.tablaegresos.setItems(listas);
    }    
}