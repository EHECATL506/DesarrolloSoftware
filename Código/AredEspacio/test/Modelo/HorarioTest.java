package Modelo;

import Exceptions.NonexistentEntityException;
import junit.framework.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class HorarioTest {
    
    public HorarioTest() {
    }
    
    @Test
    public void pruebaCrearHorario(){
        Horario horario = new Horario();
        horario.setDia("Lunes");
        horario.setHora("12:00-14:00");
        Grupo grupo = Grupo.listarGrupos().get(0);
        horario.setIdGrupo(grupo);
        boolean resultado = horario.crear();
        
    }
  
    @Test(expected = Exception.class)
    public void pruebaCrearHorarioIncorrecto(){
        Horario horario = new Horario();
        horario.crear();
    }
    
    @Test
    public void pruebaActualizarHorario() throws Exception{
        Grupo grupo = Grupo.listarGrupos().get(0);
        Horario horario = grupo.getHorarioList().get(0);
        horario.setDia("Martes");
        boolean resultado = horario.actualizar();
        assertTrue("Prueba actualizar Horario", resultado);
    }
    
    @Test(expected = Exception.class)
    public void pruebaActualizarHorarioIncorrecto() throws Exception{
        Horario horario = new Horario();
        horario.actualizar();
    }
    
    @Test
    public void pruebaEliminarHorario() throws Exception{
        Grupo grupo = Grupo.listarGrupos().get(0);
        Horario horario = grupo.getHorarioList().get(0);
        boolean resultado = horario.eliminar();
        assertTrue("Prueba actualizar Horario", resultado);
    }
    
    @Test(expected = Exception.class)
    public void pruebaEliminarHorarioIncorrecto() throws NonexistentEntityException{
        Horario horario = new Horario();
        horario.eliminar();
    }
    
    //Nuevo
    @Test
    public void pruebaObtenerTodosLosHorarios(){
        boolean resultado = !Horario.listaDeHorarios().isEmpty();
        assertTrue("Prueba obtener todos los horarios", resultado);
    } 
    
}
