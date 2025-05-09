<%@page import="logica.Carreraestudiante"%>
<%@page import="logica.Carrera"%>
<%@page import="logica.Estudiante"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <%@include file="/componentes/head.jsp" %>
    <%@include file="/componentes/bodyArriba.jsp" %>

    <div class="container mt-5">
        <h1 class="text-center mb-4">Carreras del Estudiante</h1>

        <!-- Formulario para buscar estudiante por DNI -->
        <form action="SvBuscarEstudianteParaAddCarrera" method="POST" class="mb-4">
            <div class="input-group">
                <input type="text" name="dni" class="form-control" placeholder="Ingrese DNI del estudiante" required>
                <button class="btn btn-primary" type="submit">Buscar</button>
            </div>
        </form>

        <% String mensaje = (String) request.getAttribute("mensaje"); %>
        <% if (mensaje != null) { %>
            <div class="alert alert-info"><%= mensaje %></div>
        <% } %>

        <% Estudiante estudiante = (Estudiante) request.getAttribute("estudiante"); %>
        <% List<Carreraestudiante> carrerasEstudiante = (List<Carreraestudiante>) request.getAttribute("carrerasEstudiante"); %>
        <% List<Carrera> listaCarreras = (List<Carrera>) request.getAttribute("listaCarreras"); %>

        <% if (estudiante != null) { %>
            <div class="card p-3 mb-4">
                <h5>Datos del Estudiante</h5>
                <p><strong>DNI:</strong> <%= estudiante.getDniEstudiante() %></p>
                <p><strong>Nombre:</strong> <%= estudiante.getNombreEstudiante() %></p>
                <p><strong>Apellido:</strong> <%= estudiante.getApellidoEstudiante() %></p>
            </div>

            <div class="mb-4">
                <h5>Carreras Actuales</h5>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Nombre de la Carrera</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (carrerasEstudiante != null && !carrerasEstudiante.isEmpty()) {
                            for (Carreraestudiante carreraEstudiantes : carrerasEstudiante) { %>
                                <tr>
                                    <td><%= carreraEstudiantes.getIdcarrera().getNombrecarrera()%></td>
                                    <td>
                                        <form action="SvEliminarCarreraEstudiante" method="POST" style="display:inline;">
                                            <input type="hidden" name="dni" value="<%= estudiante.getDniEstudiante() %>">
                                            <input type="hidden" name="idCarreraE" value="<%= carreraEstudiantes.getIdcarrera().getId() %>">
                                            <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('¿Seguro que quieres quitar esta carrera?')">Quitar</button>
                                        </form>
                                    </td>
                                </tr>
                        <%  } 
                           } else { %>
                            <tr><td colspan="2">Este estudiante no tiene carreras asignadas.</td></tr>
                        <% } %>
                    </tbody>
                </table>
            </div>

            <!-- Formulario para agregar nueva carrera -->
            <div class="mb-4">
                <h5>Agregar Nueva Carrera</h5>
                <form action="SvGuardarAddCarreraEstudiante" method="POST" class="d-flex gap-2">
                    <input type="hidden" name="dni" value="<%= estudiante.getDniEstudiante() %>">
                    <select name="idCarrera" class="form-select" required>
                        <option value="" disabled selected>Seleccione una carrera</option>
                        <% 
                
                                if (listaCarreras != null) {
                                for (Carrera carrera : listaCarreras) {
                                    boolean yaAsignada = false;
                                    if (carrerasEstudiante != null) {
                                        for (Carreraestudiante cEst : carrerasEstudiante) {
                                            if (cEst.getIdcarrera().getId().equals(carrera.getId())) {
                                                yaAsignada = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (!yaAsignada) {
                        %>
                            <option value="<%= carrera.getId() %>"><%= carrera.getNombrecarrera() %></option>
                        <%      }
                                }
                            }
                        %>
                    </select>
                    <button class="btn btn-success" type="submit">Agregar</button>
                </form>
            </div>
        <% } %>
    </div>

    <%@include file="/componentes/bodyDebajo.jsp" %>
</html>
