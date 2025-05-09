/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.ControladoraLogica;

/**
 *
 * @author josepino
 */
@WebServlet(name = "SvPromoverEstudiantes", urlPatterns = {"/SvPromoverEstudiantes"})
public class SvPromoverEstudiantes extends HttpServlet {

    ControladoraLogica controladoraLogica = new ControladoraLogica();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] dnisSeleccionados = request.getParameterValues("dniSeleccionados");

        StringBuilder mensajeFinal = new StringBuilder();

        if (dnisSeleccionados != null) {
            int totalPromovidos = 0;
            int totalGraduados = 0;
            int noPromovidos = 0;

            for (String dni : dnisSeleccionados) {
                String[] infoEstudiante = dni.split("\\|");
                String dniEstudiante = infoEstudiante[0];
                int carreraIdEstudiante = Integer.parseInt(infoEstudiante[1]);
                int semestreEstudiante = Integer.parseInt(infoEstudiante[2]);

                // Promoción y resultado
                String resultado = controladoraLogica.promoverEstudiante(dniEstudiante, carreraIdEstudiante, semestreEstudiante);

                switch (resultado) {
                    case "graduado":
                        totalGraduados++;
                        break;
                    case "promovido":
                        totalPromovidos++;
                        break;
                    case "no_aprobado":
                        noPromovidos++;
                        break;
                }
            }

            // Construimos el mensaje
            if (totalPromovidos > 0) {
                mensajeFinal.append("✅ ").append(totalPromovidos).append(" estudiante(s) promovido(s).<br>");
            }
            if (totalGraduados > 0) {
                mensajeFinal.append("🎓 ").append(totalGraduados).append(" estudiante(s) graduado(s).<br>");
            }
            if (noPromovidos > 0) {
                mensajeFinal.append("⚠️ ").append(noPromovidos).append(" estudiante(s) no pudieron ser promovidos porque no aprobaron todas las materias.<br>");
            }

            request.setAttribute("mensaje", mensajeFinal.toString());

        } else {
            request.setAttribute("mensaje", "⚠️ No se seleccionó ningún estudiante.");
        }

        // Redirigimos al JSP mostrando el mensaje
       response.sendRedirect("SvListarEstudiantesPromover?mensaje=" + java.net.URLEncoder.encode(mensajeFinal.toString(), "UTF-8"));
 }
}


