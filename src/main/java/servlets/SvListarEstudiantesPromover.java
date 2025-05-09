/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.Carrera;
import logica.Carreraestudiante;
import logica.ControladoraLogica;
import logica.Estudiante;
import logica.NivelCursoEstudiante;

/**
 *
 * @author josepino
 */
@WebServlet(name = "SvListarEstudiantesPromover", urlPatterns = {"/SvListarEstudiantesPromover"})
public class SvListarEstudiantesPromover extends HttpServlet {

    ControladoraLogica controladoraLogica = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener la lista completa de estudiantes con semestre y carrera
        List<NivelCursoEstudiante> listaNivelCursoEstudiante = controladoraLogica.listarEstudianteConSemestre();
        List<Carrera> listaCarreras = controladoraLogica.listarCarreras();

        // Obtener filtros desde el request
        String semestreFiltro = request.getParameter("semestre");
        String carreraFiltro = request.getParameter("carrera");

        // Aplicar los filtros
        List<NivelCursoEstudiante> estudiantesFiltrados = new ArrayList<>();
        for (NivelCursoEstudiante nce : listaNivelCursoEstudiante) {
            boolean coincideSemestre = semestreFiltro == null || semestreFiltro.isEmpty()
                    || nce.getIdSemestre().getIdSemestre() == Integer.parseInt(semestreFiltro);

            boolean coincideCarrera = carreraFiltro == null || carreraFiltro.isEmpty()
                    || nce.getIdCarrera().getId() == Integer.parseInt(carreraFiltro);

            if (coincideSemestre && coincideCarrera) {
                estudiantesFiltrados.add(nce);
            }
        }

        // Enviar datos a la vista
        request.setAttribute("listaEstudiantesFiltrados", estudiantesFiltrados);
        request.setAttribute("listaCarreras", listaCarreras);
        request.setAttribute("carreraSeleccionada", carreraFiltro);
        request.setAttribute("semestreSeleccionado", semestreFiltro);
        String mensaje = request.getParameter("mensaje");
        if (mensaje != null && !mensaje.isEmpty()) {
            request.setAttribute("mensaje", mensaje);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("promoverLoteEstudiantes.jsp");
        dispatcher.forward(request, response);
    }

}
