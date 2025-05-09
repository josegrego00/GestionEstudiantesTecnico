package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.ControladoraLogica;
import logica.InscripEstudianteMateria;

@WebServlet(name = "SvActualizarEstadoMateria", urlPatterns = {"/SvActualizarEstadoMateria"})
public class SvActualizarEstadoMateria extends HttpServlet {
    ControladoraLogica controladoraLogica = new ControladoraLogica();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String dniEstudiante = request.getParameter("dniEstudiante");
        String idMateria = request.getParameter("idMateria");
        String nuevoEstado = request.getParameter("nuevoEstado");

        System.out.println("🔄 Cambiando estado de materia ID: " + idMateria + " a " + nuevoEstado);

        if (dniEstudiante != null && idMateria != null && nuevoEstado != null) {
            controladoraLogica.actualizarEstadoMateria(dniEstudiante, Integer.parseInt(idMateria), nuevoEstado);
        }

        // Redirigir a la misma página para ver los cambios
        response.sendRedirect("SvListarEstudiantesConMateriasPendientes?dniEstudiante=" + dniEstudiante + "&idCarrera=" + request.getParameter("idCarrera"));

    }
}
