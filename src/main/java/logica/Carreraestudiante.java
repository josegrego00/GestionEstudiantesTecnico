/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.io.Serializable;
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
import javax.persistence.Table;

/**
 *
 * @author josepino
 */
@Entity
@Table(name = "carreraestudiante")
@NamedQueries({
    @NamedQuery(name = "Carreraestudiante.findAll", query = "SELECT c FROM Carreraestudiante c"),
    @NamedQuery(name = "Carreraestudiante.findById", query = "SELECT c FROM Carreraestudiante c WHERE c.id = :id")})
public class Carreraestudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "idcarrera", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Carrera idcarrera;
    @JoinColumn(name = "idestudiante", referencedColumnName = "dni_estudiante")
    @ManyToOne(optional = false)
    private Estudiante idestudiante;

    public Carreraestudiante() {
    }

    public Carreraestudiante(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Carrera getIdcarrera() {
        return idcarrera;
    }

    public void setIdcarrera(Carrera idcarrera) {
        this.idcarrera = idcarrera;
    }

    public Estudiante getIdestudiante() {
        return idestudiante;
    }

    public void setIdestudiante(Estudiante idestudiante) {
        this.idestudiante = idestudiante;
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
        if (!(object instanceof Carreraestudiante)) {
            return false;
        }
        Carreraestudiante other = (Carreraestudiante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logica.Carreraestudiante[ id=" + id + " ]";
    }
    
}
