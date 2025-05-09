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
@WebServlet(name = "SvListarEstudiantes", urlPatterns = {"/SvListarEstudiantes"})
public class SvListarEstudiantes extends HttpServlet {

    ControladoraLogica controladoraLogica = new ControladoraLogica();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String carreraParam = request.getParameter("carrera");
        String semestreParam = request.getParameter("semestre");
        List<Estudiante> listaEstudiantes = new ArrayList<>();
        List<NivelCursoEstudiante> listaEstudiantesConSemestre = new ArrayList<>();
        List<Carreraestudiante> listaEstudiantesConCarrera = new ArrayList<>();

        listaEstudiantes = controladoraLogica.listarEstudiantes();
        listaEstudiantesConSemestre = controladoraLogica.listarTodosEstudianteConSemestre();
        listaEstudiantesConCarrera = controladoraLogica.listarEstudianteConCarreras();
        List<Carrera> listaCarreras = controladoraLogica.listarCarreras();
        if ((carreraParam != null && !carreraParam.isEmpty()) || (semestreParam != null && !semestreParam.isEmpty())) {
            List<NivelCursoEstudiante> listaFiltrada = new ArrayList<>();

            for (NivelCursoEstudiante estudiante : listaEstudiantesConSemestre) {
                boolean coincideCarrera = (carreraParam == null || carreraParam.isEmpty())
                        || estudiante.getIdCarrera().getNombrecarrera().equals(carreraParam);

                boolean coincideSemestre = (semestreParam == null || semestreParam.isEmpty())
                        || String.valueOf(estudiante.getIdSemestre().getIdSemestre()).equals(semestreParam);

                if (coincideCarrera && coincideSemestre) {
                    listaFiltrada.add(estudiante);
                }
            }

            listaEstudiantesConSemestre = listaFiltrada;
        }

        request.setAttribute("listaEstudiantes", listaEstudiantes);
        request.setAttribute("listaEstudiantesConSemestre", listaEstudiantesConSemestre);
        request.setAttribute("listaEstudiantesConCarrera", listaEstudiantesConCarrera);
        request.setAttribute("listaCarreras", listaCarreras);

        RequestDispatcher dispatcher = request.getRequestDispatcher("estudiantes.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
