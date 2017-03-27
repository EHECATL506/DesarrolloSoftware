/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Jonathan
 */
public class Foto {
    private FileInputStream fileFoto;
    
    public String buscarImagen() {
        FileChooser explorador = new FileChooser();
        explorador.setTitle("Buscar Foto");
        explorador.getExtensionFilters().add(new FileChooser.ExtensionFilter("Foto", "*.jpg","*.png"));
        try {
            String ruta = explorador.showOpenDialog(new Stage()).getPath();
            return ruta;
        } catch (Exception e) {
            return null;
        }
    }
    
    
    public Image obtenerImagen (byte[] imagen) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(imagen);
            BufferedImage read = ImageIO.read(in);
            return SwingFXUtils.toFXImage(read, null);
        } catch (Exception ex) {
            return null;
        }
    } 
    
    
    public byte[] agregarImagen(String rutaFoto) {
        try {
            fileFoto = new FileInputStream(new File(rutaFoto));
            BufferedImage bufferedImage = ImageIO.read(fileFoto);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", out);
            out.flush();
            return out.toByteArray();
        } catch (IOException ex) {
            return null;
        }
    }
}
