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
@Table(name = "carrera")
@NamedQueries({
    @NamedQuery(name = "Carrera.findAll", query = "SELECT c FROM Carrera c"),
    @NamedQuery(name = "Carrera.findById", query = "SELECT c FROM Carrera c WHERE c.id = :id"),
    @NamedQuery(name = "Carrera.findByNombrecarrera", query = "SELECT c FROM Carrera c WHERE c.nombrecarrera = :nombrecarrera"),
    @NamedQuery(name = "Carrera.findByDescripcion", query = "SELECT c FROM Carrera c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Carrera.findByCantidadSemestres", query = "SELECT c FROM Carrera c WHERE c.cantidadSemestres = :cantidadSemestres")})
public class Carrera implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombrecarrera")
    private String nombrecarrera;
    @Size(max = 500)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_semestres")
    private int cantidadSemestres;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCarrera")
    private List<NivelCursoEstudiante> nivelCursoEstudianteList;
    @OneToMany(mappedBy = "idCarrera")
    private List<InscripEstudianteMateria> inscripEstudianteMateriaList;
    @OneToMany(mappedBy = "idCarrera", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Materias> materiasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcarrera")
    private List<Carreraestudiante> carreraestudianteList;

    public Carrera() {
    }

    public Carrera(Integer id) {
        this.id = id;
    }

    public Carrera(Integer id, String nombrecarrera, int cantidadSemestres) {
        this.id = id;
        this.nombrecarrera = nombrecarrera;
        this.cantidadSemestres = cantidadSemestres;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombrecarrera() {
        return nombrecarrera;
    }

    public void setNombrecarrera(String nombrecarrera) {
        this.nombrecarrera = nombrecarrera;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidadSemestres() {
        return cantidadSemestres;
    }

    public void setCantidadSemestres(int cantidadSemestres) {
        this.cantidadSemestres = cantidadSemestres;
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

    public List<Materias> getMateriasList() {
        return materiasList;
    }

    public void setMateriasList(List<Materias> materiasList) {
        this.materiasList = materiasList;
    }

    public List<Carreraestudiante> getCarreraestudianteList() {
        return carreraestudianteList;
    }

    public void setCarreraestudianteList(List<Carreraestudiante> carreraestudianteList) {
        this.carreraestudianteList = carreraestudianteList;
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
        if (!(object instanceof Carrera)) {
            return false;
        }
        Carrera other = (Carrera) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logica.Carrera[ id=" + id + " ]";
    }

}
