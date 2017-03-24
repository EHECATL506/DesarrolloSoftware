/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA.ClasesEntidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mauricio Juárez
 */
@Entity
@Table(name = "contador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contador.findAll", query = "SELECT c FROM Contador c")
    , @NamedQuery(name = "Contador.findByIdContador", query = "SELECT c FROM Contador c WHERE c.idContador = :idContador")
    , @NamedQuery(name = "Contador.findByNombre", query = "SELECT c FROM Contador c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Contador.findByContador", query = "SELECT c FROM Contador c WHERE c.contador = :contador")})
public class Contador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idContador")
    private Integer idContador;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "contador")
    private int contador;

    public Contador() {
    }

    public Contador(Integer idContador) {
        this.idContador = idContador;
    }

    public Contador(Integer idContador, String nombre, int contador) {
        this.idContador = idContador;
        this.nombre = nombre;
        this.contador = contador;
    }

    public Integer getIdContador() {
        return idContador;
    }

    public void setIdContador(Integer idContador) {
        this.idContador = idContador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContador != null ? idContador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contador)) {
            return false;
        }
        Contador other = (Contador) object;
        if ((this.idContador == null && other.idContador != null) || (this.idContador != null && !this.idContador.equals(other.idContador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPA.ClasesEntidad.Contador[ idContador=" + idContador + " ]";
    }
    
}
