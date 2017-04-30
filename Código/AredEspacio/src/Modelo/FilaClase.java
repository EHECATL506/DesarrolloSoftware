package Modelo;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Jonathan
 */
public class FilaClase {

    private final SimpleStringProperty alumno;
    private final SimpleStringProperty grupo;
    private final SimpleStringProperty fechaIngreso;

    public FilaClase(String alumno, String grupo, String fechaIngreso) {
        this.alumno = new SimpleStringProperty(alumno);
        this.grupo = new SimpleStringProperty(grupo);
        this.fechaIngreso = new SimpleStringProperty(fechaIngreso);
    }
    
    public String getAlumno() {
        return alumno.get();
    }
 
    public String getGrupo() {
        return grupo.get();
    }

    public String getFechaIngreso() {
        return fechaIngreso.get();
    }
    
}
