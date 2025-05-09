<%@page import="logica.Carrera"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <%@include file="/componentes/head.jsp" %>
    <%@include file="/componentes/bodyArriba.jsp" %>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <h1 class="text-center mb-4">Crear Estudiante</h1>
                <form action="SvCrearEstudiante" method="POST">
                    <!-- DNI del Estudiante -->
                    <div class="mb-3">
                        <label for="dni" class="form-label">DNI del Estudiante</label>
                        <input type="text" class="form-control" id="dni" name="dni" required>
                    </div>

                    <!-- Nombre del Estudiante -->
                    <div class="mb-3">
                        <label for="nombre" class="form-label">Nombre</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" required>
                    </div>

                    <!-- Apellido del Estudiante -->
                    <div class="mb-3">
                        <label for="apellido" class="form-label">Apellido</label>
                        <input type="text" class="form-control" id="apellido" name="apellido" required>
                    </div>

                    <!-- Teléfono del Estudiante -->
                    <div class="mb-3">
                        <label for="telefono" class="form-label">Teléfono</label>
                        <input type="text" class="form-control" id="telefono" name="telefono">
                    </div>

                    <!-- Estado del Estudiante (Activo/Inactivo) -->
                    <div class="mb-3">
                        <label for="activo" class="form-label">Estado</label>
                        <select class="form-select" id="activo" name="activo" required>
                            <option value=1>Activo</option>
                            <option value=0>Inactivo</option>
                        </select>
                    </div>
                      <!-- Seleccionar Carrera -->
                    <div class="mb-3">
                        <label for="carrera" class="form-label">Carrera</label>
                        <select class="form-select" id="carrera" name="carrera" required>
                            <option value="">Seleccione una carrera</option>
                            <%
                                List<Carrera> listaCarreras = (List<Carrera>) request.getAttribute("listaCarreras");
                                if (listaCarreras != null) {
                                    for (Carrera carrera : listaCarreras) {
                            %>
                            <option value="<%= carrera.getId()%>"><%= carrera.getNombrecarrera()%></option>
                            <% 
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <!-- Botón de Envío -->
                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary">Guardar Estudiante</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <%@include file="/componentes/bodyDebajo.jsp" %>
</html> 
