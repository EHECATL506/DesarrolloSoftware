/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import ControladorBD.ClaseJpaController;
import Exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mauricio Juárez
 */
@Entity
@Table(name = "clase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clase.findAll", query = "SELECT c FROM Clase c")
    , @NamedQuery(name = "Clase.findByIdAlumno", query = "SELECT c FROM Clase c WHERE c.idAlumno = :idAlumno")
    , @NamedQuery(name = "Clase.findByIdGrupo", query = "SELECT c FROM Clase c WHERE c.idGrupo.idGrupo = :idGrupo")
    , @NamedQuery(name = "Clase.findByIdClase", query = "SELECT c FROM Clase c WHERE c.idClase = :idClase")
    , @NamedQuery(name = "Clase.findByFechaRegistro", query = "SELECT c FROM Clase c WHERE c.fechaRegistro = :fechaRegistro")})
public class Clase implements Serializable {

    @OneToMany(mappedBy = "idClase")
    private List<Asistencia> asistenciaList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClase")
    private List<Pago> pagoList;

    @Basic(optional = false)
    @Column(name = "fechaRegistro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idClase")
    private Integer idClase;
    @JoinColumn(name = "idAlumno", referencedColumnName = "idAlumno")
    @ManyToOne(optional = false)
    private Alumno idAlumno;
    @JoinColumn(name = "idGrupo", referencedColumnName = "idGrupo")
    @OneToOne(optional = false)
    private Grupo idGrupo;

    public Clase() {
    }

    public Clase(Integer idClase) {
        this.idClase = idClase;
    }

    public Integer getIdClase() {
        return idClase;
    }

    public void setIdClase(Integer idClase) {
        this.idClase = idClase;
    }

    public Alumno getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Alumno idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Grupo getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Grupo idGrupo) {
        this.idGrupo = idGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClase != null ? idClase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clase)) {
            return false;
        }
        Clase other = (Clase) object;
        if ((this.idClase == null && other.idClase != null) || (this.idClase != null && !this.idClase.equals(other.idClase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Clase[ idClase=" + idClase + " ]";
    }

    public String getMatricula(){
        return this.idAlumno.getMatricula();
    }
    
    public String getNombre(){
        return this.idAlumno.getNombre();
    }
    
    public String getApellidos(){
        return this.idAlumno.getApellidos();
    }
    
    public static List<Clase> obtenerClasesDelGrupo(int idGrupo) {
        return Persistence.createEntityManagerFactory("AredEspacioPU", null).createEntityManager()
                .createNamedQuery("Clase.findByIdGrupo").setParameter("idGrupo", idGrupo).getResultList();
    }
    
    public boolean cambiarDeGrupo(Grupo grupo) throws NonexistentEntityException, Exception{
        ClaseJpaController controller = new ClaseJpaController(Persistence.createEntityManagerFactory("AredEspacioPU", null));
        this.idGrupo=grupo;
        controller.edit(this);
        return true;
    }
    
    public static List<Clase> listaDeClases(){
        ClaseJpaController controller = new ClaseJpaController(Persistence.createEntityManagerFactory("AredEspacioPU", null));
        return controller.findClaseEntities();
    }
    
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @XmlTransient
    public List<Pago> getPagoList() {
        return pagoList;
    }

    public void setPagoList(List<Pago> pagoList) {
        this.pagoList = pagoList;
    } 

    @XmlTransient
    public List<Asistencia> getAsistenciaList() {
        return asistenciaList;
    }

    public void setAsistenciaList(List<Asistencia> asistenciaList) {
        this.asistenciaList = asistenciaList;
    }
}