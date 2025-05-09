package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.ControladoraLogica;
import logica.Materias;
import logica.Semestre;

/**
 * Servlet para buscar y mostrar información de una materia.
 */
@WebServlet(name = "SvBuscarMateria", urlPatterns = {"/SvBuscarMateria"})
public class SvBuscarMateria extends HttpServlet {
    
    ControladoraLogica controladoraLogica = new ControladoraLogica();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String materiaBuscar = request.getParameter("materia_buscar");
        Materias materiaEncontrada = null;
        
        if (materiaBuscar != null && !materiaBuscar.isEmpty()) {
            try {
                // Intentar buscar por ID si es numérico
                int idMateria = Integer.parseInt(materiaBuscar);
                materiaEncontrada = controladoraLogica.buscarMateriaPorId(idMateria);
            } catch (NumberFormatException e) {
                // Si no es un número, buscar por nombre
                System.out.println(materiaBuscar);
                materiaEncontrada = controladoraLogica.buscarMateriaPorNombre(materiaBuscar);
            }
        }
        
        if (materiaEncontrada != null) {
            request.setAttribute("materia", materiaEncontrada);
        } else {
            request.setAttribute("mensajeError", "Materia no encontrada.");
        }
        
        // Obtener la lista de semestres para el formulario
        List<Semestre> listaSemestres = controladoraLogica.listarSemestres();
        request.setAttribute("listaSemestres", listaSemestres);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("editarMateria.jsp");
        dispatcher.forward(request, response);
    }
}
