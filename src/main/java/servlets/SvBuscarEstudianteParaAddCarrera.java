/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet(name = "SvBuscarEstudianteParaAddCarrera", urlPatterns = {"/SvBuscarEstudianteParaAddCarrera"})
public class SvBuscarEstudianteParaAddCarrera extends HttpServlet {

    ControladoraLogica controladoraLogica = new ControladoraLogica();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String dni = request.getParameter("dni");

        Estudiante estudiante = controladoraLogica.buscarEstudiante(dni);

        if (estudiante.getActivo() != false) {
            if (estudiante != null) {
                // Obtener las carreras actuales del estudiante
                List<Carreraestudiante> carrerasEstudiante = controladoraLogica.obtenerCarrerasEstudiante(estudiante);

                if (carrerasEstudiante != null && !carrerasEstudiante.isEmpty()) {
                    // Obtener la lista completa de carreras disponibles
                    List<Carrera> listaCarreras = controladoraLogica.listarCarreras();

                    // Enviar datos al JSP
                    request.setAttribute("estudiante", estudiante);
                    request.setAttribute("carrerasEstudiante", carrerasEstudiante);
                    request.setAttribute("listaCarreras", listaCarreras);

                } else {
                    // Si el estudiante no tiene ninguna carrera
                    request.setAttribute("mensaje", "Este estudiante no está inscrito en ninguna carrera.");
                }

            } else {
                // Estudiante no encontrado
                request.setAttribute("mensaje", "Estudiante no encontrado.");
            }

        }else{
            request.setAttribute("mensaje", "El Estudiante esta Inactivo.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("addCarrera.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
