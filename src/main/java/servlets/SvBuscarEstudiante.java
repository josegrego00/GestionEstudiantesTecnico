/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.ControladoraLogica;
import logica.Estudiante;


@WebServlet(name = "SvBuscarEstudiante", urlPatterns = {"/SvBuscarEstudiante"})
public class SvBuscarEstudiante extends HttpServlet {
    ControladoraLogica controladoraLogica= new ControladoraLogica();

 
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
        String dni = request.getParameter("dni_editar");
        Estudiante estudiante=controladoraLogica.buscarEstudiante(dni);
         if (estudiante != null) {
            request.setAttribute("estudiante", estudiante);
            RequestDispatcher dispatcher = request.getRequestDispatcher("editarEstudiante.jsp");
            dispatcher.forward(request, response);
         } else {
            request.setAttribute("mensajeError", "Estudiante no encontrado.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("editarEstudiante.jsp");
            dispatcher.forward(request, response);
        }
        }

  
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
