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

/**
 *
 * @author josepino
 */
@WebServlet(name = "SvVerCarreras", urlPatterns = {"/SvVerCarreras"})
public class SvVerCarreras extends HttpServlet {

    ControladoraLogica controladoraLogica= new ControladoraLogica();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener la lista de carreras desde algún servicio o DAO
        List<Carrera> listaCarreras = controladoraLogica.listarCarreras();

        // Enviar la lista como atributo a la JSP
        request.setAttribute("listaCarreras", listaCarreras);

        // Redirigir a la página que muestra la lista
        request.getRequestDispatcher("verCarreras.jsp").forward(request, response);
    }


}
