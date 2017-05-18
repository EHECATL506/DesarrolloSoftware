package Modelo;

import java.sql.Date;
import java.util.GregorianCalendar;
import org.junit.Assert;
import org.junit.Test;

//Nuevo
public class AsistenciaTest {
    
    public AsistenciaTest() {
    }
    
    @Test
    public void pruebaCrearAsistenciaCorrectamente(){
        Asistencia asistencia = new Asistencia();
        asistencia.setAsistio(true);
        asistencia.setFecha(new Date(new GregorianCalendar().getTimeInMillis()));
        asistencia.setIdClase(Clase.listaDeClases().get(0));
        boolean resultado = asistencia.crear();
        Assert.assertTrue("Prueba registrar asistencia correctamente", resultado);
    }
    
    @Test(expected = Exception.class)
    public void pruebaCrearAsistenciaIncorrecta()throws Exception{
        Asistencia asistencia = new Asistencia();
        boolean resultado = asistencia.crear();
        Assert.assertTrue("Prueba registrar asistencia incorrecta", resultado);
    }
}
