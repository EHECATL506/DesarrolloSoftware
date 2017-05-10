/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import ControladorBD.PromocionJpaController;
import ControladorBD.exceptions.NonexistentEntityException;
import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mauricio
 */
@Entity
@Table(name = "promocion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promocion.findAll", query = "SELECT p FROM Promocion p")
    , @NamedQuery(name = "Promocion.findByIdPromocion", query = "SELECT p FROM Promocion p WHERE p.idPromocion = :idPromocion")
    , @NamedQuery(name = "Promocion.findByTipo", query = "SELECT p FROM Promocion p WHERE p.tipo = :tipo")
    , @NamedQuery(name = "Promocion.findByDescripcion", query = "SELECT p FROM Promocion p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Promocion.findByDescuento", query = "SELECT p FROM Promocion p WHERE p.descuento = :descuento")})
public class Promocion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPromocion")
    private Integer idPromocion;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "descuento")
    private float descuento;
    
    public Promocion() {
    }
    
    public Promocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }
    
    public Promocion(Integer idPromocion, String tipo, String descripcion, float descuento) {
        this.idPromocion = idPromocion;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.descuento = descuento;
    }
    
    public Integer getIdPromocion() {
        return idPromocion;
    }
    
    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public float getDescuento() {
        return descuento;
    }
    
    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPromocion != null ? idPromocion.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promocion)) {
            return false;
        }
        Promocion other = (Promocion) object;
        if ((this.idPromocion == null && other.idPromocion != null) || (this.idPromocion != null && !this.idPromocion.equals(other.idPromocion))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Modelo.Promocion[ idPromocion=" + idPromocion + " ]";
    }
    
    public String getPorcentajeDeDescuento(){
        Double valor = new Double(this.descuento*100);
        return valor.intValue()+"%";
    }
    
    private static PromocionJpaController obtenerController() {
        return new PromocionJpaController(
                Persistence.createEntityManagerFactory("AredEspacioPU", null));
    }
    
    public static List<Promocion> listaDePromociones() {
        return obtenerController().findPromocionEntities();
    }
    
    public boolean crear() {
        obtenerController().create(this);
        return true;
    }
    
    public boolean eliminar() throws NonexistentEntityException {
        obtenerController().destroy(this.idPromocion);
        return true;
    }
    
    public boolean actualizar() throws Exception {
        obtenerController().edit(this);
        return true;
    }
}
