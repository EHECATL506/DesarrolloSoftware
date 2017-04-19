package Modelo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import org.junit.Assert;
import org.junit.Test;

public class GrupoTest {
    /*
    public GrupoTest() {
    }
    
    @Test
    public void pruebaCrearGrupoCorrecto(){
        Grupo grupo = new Grupo();
        grupo.setNivel("Basico");
        grupo.setSalon("CC3");
        grupo.setTipoDeDanza("Ballet");
        Maestro maestro = Maestro.obtenerMaestroPorApellido("").get(0);
        grupo.setIdMaestro(maestro);
        GregorianCalendar calendar = new GregorianCalendar();
        grupo.setInicioDeGrupo(new Date(calendar.getTimeInMillis()));
        ArrayList<Horario> horarios = new ArrayList<>();
        boolean resultado = grupo.crear(horarios);
        Assert.assertTrue("Prueba crear un Grupo correctamente", resultado);
    }
    
    @Test (expected = Exception.class)
    public void pruebaCrearGrupoVacio(){
        Grupo grupo = new Grupo();
        grupo.crear(null);
    }
    
    @Test
    public void pruebaObtenerTodosLosGrupos(){
        ArrayList<Grupo> grupos = new ArrayList<>(Grupo.listarGrupos());
        boolean resultado = !grupos.isEmpty();
        Assert.assertTrue("Prueba obtener todos los grupos",resultado);
    }
    
    @Test
    public void pruebaActualizarGrupo() throws Exception{
        ArrayList<Horario> horarios = new ArrayList<>();
        Grupo grupo = Grupo.listarGrupos().get(0);
        grupo.setSalon("101");
        grupo.setTipoDeDanza("Cumbia");
        grupo.setNivel("Avanzado");
        boolean resultado = grupo.actualizar(horarios, horarios);
        Assert.assertTrue("Prueba actualizar grupo correctamente",resultado);
    }
    
    @Test (expected = Exception.class)
    public void pruebaActualizarrGrupoErroneo() throws Exception{
        Grupo grupo = new Grupo();
        grupo.actualizar(null, null);
    }
*/
}
