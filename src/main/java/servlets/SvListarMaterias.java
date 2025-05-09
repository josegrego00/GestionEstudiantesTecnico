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
import logica.Materias;

@WebServlet(name = "SvListarMaterias", urlPatterns = {"/SvListarMaterias"})
public class SvListarMaterias extends HttpServlet {

    ControladoraLogica controladoraLogica = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Materias> listaMaterias = controladoraLogica.listarMaterias();
        
        String filtroSemestre = request.getParameter("semestre");
        if (filtroSemestre != null && !filtroSemestre.isEmpty()) {
            int semestreSeleccionado = Integer.parseInt(filtroSemestre);
            List<Materias> materiasFiltradas = new ArrayList<>();

            for (Materias materia : listaMaterias) {
                // Acceder al atributo semestre de la materia
              /*  if (materia.getIdSemestrePertenece() == semestreSeleccionado) {
                    materiasFiltradas.add(materia);
                }*/
            }
            request.setAttribute("listaMaterias", materiasFiltradas);
        } else {
            request.setAttribute("listaMaterias", listaMaterias);
        }
        request.setAttribute("semestreSeleccionado", filtroSemestre);

        RequestDispatcher dispatcher = request.getRequestDispatcher("verMaterias.jsp");

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Se puede dejar vacío o agregar lógica si se necesita
    }

    @Override
    public String getServletInfo() {
        return "Servlet para listar materias";
    }
}
