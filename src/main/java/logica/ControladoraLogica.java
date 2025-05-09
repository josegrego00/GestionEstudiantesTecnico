/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import persistencia.CarreraestudianteJpaController;
import persistencia.ControladoraPersistencia;

public class ControladoraLogica {

    ControladoraPersistencia controladoraPersistencia = new ControladoraPersistencia();

    public ControladoraLogica() {

    }

    public void crearEstudiante(String dni, String nombre, String apellido, String telefono, Integer semestre, Carrera carrera, boolean activo) {
        // Crear instancia del estudiante y asignar valores
        // Validar que el DNI no sea nulo o vacío
        if (dni == null || dni.trim().isEmpty()) {
            System.out.println("⚠ Error: El DNI del estudiante no puede estar vacío.");
            return;
        }

        // Verificar si el estudiante ya existe en la base de datos
        Estudiante estudianteExistente = buscarEstudiante(dni);
        if (estudianteExistente != null) {
            System.out.println("⚠ Error: El estudiante con DNI " + dni + " ya existe en la base de datos.");
            return; // Evita la inserción duplicada
        }
        if (semestre == null) {
            semestre = 1;
        }

        try {
            EntityManager em = controladoraPersistencia.getEntityManager();
            em.getTransaction().begin();

            StoredProcedureQuery sp = em.createStoredProcedureQuery("crear_estudiante_con_carrera");
            sp.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter(5, Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter(6, Boolean.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter(7, Integer.class, ParameterMode.IN);

            sp.setParameter(1, dni);
            sp.setParameter(2, nombre);
            sp.setParameter(3, apellido);
            sp.setParameter(4, telefono);
            sp.setParameter(5, semestre);
            sp.setParameter(6, activo);
            sp.setParameter(7, carrera.getId());
            System.out.println("Carrera seleccionada: " + carrera.getId());

            sp.execute();

            em.getTransaction().commit();
            em.close();

            System.out.println("✅ Estudiante insertado con procedure.");
        } catch (Exception e) {
            System.out.println("❌ Error al llamar el procedure: " + e.getMessage());
        }
    }

    public List<Estudiante> listarEstudiantes() {
        return controladoraPersistencia.listarEstudiantes();
    }

    public Estudiante buscarEstudiante(String dni) {

        List<Estudiante> listaEstudiantes = listarEstudiantes();
        for (Estudiante estudiante : listaEstudiantes) {
            if (estudiante.getDniEstudiante().equalsIgnoreCase(dni)) {
                return estudiante;
            }
        }
        return null;
    }

    public void actualizarEstudiante(String dni, String nombre, String apellido, String telefono, boolean activo) {
        Estudiante estudiante = buscarEstudiante(dni);
        if (estudiante != null) {
            estudiante.setNombreEstudiante(nombre);
            estudiante.setApellidoEstudiante(apellido);
            estudiante.setTelefono(telefono);
            estudiante.setActivo(activo);
            controladoraPersistencia.actualizarEstudiante(estudiante);
        }

    }

    public void cargarMateriasYSemestre(Estudiante estudiante, Carrera carrera, Semestre semestre) {

        NivelCursoEstudiante nivelCursoEstudiante = new NivelCursoEstudiante();

        nivelCursoEstudiante.setDniEstudiante(estudiante);
        nivelCursoEstudiante.setIdSemestre(semestre);
        controladoraPersistencia.cargarSemestreInicial(nivelCursoEstudiante);

        //obtengo las materias del semestre
        List<Materias> materiasLista = listarMaterias();

        for (Materias materiaACargar : materiasLista) {

            if (materiaACargar.getIdCarrera().getId().equals(carrera.getId())) {

                InscripEstudianteMateria inscripEstudianteMateria = new InscripEstudianteMateria();
                inscripEstudianteMateria.setDniEstudiante(estudiante);
                inscripEstudianteMateria.setIdMateria(materiaACargar);
                inscripEstudianteMateria.setEstado("Por Cursar");

                controladoraPersistencia.cargarMateriasEstudiante(inscripEstudianteMateria);
            }

        }

    }

    public Semestre buscarSemestre(int semestre) {
        return controladoraPersistencia.buscarSemestre(semestre);
    }

    public List<NivelCursoEstudiante> listarEstudianteConSemestre() {
        List<NivelCursoEstudiante> listaFiltrado = new ArrayList<>();
        List<NivelCursoEstudiante> listaCompleta = controladoraPersistencia.listarEstudianteConSemestre();
        for (NivelCursoEstudiante estudiante : listaCompleta) {

            if (estudiante.getIdSemestre().getIdSemestre() != 4 && estudiante.getDniEstudiante().getActivo() == true) {
                listaFiltrado.add(estudiante);
            }
        }
        return listaFiltrado;
    }

    public List<Materias> listarMaterias() {
        return controladoraPersistencia.listarMaterias();
    }

    public List<Semestre> listarSemestres() {
        return controladoraPersistencia.listarSemestre();
    }

    public Materias buscarMateriaPorId(int idMateria) {
        return controladoraPersistencia.buscarMateriaPorId(idMateria);
    }

    public Materias buscarMateriaPorNombre(String materiaBuscar) {
        List<Materias> listarDeMaterias = listarMaterias();
        for (Materias materia : listarDeMaterias) {
            if (materia.getNombreMateria().equalsIgnoreCase(materiaBuscar)) {
                return materia;
            }
        }
        return null;
    }

    public void actualizarMateria(Materias materia) {
        controladoraPersistencia.actualizarMateria(materia);
    }

    public void eliminarMateria(int idMateria) {
        controladoraPersistencia.eliminarMateria(idMateria);
    }

    public void crearMateria(String nombreMateria, Carrera idcarrera) {
        Materias materia = new Materias();
        materia.setNombreMateria(nombreMateria);
        materia.setIdCarrera(idcarrera);
        //   materia.setIdSemestrePertenece(idSemestre);
        controladoraPersistencia.crearMateria(materia);
    }  
    
    public void crearMateria(String nombreMateria) {
        Materias materia = new Materias();
        materia.setNombreMateria(nombreMateria);
        //   materia.setIdSemestrePertenece(idSemestre);
        controladoraPersistencia.crearMateria(materia);
    }

    public List<InscripEstudianteMateria> obtenerInscripEstudianteMateria() {
        return controladoraPersistencia.obtenerInscripEstudianteMateria();
    }

    public List<InscripEstudianteMateria> obtenerInscripEstudianteMateriaPorEstudiante(String dniEstudiante) {
        List<InscripEstudianteMateria> listaEstudianteMateriasAprovadas = obtenerInscripEstudianteMateria();
        List<InscripEstudianteMateria> listaMateriasEstudianteAprovadas = new ArrayList<>();

        for (InscripEstudianteMateria ins : listaEstudianteMateriasAprovadas) {
            if (ins.getDniEstudiante() != null
                    && ins.getDniEstudiante().getDniEstudiante() != null
                    && ins.getDniEstudiante().getDniEstudiante().equalsIgnoreCase(dniEstudiante)) {
                if (ins.getEstado() != null && ins.getEstado().equalsIgnoreCase("aprobado")) {
                    listaMateriasEstudianteAprovadas.add(ins);
                }
            }
        }
        return listaMateriasEstudianteAprovadas;
    }

    public List<InscripEstudianteMateria> obtenerInscripEstudianteMateriaPorEstudiantePendiantes(String dniEstudiante) {
        List<InscripEstudianteMateria> listaEstudianteMateriasPorCursar = obtenerInscripEstudianteMateria();
        List<InscripEstudianteMateria> listaMateriasEstudiantePorCursar = new ArrayList<>();
        for (InscripEstudianteMateria ins : listaEstudianteMateriasPorCursar) {
            if (ins.getDniEstudiante().getDniEstudiante().equalsIgnoreCase(dniEstudiante)) {
                if (ins.getEstado().equalsIgnoreCase("por cursar")) {
                    listaMateriasEstudiantePorCursar.add(ins);
                }
            }
        }
        return listaMateriasEstudiantePorCursar;
    }

    public InscripEstudianteMateria obtenerInscripEstudianteMateriaPorEstudianteYMateria(String dniEstudiante, int idMateria) {
        List<InscripEstudianteMateria> listaInscripciones = obtenerInscripEstudianteMateria();
        for (InscripEstudianteMateria ins : listaInscripciones) {
            if (ins.getDniEstudiante().getDniEstudiante().equalsIgnoreCase(dniEstudiante)
                    && ins.getIdMateria().getIdMateria() == idMateria) {
                return ins;
            }
        }
        return null;
    }

    public void actualizarInscripEstudianteMateria(InscripEstudianteMateria inscripcion) {
        controladoraPersistencia.actualizarInscripEstudianteMateria(inscripcion);
    }

    public void actualizarEstadoMateria(String dniEstudiante, int idMateria, String nuevoEstado) {
        InscripEstudianteMateria inscripcion = obtenerInscripcionPorEstudianteYMateria(dniEstudiante, idMateria);

        if (inscripcion != null) {
            inscripcion.setEstado(nuevoEstado);
            actualizarInscripcion(inscripcion); // Método que guarda los cambios en la base de datos
            System.out.println("✅ Estado actualizado correctamente.");
        } else {
            System.out.println("⚠️ No se encontró la inscripción para actualizar.");
        }
    }

    public InscripEstudianteMateria obtenerInscripcionPorEstudianteYMateria(String dniEstudiante, int idMateria) {
        List<InscripEstudianteMateria> listaInscripciones = obtenerInscripEstudianteMateria();
        for (InscripEstudianteMateria ins : listaInscripciones) {
            if (ins.getDniEstudiante().getDniEstudiante().equalsIgnoreCase(dniEstudiante)
                    && ins.getIdMateria().getIdMateria() == idMateria) {
                return ins;
            }
        }
        return null;
    }

    public void actualizarInscripcion(InscripEstudianteMateria inscripcion) {
        controladoraPersistencia.actualizarInscripEstudianteMateria(inscripcion);
    }

    public List<Carrera> listarCarreras() {
        return controladoraPersistencia.listarCarreraImtel();
    }

    /**
     *
     * @param idCarrera
     * @return
     */
    public Carrera buscarCarrera(int idCarrera) {
        return controladoraPersistencia.buscarCarrera(idCarrera);
    }

    public void asignarCarreraAEstudiante(String dni, int carrera) {
        try {
            EntityManager em = controladoraPersistencia.getEntityManager();
            em.getTransaction().begin();

            StoredProcedureQuery sp = em.createStoredProcedureQuery("asignar_carrera_a_estudiante");
            sp.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);

            sp.setParameter(1, dni);
            sp.setParameter(2, carrera);

            sp.execute();
            em.getTransaction().commit();
            em.close();

            System.out.println("✅ Carrera y materias asignadas correctamente.");
        } catch (Exception e) {
            System.out.println("❌ Error al asignar carrera: " + e.getMessage());
        }
    }

    public List<Carreraestudiante> listarEstudianteConCarreras() {
        return controladoraPersistencia.listarEstudianteConCarreras();
    }

    public List<Carreraestudiante> obtenerCarrerasEstudiante(Estudiante estudiante) {
        List<Carreraestudiante> listaCursandoEstudiante = new ArrayList<>();
        List<Carreraestudiante> listaCarrera = controladoraPersistencia.obtenerCarrerasEstudiante();
        for (Carreraestudiante carreraestudiante : listaCarrera) {
            if (carreraestudiante.getIdestudiante().getDniEstudiante().equals(estudiante.getDniEstudiante())) {
                listaCursandoEstudiante.add(carreraestudiante);
            }
        }
        return listaCursandoEstudiante;
    }

    public void guardarCarreraEstudiante(Carreraestudiante addCarreraEstudiante) {
        controladoraPersistencia.guardarCarreraEstudiante(addCarreraEstudiante);
    }

    public void eliminarCarreraDeEstudiante(String dni, int carrera) {
        try {
            EntityManager em = controladoraPersistencia.getEntityManager();
            em.getTransaction().begin();

            StoredProcedureQuery sp = em.createStoredProcedureQuery("remover_carrera_de_estudiante");
            sp.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);

            sp.setParameter(1, dni);
            sp.setParameter(2, carrera);

            sp.execute();
            em.getTransaction().commit();
            em.close();

            System.out.println("✅ Carrera y materias eliminadas correctamente.");
        } catch (Exception e) {
            System.out.println("❌ Error al eliminar carrera y materias: " + e.getMessage());
        }
    }

    public Carrera buscarCarreraAbre(String abrevCarrera) {
        List<Carrera> listaCarreraAbre = listarCarreras();
        for (Carrera carrera : listaCarreraAbre) {
            if (carrera.getDescripcion().equalsIgnoreCase(abrevCarrera)) {
                return carrera;
            }
        }
        return null;
    }

    public String promoverEstudiante(String dni, int carreraId, int semestreActual) {
        try {
            // 1. Validación básica de parámetros
            if (dni == null || dni.trim().isEmpty()) {
                throw new IllegalArgumentException("DNI del estudiante no puede estar vacío");
            }

            // 2. Obtener información de la carrera
            Carrera carrera = controladoraPersistencia.buscarCarrera(carreraId);
            if (carrera == null) {
                throw new IllegalStateException("No se encontró la carrera con ID: " + carreraId);
            }

            int totalSemestres = carrera.getCantidadSemestres();
            String nombreCarrera = carrera.getNombrecarrera();

            // 3. Validar semestre actual
            if (semestreActual < 1 || semestreActual > totalSemestres) {
                throw new IllegalStateException(String.format(
                        "Semestre actual (%d) no válido para carrera %s (%d semestres)",
                        semestreActual, nombreCarrera, totalSemestres));
            }

            // 4. Buscar registro académico del estudiante
            NivelCursoEstudiante nivel = buscarSemestrePorDniEstudiante(dni, carreraId);
            if (nivel == null) {
                throw new IllegalStateException("No se encontró registro académico para el estudiante");
            }

            // 5. Proceso de graduación (semestre 4)
            if (semestreActual == totalSemestres) {
                if (!validarMateriasAprobadas(dni, carreraId)) {
                    System.out.printf("[%s] Estudiante %s no aprobó todas las materias.%n",
                            nombreCarrera, dni);
                    return "no_aprobado";
                }

                // Configurar semestre 4 (graduación)
                Semestre semestreGraduacion = controladoraPersistencia.buscarSemestre(4);
                if (semestreGraduacion == null) {
                    throw new IllegalStateException("No se encontró el semestre de graduación (4)");
                }

                nivel.setIdSemestre(semestreGraduacion);
                controladoraPersistencia.actualizarNivelEstudiante(nivel);

                // Actualizar inscripciones al semestre 4
                actualizarInscripcionesSemestre(dni, semestreGraduacion);

                System.out.printf("[%s] ¡Estudiante %s graduado exitosamente!%n",
                        nombreCarrera, dni);
                return "graduado";
            } // 6. Proceso de promoción normal
            else {
                int siguienteSemestre = semestreActual + 1;
                Semestre nuevoSemestre = controladoraPersistencia.buscarSemestre(siguienteSemestre);

                if (nuevoSemestre == null) {
                    throw new IllegalStateException("No se encontró el semestre " + siguienteSemestre);
                }

                nivel.setIdSemestre(nuevoSemestre);
                controladoraPersistencia.actualizarNivelEstudiante(nivel);
                actualizarInscripcionesSemestre(dni, nuevoSemestre);

                System.out.printf("[%s] Estudiante %s promovido al semestre %d%n",
                        nombreCarrera, dni, siguienteSemestre);
                return "promovido";
            }
        } catch (Exception e) {
            System.err.println("Error en promoverEstudiante: " + e.getMessage());
            return "error";
        }
    }

    private void actualizarInscripcionesSemestre(String dni, Semestre nuevoSemestre) {
        List<InscripEstudianteMateria> inscripciones = controladoraPersistencia.buscarTodasLasInscripcionesEstudiantes();
        if (inscripciones != null && !inscripciones.isEmpty()) {
            for (InscripEstudianteMateria inscripcion : inscripciones) {
                inscripcion.setIdSemestre(nuevoSemestre);
                controladoraPersistencia.actualizarInscripcion(inscripcion);
            }
        }
    }

    public Semestre obtenerSemestreActualEstudiante(String dniEstudiante) {
        // Esto suponiendo que tenés una lista de NivelCursoEstudiante por estudiante
        NivelCursoEstudiante nivel = buscarSemestrePorDniEstudiante(dniEstudiante);
        return nivel != null ? nivel.getIdSemestre() : null;
    }

    private NivelCursoEstudiante buscarSemestrePorDniEstudiante(String dniEstudiante) {
        List<NivelCursoEstudiante> listar = controladoraPersistencia.listarEstudianteConSemestre();
        for (NivelCursoEstudiante nivel : listar) {
            if (nivel.getDniEstudiante().getDniEstudiante().equals(dniEstudiante)) {
                return nivel;
            }
        }
        return null;
    }

    public List<Materias> listarMateriasPorId(int idCarrera) {
        List<Materias> lista = listarMaterias();
        List<Materias> listaPorCarrera = new ArrayList<>();
        for (Materias materiaCarrera : lista) {
            if (materiaCarrera.getIdCarrera().getId().equals(idCarrera)) {
                listaPorCarrera.add(materiaCarrera);
            }
        }
        return listaPorCarrera;
    }

    public List<InscripEstudianteMateria> filtrarInscripciones(Integer materiaSeleccionada, String estadoMateria, Integer idCarrera, Integer idSemestre) {

        List<InscripEstudianteMateria> listaFiltrada = new ArrayList<>();
        List<InscripEstudianteMateria> listaCompleta = obtenerInscripEstudianteMateria();

        for (InscripEstudianteMateria ins : listaCompleta) {
            boolean cumpleFiltro = true;

            // Filtrar por materia seleccionada si se proporciona
            if (materiaSeleccionada != null && !ins.getIdMateria().getIdMateria().equals(materiaSeleccionada)) {
                cumpleFiltro = false;
            }

            // Filtrar por estado de la materia si se proporciona
            if (estadoMateria != null && !ins.getEstado().equalsIgnoreCase(estadoMateria)) {
                cumpleFiltro = false;
            }

            // Filtrar por carrera seleccionada si se proporciona
            if (idCarrera != null && ins.getIdCarrera() != null && !ins.getIdCarrera().getId().equals(idCarrera)) {
                cumpleFiltro = false;
            }

            // ✅ Filtrar por semestre si se proporciona
            if (idSemestre != null && ins.getIdSemestre() != null && !ins.getIdSemestre().getIdSemestre().equals(idSemestre)) {
                cumpleFiltro = false;
            }

            // Si cumple con todos los filtros, lo agregamos a la lista
            if (cumpleFiltro) {
                listaFiltrada.add(ins);
            }
        }

        return listaFiltrada;
    }

    private boolean validarMateriasAprobadas(String dni, int carreraId) {
        List<InscripEstudianteMateria> inscripciones = controladoraPersistencia.obtenerInscripEstudianteMateria();
        List<InscripEstudianteMateria> materiasCarrera = new ArrayList<>();

        // Filtrar todas las materias inscritas para ese estudiante y carrera
        for (InscripEstudianteMateria insc : inscripciones) {
            if (insc.getDniEstudiante().getDniEstudiante().equals(dni)
                    && insc.getIdCarrera().getId() == carreraId) {
                materiasCarrera.add(insc);
            }
        }

        if (materiasCarrera.isEmpty()) {
            System.out.println("⚠️ El estudiante no tiene materias registradas en la carrera.");
            return false;
        }

        // Verificar que todas estén aprobadas
        for (InscripEstudianteMateria insc : materiasCarrera) {
            if (!"Aprobado".equalsIgnoreCase(insc.getEstado())) {
                return false;
            }
        }

        return true;
    }

    public List<Carreraestudiante> listarCarrerasDeEstudiante(String dni) {

        List<Carreraestudiante> listaCarrerasPorEstudiante = new ArrayList<>();

        for (Carreraestudiante carreras : controladoraPersistencia.listarEstudianteConCarreras()) {
            if (carreras.getIdestudiante().getDniEstudiante().equals(dni)) {
                listaCarrerasPorEstudiante.add(carreras);
            }
        }
        return listaCarrerasPorEstudiante;
    }

    private List<InscripEstudianteMateria> buscarInscripcionesPorDni(String dni) {
        List<InscripEstudianteMateria> listaTodosEstudiantes = controladoraPersistencia.buscarTodasLasInscripcionesEstudiantes();
        List<InscripEstudianteMateria> listaUnEstudiante = new ArrayList<>();
        for (InscripEstudianteMateria iem : listaTodosEstudiantes) {
            if (iem.getDniEstudiante().getDniEstudiante().equals(dni)) {
                listaUnEstudiante.add(iem);
            }
        }
        return listaUnEstudiante;
    }

    public List<NivelCursoEstudiante> listarTodosEstudianteConSemestre() {
        return controladoraPersistencia.listarEstudianteConSemestre();
    }

    public void crearCarreraImtel(String nombreCarrera, String abreviatura, int semestreDeCarrera, String[] listaMaterias) throws Exception {

        // Validación 1: Nombre no vacío
        if (nombreCarrera == null || nombreCarrera.trim().isEmpty()) {
            throw new Exception("El nombre de la carrera no puede estar vacío");
        }

        // Validación 2: Verificar si la carrera ya existe
        Carrera carreraExistente = controladoraPersistencia.buscarCarreraPorNombre(nombreCarrera);
        if (carreraExistente != null) {
            throw new Exception("Ya existe una carrera con el nombre: " + nombreCarrera);
        }

        // Validación 3: Cantidad mínima de materias
        if (listaMaterias == null || listaMaterias.length < 5) {
            throw new Exception("Debe haber al menos 5 materias para crear la carrera");
        }

        // Validación 4: Semestres válidos (1-3)
        if (semestreDeCarrera < 1 || semestreDeCarrera > 3) {
            throw new Exception("La cantidad de semestres debe estar entre 1 y 3");
        }

        // Validación 5: Materias no vacías
        for (String nombreMateria : listaMaterias) {
            if (nombreMateria == null || nombreMateria.trim().isEmpty()) {
                throw new Exception("El nombre de una materia no puede estar vacío");
            }
        }
        // 1. Crear la carrera
        Carrera nuevaCarrera = new Carrera();
        nuevaCarrera.setNombrecarrera(nombreCarrera);
        nuevaCarrera.setDescripcion(abreviatura);
        nuevaCarrera.setCantidadSemestres(semestreDeCarrera);
        // 3. Persistir la carrera (esto generará el ID)
        controladoraPersistencia.crearCarrera(nuevaCarrera);

        // 4. Crear y asociar las materias
        for (String nombreMateria : listaMaterias) {
            if (nombreMateria != null && !nombreMateria.trim().isEmpty()) {
                Materias nuevaMateria = new Materias();
                nuevaMateria.setNombreMateria(nombreMateria);
                nuevaMateria.setIdCarrera(nuevaCarrera);

                controladoraPersistencia.crearMateria(nuevaMateria);
            }

        }
    }

    private NivelCursoEstudiante buscarSemestrePorDniEstudiante(String dni, int carreraId) {
        List<NivelCursoEstudiante> listar = controladoraPersistencia.listarEstudianteConSemestre();
        for (NivelCursoEstudiante nivel : listar) {
            if (nivel.getDniEstudiante().getDniEstudiante().equals(dni) && nivel.getIdCarrera().getId() == carreraId) {
                return nivel;
            }
        }
        return null;
    }

    public void actualizarCarrera(Carrera carreraModificada) {
    try {
        Carrera carreraExistente = buscarCarrera(carreraModificada.getId());
        if (carreraExistente != null) {
            carreraExistente.setNombrecarrera(carreraModificada.getNombrecarrera());
            carreraExistente.setDescripcion(carreraModificada.getDescripcion());
            carreraExistente.setCantidadSemestres(carreraModificada.getCantidadSemestres());
            
            controladoraPersistencia.actualizarCarrera(carreraExistente);
            System.out.println("Datos simples de la carrera actualizados correctamente.");
        } else {
            System.out.println("Carrera no encontrada.");
        }
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error al actualizar la carrera: " + e.getMessage());
    }
}
    public List<Usuario> listarUsuarios(){
    return controladoraPersistencia.listarUsuarios();
    }
    

    public Usuario validarUsuario(String nombreUsuario, String password) {
        List<Usuario> listaUsuarios=listarUsuarios();
        for(Usuario user:listaUsuarios){
            if(user.getNombreUsuario().equals(nombreUsuario)&& user.getContrasenna().equals(password)){
            return user;
            }
        }
        return null;
    }



}
