package Modelo;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
//Nuevo
public class EgresoTest {
    
    public EgresoTest() {
    }

    @Test
    public void pruebaCrearEgresoCorrectamente() {
        Egreso egreso = new Egreso();
        egreso.setMonto(0);
        egreso.setMotivo("Prueba ");
        egreso.setFecha(new Date(new GregorianCalendar().getTimeInMillis()));
        boolean resultado = egreso.crear();
        assertTrue("Prueba registrar egreso correctamente", resultado);
    }

    @Test(expected=Exception.class)
    public void pruebaCrearEgresoInorrectamente()throws Exception {
        Egreso egreso = new Egreso();
        boolean resultado = egreso.crear();
        assertTrue("Prueba registrar egreso incorrectamente", resultado);
    }

    @Test
    public void pruebaObtenerTodosLosEgresos() {
        List<Egreso> lista = Egreso.obtenerTodosLosEgresos();
        boolean resultado = !lista.isEmpty();
        assertTrue("Prueba obtener todos los egresos",resultado);
    }
}
