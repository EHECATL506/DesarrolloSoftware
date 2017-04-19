/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.regex.Pattern;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Jonathan
 */
public class Validar {
     //validarTexto
    public static boolean texto(TextField texto) {
        texto.setStyle(null);
        Pattern auto = Pattern.compile("^([0-9A-Za-zñÑáéíóúÁÉÍÓÚ]+\\s?[0-9A-Za-zñÑáéíóúÁÉÍÓÚ,&.-:+%=/?@#]*\\s?)+$");
        boolean valido = auto.matcher(texto.getText()).find();
        if (valido && (texto.getText().length() < 45) ) return true;
        else texto.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");return false;
    }
    //validarCorreo
    public static boolean correo(TextField texto) {
        texto.setStyle(null);
        Pattern correo = Pattern.compile("^[_A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        boolean valido = correo.matcher(texto.getText()).find();
        if (valido) return true;
        else texto.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");return false;
    }
    //validarNumero
    public static boolean codigoPostal(TextField texto) {
        texto.setStyle(null);
        Pattern postal = Pattern.compile("^\\d{5}$");
        boolean valido = postal.matcher(texto.getText()).find();
        if (valido) return true;    
        else texto.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");return false;
    } 
    //validarCelular
    public static boolean celular(TextField texto) {
        texto.setStyle(null);
        Pattern digito = Pattern.compile("^\\d{10}$");
        boolean valido = digito.matcher(texto.getText()).find();
        if (valido) return true;
        else texto.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");return false;
    }
    //validarTelefono
    public static boolean telefono(TextField texto) {
        texto.setStyle(null);
        Pattern telefono = Pattern.compile("^\\d{7,10}$");
        boolean valido = telefono.matcher(texto.getText()).find();
        if (valido) return true;
        else texto.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");return false;
    }
    //validarFecha
    public static boolean fecha(DatePicker texto) {
        texto.setStyle(null);
        Pattern fecha = Pattern.compile("^\\d{4}-\\d{2}-\\d{1,2}$");
        boolean valido = fecha.matcher( (texto.getValue() == null) ? "" : texto.getValue().toString() ).find();        
        if (valido) return true;
        else texto.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");return false;
    }
    //validarCombo
    public static boolean combo(ComboBox texto) {
        texto.setStyle(null);
        if (texto.getValue() != null) 
            return true;
        else 
            texto.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        return false;
    }
    
    public static boolean area(TextArea texto) {
        texto.setStyle(null);
        Pattern auto = Pattern.compile("^([0-9A-Za-zñÑáéíóúÁÉÍÓÚ]+\\s?[0-9A-Za-zñÑáéíóúÁÉÍÓÚ,&.-:+%=/?@#]*\\s?)+$");
        boolean valido = auto.matcher(texto.getText()).find();
        if (valido && (texto.getText().length() < 64)) return true;
        else texto.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");return false;
    }
}
