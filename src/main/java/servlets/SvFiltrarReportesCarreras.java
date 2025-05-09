package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logica.Carrera;
import logica.ControladoraLogica;
import logica.InscripEstudianteMateria;
import logica.Materias;
import logica.Semestre;

@WebServlet(name = "SvFiltrarReportesCarreras", urlPatterns = {"/SvFiltrarReportesCarreras"})
public class SvFiltrarReportesCarreras extends HttpServlet {

    ControladoraLogica control = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener todas las carreras
        List<Carrera> listaCarreras = control.listarCarreras();
        request.setAttribute("listaCarreras", listaCarreras);

        // Si se seleccionó una carrera, buscar materias
        String idCarreraStr = request.getParameter("carrera");
        if (idCarreraStr != null && !idCarreraStr.isEmpty()) {
            int idCarrera = Integer.parseInt(idCarreraStr);
            List<Materias> listaMaterias = control.listarMateriasPorId(idCarrera);
            request.setAttribute("listaMaterias", listaMaterias);
        }

        request.getRequestDispatcher("FiltroReportes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener parámetros del formulario
        String carreraSeleccionada = request.getParameter("carrera");
        String materiaSeleccionadaParam = request.getParameter("materia");
        Integer materiaSeleccionada = null;
        String estadoMateria = request.getParameter("estado");
        String idSemestreParam = request.getParameter("semestre");
        Integer idSemestre = null;

        // Parsear semestre
        if (idSemestreParam != null && !idSemestreParam.isEmpty()) {
            idSemestre = Integer.parseInt(idSemestreParam);
        }

        // Cargar carreras siempre
        List<Carrera> listaCarreras = control.listarCarreras();
        request.setAttribute("listaCarreras", listaCarreras);

        // Cargar materias si se seleccionó carrera
        if (carreraSeleccionada != null && !carreraSeleccionada.isEmpty()) {
            int idCarrera = Integer.parseInt(carreraSeleccionada);
            List<Materias> listaMaterias = control.listarMateriasPorId(idCarrera);
            request.setAttribute("listaMaterias", listaMaterias);
        }

        // Parsear materiaSeleccionada correctamente
        if (materiaSeleccionadaParam != null && !materiaSeleccionadaParam.isEmpty()) {
            materiaSeleccionada = Integer.parseInt(materiaSeleccionadaParam);
        }

        // Convertir carreraSeleccionada a Integer
        Integer idCarrera = null;
        if (carreraSeleccionada != null && !carreraSeleccionada.isEmpty()) {
            idCarrera = Integer.parseInt(carreraSeleccionada);
        }

        // Filtrar inscripciones (materia puede ser null y tu método lo maneja)
        List<InscripEstudianteMateria> listaInscripciones = control.filtrarInscripciones(materiaSeleccionada, estadoMateria, idCarrera, idSemestre);
        // Filtrar solo estudiantes activos y que no estén graduados (semestre != 4)
        listaInscripciones.removeIf(inscripcion
                -> inscripcion.getDniEstudiante().getActivo() == false
                || inscripcion.getIdSemestre().getIdSemestre() == 4
        );

        // Setear atributos para que el JSP conserve los valores seleccionados
        request.setAttribute("listaInscripciones", listaInscripciones);
        request.setAttribute("carreraSeleccionada", carreraSeleccionada);
        request.setAttribute("materiaSeleccionada", materiaSeleccionada);
        request.setAttribute("estadoSeleccionado", estadoMateria);
        
        request.setAttribute("semestreSeleccionado", idSemestre);
        System.out.println("este es el semestre"+idSemestreParam);

        // Redirigir al JSP
        request.getRequestDispatcher("FiltroReportes.jsp").forward(request, response);
    }

}
