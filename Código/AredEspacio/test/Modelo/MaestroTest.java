package Modelo;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class MaestroTest {

    @Test
    public void pruebaCrearMaestroCorrectamente() throws Exception {
        Maestro maestro = new Maestro();
        maestro.setNombre("Pedro");
        maestro.setApellidos("Sanchez");
        maestro.setCiudad("Xalapa");
        maestro.setCodigoPostal("91315");
        maestro.setCorreoElectronico("pedro_12345@hotmail.com");
        maestro.setDeshabilitado(false);
        maestro.setDomicilio("Av. Xalapa #6");
        maestro.setEstado("Veracruz");
        GregorianCalendar calendar = new GregorianCalendar();
        maestro.setFechaDeRegistro(new Date(calendar.getTimeInMillis()));
        maestro.setFechaDeNacimiento(new Date(calendar.getTimeInMillis()));
        maestro.setFoto(null);
        maestro.setGenero(true);
        maestro.setTelefono("1234567890");
        maestro.setTelefonoMovil("1234567890");
        boolean resultados = maestro.crear();
        assertTrue("Prueba crear maestro en la base de datos", resultados);
    }

    @Test(expected = Exception.class)
    public void pruebaCrearMaestroVacio() throws Exception {
        Maestro maestro = new Maestro();
        maestro.crear();
    }

    @Test(expected = Exception.class)
    public void pruebaCrearMaestroConDatosErroneosIncompletos() throws Exception {
        Maestro maestro = new Maestro();
        maestro.setNombre("asdasd");
        maestro.setTelefonoMovil(null);
        maestro.setCorreoElectronico("124215");
        maestro.setGenero(false);
        maestro.setId(12314435);
        maestro.crear();
    }

    @Test
    public void pruebaActualizarMaestroCorrectamente() throws Exception {
        Maestro maestro = Maestro.obtenerMaestroPorApellido("").get(0);
        maestro.setNombre("Nombre actualizado");
        boolean resultados = maestro.actualizar();
        assertTrue("Prueba actualizar maestro en la base de datos", resultados);
    }

    @Test(expected = Exception.class)
    public void pruebaActualizarMaestroVacio() throws Exception {
        Maestro maestro = new Maestro();
        maestro.crear();
    }

    @Test
    public void pruebaObtenerMaestrosPorNoDeColaborador() {
        List<Maestro> maestros = Maestro.obtenerMaestroPorNoDeColaborador("");
        boolean resultado = maestros.size()>0;
        Assert.assertTrue("Prueba Obtener Maestros por No de Colaborador",resultado);
    }
    
    @Test
    public void pruebaObtenerMaestrosPorNombre() {
        List<Maestro> maestros = Maestro.obtenerMaestroPorNombre("");
        boolean resultado = maestros.size()>0;
        Assert.assertTrue("Prueba Obtener Maestros por Nombre", resultado);
    }

    @Test
    public void pruebaObtenerMaestrosPorApellido() {
        List<Maestro> maestros = Maestro.obtenerMaestroPorApellido("");
        boolean resultado = maestros.size()>0;
        Assert.assertTrue("Prueba Obtener Maestros por Apellido",resultado);
    }

    @Test
    public void pruebaNoEncontrarMaestrosPorNoDeColaborador() {
        List<Maestro> maestros = Maestro.obtenerMaestroPorNoDeColaborador("ABCDE");
        boolean resultado = maestros.isEmpty();
        Assert.assertTrue("Prueba no encontrar Maestros "
               + "por No de Colaborador teniendo en cuenta "
                + "que el No de Colaborador solo tiene una letra",resultado);
    }

    @Test
    public void pruebaNoEncontrarMaestrosPorNombre() {
        List<Maestro> maestros = Maestro.obtenerMaestroPorNombre("1234567890");
        boolean resultado = maestros.isEmpty();
        Assert.assertTrue("Prueba no encontrar Maestros por Nombre teniendo"
                + " en cuenta que el nombre no tiene numeros",resultado);
    }

    @Test
    public void pruebaNoEncontrarMaestrosPorApellido() {
        List<Maestro> maestros = Maestro.obtenerMaestroPorApellido("1234567890");
        boolean resultado = maestros.isEmpty();
        Assert.assertTrue("Prueba no encontrar Maestros por Apellido"
                + " en cuenta que el Apellido no tiene numeros",resultado);
    }

    
}
