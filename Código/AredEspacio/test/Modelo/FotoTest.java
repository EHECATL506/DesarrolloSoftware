package Modelo;

import javafx.scene.image.Image;
import org.junit.Assert;
import org.junit.Test;

public class FotoTest {

    public FotoTest() {
    }

    @Test
    public void pruebaObtenerImagenCorrecta() {
        String ruta = "C:\\Users\\Mauricio Juárez\\Desktop\\Imagen_TEST.jpg";
        Foto foto = new Foto();
        byte[]  arreglo = foto.agregarImagen(ruta);
        Image imagen = foto.obtenerImagen(arreglo);
        Assert.assertNotNull("Obtener Imagen de arreglo de byte",imagen);
    }

    @Test
    public void pruebaObtenerImagenIncorrecto() {
        Foto foto = new Foto();
        byte[]  arreglo = null;
        Image imagen = foto.obtenerImagen(arreglo);
        Assert.assertNull("Obtener imagen de arreglo de byte erroneo o vacio", imagen);
    }

    @Test
    public void pruebaAgregarImagenCorecta() {
        String ruta = "C:\\Users\\Mauricio Juárez\\Desktop\\Imagen_TEST.jpg";
        Foto foto = new Foto();
        byte[] imagen = foto.agregarImagen(ruta);
        Assert.assertNotNull("Agregar imagen para tranformar en arreglo de byte", imagen);
    }

    @Test
    public void pruebaAgregarImagenIncorrecta() {
        String ruta = "file:C:Imagen_TEST.jpg";
        Foto foto = new Foto();
        byte[] imagen = foto.agregarImagen(ruta);
        Assert.assertNull("Agregar imagen para tranformar en arreglo de byte", imagen);
    }

}
