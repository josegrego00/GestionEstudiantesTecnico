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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author josepino
 */
@Entity
@Table(name = "inscrip_estudiante_materia")
@NamedQueries({
    @NamedQuery(name = "InscripEstudianteMateria.findAll", query = "SELECT i FROM InscripEstudianteMateria i"),
    @NamedQuery(name = "InscripEstudianteMateria.findById", query = "SELECT i FROM InscripEstudianteMateria i WHERE i.id = :id"),
    @NamedQuery(name = "InscripEstudianteMateria.findByEstado", query = "SELECT i FROM InscripEstudianteMateria i WHERE i.estado = :estado")})
public class InscripEstudianteMateria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "id_carrera", referencedColumnName = "id")
    @ManyToOne
    private Carrera idCarrera;
    @JoinColumn(name = "dni_estudiante", referencedColumnName = "dni_estudiante")
    @ManyToOne(optional = false)
    private Estudiante dniEstudiante;
    @JoinColumn(name = "id_materia", referencedColumnName = "id_materia")
    @ManyToOne(optional = false)
    private Materias idMateria;
    @JoinColumn(name = "id_semestre", referencedColumnName = "id_semestre")
    @ManyToOne
    private Semestre idSemestre;

    public InscripEstudianteMateria() {
    }

    public InscripEstudianteMateria(Integer id) {
        this.id = id;
    }

    public InscripEstudianteMateria(Integer id, String estado) {
        this.id = id;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Carrera getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Carrera idCarrera) {
        this.idCarrera = idCarrera;
    }

    public Estudiante getDniEstudiante() {
        return dniEstudiante;
    }

    public void setDniEstudiante(Estudiante dniEstudiante) {
        this.dniEstudiante = dniEstudiante;
    }

    public Materias getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Materias idMateria) {
        this.idMateria = idMateria;
    }

    public Semestre getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(Semestre idSemestre) {
        this.idSemestre = idSemestre;
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
        if (!(object instanceof InscripEstudianteMateria)) {
            return false;
        }
        InscripEstudianteMateria other = (InscripEstudianteMateria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logica.InscripEstudianteMateria[ id=" + id + " ]";
    }
    
}
