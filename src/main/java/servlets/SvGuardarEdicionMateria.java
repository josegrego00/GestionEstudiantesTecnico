package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.ControladoraLogica;
import logica.Materias;

/**
 * Servlet para guardar la edición de una materia.
 */
@WebServlet(name = "SvGuardarEdicionMateria", urlPatterns = {"/SvGuardarEdicionMateria"})
public class SvGuardarEdicionMateria extends HttpServlet {
    
    ControladoraLogica controladoraLogica = new ControladoraLogica();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int idMateria = Integer.parseInt(request.getParameter("id_materia"));
        String nombreMateria = request.getParameter("nombre_materia");
        int idSemestre = Integer.parseInt(request.getParameter("id_semestre"));

        // Obtener la materia desde la base de datos
        Materias materia = controladoraLogica.buscarMateriaPorId(idMateria);
        
        if (materia != null) {
            // Actualizar los datos de la materia
            materia.setNombreMateria(nombreMateria);
//-------------------             materia.setIdSemestrePertenece(idSemestre);

            // Guardar cambios
            controladoraLogica.actualizarMateria(materia);

            request.setAttribute("materia", materia);
            request.setAttribute("mensajeExito", "Materia actualizada correctamente.");
        } else {
            request.setAttribute("mensajeError", "Error al actualizar la materia.");
        }

        response.sendRedirect("index.jsp");
    }
}
