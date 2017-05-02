/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import ControladorBD.DanzaJpaController;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mauricio
 */
@Entity
@Table(name = "danza")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Danza.findAll", query = "SELECT d FROM Danza d")
    , @NamedQuery(name = "Danza.findByIdDanza", query = "SELECT d FROM Danza d WHERE d.idDanza = :idDanza")
    , @NamedQuery(name = "Danza.findByTipoDanza", query = "SELECT d FROM Danza d WHERE d.tipoDanza = :tipoDanza")})
public class Danza implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDanza")
    private List<Grupo> grupoList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDanza")
    private Integer idDanza;
    @Basic(optional = false)
    @Column(name = "tipoDanza")
    private String tipoDanza;

    public Danza() {
    }

    public Danza(Integer idDanza) {
        this.idDanza = idDanza;
    }

    public Danza(String tipoDanza) {
        this.tipoDanza = tipoDanza;
    }

    public Danza(Integer idDanza, String tipoDanza) {
        this.idDanza = idDanza;
        this.tipoDanza = tipoDanza;
    }

    public Integer getIdDanza() {
        return idDanza;
    }

    public void setIdDanza(Integer idDanza) {
        this.idDanza = idDanza;
    }

    public String getTipoDanza() {
        return tipoDanza;
    }

    public void setTipoDanza(String tipoDanza) {
        this.tipoDanza = tipoDanza;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDanza != null ? idDanza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Danza)) {
            return false;
        }
        Danza other = (Danza) object;
        if ((this.idDanza == null && other.idDanza != null) || (this.idDanza != null && !this.idDanza.equals(other.idDanza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Danza[ idDanza=" + idDanza + " ]";
    }
    
    public static List<Danza> obtenerTodas(){
        return Persistence.createEntityManagerFactory("AredEspacioPU",null)
                .createEntityManager().createNamedQuery("Danza.findAll").getResultList();
    }
    
    public static Danza buscarPorTipoDanza(String tipoDanza){
    return (Danza) Persistence.createEntityManagerFactory("AredEspacioPU",null)
                .createEntityManager()
            .createNamedQuery("Danza.findByTipoDanza").setParameter("tipoDanza", tipoDanza).getSingleResult();
    }

    public boolean crear() {
        if(this.tipoDanza==null || this.tipoDanza.equals("")){
            return false;
        }
        DanzaJpaController danza = new DanzaJpaController(
                Persistence.createEntityManagerFactory("AredEspacioPU",null));
        danza.create(this);
        return true;
    }

    @XmlTransient
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }
}
