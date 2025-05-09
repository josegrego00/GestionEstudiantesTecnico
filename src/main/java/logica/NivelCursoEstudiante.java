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
@Table(name = "nivel_curso_estudiante")
@NamedQueries({
    @NamedQuery(name = "NivelCursoEstudiante.findAll", query = "SELECT n FROM NivelCursoEstudiante n"),
    @NamedQuery(name = "NivelCursoEstudiante.findById", query = "SELECT n FROM NivelCursoEstudiante n WHERE n.id = :id")})
public class NivelCursoEstudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_carrera", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Carrera idCarrera;
    @JoinColumn(name = "dni_estudiante", referencedColumnName = "dni_estudiante")
    @ManyToOne(optional = false)
    private Estudiante dniEstudiante;
    @JoinColumn(name = "id_semestre", referencedColumnName = "id_semestre")
    @ManyToOne(optional = false)
    private Semestre idSemestre;

    public NivelCursoEstudiante() {
    }

    public NivelCursoEstudiante(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof NivelCursoEstudiante)) {
            return false;
        }
        NivelCursoEstudiante other = (NivelCursoEstudiante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logica.NivelCursoEstudiante[ id=" + id + " ]";
    }
    
}
