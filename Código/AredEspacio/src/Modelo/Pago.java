/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import ControladorBD.PagoJpaController;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jonathan
 */
@Entity
@Table(name = "pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pago.findAll", query = "SELECT p FROM Pago p")
    , @NamedQuery(name = "Pago.findByIdPago", query = "SELECT p FROM Pago p WHERE p.idPago = :idPago")
    , @NamedQuery(name = "Pago.findByIdClase", query = "SELECT p FROM Pago p WHERE p.idClase.idClase = :idClase AND p.fechaPago>:fecha")
    , @NamedQuery(name = "Pago.findByFolio", query = "SELECT p FROM Pago p WHERE p.folio = :folio")
    , @NamedQuery(name = "Pago.findByDescuento", query = "SELECT p FROM Pago p WHERE p.descuento = :descuento")
    , @NamedQuery(name = "Pago.findByAbono", query = "SELECT p FROM Pago p WHERE p.abono = :abono")
    , @NamedQuery(name = "Pago.findByFechaPago", query = "SELECT p FROM Pago p WHERE p.fechaPago = :fechaPago")
    , @NamedQuery(name = "Pago.findByStatus", query = "SELECT p FROM Pago p WHERE p.status = :status")
    , @NamedQuery(name = "Pago.findByTipoDePago", query = "SELECT p FROM Pago p WHERE p.tipoDePago = :tipoDePago")})
public class Pago implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPago")
    private Integer idPago;
    @Basic(optional = false)
    @Column(name = "folio")
    private String folio;
    @Basic(optional = false)
    @Column(name = "descuento")
    private float descuento;
    @Basic(optional = false)
    @Column(name = "abono")
    private float abono;
    @Basic(optional = false)
    @Column(name = "fechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "tipoDePago")
    private String tipoDePago;
    
    @JoinColumn(name = "idClase", referencedColumnName = "idClase")
    @ManyToOne(optional = false)
    private Clase idClase;
    
    @JoinColumn(name = "idPromocion", referencedColumnName = "idPromocion")
    @ManyToOne(optional = false)
    private Promocion idPromocion;

    public Pago() {
    }

    public Pago(Integer idPago) {
        this.idPago = idPago;
    }

    public Pago(Integer idPago, String folio, float descuento, float abono, Date fechaPago, String status, String tipoDePago) {
        this.idPago = idPago;
        this.folio = folio;
        this.descuento = descuento;
        this.abono = abono;
        this.fechaPago = fechaPago;
        this.status = status;
        this.tipoDePago = tipoDePago;
    }

    public Promocion getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Promocion idPromocion) {
        this.idPromocion = idPromocion;
    }
    
    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public float getAbono() {
        return abono;
    }

    public void setAbono(float abono) {
        this.abono = abono;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipoDePago() {
        return tipoDePago;
    }

    public void setTipoDePago(String tipoDePago) {
        this.tipoDePago = tipoDePago;
    }

    public Clase getIdClase() {
        return idClase;
    }

    public void setIdClase(Clase idClase) {
        this.idClase = idClase;
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
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.idPago == null && other.idPago != null) || (this.idPago != null && !this.idPago.equals(other.idPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Pago[ idPago=" + idPago + " ]";
    }
    
    public static List<Pago> obtenerPagosPorIdDeClase(int idClase, Date fecha) {
        return Persistence.createEntityManagerFactory("AredEspacioPU", null).createEntityManager()
                .createNamedQuery("Pago.findByIdClase")
                .setParameter("idClase", idClase)
                .setParameter("fecha", fecha).getResultList();
    }
    
    public static List<Pago> obtenerTodosLosPagos(){
        PagoJpaController controller = new PagoJpaController(
                Persistence.createEntityManagerFactory("AredEspacioPU", null));
        return controller.findPagoEntities();
    }
    
    public String getGrupo(){
        return "Danza: "+this.idClase.getIdGrupo().getTipoDeDanza()
                +"\nNivel: " +this.idClase.getIdGrupo().getNivel()
                +"\nSalon: " +this.idClase.getIdGrupo().getSalon();
    }
    
    public String getAlumno(){
        return this.idClase.getNombre()+" "+this.idClase.getApellidos();
    }
    
    public String getPromocion(){
        return "Descuento: "+this.idPromocion.getPorcentajeDeDescuento()
              +"\nDescripción: "+this.idPromocion.getDescripcion();
    }
    
    public String getAbonoString(){
        return ""+new Double(this.abono).intValue();
    }
    
    public String getFecha(){
        GregorianCalendar fecha = new GregorianCalendar();
        fecha.setTimeInMillis(this.fechaPago.getTime());
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int año = fecha.get(Calendar.YEAR);
        return String.format(" %1$02d/%2$02d/%3$04d", dia, mes, año);
    }
    
}
