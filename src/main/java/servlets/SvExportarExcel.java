package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logica.Carreraestudiante;
import logica.ControladoraLogica;
import logica.Estudiante;
import logica.NivelCursoEstudiante;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet(name = "SvExportarExcel", urlPatterns = {"/SvExportarExcel"})
public class SvExportarExcel extends HttpServlet {

    ControladoraLogica controladora = new ControladoraLogica();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String filtroCarrera = request.getParameter("carrera");
        String filtroSemestre = request.getParameter("semestre");

        // Obtener la lista de estudiantes con semestre y carrera
        List<NivelCursoEstudiante> estudiantesConSemestre = controladora.listarTodosEstudianteConSemestre();

        // Filtrado según carrera y semestre
        List<NivelCursoEstudiante> filtrados = new ArrayList<>();
        for (NivelCursoEstudiante nce : estudiantesConSemestre) {
            String nombreCarrera = nce.getIdCarrera().getNombrecarrera();
            int semestre = nce.getIdSemestre().getIdSemestre();

            boolean coincideCarrera = filtroCarrera == null || filtroCarrera.isEmpty() || nombreCarrera.equals(filtroCarrera);
            boolean coincideSemestre = filtroSemestre == null || filtroSemestre.isEmpty() || String.valueOf(semestre).equals(filtroSemestre);

            if (coincideCarrera && coincideSemestre) {
                filtrados.add(nce);
            }
        }

        // Crear el archivo Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=estudiantes_filtrados.xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Estudiantes");

        // Encabezado
        String[] encabezados = {"DNI", "Nombre", "Apellido", "Teléfono", "Carrera", "Semestre", "Estado"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < encabezados.length; i++) {
            headerRow.createCell(i).setCellValue(encabezados[i]);
        }

        // Llenar filas con los estudiantes filtrados
        int rowNum = 1;
        for (NivelCursoEstudiante nce : filtrados) {
            Estudiante estudiante = nce.getDniEstudiante();
            String nombreCarrera = nce.getIdCarrera().getNombrecarrera();
            int semestre = nce.getIdSemestre().getIdSemestre();

            // Crear una fila para el estudiante
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(estudiante.getDniEstudiante());
            row.createCell(1).setCellValue(estudiante.getNombreEstudiante());
            row.createCell(2).setCellValue(estudiante.getApellidoEstudiante());
            row.createCell(3).setCellValue(estudiante.getTelefono());
            row.createCell(4).setCellValue(nombreCarrera);
            String semestreTexto = (nce.getIdSemestre().getIdSemestre() == 4) ? "Graduado" : String.valueOf(nce.getIdSemestre().getIdSemestre());
            row.createCell(5).setCellValue(semestreTexto);
            row.createCell(6).setCellValue(estudiante.getActivo() ? "Activo" : "No Activo");
        }

        // Escribir el archivo al response
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
