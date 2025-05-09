package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.Carrera;
import logica.ControladoraLogica;
import logica.Materias;
import logica.Semestre;

@WebServlet(name = "SvCrearCarreras", urlPatterns = {"/SvCrearCarreras"})
public class SvCrearCarreras extends HttpServlet {

    ControladoraLogica controladoraLogica = new ControladoraLogica();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 1. Obtener parámetros del formulario
            String nombreCarrera = request.getParameter("nombreCarrera");
            int cantidadSemestres = Integer.parseInt(request.getParameter("cantidadSemestres"));
            String[] listaMaterias = request.getParameterValues("materias");

            // 2. Validaciones
            if (listaMaterias == null || listaMaterias.length < 5) {
                throw new Exception("Debe agregar al menos 5 materias para la carrera");
            }

            if (nombreCarrera == null || nombreCarrera.trim().isEmpty()) {
                throw new Exception("El nombre de la carrera es obligatorio");
            }

            // 2. Generar abreviatura automática
            String abreviatura = generarAbreviatura(nombreCarrera);

            controladoraLogica.crearCarreraImtel(nombreCarrera, abreviatura, cantidadSemestres, listaMaterias);

            // 5. Redireccionar con mensaje de éxito
            request.getSession().setAttribute("mensajeExito", "Carrera creada exitosamente con " + listaMaterias.length + " materias");
            response.sendRedirect("crearCarrera.jsp");

        } catch (Exception e) {
            // Manejo de errores
            request.getSession().setAttribute("mensajeError", "Error al crear carrera: " + e.getMessage());
            response.sendRedirect("crearCarrera.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para creación de carreras y materias asociadas";
    }

    private String generarAbreviatura(String nombreCarrera) {
        // Eliminar espacios múltiples y dividir en palabras
        String[] palabras = nombreCarrera.trim().split("\\s+");
        StringBuilder abreviatura = new StringBuilder();

        // Palabras a ignorar (artículos, preposiciones)
        Set<String> ignorar = new HashSet<>(Arrays.asList(
                "de", "en", "y", "la", "el", "a", "con", "por", "para", "del", "al"
        ));

        for (String palabra : palabras) {
            if (!palabra.isEmpty() && !ignorar.contains(palabra.toLowerCase())) {
                abreviatura.append(Character.toUpperCase(palabra.charAt(0)));
            }
        }

        // Limitar a máximo 5 caracteres
        return abreviatura.length() > 5 ? abreviatura.substring(0, 5) : abreviatura.toString();// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
