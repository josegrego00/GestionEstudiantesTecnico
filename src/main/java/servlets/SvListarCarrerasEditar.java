package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.Carrera;
import logica.ControladoraLogica;
import logica.Materias;

/**
 *
 * @author josepino
 */
@WebServlet(name = "SvListarCarrerasEditar", urlPatterns = {"/SvListarCarrerasEditar"})
public class SvListarCarrerasEditar extends HttpServlet {

    ControladoraLogica controladoraLogica = new ControladoraLogica();

    // Método GET para cargar todas las carreras en el select
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Método doGet para obtener las carreras y mostrar la lista en el JSP

        // Obtener la lista de carreras desde la lógica de negocio
        List<Carrera> listaCarreras = controladoraLogica.listarCarreras();

        // Poner la lista de carreras en el request para enviarla al JSP
        request.setAttribute("listaCarreras", listaCarreras);

        // Redirigir a la vista que mostrará la lista de carreras
        request.getRequestDispatcher("editarCarreras.jsp").forward(request, response);
    }

}
