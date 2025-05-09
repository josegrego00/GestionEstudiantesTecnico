<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <%@include file="/componentes/head.jsp" %>
    <%@include file="/componentes/bodyArriba.jsp" %>

    <div class="container mt-5">
        <h1 class="text-center mb-4">Cargar Lote de Estudiantes</h1>

        <!-- Formulario para carga de archivo -->
        <form action="SvCargarLoteEstudiantes" method="POST" enctype="multipart/form-data">
            <div class="mb-4 text-center">
                <label for="archivoExcel" class="form-label">Seleccione un archivo Excel</label>
                <input type="file" class="form-control mb-2" id="archivoExcel" name="archivoExcel" accept=".xlsx, .xls" required>
                <button type="submit" class="btn btn-primary mt-2">
                    <i class="fas fa-upload"></i> Cargar Archivo
                </button>
            </div>
        </form>

        <!-- Mensaje de éxito o error -->
        <% String mensaje = (String) request.getAttribute("mensaje"); %>
        <% if (mensaje != null) { %>
            <div class="alert alert-info text-center"><%= mensaje %></div>
        <% } %>

        <!-- Tabla de referencia para formato del archivo -->
       <!-- Tabla de referencia para formato del archivo -->
<h5 class="text-center mt-4">Formato Requerido para el Archivo</h5>
<div class="table-responsive">
    <table class="table table-bordered text-center">
        <thead class="table-dark">
            <tr>
                <th>DNI</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Teléfono</th>
                <th>Semestre</th>
                <th>Estado (Activo/Inactivo)</th>
                <th>Carrera</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>12345678</td>
                <td>Juan</td>
                <td>Pérez</td>
                <td>987654321</td>
                <td>1</td>
                <td>Activo</td>
                <td>ATS</td>
            </tr>
            <tr>
                <td>87654321</td>
                <td>María</td>
                <td>González</td>
                <td>123456789</td>
                <td>2</td>
                <td>Inactivo</td>
                <td>SO</td>
            </tr>
        </tbody>
    </table>
</div>

<!-- Tabla de abreviaturas de carreras -->
<h5 class="text-center mt-5">Abreviaturas de Carreras</h5>
<div class="table-responsive">
    <table class="table table-bordered text-center">
        <thead class="table-dark">
            <tr>
                <th>Carrera</th>
                <th>Abreviatura</th>
            </tr>
        </thead>
        <tbody>
            <tr><td>Asistente de Trabajo Social</td><td>ATS</td></tr>
            <tr><td>Asistente a la Primera Infancia</td><td>API</td></tr>
            <tr><td>Seguridad Ocupacional</td><td>SO</td></tr>
            <tr><td>Auxiliar Contable</td><td>AC</td></tr>
            <tr><td>Asistente Administrativo</td><td>AA</td></tr>
            <tr><td>Técnico en Sistema</td><td>TS</td></tr>
            <tr><td>Técnico en redes y Línea Telefónica</td><td>TRT</td></tr>
            <tr><td>Mecánico de Motores Diésel</td><td>MMD</td></tr>
            <tr><td>Mecánico de Motocicleta</td><td>MM</td></tr>
            <tr><td>Técnico Académico en Inglés N-B2</td><td>TAI</td></tr>
            <tr><td>Marketing Digital</td><td>MKD</td></tr>
        </tbody>
    </table>
</div>

    <%@include file="/componentes/bodyDebajo.jsp" %>
</html>
