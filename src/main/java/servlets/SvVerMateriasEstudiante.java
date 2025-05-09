package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.ControladoraLogica;
import logica.InscripEstudianteMateria;
import logica.Materias;

@WebServlet(name = "SvVerMateriasEstudiante", urlPatterns = {"/SvVerMateriasEstudiante"})
public class SvVerMateriasEstudiante extends HttpServlet {

    ControladoraLogica controladora = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el DNI del estudiante desde el request
        String dniEstudiante = request.getParameter("dniEstudiante");

        if (dniEstudiante != null && !dniEstudiante.isEmpty()) {
            // Obtener las materias cursadas por el estudiante
            List<InscripEstudianteMateria> materiasCursadas = controladora.obtenerInscripEstudianteMateriaPorEstudiante(dniEstudiante);
                        
            // Obtener las materias que debe cursar hasta su semestre actual
            List<InscripEstudianteMateria> materiasPendientes = controladora.obtenerInscripEstudianteMateriaPorEstudiantePendiantes(dniEstudiante);

            // Enviar los datos al JSP
            request.setAttribute("materiasCursadas", materiasCursadas);
            request.setAttribute("materiasPendientes", materiasPendientes);
            request.setAttribute("dniEstudiante", dniEstudiante);

            // Redirigir a la vista JSP
            request.getRequestDispatcher("verMateriasEstudiante.jsp").forward(request, response);
        } else {
            // Si no se recibe un DNI válido, redirigir a la lista de estudiantes con un mensaje de error
            request.setAttribute("mensajeError", "No se encontró el estudiante.");
            request.getRequestDispatcher("listarEstudiantes.jsp").forward(request, response);
        }
    }
}
