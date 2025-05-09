<%@page import="logica.Semestre"%>
<%@page import="logica.NivelCursoEstudiante"%>
<%@page import="logica.Estudiante"%>
<%@page import="logica.InscripEstudianteMateria"%>
<%@page import="logica.Carrera"%>
<%@page import="logica.Materias"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <%@include file="/componentes/head.jsp" %>
    <%@include file="/componentes/bodyArriba.jsp" %>

    <%
        String carreraSeleccionada = request.getParameter("carrera");
        boolean carreraSeleccionadaBool = carreraSeleccionada != null && !carreraSeleccionada.isEmpty();
        String materiaSeleccionada = request.getParameter("materia");
        String estadoSeleccionado = request.getParameter("estado");
        String semestreSeleccionado = request.getParameter("semestre");

    %>

    <div class="container my-5">
        <div class="row mb-4">
            <div class="col text-center">
                <h1 class="display-5 fw-bold">Filtrar Reportes Académicos</h1>
            </div>
        </div>

        <div class="card shadow-sm p-4 mb-5">
            <form method="post" action="SvFiltrarReportesCarreras" class="row g-4">
                <!-- Select de Carrera -->
                <div class="col-md-3">
                    <label for="filtroCarrera" class="form-label">Carrera:</label>
                    <select class="form-select" id="filtroCarrera" name="carrera" onchange="this.form.submit()">
                        <option value="">Todas</option>
                        <%                            List<Carrera> listaCarreras = (List<Carrera>) request.getAttribute("listaCarreras");
                            if (listaCarreras != null) {
                                for (Carrera carrera : listaCarreras) {
                                    boolean selected = carreraSeleccionada != null && carreraSeleccionada.equals(String.valueOf(carrera.getId()));
                        %>
                        <option value="<%= carrera.getId()%>" <%= selected ? "selected" : ""%>>
                            <%= carrera.getNombrecarrera()%>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>

                <!-- Select de Materia -->
                <div class="col-md-3">
                    <label for="filtroMateria" class="form-label">Materia:</label>
                    <select class="form-select" id="filtroMateria" name="materia" <%= !carreraSeleccionadaBool ? "disabled" : ""%>>
                        <option value="">Todas</option>
                        <%
                            if (carreraSeleccionadaBool) {
                                List<Materias> listaMaterias = (List<Materias>) request.getAttribute("listaMaterias");
                                if (listaMaterias != null) {
                                    for (Materias materia : listaMaterias) {
                        %>
                        <option value="<%= materia.getIdMateria()%>" 
                                <%= materiaSeleccionada != null && materiaSeleccionada.equals(String.valueOf(materia.getIdMateria())) ? "selected" : ""%>>
                            <%= materia.getNombreMateria()%>
                        </option>

                        <%
                                    }
                                }
                            }
                        %>
                    </select>
                </div>

                <!-- Select de Estado -->
                <div class="col-md-3">
                    <label for="filtroEstado" class="form-label">Estado:</label>
                    <select class="form-select" id="filtroEstado" name="estado" <%= !carreraSeleccionadaBool ? "disabled" : ""%>>
                        <option value="Aprobado" <%= "Aprobado".equals(estadoSeleccionado) ? "selected" : ""%>>Aprobado</option>
                        <option value="Por Cursar" <%= "Por Cursar".equals(estadoSeleccionado) ? "selected" : ""%>>Por Cursar</option>

                    </select>
                </div>

                <!-- Select de Semestre -->
                <div class="col-md-3">
                    <label for="filtroSemestre" class="form-label">Semestre:</label>
                    <select class="form-select" id="filtroSemestre" name="semestre" <%= !carreraSeleccionadaBool ? "disabled" : ""%>>
                        <option value="">Todos</option>
                        <option value="1" <%= "1".equals(semestreSeleccionado) ? "selected" : ""%>>1er Semestre</option>
                        <option value="2" <%= "2".equals(semestreSeleccionado) ? "selected" : ""%>>2do Semestre</option>
                        <option value="3" <%= "3".equals(semestreSeleccionado) ? "selected" : ""%>>3er Semestre</option>

                    </select>
                </div>

                <!-- Botones -->
                <div class="col-md-6">
                    <button type="submit" class="btn btn-primary w-100">
                        <i class="fas fa-filter"></i> Filtrar
                    </button>
                </div>
                <div class="col-md-6">
                    <button type="submit" formaction="SvExportarReportes" class="btn btn-success w-100">
                        <i class="fas fa-file-export"></i> Exportar
                    </button>
                </div>
            </form>
        </div>

        <%-- Resultados --%>
        <%
            List<InscripEstudianteMateria> listaInscripciones = (List<InscripEstudianteMateria>) request.getAttribute("listaInscripciones");
            if (listaInscripciones != null && !listaInscripciones.isEmpty()) {
        %>
        <div class="card shadow-sm p-4">
            <h4 class="mb-3">Resultados encontrados</h4>
            <p>Total de Registros <strong><%= listaInscripciones.size()%></strong></p>
            <div class="table-responsive">
                <table class="table table-striped table-hover align-middle">
                    <thead class="table-dark">
                        <tr>
                            <th>DNI</th>
                            <th>Estudiante</th>
                            <th>Materia</th>
                            <th>Estado</th>
                            <th>Semestre</th> 
                        </tr>
                    </thead>
                    <tbody>
                        <% for (InscripEstudianteMateria ins : listaInscripciones) {
                                String semestre = "No asignado";
                                Estudiante estudiante = ins.getDniEstudiante();

                                if (estudiante != null && estudiante.getNivelCursoEstudianteList() != null && !estudiante.getNivelCursoEstudianteList().isEmpty()) {

                                    // Comprobar que la materia actual está asociada con una carrera y encontrar el semestre correcto
                                    NivelCursoEstudiante semestrePorCarrera = null;

                                    // Iterar sobre los niveles de curso para obtener el semestre correcto de la carrera en que el estudiante está inscrito
                                    for (NivelCursoEstudiante nivelCurso : estudiante.getNivelCursoEstudianteList()) {
                                        if (nivelCurso != null && nivelCurso.getIdSemestre() != null) {
                                            // Comprobar si el nivel está relacionado con la carrera de la materia
                                            if (ins.getIdMateria().getIdCarrera().getId().equals(nivelCurso.getIdCarrera().getId())) {
                                                semestrePorCarrera = nivelCurso;  // Asignamos el semestre correspondiente
                                                break;  // Salir del bucle si encontramos el semestre correspondiente
                                            }
                                        }
                                    }

                                    // Si encontramos un semestre específico, lo asignamos, si no, se mantiene "No asignado"
                                    if (semestrePorCarrera != null) {
                                        semestre = semestrePorCarrera.getIdSemestre().getNombreSemestre();
                                    }
                                }
                        %>
                        <tr>
                            <td><%= estudiante.getDniEstudiante()%></td>
                            <td><%= estudiante.getNombreEstudiante()%> <%= estudiante.getApellidoEstudiante()%></td>
                            <td><%= ins.getIdMateria().getNombreMateria()%></td>
                            <td><%= ins.getEstado()%></td>
                            <td><%= semestre%></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
        <% } else if (request.getAttribute("listaInscripciones") != null) { %>
        <div class="alert alert-warning text-center mt-4" role="alert">
            <i class="fas fa-exclamation-circle me-2"></i>No se encontraron resultados con esos filtros.
        </div>
        <% }%>
    </div>

    <%@include file="/componentes/bodyDebajo.jsp" %>
</html>
