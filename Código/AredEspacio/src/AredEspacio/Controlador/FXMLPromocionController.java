package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import ControladorBD.exceptions.NonexistentEntityException;
import Modelo.Mensaje;
import Modelo.Promocion;
import Modelo.Validar;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLPromocionController extends MainController implements Initializable {

    @FXML
    private TableView<Promocion> tPromociones;

    @FXML
    private TableColumn cvTipo;

    @FXML
    private TableColumn cvDescripcion;

    @FXML
    private TableColumn cvDescuento;

    @FXML
    private TextArea descripcion;

    @FXML
    private Spinner<Integer> descuento;

    @FXML
    private ComboBox<String> tipo;

    @FXML
    private Button bNuevo;

    @FXML
    private Button bActualizar;

    @FXML
    private Button bEliminar;

    @FXML
    private Button bCrear;

    @FXML
    private Button bLimpiar;

    @FXML
    private TableView tCombinarPromocion;

    @FXML
    private TableColumn ccTipo;

    @FXML
    private TableColumn ccDescripcion;

    @FXML
    private TableColumn ccDescuento;

    @FXML
    private Button bAgregar;
    private boolean nuevoActivo = true;
    private Promocion promocionActual;
    private List<Promocion> combinarPromociones;

    private void habilitarCampos() {
        this.tipo.setDisable(false);
        this.descripcion.setDisable(false);
        this.descuento.setDisable(false);
    }

    private void limpiarCampos() {
        this.tipo.getSelectionModel().clearSelection();
        this.descripcion.setText("");
        this.descuento.getValueFactory().setValue(5);
    }

    @FXML
    void agregarNuevaPromocion(ActionEvent event) {
        if (this.nuevoActivo) {
            this.bEliminar.setDisable(true);
            this.bActualizar.setDisable(true);
            this.bAgregar.setDisable(true);
            this.habilitarCampos();
            this.limpiarCampos();
            this.bNuevo.setText("Registrar");
            this.nuevoActivo = false;
        } else {
            boolean bTipo = Validar.combo(this.tipo);
            boolean bDescripcion = Validar.area(this.descripcion);
            if (bTipo && bDescripcion) {
                Promocion promocion = new Promocion();
                promocion.setDescripcion(this.descripcion.getText());
                promocion.setDescuento(((float) this.descuento.getValue() / 100));
                promocion.setTipo(this.tipo.getValue());
                promocion.crear();
                Mensaje.informacion("Exito!! al registrar la promoción");
                this.escena.cargarEscena(EscenaPrincipal.EscenaPromocion);
            } else {
                Mensaje.advertencia("Corrija los campos en rojo");
            }
        }
    }

    private void actualizarTablaDeCombinar() {
        this.tCombinarPromocion.setItems(FXCollections.observableArrayList(
                this.combinarPromociones
        ));
    }

    @FXML
    void agregarPromocionParaCombinar(ActionEvent event) {
        if (this.combinarPromociones.isEmpty()) {
            this.combinarPromociones.add(this.promocionActual);
            this.actualizarTablaDeCombinar();
            this.bLimpiar.setDisable(false);
        } else {
            if (this.combinarPromociones.contains(this.promocionActual)) {
                Mensaje.advertencia("Ya contiene esa promoción");
            } else {
                if (this.combinarPromociones.get(0).getTipo().equals(this.promocionActual.getTipo())) {
                    float total = 0f;
                    for (Promocion promocion : this.combinarPromociones) {
                        total += promocion.getDescuento();
                    }
                    total += this.promocionActual.getDescuento();
                    if (total <= 1f) {
                        this.combinarPromociones.add(promocionActual);
                        this.actualizarTablaDeCombinar();
                        this.bCrear.setDisable(false);
                    } else {
                        Mensaje.advertencia("La suma del descuento no puede ser mayor al 100%");
                    }
                } else {
                    Mensaje.advertencia("Las promociones deben ser del mismo tipo");
                }
            }
        }
    }

    @FXML
    void limpiarCombinar(ActionEvent event) {
        this.combinarPromociones.clear();
        this.actualizarTablaDeCombinar();
        this.bLimpiar.setDisable(true);
        this.bCrear.setDisable(true);
    }

    @FXML
    void crearPromocionCombinada(ActionEvent event) {
        try {
            Promocion promocion = new Promocion();
            promocion.setTipo(this.combinarPromociones.get(0).getTipo());
            promocion.setDescuento(0);
            promocion.setDescripcion("");
            for (Promocion pro : this.combinarPromociones) {
                String descripcion = promocion.getDescripcion();
                float descuento = promocion.getDescuento();
                promocion.setDescripcion(descripcion + pro.getDescripcion()+ "\n");
                promocion.setDescuento(descuento + pro.getDescuento());
            }
            promocion.crear();
            for (Promocion pro : this.combinarPromociones) {
                pro.eliminar();
            }
            Mensaje.informacion("Exito!! al registrar la promoción combinada");
            this.escena.cargarEscena(EscenaPrincipal.EscenaPromocion);
        } catch (Exception e) {
            Mensaje.advertencia("No hay conexión, Intentelo mas tarde");
        }
    }

    @FXML
    void eliminarPromocion(ActionEvent event) {
        if (Mensaje.confirmacion("¿Esta Seguro de Eliminar esta Promoción?")) {
            try {
                this.promocionActual.eliminar();
                Mensaje.informacion("Exito!! al eliminar la promoción");
                this.escena.cargarEscena(EscenaPrincipal.EscenaPromocion);
            } catch (NonexistentEntityException ex) {
                Mensaje.advertencia("No hay conexión, intentelo mas tarde");
            }
        }

    }

    @FXML
    void actualizarPromocion(ActionEvent event) {
        boolean bTipo = Validar.combo(this.tipo);
        boolean bDescripcion = Validar.area(this.descripcion);
        if (bTipo && bDescripcion) {
            Promocion promocion = this.promocionActual;
            promocion.setDescripcion(this.descripcion.getText());
            promocion.setDescuento(((float) this.descuento.getValue() / 100));
            promocion.setTipo(this.tipo.getValue());
            try {
                promocion.actualizar();
                Mensaje.informacion("Exito!! al actualizar la promoción");
                this.escena.cargarEscena(EscenaPrincipal.EscenaPromocion);
            } catch (Exception ex) {
                Mensaje.advertencia("No hay conexión, Intentelo mas tarde");
            }
        } else {
            Mensaje.advertencia("Corrija los campos en rojo");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.cvTipo.setCellValueFactory(
                new PropertyValueFactory<>("Tipo")
        );
        this.cvDescripcion.setCellValueFactory(
                new PropertyValueFactory<>("Descripcion")
        );
        this.cvDescuento.setCellValueFactory(
                new PropertyValueFactory<>("PorcentajeDeDescuento")
        );
        this.ccTipo.setCellValueFactory(
                new PropertyValueFactory<>("Tipo")
        );
        this.ccDescripcion.setCellValueFactory(
                new PropertyValueFactory<>("Descripcion")
        );
        this.ccDescuento.setCellValueFactory(
                new PropertyValueFactory<>("PorcentajeDeDescuento")
        );
        this.tPromociones.setItems(FXCollections.observableArrayList(
                Promocion.listaDePromociones()
        ));
        this.tipo.getItems().addAll("Mensualidad", "Inscripción");
        SpinnerValueFactory<Integer> valueFactory
                = new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 100, 0, 5);
        this.descuento.setValueFactory(valueFactory);
        this.combinarPromociones = new ArrayList<>();
        this.tPromociones.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    this.promocionActual = newSelection;
                    this.bActualizar.setDisable(false);
                    this.bEliminar.setDisable(false);
                    this.bAgregar.setDisable(false);
                    this.tipo.getSelectionModel().select(this.promocionActual.getTipo());
                    this.descripcion.setText(this.promocionActual.getDescripcion());
                    Double valor = new Double(this.promocionActual.getDescuento() * 100);
                    this.descuento.getValueFactory()
                            .setValue(valor.intValue());
                    this.bNuevo.setText("Nuevo");
                    this.nuevoActivo = true;
                    this.habilitarCampos();
                });
    }
}
