<%@page import="logica.Carreraestudiante"%>
<%@page import="logica.Carrera"%>
<%@page import="java.util.List"%>
<%@page import="logica.Estudiante"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <%@include file="/componentes/head.jsp" %>
    <%@include file="/componentes/bodyArriba.jsp" %>

    <div class="container mt-5">
        <h1 class="text-center mb-4">Editar Datos Estudiante</h1>

        <!-- Formulario de búsqueda por DNI -->
        <!-- Formulario de edición -->
        <form action="SvBuscarEstudiante" method="POST">
            <div class="mb-4 d-flex">
                <input type="text" class="form-control me-2" id="buscarDNI" placeholder="Ingrese DNI" name="dni_editar" required>
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-search"></i> Buscar
                </button>
            </div>
        </form>


        <!-- Mostrar mensaje de error si el estudiante no se encuentra -->
        <% String mensajeError = (String) request.getAttribute("mensajeError"); %>
        <% if (mensajeError != null) {%>
        <div class="alert alert-danger"><%= mensajeError%></div>
        <% } %>
        <% Estudiante estudiante = (Estudiante) request.getAttribute("estudiante");%>

        <form action="SvGuardarEdicion" method="POST">
            <div class="mb-3">
                <label for="dni" class="form-label">DNI del Estudiante</label>
                <input type="text" class="form-control" id="dni" name="dni" required readonly value="<%= (estudiante != null) ? estudiante.getDniEstudiante() : ""%>">
            </div>

            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="nombre" name="nombre" required value="<%= (estudiante != null) ? estudiante.getNombreEstudiante() : ""%>">
            </div>

            <div class="mb-3">
                <label for="apellido" class="form-label">Apellido</label>
                <input type="text" class="form-control" id="apellido" name="apellido" required value="<%= (estudiante != null) ? estudiante.getApellidoEstudiante() : ""%>">
            </div>

            <div class="mb-3">
                <label for="telefono" class="form-label">Teléfono</label>
                <input type="text" class="form-control" id="telefono" name="telefono" value="<%= (estudiante != null) ? estudiante.getTelefono() : ""%>">
            </div>

            <div class="mb-3">
                <label for="activo" class="form-label">Estado</label>
                <select class="form-select" id="activo" name="activo" required>
                    <option value="true" <%= (estudiante != null && estudiante.getActivo() == true) ? "selected" : ""%>>Activo</option>
                    <option value="false" <%= (estudiante != null && estudiante.getActivo() == false) ? "selected" : ""%>>Inactivo</option>
                </select>
            </div>

            <!-- Botones de acción -->
            <div class="d-flex justify-content-between">
                <button type="submit" class="btn btn-success">
                    <i class="fas fa-edit"></i> Guardar Edicion
                </button>
            </div>
        </form>
    </div>

    <%@include file="/componentes/bodyDebajo.jsp" %>
</html>
