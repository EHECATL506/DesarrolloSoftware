package AredEspacio;

import AredEspacio.Controlador.MainController;
import Modelo.TipoDeMenu;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
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
    public static String EscenaAgregarGrupo = "Vista/FXMLAsignarGrupoAlumno.fxml";
    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        this.mainStage = stage;
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
