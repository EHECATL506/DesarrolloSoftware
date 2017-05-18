package Modelo;

import ControladorBD.EgresoJpaController;
import ControladorBD.MaestroJpaController;
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
    , @NamedQuery(name = "Egreso.findByIdEgreso", query = "SELECT e FROM Egreso e WHERE e.idEgreso = :idEgreso")
    , @NamedQuery(name = "Egreso.findByMonto", query = "SELECT e FROM Egreso e WHERE e.monto = :monto")
    , @NamedQuery(name = "Egreso.findByFecha", query = "SELECT e FROM Egreso e WHERE e.fecha = :fecha")
    , @NamedQuery(name = "Egreso.findByIdMaestro", query = "SELECT e FROM Egreso e WHERE e.idMaestro = :idMaestro")})
public class Egreso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEgreso")
    private Integer idEgreso;
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

    public Egreso(Integer idEgreso) {
        this.idEgreso = idEgreso;
    }

    public Egreso(Integer idEgreso, int monto, Date fecha, String motivo) {
        this.idEgreso = idEgreso;
        this.monto = monto;
        this.fecha = fecha;
        this.motivo = motivo;
    }

    public Integer getIdEgreso() {
        return idEgreso;
    }

    public void setIdPago(Integer idPago) {
        this.idEgreso = idPago;
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
        hash += (idEgreso != null ? idEgreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Egreso)) {
            return false;
        }
        Egreso other = (Egreso) object;
        if ((this.idEgreso == null && other.idEgreso != null) || (this.idEgreso != null && !this.idEgreso.equals(other.idEgreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Egreso[ idEgreso=" + idEgreso + " ]";
    }

    public static List<Egreso> obtenerTodosLosEgresos() {
        EgresoJpaController controller
                = new EgresoJpaController(Persistence.createEntityManagerFactory("AredEspacioPU", null));
        return controller.findEgresoEntities();
    }

    public boolean crear() {
        EgresoJpaController controller
                = new EgresoJpaController(Persistence.createEntityManagerFactory("AredEspacioPU", null));
        controller.create(this);
        return true;
    }

    public String getMontoCompleto() {
        return String.valueOf(this.monto);
    }

    public String getFechaCompleta() {
        GregorianCalendar fecha = new GregorianCalendar();
        fecha.setTimeInMillis(this.fecha.getTime());
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int año = fecha.get(Calendar.YEAR);
        return String.format(" %1$02d/%2$02d/%3$04d", dia, mes, año);
    }
    
    public String getMotivoCompleto(){
        if(this.idMaestro==null){
            return this.motivo;
        }else{
            MaestroJpaController controller = new MaestroJpaController
                (Persistence.createEntityManagerFactory("AredEspacioPU", null));
            Maestro maestro = controller.findMaestro(this.idMaestro);
            return this.motivo+": "+maestro.getNombre()+" "+maestro.getApellidos();
        }
    }
}
