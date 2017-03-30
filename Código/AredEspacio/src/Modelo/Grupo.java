package Modelo;

import ControladorBD.GrupoJpaController;
import Exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g")
    , @NamedQuery(name = "Grupo.findByIdGrupo", query = "SELECT g FROM Grupo g WHERE g.idGrupo = :idGrupo")
    , @NamedQuery(name = "Grupo.findBySalon", query = "SELECT g FROM Grupo g WHERE g.salon = :salon")
    , @NamedQuery(name = "Grupo.findByTipoDeDanza", query = "SELECT g FROM Grupo g WHERE g.tipoDeDanza LIKE :tipoDeDanza")
    , @NamedQuery(name = "Grupo.findByDanza", query = "SELECT g FROM Grupo g WHERE g.tipoDeDanza LIKE :tipoDeDanza")
    , @NamedQuery(name = "Grupo.findByInicioDeGrupo", query = "SELECT g FROM Grupo g WHERE g.inicioDeGrupo = :inicioDeGrupo")
    , @NamedQuery(name = "Grupo.findByFinDeGrupo", query = "SELECT g FROM Grupo g WHERE g.finDeGrupo = :finDeGrupo")})
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Basic(optional = false)
    @Column(name = "idGrupo")
    private Integer idGrupo;

    @Basic(optional = false)
    @Column(name = "salon")
    private String salon;
    @Basic(optional = false)
    @Column(name = "tipoDeDanza")
    private String tipoDeDanza;

    @Basic(optional = false)
    @Column(name = "nivel")
    private String nivel;

    @Basic(optional = false)
    @Column(name = "inicioDeGrupo")
    @Temporal(TemporalType.DATE)
    private Date inicioDeGrupo;
    @Column(name = "finDeGrupo")
    @Temporal(TemporalType.DATE)
    private Date finDeGrupo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGrupo")
    private List<Horario> horarioList;

    @JoinColumn(name = "idMaestro", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Maestro idMaestro;

    /*@OneToOne(cascade = CascadeType.ALL, mappedBy = "idGrupo")
    private Clase clase;
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGrupo")
    private List<Clase> claseList;

    @XmlTransient
    public List<Horario> getHorarioList() {
        return horarioList;
    }

    public Grupo() {
    }

    public Grupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNivel() {
        return this.nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Grupo(Integer idGrupo, String salon, String tipoDeDanza, Date inicioDeGrupo) {
        this.idGrupo = idGrupo;
        this.salon = salon;
        this.tipoDeDanza = tipoDeDanza;
        this.inicioDeGrupo = inicioDeGrupo;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public String getTipoDeDanza() {
        return tipoDeDanza;
    }

    public void setTipoDeDanza(String tipoDeDanza) {
        this.tipoDeDanza = tipoDeDanza;
    }

    public Date getInicioDeGrupo() {
        return inicioDeGrupo;
    }

    public void setInicioDeGrupo(Date inicioDeGrupo) {
        this.inicioDeGrupo = inicioDeGrupo;
    }

    public Date getFinDeGrupo() {
        return finDeGrupo;
    }

    public void setFinDeGrupo(Date finDeGrupo) {
        this.finDeGrupo = finDeGrupo;
    }

    public void setHorarioList(List<Horario> horarioList) {
        this.horarioList = horarioList;
    }

    public Maestro getIdMaestro() {
        return idMaestro;
    }

    public String getMaestro() {
        return this.idMaestro.getNombre() + " " + this.idMaestro.getApellidos();
    }

    public void setIdMaestro(Maestro idMaestro) {
        this.idMaestro = idMaestro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrupo != null ? idGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.idGrupo == null && other.idGrupo != null) || (this.idGrupo != null && !this.idGrupo.equals(other.idGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Grupo[ idGrupo=" + idGrupo + " ]";
    }

    public boolean crear(ArrayList<Horario> horarios) {
        GrupoJpaController controller = new GrupoJpaController(
                Persistence.createEntityManagerFactory("AredEspacioPU", null)
        );
        controller.create(this);
        Horario.crearHorarios(horarios, this);
        return true;
    }

    public static List<Grupo> listarGrupos() {
        return Persistence.createEntityManagerFactory("AredEspacioPU", null)
                .createEntityManager()
                .createNamedQuery("Grupo.findAll").getResultList();
    }

    public boolean actualizar(ArrayList<Horario> horarios, ArrayList<Horario> horariosEliminados) throws NonexistentEntityException, Exception {
        GrupoJpaController controller = new GrupoJpaController(
                Persistence.createEntityManagerFactory("AredEspacioPU", null)
        );
        controller.edit(this);

        for (Horario horario : horarios) {
            try {
                horario.actualizar();
            } catch (Exception e) {
                horario.setIdGrupo(this);
                horario.crear();
            }
        }

        for (Horario horario : horariosEliminados) {
            horario.eliminar();
        }
        return true;
    }
    
    public List<Grupo> obtenerDanzas() {
        EntityManager em = Persistence.createEntityManagerFactory("AredEspacioPU", null).createEntityManager();
        return em.createNamedQuery("Grupo.findByTipoDeDanza").setParameter("tipoDeDanza", "%" + "" + "%").getResultList();
    }
    
    public List<Grupo> obtenerIdGrupo(String tipoDanza) {
        EntityManager em = Persistence.createEntityManagerFactory("AredEspacioPU", null).createEntityManager();
        return em.createNamedQuery("Grupo.findByDanza").setParameter("tipoDeDanza", "%" + tipoDanza + "%").getResultList();
    }
}
