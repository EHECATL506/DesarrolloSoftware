package Modelo;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Jonathan
 */
public class FilaClase {

    private final SimpleStringProperty grupo;
    private final SimpleStringProperty fechaIngreso;
    private final SimpleStringProperty proximoPago;
    private final SimpleStringProperty danza;
    private final SimpleStringProperty status;
    private Clase clase;

    public FilaClase(String grupo, String fechaIngreso, String proximoPago, String danza, String status, Clase c) {
        this.proximoPago = new SimpleStringProperty(proximoPago);
        this.grupo = new SimpleStringProperty(grupo);
        this.fechaIngreso = new SimpleStringProperty(fechaIngreso);
        this.danza = new SimpleStringProperty(danza);
        this.status = new SimpleStringProperty(status);
        this.clase = c;
    }
    
    public Clase getClase() {
        return clase;
    }
    
    public String getProximoPago() {
        return proximoPago.get();
    }
 
    public String getGrupo() {
        return grupo.get();
    }

    public String getFechaIngreso() {
        return fechaIngreso.get();
    }
    
    public String getDanza() {
        return danza.get();
    }
    
    public String getStatus() {
        return status.get();
    }
    
}
