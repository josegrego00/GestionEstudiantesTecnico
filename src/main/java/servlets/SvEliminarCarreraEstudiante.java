/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.Carrera;
import logica.Carreraestudiante;
import logica.ControladoraLogica;
import logica.Estudiante;

/**
 *
 * @author josepino
 */
@WebServlet(name = "SvEliminarCarreraEstudiante", urlPatterns = {"/SvEliminarCarreraEstudiante"})
public class SvEliminarCarreraEstudiante extends HttpServlet {

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
        String idCarreraStr = request.getParameter("idCarreraE");

        if (dni != null && idCarreraStr != null) {
            try {
                int idCarrera = Integer.parseInt(idCarreraStr);
                // Primero obtener todas las carreras del estudiante
                List<Carreraestudiante> carrerasEstudiante = controladoraLogica.listarCarrerasDeEstudiante(dni);
                
                if(carrerasEstudiante.size()<=1){
                request.setAttribute("mensaje", "⚠️ No se Puede Eliminar la carrera, Debe al menos tener una carrera a estudiar");
                }else{
                
                controladoraLogica.eliminarCarreraDeEstudiante(dni, idCarrera);
                request.setAttribute("mensaje", "✅ Carrera y materias eliminadas correctamente.");
                }

            } catch (NumberFormatException e) {
                request.setAttribute("mensaje", "⚠️ ID de carrera inválido.");
            } catch (Exception e) {
                request.setAttribute("mensaje", "❌ Error al eliminar carrera: " + e.getMessage());
            }
        } else {
            request.setAttribute("mensaje", "⚠️ Datos incompletos para eliminar.");
        }

        // Redirigir nuevamente al servlet que muestra la info del estudiante
        request.getRequestDispatcher("/SvBuscarEstudianteParaAddCarrera").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
