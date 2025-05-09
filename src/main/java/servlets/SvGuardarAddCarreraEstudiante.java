/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "SvGuardarAddCarreraEstudiante", urlPatterns = {"/SvGuardarAddCarreraEstudiante"})
public class SvGuardarAddCarreraEstudiante extends HttpServlet {

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
        String idCarreraStr = request.getParameter("idCarrera");

        if (dni != null && idCarreraStr != null) {
            try {
                int idCarrera = Integer.parseInt(idCarreraStr);
                
                
                    controladoraLogica.asignarCarreraAEstudiante(dni, idCarrera);
                    // Guardar mensaje en request
                    request.setAttribute("mensaje", "✅ Se ha agregado una nueva carrera correctamente.");

            } catch (NumberFormatException e) {
                request.setAttribute("mensaje", "⚠️ ID de carrera inválido.");
            }
        } else {
            request.setAttribute("mensaje", "⚠️ Datos incompletos.");
        }

        // Reenviar al servlet para que recargue con los datos actualizados
        request.getRequestDispatcher("/SvBuscarEstudianteParaAddCarrera").forward(request, response);
    }
    

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
