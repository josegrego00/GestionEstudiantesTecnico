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
@Table(name = "estudiante")
@NamedQueries({
    @NamedQuery(name = "Estudiante.findAll", query = "SELECT e FROM Estudiante e"),
    @NamedQuery(name = "Estudiante.findByIdEstudiante", query = "SELECT e FROM Estudiante e WHERE e.idEstudiante = :idEstudiante"),
    @NamedQuery(name = "Estudiante.findByDniEstudiante", query = "SELECT e FROM Estudiante e WHERE e.dniEstudiante = :dniEstudiante"),
    @NamedQuery(name = "Estudiante.findByNombreEstudiante", query = "SELECT e FROM Estudiante e WHERE e.nombreEstudiante = :nombreEstudiante"),
    @NamedQuery(name = "Estudiante.findByApellidoEstudiante", query = "SELECT e FROM Estudiante e WHERE e.apellidoEstudiante = :apellidoEstudiante"),
    @NamedQuery(name = "Estudiante.findByTelefono", query = "SELECT e FROM Estudiante e WHERE e.telefono = :telefono"),
    @NamedQuery(name = "Estudiante.findByActivo", query = "SELECT e FROM Estudiante e WHERE e.activo = :activo")})
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estudiante")
    private Integer idEstudiante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "dni_estudiante")
    private String dniEstudiante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre_estudiante")
    private String nombreEstudiante;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "apellido_estudiante")
    private String apellidoEstudiante;
    @Size(max = 20)
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "activo")
    private Boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dniEstudiante")
    private List<NivelCursoEstudiante> nivelCursoEstudianteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dniEstudiante")
    private List<InscripEstudianteMateria> inscripEstudianteMateriaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idestudiante")
    private List<Carreraestudiante> carreraestudianteList;

    public Estudiante() {
    }

    public Estudiante(Integer idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Estudiante(Integer idEstudiante, String dniEstudiante, String nombreEstudiante, String apellidoEstudiante) {
        this.idEstudiante = idEstudiante;
        this.dniEstudiante = dniEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoEstudiante = apellidoEstudiante;
    }

    public Integer getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Integer idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getDniEstudiante() {
        return dniEstudiante;
    }

    public void setDniEstudiante(String dniEstudiante) {
        this.dniEstudiante = dniEstudiante;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getApellidoEstudiante() {
        return apellidoEstudiante;
    }

    public void setApellidoEstudiante(String apellidoEstudiante) {
        this.apellidoEstudiante = apellidoEstudiante;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    public List<Carreraestudiante> getCarreraestudianteList() {
        return carreraestudianteList;
    }

    public void setCarreraestudianteList(List<Carreraestudiante> carreraestudianteList) {
        this.carreraestudianteList = carreraestudianteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstudiante != null ? idEstudiante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudiante)) {
            return false;
        }
        Estudiante other = (Estudiante) object;
        if ((this.idEstudiante == null && other.idEstudiante != null) || (this.idEstudiante != null && !this.idEstudiante.equals(other.idEstudiante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logica.Estudiante[ idEstudiante=" + idEstudiante + " ]";
    }
    
}
