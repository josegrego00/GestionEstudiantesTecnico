package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import logica.ControladoraLogica;
import logica.Carrera;
import logica.Estudiante;
import logica.InscripEstudianteMateria;
import logica.NivelCursoEstudiante;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet(name = "SvExportarReportes", urlPatterns = {"/SvExportarReportes"})
public class SvExportarReportes extends HttpServlet {

    ControladoraLogica control = new ControladoraLogica();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener parámetros del formulario
        String carreraSeleccionada = request.getParameter("carrera");
        String materiaSeleccionadaParam = request.getParameter("materia");
        Integer materiaSeleccionada = null;
        String estadoMateria = request.getParameter("estado");
        String idSemestreParam = request.getParameter("semestre");
        Integer idSemestre = null;

        // Parsear semestre
        if (idSemestreParam != null && !idSemestreParam.isEmpty()) {
            idSemestre = Integer.parseInt(idSemestreParam);
        }

        // Parsear materiaSeleccionada
        if (materiaSeleccionadaParam != null && !materiaSeleccionadaParam.isEmpty()) {
            materiaSeleccionada = Integer.parseInt(materiaSeleccionadaParam);
        }

        // Convertir carreraSeleccionada a Integer
        Integer idCarrera = null;
        if (carreraSeleccionada != null && !carreraSeleccionada.isEmpty()) {
            idCarrera = Integer.parseInt(carreraSeleccionada);
        }

        // Filtrar las inscripciones con los parámetros seleccionados
        List<InscripEstudianteMateria> listaInscripciones = control.filtrarInscripciones(materiaSeleccionada, estadoMateria, idCarrera, idSemestre);
         listaInscripciones.removeIf(inscripcion
                -> inscripcion.getDniEstudiante().getActivo() == false
                || inscripcion.getIdSemestre().getIdSemestre() == 4
        );
        // Generar el archivo Excel con los datos filtrados
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_filtrado.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte");

        // Fila 0: Total y Carrera
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Total estudiantes:");
        headerRow.createCell(1).setCellValue(listaInscripciones != null ? listaInscripciones.size() : 0);
        headerRow.createCell(2).setCellValue("Carrera:");
        headerRow.createCell(3).setCellValue(carreraSeleccionada != null ? carreraSeleccionada : "");

        // Fila 1: Títulos
        Row titles = sheet.createRow(1);
        titles.createCell(0).setCellValue("Estudiante");
        titles.createCell(1).setCellValue("DNI");
        titles.createCell(2).setCellValue("Materia");
        titles.createCell(3).setCellValue("Estado");
        titles.createCell(4).setCellValue("Semestre");

        // Resto de filas
        if (listaInscripciones != null) {
            int rowNum = 2;
            for (InscripEstudianteMateria ins : listaInscripciones) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(ins.getDniEstudiante().getNombreEstudiante() + " " + ins.getDniEstudiante().getApellidoEstudiante());
                row.createCell(1).setCellValue(ins.getDniEstudiante().getDniEstudiante());
                row.createCell(2).setCellValue(ins.getIdMateria().getNombreMateria());
                row.createCell(3).setCellValue(ins.getEstado());

                // Determinar el semestre
                String semestre = "No asignado";
                Estudiante estudiante = ins.getDniEstudiante();
                if (estudiante != null && estudiante.getNivelCursoEstudianteList() != null && !estudiante.getNivelCursoEstudianteList().isEmpty()) {
                    NivelCursoEstudiante nivelCurso = estudiante.getNivelCursoEstudianteList().get(estudiante.getNivelCursoEstudianteList().size() - 1);
                    if (nivelCurso != null && nivelCurso.getIdSemestre() != null) {
                        semestre = nivelCurso.getIdSemestre().getNombreSemestre();
                    }
                }
                row.createCell(4).setCellValue(semestre);
            }
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
