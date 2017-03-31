package Modelo;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ClaseTest {

    public ClaseTest() {
    }

    @Test
    public void pruebaObtenerClasesPorGrupo() {
        List<Clase> clases = Clase.obtenerClasesDelGrupo(17);
        boolean resultado = clases.size() > 0;
        Assert.assertTrue("Prueba obtener Clases por Id de grupo", resultado);
    }

    @Test
    public void pruebaNoEncontrarClasesPorGrupo() {
        List<Clase> clases = Clase.obtenerClasesDelGrupo(-1);
        boolean resultado = clases.isEmpty();
        Assert.assertTrue("Prueba No encontrar Clases por Id de grupo", resultado);
    }
    
    @Test
    public void pruebaCambiarDeGrupoCorrectamente() throws Exception{
        Clase clase = Clase.obtenerClasesDelGrupo(6).get(0);
        Grupo grupo = Grupo.listarGrupos().get(0);
        boolean resultado = clase.cambiarDeGrupo(grupo);
        Assert.assertTrue("Prueba cambiar grupo en clase", resultado);
    }
    
    @Test(expected = Exception.class)
    public void pruebaCambiarDeGrupoIncorrectamente() throws Exception{
        Clase clase = Clase.obtenerClasesDelGrupo(6).get(0);
        boolean resultado = clase.cambiarDeGrupo(new Grupo());
        Assert.assertTrue("Prueba cambiar grupo en clase incorrectamente", resultado);
    }    
}
