<%@page import="logica.Estudiante"%>
<%@page import="logica.InscripEstudianteMateria"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <%@include file="/componentes/head.jsp" %>
    <%@include file="/componentes/bodyArriba.jsp" %>

    <div class="container mt-5">
        <div class="row mb-4">
            <div class="col text-center">
                <h1>Materias del Estudiante</h1>
            </div>
        </div>

        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger text-center">
            <%= request.getAttribute("error") %>
        </div>
        <% } else { %>

        <div class="card mb-4">
            <div class="card-body">
                <h5 class="card-title">Información del Estudiante</h5>
                <p><strong>DNI:</strong> <%= request.getAttribute("dniEstudiante") %></p>
                <p><strong>Nombre:</strong> <%= request.getAttribute("nombreEstudiante") %></p>
                <p><strong>Apellido:</strong> <%= request.getAttribute("apellidoEstudiante") %></p>
            </div>
        </div>

        <div class="table-responsive">
            <table class="table table-bordered text-center">
                <thead class="table-dark">
                    <tr>
                        <th>Materia</th>
                        <th>Estado</th>
                    </tr>
                </thead>
                <tbody>

                    <% 
                        List<InscripEstudianteMateria> materiasCursadas = (List<InscripEstudianteMateria>) request.getAttribute("materiasCursadas");
                        if (materiasCursadas != null && !materiasCursadas.isEmpty()) {
                            for (InscripEstudianteMateria materia : materiasCursadas) {
                    %>
                    <tr>
                        <td><%= materia.getIdMateria().getNombreMateria() %></td>
                        <td>
                            <form action="SvActualizarEstadoMateria" method="POST">
                                <input type="hidden" name="dniEstudiante" value="<%= request.getAttribute("dniEstudiante") %>">
                                <input type="hidden" name="idMateria" value="<%= materia.getIdMateria().getIdMateria() %>">
                                <input type="hidden" name="idCarrera" value="<%= request.getAttribute("idCarrera") %>">
                                <input type="hidden" name="nuevoEstado" value="<%= materia.getEstado().equals("Aprobado") ? "Por Cursar" : "Aprobado" %>">
                                <button type="submit" class="btn <%= materia.getEstado().equals("Aprobado") ? "btn-success" : "btn-warning" %>">
                                    <%= materia.getEstado() %>
                                </button>
                            </form>
                        </td>
                    </tr>
                    <% }
                        } else { %>
                    <tr><td colspan="2">No hay materias aprobadas</td></tr>
                    <% } %>

                    <%
                        List<InscripEstudianteMateria> materiasPendientes = (List<InscripEstudianteMateria>) request.getAttribute("materiasPendientes");
                        if (materiasPendientes != null && !materiasPendientes.isEmpty()) {
                            for (InscripEstudianteMateria materia : materiasPendientes) {
                    %>
                    <tr>
                        <td><%= materia.getIdMateria().getNombreMateria() %></td>
                        <td>
                            <form action="SvActualizarEstadoMateria" method="POST">
                                <input type="hidden" name="dniEstudiante" value="<%= request.getAttribute("dniEstudiante") %>">
                                <input type="hidden" name="idMateria" value="<%= materia.getIdMateria().getIdMateria() %>">
                                <input type="hidden" name="idCarrera" value="<%= request.getAttribute("idCarrera") %>">
                                <input type="hidden" name="nuevoEstado" value="<%= materia.getEstado().equals("Aprobado") ? "Por Cursar" : "Aprobado" %>">
                                <button type="submit" class="btn <%= materia.getEstado().equals("Aprobado") ? "btn-success" : "btn-warning" %>">
                                    <%= materia.getEstado() %>
                                </button>
                            </form>
                        </td>
                    </tr>
                    <% }
                        } else { %>
                    <tr><td colspan="2">No hay materias pendientes</td></tr>
                    <% } %>

                </tbody>
            </table>
        </div>

        <% } %>
    </div>

    <%@include file="/componentes/bodyDebajo.jsp" %>
</html>
