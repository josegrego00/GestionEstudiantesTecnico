<%@page import="logica.Carrera"%>
<%@page import="logica.Carreraestudiante"%>
<%@page import="logica.NivelCursoEstudiante"%>
<%@page import="logica.Estudiante"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <%@include file="/componentes/head.jsp" %>
    <%@include file="/componentes/bodyArriba.jsp" %>

    <div class="container mt-5">
        <h1 class="text-center mb-4">Lista de Estudiantes</h1>

        <!-- Filtros -->
        <form method="GET" action="SvListarEstudiantes" class="row g-3 mb-4">
            <div class="col-md-5">
                <label for="carrera" class="form-label">Filtrar por Carrera:</label>
                <select name="carrera" id="carrera" class="form-select">
                    <option value="">-- Todas las carreras --</option>
                    <%
                        List<Carrera> listaCarreras = (List<Carrera>) request.getAttribute("listaCarreras");
                        String carreraSeleccionada = request.getParameter("carrera");
                        for (Carrera carrera : listaCarreras) {
                    %>
                    <option value="<%= carrera.getNombrecarrera()%>" <%= carrera.getNombrecarrera().equals(carreraSeleccionada) ? "selected" : ""%>><%= carrera.getNombrecarrera()%></option>
                    <%
                        }
                    %>
                </select>
            </div>

            <div class="col-md-5">
                <label for="semestre" class="form-label">Filtrar por Semestre:</label>
                <select name="semestre" id="semestre" class="form-select">
                    <option value="">-- Todos los semestres --</option>
                    <%
                        for (int i = 1; i <= 3; i++) {
                            String semestreParam = request.getParameter("semestre");
                    %>
                    <option value="<%= i%>" <%= String.valueOf(i).equals(semestreParam) ? "selected" : ""%>><%= i%></option>
                    <%
                        }
                    %>
                    <option value="4"> Graduados </option>
                </select>
            </div>

            <div class="col-md-2 d-flex align-items-end">
                <button type="submit" class="btn btn-primary w-100">Filtrar</button>
            </div>
        </form>

        <!-- Botón de exportar separado -->
        <form action="SvExportarExcel" method="get" class="mb-4">
            <input type="hidden" name="carrera" value="<%= request.getParameter("carrera") != null ? request.getParameter("carrera") : ""%>">
            <input type="hidden" name="semestre" value="<%= request.getParameter("semestre") != null ? request.getParameter("semestre") : ""%>">
            <button type="submit" class="btn btn-success">
                <i class="fas fa-file-excel"></i> Exportar a Excel
            </button>
        </form>

        <!-- Encabezados -->
        <div class="row border-bottom fw-bold py-3 text-center">
            <div class="col-md-1">DNI</div>
            <div class="col-md-2">Nombre</div>
            <div class="col-md-2">Apellido</div>
            <div class="col-md-2">Teléfono</div>
            <div class="col-md-3">Carrera</div>
            <div class="col-md-1">Semestre</div>
            <div class="col-md-1">Estado</div>
        </div>

        <%
            List<NivelCursoEstudiante> listaEstudiantesConSemestre = (List<NivelCursoEstudiante>) request.getAttribute("listaEstudiantesConSemestre");
            String filtroCarrera = request.getParameter("carrera");
            String filtroSemestre = request.getParameter("semestre");

            boolean hayResultados = false;

            for (NivelCursoEstudiante nce : listaEstudiantesConSemestre) {
                Estudiante estudiante = nce.getDniEstudiante();
                String nombreCarrera = nce.getIdCarrera().getNombrecarrera();
                int semestre = nce.getIdSemestre().getIdSemestre();

                boolean coincideCarrera = (filtroCarrera == null || filtroCarrera.isEmpty() || nombreCarrera.equalsIgnoreCase(filtroCarrera));
                boolean coincideSemestre = (filtroSemestre == null || filtroSemestre.isEmpty() || semestre == Integer.parseInt(filtroSemestre));

                if (coincideCarrera && coincideSemestre) {
                    hayResultados = true;
        %>
        <div class="row border-bottom py-3 text-center">
            <div class="col-md-1"><%= estudiante.getDniEstudiante()%></div>
            <div class="col-md-2"><%= estudiante.getNombreEstudiante()%></div>
            <div class="col-md-2"><%= estudiante.getApellidoEstudiante()%></div>
            <div class="col-md-2"><%= estudiante.getTelefono()%></div>
            <div class="col-md-3"><%= nombreCarrera%></div>
            <div class="col-md-1">
                <%= (semestre == 4) ? "Graduado" : semestre%>
            </div>

            <div class="col-md-1"><%= estudiante.getActivo() ? "Activo" : "No Activo"%></div>
        </div>
        <%
                }
            }

            if (!hayResultados) {
        %>
        <div class="row border-bottom py-3 text-center">
            <div class="col-md-12">No hay estudiantes registrados para los filtros seleccionados.</div>
        </div>
        <%
            }
        %>
    </div>

    <%@include file="/componentes/bodyDebajo.jsp" %>
</html>
