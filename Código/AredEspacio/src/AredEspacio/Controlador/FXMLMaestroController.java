package AredEspacio.Controlador;

import AredEspacio.EscenaPrincipal;
import Modelo.Foto;
import Modelo.GrupoAsignado;
import Modelo.Maestro;
import Modelo.TipoDeMenu;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class FXMLMaestroController extends MainController implements Initializable {

    @FXML
    private Label titulo;
    @FXML
    private TextField campoNombre;
    @FXML
    private TextField campoApellido;
    @FXML
    private DatePicker fechaNacimiento;
    @FXML
    private TextField campoCorreo;
    @FXML
    private TextField campoTelefono;
    @FXML
    private TextField campoMovil;
    @FXML
    private TextField campoDomicilio;
    @FXML
    private TextField campoCodigoPostal;
    @FXML
    private TextField campoCiudad;
    @FXML
    private Button botonOpcion;
    @FXML
    private Button agregarFoto;
    @FXML
    private Label labelFechaDeRegistro;
    @FXML
    private Label labelFechaDeDeshabilitacion;
    @FXML
    private Label labelDeshabilitado;
    @FXML
    private TextArea motivo;
    @FXML
    private ImageView foto = null;
    @FXML
    private ComboBox campoEstado;
    @FXML
    private ComboBox campoGenero;
    private String rutaFoto;
    private Maestro maestro;

    @FXML
    private void buscarFoto() {
        this.rutaFoto = new Foto().buscarImagen();
        if (this.rutaFoto == null) {
            this.foto.setImage(new Image("Recursos/Imagenes/ErrorDeImagen.png"));
        } else {
            this.foto.setImage(
                    new Image("file:" + this.rutaFoto)
            );
        }
    }

    private void alertaSinConexion() {
        Alert alerta = new Alert(AlertType.WARNING);
        alerta.setHeaderText("No se puede conectar con la Base de Datos");
        alerta.setContentText("Revisa que el servidor de Base de Datos este funcionando");
        alerta.showAndWait();
    }

    private Maestro asignarValores(Maestro maestro) {
        maestro.setNombre(this.campoNombre.getText());
        maestro.setApellidos(this.campoApellido.getText());
        maestro.setCiudad(this.campoCiudad.getText());
        maestro.setCodigoPostal(this.campoCodigoPostal.getText());
        maestro.setCorreoElectronico(this.campoCorreo.getText());
        maestro.setDeshabilitado(false);
        maestro.setDomicilio(this.campoDomicilio.getText());
        maestro.setEstado(this.campoEstado.getValue().toString());
        LocalDate localDate = this.fechaNacimiento.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        java.util.Date date = Date.from(instant);
        maestro.setFechaDeNacimiento(new Date(date.getTime()));

        if (this.maestro == null) {
            maestro.setFechaDeRegistro(new Date(new GregorianCalendar().getTimeInMillis()));
        }

        if (this.maestro == null) {
            if (this.rutaFoto != null) {
                maestro.setFoto(new Foto().agregarImagen(this.rutaFoto));
            } else {
                maestro.setFoto(null);
            }
        } else {
            if (this.rutaFoto != null) {
                maestro.setFoto(new Foto().agregarImagen(this.rutaFoto));
            } else {
                maestro.setFoto(maestro.getFoto());
            }
        }

        if (this.campoGenero.getValue().toString().equals("Masculino")) {
            maestro.setGenero(true);
        } else {
            maestro.setGenero(false);
        }
        maestro.setTelefonoMovil(this.campoMovil.getText());
        maestro.setTelefono(this.campoTelefono.getText());
        return maestro;
    }

    public void alerta(String titulo, String contexto) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contexto);
        alerta.showAndWait();
    }

    @FXML
    private void realizarFuncion() {
        switch (this.botonOpcion.getText()) {
            case "Registrar":
                if (this.validarInformacion()) {
                    try {
                        this.asignarValores(new Maestro()).crear();
                        this.alerta("Información", "Exito! al registrar el Maestro");
                        this.escena.cargarEscena(EscenaPrincipal.EscenaMaestro);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        this.alertaSinConexion();
                    }
                }
                break;
            case "Ver Horario":
                this.verGrupos(this.maestro);
                break;
            case "Modificar":
                if (this.validarInformacion()) {
                    try {
                        this.asignarValores(this.maestro).actualizar();
                        this.alerta("Información", "Se ha actualizado la información del Maestro");
                        this.escena.cargarEscenaConParametros(EscenaPrincipal.EscenaBuscarMaestro, null, TipoDeMenu.MODIFICAR);
                    } catch (Exception ex) {
                        this.alertaSinConexion();
                    }
                }
                break;
            case "Deshabilitar":
                try {
                    this.maestro.setDeshabilitado(true);
                    this.maestro.setMotivoDeDeshabilitacion(this.motivo.getText());
                    this.maestro.setFechaDeDeshabilitacion(new Date(new GregorianCalendar().getTimeInMillis()));
                    this.maestro.actualizar();
                    this.alerta("Información", "Se ha Deshabilitado el Maestro");
                    this.escena.cargarEscenaConParametros(EscenaPrincipal.EscenaBuscarMaestro, null, TipoDeMenu.DESHABILITAR);
                } catch (Exception ex) {
                    this.alertaSinConexion();
                }
                break;
        }
    }

    private String dateToString(Date date) {
        GregorianCalendar fecha = new GregorianCalendar();
        fecha.setTimeInMillis(date.getTime());
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int año = fecha.get(Calendar.YEAR);
        return String.format(" %1$02d/%2$02d/%3$04d", dia, mes, año);
    }

    private void cargarInformacionDeMaestro(Maestro maestro) {
        this.campoApellido.setText(maestro.getApellidos());
        this.campoCiudad.setText(maestro.getCiudad());
        this.campoCodigoPostal.setText(maestro.getCodigoPostal());
        this.campoCorreo.setText(maestro.getCorreoElectronico());
        this.campoDomicilio.setText(maestro.getDomicilio());
        this.campoEstado.setValue(maestro.getEstado());
        this.campoMovil.setText(maestro.getTelefonoMovil());
        this.campoNombre.setText(maestro.getNombre());
        this.campoTelefono.setText(maestro.getTelefono());

        if (maestro.getFoto() != null) {
            this.foto.setImage(new Foto().obtenerImagen(maestro.getFoto()));
        } else {
            this.foto.setImage(new Image("Recursos/Imagenes/ErrorDeImagen.png"));
        }

        GregorianCalendar gregorian = new GregorianCalendar();
        gregorian.setTime(maestro.getFechaDeNacimiento());
        LocalDate localDate = gregorian.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.fechaNacimiento.setValue(localDate);
        this.labelFechaDeRegistro.setText(this.labelFechaDeRegistro.getText()
                + this.dateToString(new Date(maestro.getFechaDeRegistro().getTime())));

        if (maestro.getDeshabilitado()) {
            this.labelDeshabilitado.setText(this.labelDeshabilitado.getText() + " Si");
            this.motivo.setText("Motivo de Deshabilitación\n" + maestro.getMotivoDeDeshabilitacion());
            this.botonOpcion.setVisible(false);
            this.labelFechaDeDeshabilitacion.setText(this.labelFechaDeDeshabilitacion.getText()
                    + this.dateToString(new Date(maestro.getFechaDeDeshabilitacion().getTime())));
        } else {
            this.labelDeshabilitado.setText(this.labelDeshabilitado.getText() + " No");
            this.motivo.setVisible(false);
            this.botonOpcion.setText("Ver Horario");
            this.botonOpcion.setLayoutY(360);
            this.botonOpcion.setLayoutX(594);
            this.labelFechaDeRegistro.setLayoutY(390);
            this.labelFechaDeDeshabilitacion.setVisible(false);
        }
        if (maestro.getGenero()) {
            this.campoGenero.setValue("Masculino");
        } else {
            this.campoGenero.setValue("Femenino");
        }
    }

    private boolean validarCampo(TextField campo) {
        if (Pattern.compile("[a-zA-ZñÑáÁéÉíÍóÓúÚ\\s\\.\\#\\:]{1,45}").matcher(campo.getText()).matches()) {
            campo.setStyle(null);
            return true;
        } else {
            campo.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            return false;
        }
    }

    private boolean validarCampoNumerico(TextField campo) {
        if (Pattern.compile("[0-9]{5,15}").matcher(campo.getText()).matches()) {
            campo.setStyle(null);
            return true;
        } else {
            campo.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            return false;
        }
    }

    public boolean validarComboBox(ComboBox combo) {
        if (combo.getValue() != null) {
            combo.setStyle(null);
            return true;
        } else {
            combo.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
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

    public void alertaDeValidacion() {
        Alert alerta = new Alert(AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText("Corrija los campos marcados en rojo");
        alerta.setContentText("Como se deben llenar los campos:");
        TextArea textArea = new TextArea();
        textArea.setText("• No puede haber campos vacíos\n"
                + "• Se deben seleccionar la fecha de nacimiento, genero, estado\n"
                + "• Se debe agregar una foto y debe ser formato PNG\n"
                + "• Los campos “Código Postal”, “Teléfono” y “Teléfono móvil” deben ser numéricos\n"
                + "• El código postal debe ser un número mayor o igual a 5 cifras\n"
                + "• Los demás campos son llenados siguiendo el orden alfabético");
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
        boolean bApellido = this.validarCampo(this.campoApellido);
        boolean bCiudad = this.validarCampo(this.campoCiudad);
        boolean bCorreo = this.validarAlfanumerico(this.campoCorreo);
        boolean bDomicilio = this.validarAlfanumerico(this.campoDomicilio);
        boolean bNombre = this.validarCampo(this.campoNombre);
        boolean bTelefono = this.validarCampoNumerico(this.campoTelefono);
        boolean bMovil = this.validarCampoNumerico(this.campoMovil);
        boolean bCP = this.validarCampoNumerico(this.campoCodigoPostal);
        boolean bGenero = this.validarComboBox(this.campoGenero);
        boolean bEstado = this.validarComboBox(this.campoEstado);
        boolean bFechaNacimiento = this.fechaNacimiento.getValue() != null;
        boolean bFoto;

        /*if (this.maestro == null) {
            bFoto = this.rutaFoto != null; //&& !this.rutaFoto.equals("Recursos/Imagenes/ErrorDeImagen.png");
        } else {
            bFoto = true;
        }*/
        if (bFechaNacimiento) {
            this.fechaNacimiento.setStyle(null);
        } else {
            this.fechaNacimiento.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        }

        /*if (bFoto) {
            this.agregarFoto.setStyle(null);
        } else {
            this.agregarFoto.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        }*/
        if (bApellido && bCiudad && bCorreo && bDomicilio && bNombre && bTelefono && bMovil && bCP
                && bGenero && bEstado && bFechaNacimiento) {
            return true;
        } else {
            this.alertaDeValidacion();
            return false;
        }
    }

    public void inicializarComboBox() {
        this.campoGenero.getItems().addAll("Femenino", "Masculino");
        this.campoEstado.getItems().addAll("Aguascalientes", "Baja California", "Baja California Sur", "Campeche", "Chiapas", "Chihuahua", "Coahuila", "Colima",
                "Distrito Federal", "Durango", "Estado de México", "Guanajuato", "Guerrero", "HidalgoJalisco", "Michoacán", "Morelos",
                "Nayarit", "Nuevo León", "Oaxaca", "Puebla", "Querétaro", "Quintana Roo", "San Luis Potosí", "Sinaloa", "Sonora", "Tabasco",
                "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatán", "Zacatecas");
    }

    private void cargarRegistrar() {
        this.titulo.setText("Registrar Maestro");
        this.titulo.setLayoutX(345);
        this.botonOpcion.setText("Registrar");
        this.botonOpcion.setLayoutX(660);
        this.foto.setImage(new Image("Recursos/Imagenes/ImagenPorDefecto.png"));
        this.labelDeshabilitado.setVisible(false);
        this.labelFechaDeDeshabilitacion.setVisible(false);
        this.labelFechaDeRegistro.setVisible(false);
        this.motivo.setVisible(false);
        this.inicializarComboBox();
    }

    private void cargarConsultar() {
        this.titulo.setText("Información del Maestro");
        this.titulo.setLayoutX(320);
        this.cargarInformacionDeMaestro(this.maestro);
        this.campoApellido.setEditable(false);
        this.campoCiudad.setEditable(false);
        this.campoCodigoPostal.setEditable(false);
        this.campoCorreo.setEditable(false);
        this.campoDomicilio.setEditable(false);
        this.campoMovil.setEditable(false);
        this.campoNombre.setEditable(false);
        this.campoTelefono.setEditable(false);
        this.motivo.setEditable(false);
        this.agregarFoto.setVisible(false);
        this.campoEstado.setDisable(true);
        this.campoEstado.setOpacity(1.0);
        this.campoGenero.setDisable(true);
        this.campoGenero.setOpacity(1.0);
        this.fechaNacimiento.setDisable(true);
        this.fechaNacimiento.setStyle("-fx-opacity: 1");
        this.fechaNacimiento.getEditor().setStyle("-fx-opacity: 1");
    }

    private void cargarModificar() {
        this.titulo.setText("Modificar Maestro");
        this.titulo.setLayoutX(315);
        this.cargarInformacionDeMaestro(this.maestro);
        this.botonOpcion.setText("Modificar");
        this.botonOpcion.setLayoutX(650);
        this.botonOpcion.setLayoutY(425);
        this.labelFechaDeRegistro.setVisible(false);
        this.labelDeshabilitado.setVisible(false);
        this.labelFechaDeDeshabilitacion.setVisible(false);
        this.motivo.setVisible(false);
        this.inicializarComboBox();

        if (this.maestro.getDeshabilitado()) {
            this.campoApellido.setEditable(false);
            this.campoCiudad.setEditable(false);
            this.campoCodigoPostal.setEditable(false);
            this.campoCorreo.setEditable(false);
            this.campoDomicilio.setEditable(false);
            this.campoMovil.setEditable(false);
            this.campoNombre.setEditable(false);
            this.campoTelefono.setEditable(false);
            this.motivo.setEditable(false);
            this.agregarFoto.setVisible(false);
            this.campoEstado.setDisable(true);
            this.campoEstado.setOpacity(1.0);
            this.campoGenero.setDisable(true);
            this.campoGenero.setOpacity(1.0);
            this.fechaNacimiento.setDisable(true);
            this.fechaNacimiento.setStyle("-fx-opacity: 1");
            this.fechaNacimiento.getEditor().setStyle("-fx-opacity: 1");
            this.botonOpcion.setVisible(false);
            this.alerta("Información", "El maestro esta Desabilitado.\nNo se puede modificar");
        }
    }

    private void cargarDeshabilitar() {
        this.titulo.setText("Deshabilitar Maestro");
        this.titulo.setLayoutX(345);
        this.motivo.setVisible(true);
        this.cargarInformacionDeMaestro(this.maestro);
        this.campoApellido.setEditable(false);
        this.campoCiudad.setEditable(false);
        this.campoCodigoPostal.setEditable(false);
        this.campoCorreo.setEditable(false);
        this.campoDomicilio.setEditable(false);
        this.campoMovil.setEditable(false);
        this.campoNombre.setEditable(false);
        this.campoTelefono.setEditable(false);
        this.motivo.setEditable(false);
        this.agregarFoto.setVisible(false);
        this.campoEstado.setDisable(true);
        this.campoEstado.setOpacity(1.0);
        this.campoGenero.setDisable(true);
        this.campoGenero.setOpacity(1.0);
        this.fechaNacimiento.setDisable(true);
        this.fechaNacimiento.setStyle("-fx-opacity: 1");
        this.fechaNacimiento.getEditor().setStyle("-fx-opacity: 1");

        if (this.maestro.getDeshabilitado()) {
            this.motivo.setEditable(false);
            this.botonOpcion.setVisible(false);
            this.labelFechaDeDeshabilitacion.setVisible(true);
            this.labelFechaDeDeshabilitacion.setLayoutY(430);

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Información");
            alert.setHeaderText("El Maestro esta Deshabilitado");
            alert.setContentText("¿Desea habilitarlo?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                this.maestro.setDeshabilitado(false);
                try {
                    this.maestro.actualizar();
                } catch (Exception ex) {
                    this.alertaSinConexion();
                }
                this.alerta("Información", "Ya esta habilitado el Maestro");
                this.escena.cargarEscena(EscenaPrincipal.EscenaBuscarMaestro);
            } else {
                this.escena.cargarEscena(EscenaPrincipal.EscenaBuscarMaestro);
            }
        } else {
            this.botonOpcion.setVisible(true);
            this.labelFechaDeDeshabilitacion.setVisible(false);
            this.labelFechaDeRegistro.setLayoutY(430);
            this.botonOpcion.setText("Deshabilitar");
            this.botonOpcion.setLayoutX(645);
            this.botonOpcion.setLayoutY(426);
            this.motivo.setVisible(true);
            this.motivo.setEditable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            if (this.parametros == null) {
                this.cargarRegistrar();
            } else {
                this.maestro = (Maestro) this.parametros;
                if (TipoDeMenu.CONSULTAR == this.tipoMenu) {
                    this.cargarConsultar();
                } else {
                    if (TipoDeMenu.MODIFICAR == this.tipoMenu) {
                        this.cargarModificar();
                    } else {
                        if (TipoDeMenu.DESHABILITAR == this.tipoMenu) {
                            this.cargarDeshabilitar();
                        }
                    }
                }
            }
        });
    }

    public void verGrupos(Maestro maestro) {
        Dialog ventana = new Dialog();
        ventana.setTitle("Horario");
        ventana.setHeaderText("Horario que tiene asignado el maestro: "
                + maestro.getNombre() + " " + maestro.getApellidos());
        ventana.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        GridPane panel = new GridPane();
        panel.setHgap(10);
        panel.setVgap(10);
        panel.setPadding(new Insets(10, 10, 10, 10));
        TableView tabla = new TableView();
        TableColumn cId = new TableColumn();
        cId.setPrefWidth(40);
        cId.setText("ID");
        cId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        TableColumn cSalon = new TableColumn();
        cSalon.setText("Salon");
        cSalon.setPrefWidth(100);
        cSalon.setCellValueFactory(new PropertyValueFactory<>("Salon"));
        TableColumn cTipoDeDanza = new TableColumn();
        cTipoDeDanza.setText("Tipo de Danza");
        cTipoDeDanza.setPrefWidth(160);
        cTipoDeDanza.setCellValueFactory(new PropertyValueFactory<>("TipoDeDanza"));
        TableColumn cDia = new TableColumn();
        cDia.setText("Día");
        cDia.setPrefWidth(120);
        cDia.setCellValueFactory(new PropertyValueFactory<>("Dia"));
        TableColumn cHora = new TableColumn();
        cHora.setText("Hora");
        cHora.setPrefWidth(180);
        cHora.setCellValueFactory(new PropertyValueFactory<>("Hora"));
        ObservableList lista = FXCollections.observableArrayList(GrupoAsignado.obtenerGruposAsignado(maestro));
        tabla.setItems(lista);
        tabla.getColumns().addAll(cId, cSalon, cTipoDeDanza, cDia, cHora);
        tabla.setPrefHeight(200);
        tabla.setPrefWidth(600);
        panel.setPadding(new Insets(5));
        panel.getChildren().add(tabla);
        ventana.getDialogPane().setContent(panel);
        ventana.show();
    }
}
