package AredEspacio.Controlador;

import ControladorBD.ClaseJpaController;
import Modelo.Alumno;
import Modelo.Clase;
import Modelo.FilaHorario;
import Modelo.Grupo;
import Modelo.Horario;
import Modelo.Mensaje;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FXMLAsignarGrupoAlumnoController extends MainController implements Initializable {

    @FXML
    private ComboBox cBClase;
    @FXML
    private TextField tFDescuento;
    @FXML
    private TableColumn<FilaHorario, String> tCHora;
    @FXML
    private TextField tFResta;
    @FXML
    private TextField tFCantidad;
    @FXML
    private TableView<FilaHorario> tVHorario;
    @FXML
    private TextField tFTotal;
    @FXML
    private TableColumn<FilaHorario, String> tCDia;
    @FXML
    private TableColumn<FilaHorario, String> tCClase;
    @FXML
    private TextField tFNivel;
    @FXML
    private TableColumn<FilaHorario, String> tCNivel;
    @FXML
    private TextField tFPago;
    @FXML
    private TableColumn<FilaHorario, String> tCMaestro;
    private Alumno alumno;
    private Grupo grupo;
    ObservableList<FilaHorario> lista;

    public void desplegarGrupos() {
        /*lista = FXCollections.observableArrayList();
        for (Clase g : alumno.getClaseList()) {

            String clase = g.getIdGrupo().getTipoDeDanza();
            String maestro = (g.getIdGrupo().getIdMaestro().getNombre() + " ")
                    + g.getIdGrupo().getIdMaestro().getApellidos();
            String nivel = g.getIdGrupo().getNivel();
            for (Horario horario : g.getIdGrupo().getHorarioList()) {
                String dia = horario.getDia();
                String hora = horario.getHora();
                lista.add(new FilaHorario(clase, maestro, dia, hora, nivel));
            }

            tVHorario.setItems(lista);
        }
        */
    }

    @FXML
    void bAgregar(ActionEvent event) {
       /*
        String sClase = (String) cBClase.getValue();
        System.out.println(sClase);
        List<Grupo> temp = grupo.obtenerIdGrupo(sClase);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AredEspacioPU");
        ClaseJpaController jpa = new ClaseJpaController(emf);

        Clase clase = new Clase();
        clase.setIdGrupo(temp.get(0));
        clase.setIdAlumno(alumno);
        for (Clase g : alumno.getClaseList()) {
            if (g.getIdGrupo().getIdGrupo() != temp.get(0).getIdGrupo()) {
                continue;
            } else {
                jpa.create(clase);
                Mensaje.informacion("El alumno ha sido asiganda al grupo de " + sClase);
                desplegarGrupos();
            }
            System.out.println("no");
        }
        */
    }

    @FXML
    void bQuitar(ActionEvent event) {

    }

    @FXML
    void bInscribir(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*Platform.runLater(() -> {
            grupo = new Grupo();

            List<Grupo> danzas = grupo.obtenerDanzas();
            ObservableList clases = FXCollections.observableArrayList();
            for (Grupo o : danzas) {
                String v = o.getTipoDeDanza();
                clases.add(v);
            }
            cBClase.setItems(clases);

            tCClase.setCellValueFactory(new PropertyValueFactory<>("Clase"));
            tCMaestro.setCellValueFactory(new PropertyValueFactory<>("Maestro"));
            tCDia.setCellValueFactory(new PropertyValueFactory<>("Dia"));
            tCHora.setCellValueFactory(new PropertyValueFactory<>("Hora"));
            alumno = (Alumno) this.parametros;
            desplegarGrupos();
        });
        */
    }
}
