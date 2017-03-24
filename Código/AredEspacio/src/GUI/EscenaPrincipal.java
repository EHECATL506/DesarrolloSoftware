package GUI;

import GUI.Controllers.MainController;
import Modelo.Enumeradores.TipoMenu;
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
    public static String Marco = "FXML/FXMLMarco.fxml";
    public static String EscenaBusquedaMaestro = "FXML/FXMLMaestroBusqueda.fxml";
    public static String EscenaMaestro = "FXML/FXMLMaestro.fxml";
    public static String EscenaGrupo = "FXML/FXMLGrupo.fxml";

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        this.mainStage = stage;
        this.mainScene = root;
        this.cargarEscena(EscenaPrincipal.EscenaMaestro);
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
    
    public boolean cargarEscena(String archivoFXML){
        MainController mainController = this.obtenerController(archivoFXML);
        if(mainController!=null){
            mainController.setEscena(this);
            return true;
        }else{
            return false;
        }
    }

    public boolean cargarEscenaConParametros(String archivoFXML, String parametros) {
        MainController mainController = this.obtenerController(archivoFXML);
        if(mainController!=null){
            mainController.setParametros(parametros);
            mainController.setEscena(this);
            return true;
        }else{
            return false;
        }
    }
    
    public boolean cargarEscenaConTipoDeMenu(String archivoFXML, TipoMenu tipoDeMenu) {
        MainController mainController = this.obtenerController(archivoFXML);
        if(mainController!=null){
            mainController.setTipoMenu(tipoDeMenu);
            mainController.setEscena(this);
            return true;
        }else{
            return false;
        }
    }
    
    public boolean cargarEscenaConParametrosYTipoDeMenu(String archivoFXML,TipoMenu tipoDeMenu, String parametros) {
        MainController mainController = this.obtenerController(archivoFXML);
        if(mainController!=null){
            mainController.setParametros(parametros);
            mainController.setTipoMenu(tipoDeMenu);
            mainController.setEscena(this);
            return true;
        }else{
            return false;
        }
    }
    
    /*
    public boolean cargarEscena(String archivoFXML) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(archivoFXML));
            AnchorPane screen = (AnchorPane) myLoader.load();
            if (EscenaPrincipal.Marco.equals(archivoFXML) || mainScene.getChildren().isEmpty()) {
                mainScene.getChildren().add(screen);
            } else {
                mainScene.getChildren().remove(0);
                mainScene.getChildren().add(0, screen);
            }
            
            
            ControlDeEscena myScreenControler = ((ControlDeEscena) myLoader.getController());
            myScreenControler.setEscena(this);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public boolean cargarEscenaConParametros(String archivoFXML, String parametros) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(archivoFXML));
            AnchorPane screen = (AnchorPane) myLoader.load();
            if (EscenaPrincipal.Marco.equals(archivoFXML) || mainScene.getChildren().isEmpty()) {
                mainScene.getChildren().add(screen);
            } else {
                mainScene.getChildren().remove(0);
                mainScene.getChildren().add(0, screen);
            }
            ControlDeEscena myScreenControler = ((ControlDeEscena) myLoader.getController());
            
            MainController controller = myLoader.getController();
            controller.setParametros(parametros);
            myScreenControler.setEscena(this);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
     */
    public static void main(String[] args) {
        launch(args);
    }
}
