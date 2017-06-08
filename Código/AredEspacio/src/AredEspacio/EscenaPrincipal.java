package AredEspacio;

import AredEspacio.Controlador.MainController;
import Modelo.TipoDeMenu;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EscenaPrincipal extends Application {

    private Stage mainStage;
    private Group mainScene;
    public static String Marco = "Vista/FXMLMarco.fxml";
    public static String EscenaBuscarMaestro = "Vista/FXMLMaestroBusqueda.fxml";
    public static String EscenaMaestro = "Vista/FXMLMaestro.fxml";
    public static String EscenaGrupo = "Vista/FXMLGrupo.fxml";
    public static String EscenaRegistrarAlumno = "Vista/FXMLRegistrarAlumno.fxml";
    public static String EscenaBuscarAlumno = "Vista/FXMLBuscarAlumno.fxml";
    public static String EscenaEliminarAlumno = "Vista/FXMLEliminarAlumno.fxml";
    public static String EscenaInicio = "Vista/FXMLInicio.fxml";
    public static String EscenaModificarGrupo = "Vista/FXMLModificarGrupo.fxml";
    public static String EscenaBuscarGrupo = "Vista/FXMLBuscarGrupo.fxml";
    public static String EscenaCambiarDeGrupo = "Vista/FXMLCambioDeGrupo.fxml";
    public static String EscenaIncribirAlumno = "Vista/FXMLIncribirAlumno.fxml";
    public static String EscenaRegistrarEgreso = "Vista/FXMLEgreso.fxml";
    public static String EscenaPaseDeLista = "Vista/FXMLPaseDeLista.fxml";
    public static String EscenaListaDeDanza = "Vista/FXMLDanza.fxml";
    public static String EscenaPromocion = "Vista/FXMLPromocion.fxml";
    public static String EscenaReporteDeEgresos="Vista/FXMLReporteDeEgresos.fxml";
    public static String EscenaReporteDeIngresos="Vista/FXMLReporteDeIngresos.fxml";
    public static String EscenaPagoMaestro="Vista/FXMLPagoMaestro.fxml";
    public static String EscenaNotificaciones="Vista/FXMLNotificaciones.fxml";
    public static String EscenaPagarMensualidad="Vista/FXMLPagarMensualidad.fxml";

    
    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        this.mainStage = stage;

        this.mainStage.setResizable(false);
        this.mainStage.setTitle("Ared Espacio");
        this.mainStage.getIcons().add(new Image("Recursos/Imagenes/Icono.png"));
        this.mainStage.setHeight(580);
        this.mainStage.setWidth(807);
        this.mainScene = root;
        this.cargarEscena(EscenaPrincipal.EscenaInicio);
        this.cargarEscena(EscenaPrincipal.Marco);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private MainController obtenerController(String archivoFXML) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(archivoFXML));
            AnchorPane screen = (AnchorPane) myLoader.load();
            if (EscenaPrincipal.Marco.equals(archivoFXML) || mainScene.getChildren().isEmpty()) {
                mainScene.getChildren().add(screen);
            } else {
                mainScene.getChildren().remove(0);
                mainScene.getChildren().add(0, screen);
            }
            return (MainController) myLoader.getController();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean cargarEscena(String archivoFXML) {
        MainController mainController = this.obtenerController(archivoFXML);
        if (mainController != null) {
            mainController.setEscena(this);
            return true;
        } else {
            return false;
        }
    }

    public boolean cargarEscenaConParametros(String archivoFXML, Object parametros, TipoDeMenu tipoDeMenu) {
        MainController mainController = obtenerController(archivoFXML);

        if (mainController != null) {
            mainController.setParametros(parametros);
            mainController.setEscena(this);
            mainController.setTipoMenu(tipoDeMenu);
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
