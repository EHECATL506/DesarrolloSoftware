/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import ControladorBD.EgresosJpaController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author EHECA
 */
@Entity
@Table(name = "egresos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Egresos.findAll", query = "SELECT e FROM Egresos e"),
    @NamedQuery(name = "Egresos.findByIdpago", query = "SELECT e FROM Egresos e WHERE e.idpago = :idpago"),
    @NamedQuery(name = "Egresos.findByMonto", query = "SELECT e FROM Egresos e WHERE e.monto = :monto"),
    @NamedQuery(name = "Egresos.findByFechaInicio", query = "SELECT e FROM Egresos e WHERE e.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Egresos.findByMotivo", query = "SELECT e FROM Egresos e WHERE e.motivo = :motivo"),
    @NamedQuery(name = "Egresos.findByIdMaestro", query = "SELECT e FROM Egresos e WHERE e.idMaestro = :idMaestro"),
    @NamedQuery(name = "Egresos.findByFechaFin", query = "SELECT e FROM Egresos e WHERE e.fechaFin = :fechaFin")})
public class Egresos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Idpago")
    private Integer idpago;
    @Basic(optional = false)
    @Column(name = "Monto")
    private int monto;
    @Basic(optional = false)
    @Column(name = "FechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "Motivo")
    private String motivo;
    @Column(name = "IdMaestro")
    private Integer idMaestro;
    @Basic(optional = false)
    @Column(name = "FechaFin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    public Egresos() {
    }

    public Egresos(Integer idpago) {
        this.idpago = idpago;
    }

    public Egresos(Integer idpago, int monto, Date fechaInicio, String motivo, Date fechaFin) {
        this.idpago = idpago;
        this.monto = monto;
        this.fechaInicio = fechaInicio;
        this.motivo = motivo;
        this.fechaFin = fechaFin;
    }

    public Integer getIdpago() {
        return idpago;
    }

    public void setIdpago(Integer idpago) {
        this.idpago = idpago;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
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

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpago != null ? idpago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Egresos)) {
            return false;
        }
        Egresos other = (Egresos) object;
        if ((this.idpago == null && other.idpago != null) || (this.idpago != null && !this.idpago.equals(other.idpago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Egresos[ idpago=" + idpago + " ]";
    }
    
     public boolean crear(ArrayList<Egresos> egresos) {
        EgresosJpaController controller = new EgresosJpaController(
                Persistence.createEntityManagerFactory("AredEspacioPU", null)
        );
        controller.create(this);
        return true;
    }
     
     public static List<Egresos> listar() {
        return Persistence.createEntityManagerFactory("AredEspacioPU", null)                
                .createEntityManager()
                .createNamedQuery("Egresos.findAll").getResultList();
    }
}
