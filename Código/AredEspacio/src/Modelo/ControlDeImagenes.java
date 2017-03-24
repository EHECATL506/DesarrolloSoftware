package Modelo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;

public class ControlDeImagenes {

    public String buscarImagen() {
        FileChooser explorador = new FileChooser();
        explorador.setTitle("Buscar Foto");
        explorador.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Fotos", "*.png", "*.jpg")
        );
        try {
            String ruta = explorador.showOpenDialog(new Stage()).getPath();
            return ruta;
        } catch (Exception e) {
            return null;
        }
    }

    public Image StingToImage(byte[] arreglo) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(arreglo);
            BufferedImage read = ImageIO.read(in);
            return SwingFXUtils.toFXImage(read, null);
        } catch (IOException ex) {
            return null;
        }
    }

    public byte[] ImageToString(String ruta) {
        File file = new File(ruta);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            byte[] buffer;
            try
            (ByteArrayOutputStream out = new ByteArrayOutputStream(262144)) {
                ImageIO.write(bufferedImage, "png", out);
                out.flush();
                buffer = out.toByteArray();
            }
            return buffer;
        } catch (IOException e) {
            return null;
        }
    }
}
