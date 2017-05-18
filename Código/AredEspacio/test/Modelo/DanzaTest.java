package Modelo;

import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
//Nuevo
public class DanzaTest {

    public DanzaTest() {
    }

    @Test
    public void pruebaObtenerTodasLasDanzas() {
        List<Danza> lista = Danza.obtenerTodas();
        boolean resultado = !lista.isEmpty();
        assertTrue("Prueba obtener todas las danzas", resultado);
    }

    @Test
    public void pruebaCrearDanzaCorrectamente() {
        Danza danza = new Danza();
        danza.setTipoDanza("Prueba Crear danza");
        boolean resultado = danza.crear();
        assertTrue("Prueba crear danza correctamente", resultado);
    }

    @Test(expected = Exception.class)
    public void pruebaCrearDanzaIncorrectamente() throws Exception {
        Danza danza = new Danza();
        boolean resultado = danza.crear();
        assertTrue("Prueba crear danza incorrectamente", resultado);
    }

    @Test
    public void pruebaActualizarDanzaCorrectamente() throws Exception {
        Danza danza = Danza.obtenerTodas().get(0);
        danza.setTipoDanza("Prueba actualizar danza");
        boolean resultado = danza.actualizar();
        assertTrue("Prueba actualizar danza conrrectamente", resultado);
    }

    @Test
    public void pruebaActualizarDanzaIncorrectamente() throws Exception {
        Danza danza = new Danza();
        boolean resultado = danza.actualizar();
        assertTrue("Prueba actualizar danza inconrrectamente", resultado);
    }

    @Test
    public void pruebaEliminarDanzaCorrectamente() throws Exception {
        Danza danza = Danza.obtenerTodas().get(0);
        boolean resultado = danza.eliminar();
        assertTrue("Prueba actualizar danza conrrectamente", resultado);
    }
    
    @Test(expected=Exception.class)
    public void pruebaEliminarDanzaIncorrectamente() throws Exception {
        Danza danza = new Danza();
        boolean resultado = danza.eliminar();
        assertTrue("Prueba actualizar danza inconrrectamente", resultado);
    }
    
    @Test
    public void pruebaBuscarDanzaPorNombre(){
        Danza danza = Danza.obtenerTodas().get(0);
        Danza danzaEncontrada = Danza.buscarPorTipoDanza(danza.getTipoDanza());
        Assert.assertEquals("Prueba buscar danza por nombre de la danza",danzaEncontrada, danza);
    }
}
