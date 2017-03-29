/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Jonathan
 */
public class FilaHorario {

    private final SimpleStringProperty clase;
    private final SimpleStringProperty maestro;
    private final SimpleStringProperty dia;
    private final SimpleStringProperty hora;
    private final SimpleStringProperty nivel;

    public FilaHorario(String clase, String maestro, String dia, String hora, String nivel) {
        this.clase = new SimpleStringProperty(clase);
        this.maestro = new SimpleStringProperty(maestro);
        this.dia = new SimpleStringProperty(dia);
        this.hora = new SimpleStringProperty(hora);
        this.nivel = new SimpleStringProperty(nivel);
    }

    public String getClase() {
        return clase.get();
    }

    public String getMaestro() {
        return maestro.get();
    }

    public String getDia() {
        return dia.get();
    }

    public String getHora() {
        return hora.get();
    }
    
    public String getNivel() {
        return nivel.get();
    }
}
