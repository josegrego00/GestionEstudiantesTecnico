package servlets;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import logica.ControladoraLogica;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet(name = "SvCargarLoteEstudiantes", urlPatterns = {"/SvCargarLoteEstudiantes"})
@MultipartConfig(  
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)

public class SvCargarLoteEstudiantes extends HttpServlet {

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
        
        Part filePart = request.getPart("archivoExcel");
        
        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("mensaje", "Error: No se seleccionó ningún archivo.");
            request.getRequestDispatcher("cargarLoteEstudiantes.jsp").forward(request, response);
            return;
        }

        try (InputStream inputStream = filePart.getInputStream()) {
            Workbook workbook;
            String fileName = filePart.getSubmittedFileName();

            // Determinar si el archivo es XLS o XLSX
            if (fileName.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else if (fileName.endsWith(".xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                throw new IllegalArgumentException("Formato de archivo no soportado.");
            }

            // Obtener la primera hoja
            Sheet hoja = workbook.getSheetAt(0);
            
            // Iterar sobre las filas, saltando la primera si es un encabezado
            boolean primeraFila = true;
            for (Row fila : hoja) {
                if (primeraFila) {
                    primeraFila = false;
                    continue; // Saltar la fila de encabezados
                }if (fila == null || fila.getPhysicalNumberOfCells() == 0) {
        System.out.println("Fila vacía encontrada, omitiéndola.");
        continue; // Saltar filas vacías
    }

    // 🔹 Obtener valores con validación de celdas nulas
    String dni = fila.getCell(0) != null ? obtenerValorCelda(fila.getCell(0)) : "";
    String nombre = fila.getCell(1) != null ? obtenerValorCelda(fila.getCell(1)) : "";
    String apellido = fila.getCell(2) != null ? obtenerValorCelda(fila.getCell(2)) : "";
    String telefono = fila.getCell(3) != null ? obtenerValorCelda(fila.getCell(3)) : "";

                int semestre = 1;
                try {
                    semestre = Integer.parseInt(obtenerValorCelda(fila.getCell(4)));
                } catch (NumberFormatException e) {
                    semestre = 1; // O manejar error de otra manera
                }
                boolean activo = obtenerValorCelda(fila.getCell(5)).equalsIgnoreCase("Activo");
                String abrevCarrera = fila.getCell(6) != null ? obtenerValorCelda(fila.getCell(6)).toUpperCase() : "";

                

                // Guardar en la base de datos (Aquí llamas a la lógica para registrar el estudiante)
                System.out.println("Estudiante:------------> " + dni + " - " + nombre + " - " + apellido + " - " + semestre);
                controladoraLogica.crearEstudiante(dni, nombre, apellido, telefono,semestre, controladoraLogica.buscarCarreraAbre(abrevCarrera),activo);
                }

            workbook.close();
            request.setAttribute("mensaje", "Archivo procesado con éxito.");
            request.getRequestDispatcher("cargarLoteEstudiantes.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("mensaje", "Error al procesar el archivo: " + e.getMessage());
            request.getRequestDispatcher("cargarLoteEstudiantes.jsp").forward(request, response);
            e.printStackTrace();
        }
    }

    /**
     * Método para obtener el valor de una celda sin importar su tipo (numérico o texto).
     */
    private String obtenerValorCelda(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString(); // Si es una fecha
                }
                return String.valueOf((long) cell.getNumericCellValue()); // Si es un número, convertir a String
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
