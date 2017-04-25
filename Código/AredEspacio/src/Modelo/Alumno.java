/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "alumno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alumno.findAll", query = "SELECT a FROM Alumno a")
    , @NamedQuery(name = "Alumno.findByIdAlumno", query = "SELECT a FROM Alumno a WHERE a.idAlumno = :idAlumno")
    , @NamedQuery(name = "Alumno.findByMatricula", query = "SELECT a FROM Alumno a WHERE a.matricula LIKE :matricula")
    , @NamedQuery(name = "Alumno.findByNombre", query = "SELECT a FROM Alumno a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Alumno.findByApellidos", query = "SELECT a FROM Alumno a WHERE a.apellidos LIKE :apellidos")
    , @NamedQuery(name = "Alumno.findByFechaNacimiento", query = "SELECT a FROM Alumno a WHERE a.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Alumno.findByGenero", query = "SELECT a FROM Alumno a WHERE a.genero = :genero")
    , @NamedQuery(name = "Alumno.findByCorreo", query = "SELECT a FROM Alumno a WHERE a.correo = :correo")
    , @NamedQuery(name = "Alumno.findByTelefono", query = "SELECT a FROM Alumno a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "Alumno.findByMovil", query = "SELECT a FROM Alumno a WHERE a.movil = :movil")
    , @NamedQuery(name = "Alumno.findByDomicilo", query = "SELECT a FROM Alumno a WHERE a.domicilo = :domicilo")
    , @NamedQuery(name = "Alumno.findByCp", query = "SELECT a FROM Alumno a WHERE a.cp = :cp")
    , @NamedQuery(name = "Alumno.findByCiudad", query = "SELECT a FROM Alumno a WHERE a.ciudad = :ciudad")
    , @NamedQuery(name = "Alumno.findByEstado", query = "SELECT a FROM Alumno a WHERE a.estado = :estado")
    , @NamedQuery(name = "Alumno.findByFechaRegistro", query = "SELECT a FROM Alumno a WHERE a.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "Alumno.findByFechaBaja", query = "SELECT a FROM Alumno a WHERE a.fechaBaja = :fechaBaja")
    , @NamedQuery(name = "Alumno.findByStatus", query = "SELECT a FROM Alumno a WHERE a.status = :status")})
public class Alumno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAlumno")
    private Integer idAlumno;
    @Basic(optional = false)
    @Column(name = "matricula")
    private String matricula;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "fechaNacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @Column(name = "genero")
    private String genero;
    @Basic(optional = false)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "movil")
    private String movil;
    @Basic(optional = false)
    @Column(name = "domicilo")
    private String domicilo;
    @Basic(optional = false)
    @Column(name = "cp")
    private String cp;
    @Basic(optional = false)
    @Column(name = "ciudad")
    private String ciudad;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @Basic(optional = false)
    @Column(name = "fechaRegistro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Column(name = "fechaBaja")
    @Temporal(TemporalType.DATE)
    private Date fechaBaja;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Lob
    @Column(name = "motivo")
    private String motivo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAlumno")
    private List<Clase> claseList;

    public Alumno() {
    }

    public Alumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Alumno(Integer idAlumno, String matricula, String nombre, String apellidos, Date fechaNacimiento, String genero, String correo, String telefono, String movil, String domicilo, String cp, String ciudad, String estado, Date fechaRegistro, String status, String motivo) {
        this.idAlumno = idAlumno;
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.correo = correo;
        this.telefono = telefono;
        this.movil = movil;
        this.domicilo = domicilo;
        this.cp = cp;
        this.ciudad = ciudad;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.status = status;
        this.motivo = motivo;
    }

    public Integer getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getDomicilo() {
        return domicilo;
    }

    public void setDomicilo(String domicilo) {
        this.domicilo = domicilo;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @XmlTransient
    public List<Clase> getClaseList() {
        return claseList;
    }

    public void setClaseList(List<Clase> claseList) {
        this.claseList = claseList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlumno != null ? idAlumno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alumno)) {
            return false;
        }
        Alumno other = (Alumno) object;
        if ((this.idAlumno == null && other.idAlumno != null) || (this.idAlumno != null && !this.idAlumno.equals(other.idAlumno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Alumno[ idAlumno=" + idAlumno + " ]";
    }
}
