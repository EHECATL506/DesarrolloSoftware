/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import ControladorBD.EgresoJpaController;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mauricio
 */
@Entity
@Table(name = "egreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Egreso.findAll", query = "SELECT e FROM Egreso e")
    , @NamedQuery(name = "Egreso.findByIdPago", query = "SELECT e FROM Egreso e WHERE e.idPago = :idPago")
    , @NamedQuery(name = "Egreso.findByMonto", query = "SELECT e FROM Egreso e WHERE e.monto = :monto")
    , @NamedQuery(name = "Egreso.findByFecha", query = "SELECT e FROM Egreso e WHERE e.fecha = :fecha")
    , @NamedQuery(name = "Egreso.findByIdMaestro", query = "SELECT e FROM Egreso e WHERE e.idMaestro = :idMaestro")})
public class Egreso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPago")
    private Integer idPago;
    @Basic(optional = false)
    @Column(name = "monto")
    private int monto;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Lob
    @Column(name = "motivo")
    private String motivo;
    @Column(name = "IdMaestro")
    private Integer idMaestro;

    public Egreso() {
    }

    public Egreso(Integer idPago) {
        this.idPago = idPago;
    }

    public Egreso(Integer idPago, int monto, Date fecha, String motivo) {
        this.idPago = idPago;
        this.monto = monto;
        this.fecha = fecha;
        this.motivo = motivo;
    }

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Integer getIdMaestro() {
        return idMaestro;
    }

    public void setIdMaestro(Integer idMaestro) {
        this.idMaestro = idMaestro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPago != null ? idPago.hashCode() : 0);
        return hash;
    }

    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Egreso)) {
            return false;
        }
        Egreso other = (Egreso) object;
        if ((this.idPago == null && other.idPago != null) || (this.idPago != null && !this.idPago.equals(other.idPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Egreso[ idPago=" + idPago + " ]";
    }
    
    public boolean crear(){
        EgresoJpaController controller = 
                new EgresoJpaController
        (Persistence.createEntityManagerFactory("AredEspacioPU", null));
        controller.create(this);
        return true;
    }
    public static List<Egreso> listar() {
        return Persistence.createEntityManagerFactory("AredEspacioPU", null)                
                .createEntityManager()
                .createNamedQuery("Egreso.findAll").getResultList();
    }
     public static List<Egreso> listarMeses(int mes) {
        return Persistence.createEntityManagerFactory("AredEspacioPU", null)                
                .createEntityManager()
                .createNamedQuery("SELECT * FROM Egreso WHERE MONTH(fecha) = ?").getResultList();
     }
}
