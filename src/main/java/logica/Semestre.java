/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author josepino
 */
@Entity
@Table(name = "semestre")
@NamedQueries({
    @NamedQuery(name = "Semestre.findAll", query = "SELECT s FROM Semestre s"),
    @NamedQuery(name = "Semestre.findByIdSemestre", query = "SELECT s FROM Semestre s WHERE s.idSemestre = :idSemestre"),
    @NamedQuery(name = "Semestre.findByNombreSemestre", query = "SELECT s FROM Semestre s WHERE s.nombreSemestre = :nombreSemestre")})
public class Semestre implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_semestre")
    private Integer idSemestre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre_semestre")
    private String nombreSemestre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSemestre")
    private List<NivelCursoEstudiante> nivelCursoEstudianteList;
    @OneToMany(mappedBy = "idSemestre")
    private List<InscripEstudianteMateria> inscripEstudianteMateriaList;

    public Semestre() {
    }

    public Semestre(Integer idSemestre) {
        this.idSemestre = idSemestre;
    }

    public Semestre(Integer idSemestre, String nombreSemestre) {
        this.idSemestre = idSemestre;
        this.nombreSemestre = nombreSemestre;
    }

    public Integer getIdSemestre() {
        return idSemestre;
    }

    public void setIdSemestre(Integer idSemestre) {
        this.idSemestre = idSemestre;
    }

    public String getNombreSemestre() {
        return nombreSemestre;
    }

    public void setNombreSemestre(String nombreSemestre) {
        this.nombreSemestre = nombreSemestre;
    }

    public List<NivelCursoEstudiante> getNivelCursoEstudianteList() {
        return nivelCursoEstudianteList;
    }

    public void setNivelCursoEstudianteList(List<NivelCursoEstudiante> nivelCursoEstudianteList) {
        this.nivelCursoEstudianteList = nivelCursoEstudianteList;
    }

    public List<InscripEstudianteMateria> getInscripEstudianteMateriaList() {
        return inscripEstudianteMateriaList;
    }

    public void setInscripEstudianteMateriaList(List<InscripEstudianteMateria> inscripEstudianteMateriaList) {
        this.inscripEstudianteMateriaList = inscripEstudianteMateriaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSemestre != null ? idSemestre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Semestre)) {
            return false;
        }
        Semestre other = (Semestre) object;
        if ((this.idSemestre == null && other.idSemestre != null) || (this.idSemestre != null && !this.idSemestre.equals(other.idSemestre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logica.Semestre[ idSemestre=" + idSemestre + " ]";
    }
    
}
