package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.ControladoraLogica;

/**
 * Servlet para eliminar una materia.
 */
@WebServlet(name = "SvEliminarMateria", urlPatterns = {"/SvEliminarMateria"})
public class SvEliminarMateria extends HttpServlet {

    ControladoraLogica controladoraLogica = new ControladoraLogica();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idMateria = Integer.parseInt(request.getParameter("id_materia"));

        // Intentar eliminar la materia
        try {
            controladoraLogica.eliminarMateria(idMateria);
            request.setAttribute("mensajeExito", "Materia eliminada correctamente.");
        } catch (Exception e) {
            request.setAttribute("mensajeError", "No se pudo eliminar la materia.");
        }
        response.sendRedirect("index.jsp");

    }
}
