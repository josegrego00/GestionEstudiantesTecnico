<%@page import="logica.Carrera"%>
<%@page import="logica.Estudiante"%>
<%@page import="logica.NivelCursoEstudiante"%>
<%@page import="logica.Carreraestudiante"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <%@include file="/componentes/head.jsp" %>
    <%@include file="/componentes/bodyArriba.jsp" %>

    <div class="container mt-5">
        <div class="row mb-4">
            <div class="col text-center">
                <h1>Lista de Estudiantes</h1>
            </div>
        </div>
        <%
            String carreraSeleccionada = (String) request.getAttribute("carreraSeleccionada");
            String semestreSeleccionado = (String) request.getAttribute("semestreSeleccionado");
        %>

        <form method="GET" action="SvListarEstudiantesFiltro" class="row g-3 mb-4">
            <div class="col-md-5">
                <label for="carrera" class="form-label">Filtrar por Carrera:</label>
                <select name="carrera" id="carrera" class="form-select">
                    <option value="">-- Todas las carreras --</option>
                    <%
                        List<Carrera> listaCarreras = (List<Carrera>) request.getAttribute("listaCarrerasEstudiante");
                        for (Carrera carrera : listaCarreras) {
                    %>
                    <option value="<%= carrera.getNombrecarrera()%>" <%= carrera.getNombrecarrera().equals(carreraSeleccionada) ? "selected" : ""%>>
                        <%= carrera.getNombrecarrera()%>
                    </option>
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
                    %>
                    <option value="<%= i%>" <%= String.valueOf(i).equals(semestreSeleccionado) ? "selected" : ""%>>
                        <%= i%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>

            <div class="col-md-2 d-flex align-items-end">
                <button type="submit" class="btn btn-primary w-100">Filtrar</button>
            </div>
        </form>

        <!-- Tabla de Estudiantes -->
        <div class="row">
            <div class="col">
                <div class="table-responsive">
                    <table class="table table-bordered text-center align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>DNI</th>
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Carrera</th>
                                <th>Semestre Actual</th>
                                <th>Acción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                // Lista de estudiantes filtrados y semestres
                                List<NivelCursoEstudiante> listaEstudiantesConSemestre = (List<NivelCursoEstudiante>) request.getAttribute("listaFiltradaEstudiantes");

                                if (listaEstudiantesConSemestre != null && !listaEstudiantesConSemestre.isEmpty()) {
                                    for (NivelCursoEstudiante nivelEstudiante : listaEstudiantesConSemestre) {
                                        Estudiante estudiante = nivelEstudiante.getDniEstudiante(); // Obtener el estudiante de NivelCursoEstudiante
                                        int semestreActual = nivelEstudiante.getIdSemestre().getIdSemestre(); // Obtener el semestre actual
                                        Carrera carreraEstudiante = nivelEstudiante.getIdCarrera(); // Carrera del estudiante
                            %>
                            <tr>
                                <td><%= estudiante.getDniEstudiante() %></td>
                                <td><%= estudiante.getNombreEstudiante() %></td>
                                <td><%= estudiante.getApellidoEstudiante() %></td>
                                <td><%= (carreraEstudiante != null) ? carreraEstudiante.getNombrecarrera() : "No asignada" %></td>
                                <td><%= semestreActual %>º Semestre</td>
                                <td>
                                    <% if (carreraEstudiante != null) { %>
                                    <form action="SvListarEstudiantesConMateriasPendientes" method="GET">
                                        <input type="hidden" name="dniEstudiante" value="<%= estudiante.getDniEstudiante() %>">
                                        <input type="hidden" name="idCarrera" value="<%= carreraEstudiante.getId() %>">
                                        <button type="submit" class="btn btn-info">
                                            <i class="fas fa-eye"></i> Ver Materias
                                        </button>
                                    </form>
                                    <% } else { %>
                                    <span class="text-muted">Sin carrera</span>
                                    <% } %>
                                </td>
                            </tr>
                            <%
                                    }
                                } else {
                            %>
                            <tr>
                                <td colspan="6" class="text-center text-muted">No hay estudiantes registrados</td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <%@include file="/componentes/bodyDebajo.jsp" %>
</html>
