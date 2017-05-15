/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Mauricio
 */
public class DatoPago {

    private final String grupo;
    private final String pagos;
    private final String total;
    private final String porcentaje;
    private final String TotalConPorcentaje;

    public DatoPago(String grupo, String pagos, String total, String porcentaje, String TotalConPorcentaje) {
        this.grupo = grupo;
        this.pagos = pagos;
        this.total = total;
        this.porcentaje = porcentaje;
        this.TotalConPorcentaje = TotalConPorcentaje;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getPagos() {
        return pagos;
    }

    public String getTotal() {
        return total;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    public String getTotalConPorcentaje() {
        return TotalConPorcentaje;
    }
}
