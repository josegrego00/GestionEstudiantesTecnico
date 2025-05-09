package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logica.Carrera;
import logica.ControladoraLogica;
import logica.Materias;

@WebServlet(name = "SvActualizarCarrera", urlPatterns = {"/SvActualizarCarrera"})
public class SvActualizarCarrera extends HttpServlet {

    ControladoraLogica control = new ControladoraLogica();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idCarrera = Integer.parseInt(request.getParameter("idCarrera"));
            String nombreCarrera = request.getParameter("nombreCarrera");
            int cantidadSemestres = Integer.parseInt(request.getParameter("cantidadSemestres"));

            String[] materiaIds = request.getParameterValues("materiaId");
            String[] materiaNombres = request.getParameterValues("materiaNombre");
            String[] nuevasMaterias = request.getParameterValues("materias"); // las que vienen de JS

            Carrera carrera = control.buscarCarrera(idCarrera);
            if (carrera != null) {

                // Lista final para todas las materias
                List<Materias> todasMaterias = new ArrayList<>();

                // 1. Actualizar materias existentes
                if (materiaIds != null && materiaNombres != null) {
                    for (int i = 0; i < materiaIds.length; i++) {
                        int idMateria = Integer.parseInt(materiaIds[i]);
                        String nombreMateria = materiaNombres[i];

                        Materias materia = control.buscarMateriaPorId(idMateria); // debes tener este método
                        if (materia != null) {
                            materia.setNombreMateria(nombreMateria);
                            control.actualizarMateria(materia); // debes tener este método también
                            todasMaterias.add(materia);
                        }
                    }
                }

                // 2. Agregar nuevas materias
                if (nuevasMaterias != null) {
                    for (String nombreNueva : nuevasMaterias) {
                        control.crearMateria(nombreNueva, carrera); // guardarla
                    }
                }

                // 3. Actualizar carrera
                carrera.setNombrecarrera(nombreCarrera);
                carrera.setCantidadSemestres(cantidadSemestres);
                carrera.setMateriasList(todasMaterias); // actualizamos referencia en carrera
                control.actualizarCarrera(carrera);

                request.getSession().setAttribute("mensajeExito", "Carrera actualizada correctamente.");
            } else {
                request.getSession().setAttribute("mensajeError", "Carrera no encontrada.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("mensajeError", "Error al actualizar la carrera: " + e.getMessage());
        }

        response.sendRedirect("SvVerCarreras");
    }

}
