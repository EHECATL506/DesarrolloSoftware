/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import ControladorBD.HorarioJpaController;
import Exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mauricio Juárez
 */
@Entity
@Table(name = "horario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horario.findAll", query = "SELECT h FROM Horario h")
    , @NamedQuery(name = "Horario.findByIdHorario", query = "SELECT h FROM Horario h WHERE h.idHorario = :idHorario")
    , @NamedQuery(name = "Horario.findByGrupo", query = "SELECT h FROM Horario h WHERE h.idGrupo = :idGrupo")
    , @NamedQuery(name = "Horario.findByDia", query = "SELECT h FROM Horario h WHERE h.dia = :dia")
    , @NamedQuery(name = "Horario.findByHora", query = "SELECT h FROM Horario h WHERE h.hora = :hora")})
public class Horario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idHorario")
    private Integer idHorario;
    @Basic(optional = false)
    @Column(name = "dia")
    private String dia;
    @Basic(optional = false)
    @Column(name = "hora")
    private String hora;
    @JoinColumn(name = "idGrupo", referencedColumnName = "idGrupo")
    @ManyToOne(optional = false)
    private Grupo idGrupo;
    
    public Horario() {
    }

    public Horario(String dia, String hora) {
        this.dia = dia;
        this.hora = hora;
    }
    
    public String getInicio() {
        String[] split = this.hora.split("-");
        return split[0];
    }
    
    public String getFin() {
        String[] split = this.hora.split("-");
        return split[1];
    }
    
    public Horario(Integer idHorario) {
        this.idHorario = idHorario;
    }
    
    public Horario(Integer idHorario, String dia, String hora) {
        this.idHorario = idHorario;
        this.dia = dia;
        this.hora = hora;
    }
    
    public Integer getIdHorario() {
        return idHorario;
    }
    
    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }
    
    public String getDia() {
        return dia;
    }
    
    public void setDia(String dia) {
        this.dia = dia;
    }
    
    public String getHora() {
        return hora;
    }
    
    public void setHora(String hora) {
        this.hora = hora;
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
        hash += (idHorario != null ? idHorario.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == null && object != null) {
            return false;
        }
        if (this != null && object == null) {
            return false;
        }
        Horario other = (Horario) object;
        
        return this.dia.equals(other.getDia())
                && this.hora.equals(other.getHora());
        /*
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horario)) {
        return false;
        }
        if ((this.idHorario == null && other.idHorario != null) || (this.idHorario != null && !this.idHorario.equals(other.idHorario))) {
        return false;
        }
        return true;*/
    }
    
    @Override
    public String toString() {
        return "Modelo.Horario[ idHorario=" + idHorario + " ]";
    }
    
    public boolean crear() {
        HorarioJpaController controller = new HorarioJpaController(
                Persistence.createEntityManagerFactory("AredEspacioPU", null)
        );
        controller.create(this);
        return true;
    }
    
    public boolean actualizar() throws Exception {
        HorarioJpaController controller = new HorarioJpaController(
                Persistence.createEntityManagerFactory("AredEspacioPU", null)
        );
        controller.edit(this);
        return true;
    }
    
    public boolean eliminar() throws NonexistentEntityException {
        HorarioJpaController controller = new HorarioJpaController(
                Persistence.createEntityManagerFactory("AredEspacioPU", null)
        );
        controller.destroy(this.idHorario);
        return true;
    }
    
    public static void crearHorarios(ArrayList<Horario> horarios, Grupo grupo) {
        for (Horario horario : horarios) {
            horario.setIdGrupo(grupo);
            horario.crear();
        }
    }
    
    public static List<Horario> listaDeHorarios() {
         HorarioJpaController controller = new HorarioJpaController(
                Persistence.createEntityManagerFactory("AredEspacioPU", null)
        );
        return controller.findHorarioEntities();
    }
}
