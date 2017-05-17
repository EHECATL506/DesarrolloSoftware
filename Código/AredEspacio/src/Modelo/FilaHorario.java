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
    
    private String clase;
    private String maestro;
    private String nivel;
    
    private SimpleStringProperty dia;
    private SimpleStringProperty hora;

    public FilaHorario(String dia, String hora) {
        this.dia = new SimpleStringProperty(dia);
        this.hora = new SimpleStringProperty(hora);
    }
    
    public FilaHorario(String clase, String maestro, String dia, String hora, String nivel){
        this.clase=clase;
        this.maestro=maestro;
        this.dia = new SimpleStringProperty(dia);
        this.hora = new SimpleStringProperty(hora);
        this.nivel = nivel;
    }

    public String getDia() {
        return dia.get();
    }

    public String getHora() {
        return hora.get();
    }

    public String getClase() {
        return clase;
    }

    public String getMaestro() {
        return maestro;
    }

    public String getNivel() {
        return nivel;
    }
}
