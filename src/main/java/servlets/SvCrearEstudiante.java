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
import logica.ControladoraLogica;
import logica.Estudiante;
import logica.Semestre;

/**
 *
 * @author josepino
 */
@WebServlet(name = "SvCrearEstudiante", urlPatterns = {"/SvCrearEstudiante"})
public class SvCrearEstudiante extends HttpServlet {
    ControladoraLogica controladoraLogica= new ControladoraLogica();

  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
            
    }

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    // Obtener la lista de carreras desde la base de datos
    List<Carrera> listaCarreras = controladoraLogica.listarCarreras();
        
    
    // Enviar la lista al JSP
    request.setAttribute("listaCarreras", listaCarreras);
    
    // Redirigir al formulario JSP
    request.getRequestDispatcher("crearEstudiante.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String dni = request.getParameter("dni");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String telefono = request.getParameter("telefono");
        Integer semestre=null;
        boolean activo = "1".equals(request.getParameter("activo"));

        String idCarrera = request.getParameter("carrera");
       
        //Crear estudiante 
        controladoraLogica.crearEstudiante(dni, nombre, apellido, telefono,semestre, controladoraLogica.buscarCarrera(Integer.parseInt(idCarrera)), activo);
     response.sendRedirect("SvListarEstudiantes");
        }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
