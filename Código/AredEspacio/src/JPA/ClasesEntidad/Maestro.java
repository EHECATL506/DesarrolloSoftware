package JPA.ClasesEntidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
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
 * @author Mauricio Juárez
 */
@Entity
@Table(name = "maestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maestro.findAll", query = "SELECT m FROM Maestro m")
    , @NamedQuery(name = "Maestro.findById", query = "SELECT m FROM Maestro m WHERE m.id = :id")
    , @NamedQuery(name = "Maestro.findByNoDeColaborador", query = "SELECT m FROM Maestro m WHERE m.noDeColaborador LIKE :noDeColaborador")
    , @NamedQuery(name = "Maestro.findByNombre", query = "SELECT m FROM Maestro m WHERE m.nombre LIKE :nombre")    
    , @NamedQuery(name = "Maestro.findByApellidos", query = "SELECT m FROM Maestro m WHERE m.apellidos = :apellidos")
    , @NamedQuery(name = "Maestro.findByFechaDeNacimiento", query = "SELECT m FROM Maestro m WHERE m.fechaDeNacimiento = :fechaDeNacimiento")
    , @NamedQuery(name = "Maestro.findByGenero", query = "SELECT m FROM Maestro m WHERE m.genero = :genero")
    , @NamedQuery(name = "Maestro.findByCorreoElectronico", query = "SELECT m FROM Maestro m WHERE m.correoElectronico = :correoElectronico")
    , @NamedQuery(name = "Maestro.findByTelefono", query = "SELECT m FROM Maestro m WHERE m.telefono = :telefono")
    , @NamedQuery(name = "Maestro.findByTelefonoMovil", query = "SELECT m FROM Maestro m WHERE m.telefonoMovil = :telefonoMovil")
    , @NamedQuery(name = "Maestro.findByDomicilio", query = "SELECT m FROM Maestro m WHERE m.domicilio = :domicilio")
    , @NamedQuery(name = "Maestro.findByCuidad", query = "SELECT m FROM Maestro m WHERE m.cuidad = :cuidad")
    , @NamedQuery(name = "Maestro.findByCodigoPostal", query = "SELECT m FROM Maestro m WHERE m.codigoPostal = :codigoPostal")
    , @NamedQuery(name = "Maestro.findByEstado", query = "SELECT m FROM Maestro m WHERE m.estado = :estado")
    , @NamedQuery(name = "Maestro.findByFechaDeRegistro", query = "SELECT m FROM Maestro m WHERE m.fechaDeRegistro = :fechaDeRegistro")
    , @NamedQuery(name = "Maestro.findByDeshabilitado", query = "SELECT m FROM Maestro m WHERE m.deshabilitado = :deshabilitado")
    , @NamedQuery(name = "Maestro.findByFechaDeDeshabilitacion", query = "SELECT m FROM Maestro m WHERE m.fechaDeDeshabilitacion = :fechaDeDeshabilitacion")})
public class Maestro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "noDeColaborador")
    private String noDeColaborador;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @Basic(optional = false)
    @Column(name = "fechaDeNacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaDeNacimiento;
    @Basic(optional = false)
    @Column(name = "genero")
    private String genero;
    @Basic(optional = false)
    @Column(name = "correoElectronico")
    private String correoElectronico;
    @Basic(optional = false)
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "telefonoMovil")
    private String telefonoMovil;
    @Basic(optional = false)
    @Column(name = "domicilio")
    private String domicilio;
    @Basic(optional = false)
    @Column(name = "cuidad")
    private String cuidad;
    @Basic(optional = false)
    @Column(name = "codigoPostal")
    private String codigoPostal;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @Column(name = "fechaDeRegistro")
    @Temporal(TemporalType.DATE)
    private Date fechaDeRegistro;
    @Basic(optional = false)
    @Column(name = "deshabilitado")
    private boolean deshabilitado;
    @Column(name = "fechaDeDeshabilitacion")
    @Temporal(TemporalType.DATE)
    private Date fechaDeDeshabilitacion;
    @Lob
    @Column(name = "motivoDeDeshabilitacion")
    private String motivoDeDeshabilitacion;

    public Maestro() {
    }

    public Maestro(Integer id) {
        this.id = id;
    }

    public Maestro(Integer id, String noDeColaborador, String nombre, String apellidos, byte[] foto, Date fechaDeNacimiento, String genero, String correoElectronico, String telefono, String telefonoMovil, String domicilio, String cuidad, String codigoPostal, String estado, Date fechaDeRegistro, boolean deshabilitado) {
        this.id = id;
        this.noDeColaborador = noDeColaborador;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.foto = foto;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.genero = genero;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.telefonoMovil = telefonoMovil;
        this.domicilio = domicilio;
        this.cuidad = cuidad;
        this.codigoPostal = codigoPostal;
        this.estado = estado;
        this.fechaDeRegistro = fechaDeRegistro;
        this.deshabilitado = deshabilitado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoDeColaborador() {
        return noDeColaborador;
    }

    public void setNoDeColaborador(String noDeColaborador) {
        this.noDeColaborador = noDeColaborador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getCuidad() {
        return cuidad;
    }

    public void setCuidad(String cuidad) {
        this.cuidad = cuidad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaDeRegistro() {
        return fechaDeRegistro;
    }

    public void setFechaDeRegistro(Date fechaDeRegistro) {
        this.fechaDeRegistro = fechaDeRegistro;
    }

    public boolean getDeshabilitado() {
        return deshabilitado;
    }

    public void setDeshabilitado(boolean deshabilitado) {
        this.deshabilitado = deshabilitado;
    }

    public Date getFechaDeDeshabilitacion() {
        return fechaDeDeshabilitacion;
    }

    public void setFechaDeDeshabilitacion(Date fechaDeDeshabilitacion) {
        this.fechaDeDeshabilitacion = fechaDeDeshabilitacion;
    }

    public String getMotivoDeDeshabilitacion() {
        return motivoDeDeshabilitacion;
    }

    public void setMotivoDeDeshabilitacion(String motivoDeDeshabilitacion) {
        this.motivoDeDeshabilitacion = motivoDeDeshabilitacion;
    }
    
    public static EntityManager obtenerController(){
        return Persistence.createEntityManagerFactory("AredEspacioPU", null).createEntityManager();
    }
    
    public static List<Maestro> obtenerCoincidenciasPorNombre(String nombre){
        return obtenerController().createNamedQuery("Maestro.findByNombre")
                        .setParameter("nombre","%"+ nombre +"%").getResultList();
    }
    
    public static List<Maestro> obtenerCoincidenciasPorNoDeColaborador(String noDeColaborador){
        return obtenerController().createNamedQuery("Maestro.findByNoDeColaborador")
                        .setParameter("noDeColaborador","%"+ noDeColaborador +"%").getResultList();
    }
    
    public void guardarCambios(){
        obtenerController().persist(this);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Maestro)) {
            return false;
        }
        Maestro other = (Maestro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JPA.ClasesEntidad.Maestro[ id=" + id + " ]";
    }
    
}
