package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.ControladoraLogica;
import logica.Estudiante;
import logica.InscripEstudianteMateria;
import logica.Materias;
import logica.Semestre;

@WebServlet(name = "SvListarEstudiantesConMateriasPendientes", urlPatterns = {"/SvListarEstudiantesConMateriasPendientes"})
public class SvListarEstudiantesConMateriasPendientes extends HttpServlet {

    ControladoraLogica controladoraLogica = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String dni = request.getParameter("dniEstudiante");
        String idCarreraParam = request.getParameter("idCarrera");

        int idCarrera = -1;
        if (idCarreraParam != null && !idCarreraParam.isEmpty()) {
            try {
                idCarrera = Integer.parseInt(idCarreraParam);
            } catch (NumberFormatException e) {
                System.err.println("Error al parsear ID de carrera: " + e.getMessage());
            }
        }

        // Buscar estudiante
        Estudiante estudiante = controladoraLogica.buscarEstudiante(dni);
        if (estudiante == null) {
            request.setAttribute("error", "Estudiante no encontrado");
            request.getRequestDispatcher("verMateriasPendientesAprobadasEstudiante.jsp").forward(request, response);
            return;
        }

        // Obtener semestre actual del estudiante
        Semestre semestreActual = controladoraLogica.obtenerSemestreActualEstudiante(dni);
        request.setAttribute("semestreActual", semestreActual);

        // Obtener todas las materias inscritas
        List<InscripEstudianteMateria> todasCursadas = controladoraLogica.obtenerInscripEstudianteMateriaPorEstudiante(dni);
        List<InscripEstudianteMateria> todasPendientes = controladoraLogica.obtenerInscripEstudianteMateriaPorEstudiantePendiantes(dni);

        // Filtrar por carrera
        List<InscripEstudianteMateria> materiasCursadas = new ArrayList<>();
        List<InscripEstudianteMateria> materiasPendientes = new ArrayList<>();

        for (InscripEstudianteMateria insc : todasCursadas) {
            Materias materia = insc.getIdMateria();
            if (materia != null && materia.getIdCarrera().getId() == idCarrera) {
                materiasCursadas.add(insc);
            }
        }

        for (InscripEstudianteMateria insc : todasPendientes) {
            Materias materia = insc.getIdMateria();
            if (materia != null && materia.getIdCarrera().getId() == idCarrera) {
                materiasPendientes.add(insc);
            }
        }

        // Enviar atributos al JSP
        request.setAttribute("dniEstudiante", estudiante.getDniEstudiante());
        request.setAttribute("nombreEstudiante", estudiante.getNombreEstudiante());
        request.setAttribute("apellidoEstudiante", estudiante.getApellidoEstudiante());
        request.setAttribute("materiasCursadas", materiasCursadas);
        request.setAttribute("materiasPendientes", materiasPendientes);
        request.setAttribute("idCarrera", idCarrera);

        // Debug (opcional)
        System.out.println("DNI: " + dni + " | Carrera ID: " + idCarrera);
        System.out.println("Aprobadas: " + materiasCursadas.size() + " | Pendientes: " + materiasPendientes.size());

        // Redireccionar
        RequestDispatcher dispatcher = request.getRequestDispatcher("verMateriasPendientesAprobadasEstudiante.jsp");
        dispatcher.forward(request, response);
    }
}
