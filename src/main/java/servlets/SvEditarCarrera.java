package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.Carrera;
import logica.ControladoraLogica;
import logica.Materias;
import logica.Semestre;

@WebServlet(name = "SvEditarCarrera", urlPatterns = {"/SvEditarCarrera"})
public class SvEditarCarrera extends HttpServlet {

    ControladoraLogica control = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idCarrera = Integer.parseInt(request.getParameter("idCarrera"));
        Carrera carrera = control.buscarCarrera(idCarrera); // Este método debe devolver la carrera con sus materias

        request.setAttribute("carrera", carrera);
        request.getRequestDispatcher("editarCarreras.jsp").forward(request, response);
    }
}
