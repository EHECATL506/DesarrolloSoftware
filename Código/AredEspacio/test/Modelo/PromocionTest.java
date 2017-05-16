package Modelo;

import ControladorBD.exceptions.NonexistentEntityException;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

//Nuevo
public class PromocionTest {

    public PromocionTest() {
    }

    @Test
    public void pruebaObtenerTodasLasPromociones() {
        List<Promocion> lista = Promocion.listaDePromociones();
        boolean resultado = !lista.isEmpty();
        assertTrue("Prueba obtener todos los resultados", resultado);
    }

    @Test
    public void pruebaCrearPromocionCorrectamente() {
        Promocion promocion = new Promocion();
        promocion.setDescripcion("Promocion prueba");
        promocion.setDescuento(0.4f);
        promocion.setTipo("Mensualidad");
        boolean resultado = promocion.crear();
        assertTrue("Prueba crear promocion correctamente", resultado);
    }

    @Test(expected = Exception.class)
    public void pruebaCrearPromocionIncorrectamente() throws Exception {
        Promocion promocion = new Promocion();
        boolean resultado = promocion.crear();
        assertTrue("Prueba crear promocion correctamente", resultado);
    }

    @Test
    public void pruebaActualizarPromocionCorrectamente() throws Exception {
        Promocion promocion = Promocion.listaDePromociones().get(0);
        promocion.setDescripcion("Prueba actualizar promocion");
        promocion.setDescuento(0.15f);
        promocion.setTipo("Inscripción");
        boolean resultado = promocion.actualizar();
        assertTrue("Prueba actualizar promocion correctamente", resultado);
    }

    @Test(expected = Exception.class)
    public void pruebaActualizarPromocionIncorrectamente() throws Exception {
        Promocion promocion = new Promocion();
        boolean resultado = promocion.actualizar();
        assertTrue("Prueba actualizar promocion incorrectamente", resultado);
    }

    @Test
    public void pruebaEliminarPromocionCorrectamente() throws NonexistentEntityException {
        Promocion promocion = Promocion.listaDePromociones().get(0);
        boolean resultado = promocion.eliminar();
        assertTrue("Prueba eliminar promocion correctamente", resultado);
    }

    @Test(expected = Exception.class)
    public void pruebaEliminarPromocionInorrectamente() throws NonexistentEntityException {
        Promocion promocion = new Promocion();
        boolean resultado = promocion.eliminar();
        assertTrue("Prueba eliminar promocion correctamente", resultado);
    }

}
