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

    private final SimpleStringProperty dia;
    private final SimpleStringProperty hora;

    public FilaHorario(String dia, String hora) {
        this.dia = new SimpleStringProperty(dia);
        this.hora = new SimpleStringProperty(hora);
    }

    public String getDia() {
        return dia.get();
    }

    public String getHora() {
        return hora.get();
    }
}
