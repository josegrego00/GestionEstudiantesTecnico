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
import logica.Carrera;

import logica.ControladoraLogica;
import logica.Estudiante;
import logica.NivelCursoEstudiante;
import logica.Carreraestudiante;

@WebServlet(name = "SvListarEstudiantesFiltro", urlPatterns = {"/SvListarEstudiantesFiltro"})
public class SvListarEstudiantesFiltro extends HttpServlet {

    ControladoraLogica controladoraLogica = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<NivelCursoEstudiante> listaEstudianteConSemestres = controladoraLogica.listarEstudianteConSemestre();
        List<Carrera> listaCarrerasEstudiante = controladoraLogica.listarCarreras();

        String semestreFiltro = request.getParameter("semestre");
        String carreraFiltro = request.getParameter("carrera");

        List<NivelCursoEstudiante> estudiantesFiltrados = new ArrayList<>();
        for (NivelCursoEstudiante nce : listaEstudianteConSemestres) {
            boolean coincideSemestre = semestreFiltro == null || semestreFiltro.isEmpty()
                    || nce.getIdSemestre().getIdSemestre() == Integer.parseInt(semestreFiltro);
            boolean coincideCarrera = carreraFiltro == null || carreraFiltro.isEmpty()
                    || nce.getIdCarrera().getNombrecarrera().equalsIgnoreCase(carreraFiltro);
            if (coincideSemestre && coincideCarrera) {
                estudiantesFiltrados.add(nce);
            }
        }

        request.setAttribute("listaFiltradaEstudiantes", estudiantesFiltrados);
        request.setAttribute("listaCarrerasEstudiante", listaCarrerasEstudiante);
        request.setAttribute("carreraSeleccionada", carreraFiltro);
        request.setAttribute("semestreSeleccionado", semestreFiltro);

        RequestDispatcher dispatcher = request.getRequestDispatcher("verMateriasEstudiante.jsp");
        dispatcher.forward(request, response);
}
}
