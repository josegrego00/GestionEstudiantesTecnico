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
import logica.ControladoraLogica;

@WebServlet(name = "SvGuardarEdicion", urlPatterns = {"/SvGuardarEdicion"})
public class SvGuardarEdicion extends HttpServlet {
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
            String dni = request.getParameter("dni");
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            String telefono = request.getParameter("telefono");
           boolean activo = Boolean.parseBoolean(request.getParameter("activo"));
             controladoraLogica.actualizarEstudiante(dni, nombre, apellido, telefono, activo);
         response.sendRedirect("SvListarEstudiantes");
    }

  
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
