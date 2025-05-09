<%@page import="logica.Materias"%>
<%@page import="java.util.List"%>
<%@page import="logica.Semestre"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <%@include file="/componentes/head.jsp" %>
    <%@include file="/componentes/bodyArriba.jsp" %>

    <div class="container mt-5">
        <h1 class="text-center mb-4">Editar Materia</h1>

        <!-- Row para el formulario de búsqueda -->
        <div class="row justify-content-center">
            <div class="col-md-6">
                <form action="SvBuscarMateria" method="POST">
                    <div class="mb-3 d-flex">
                        <input type="text" class="form-control me-2" placeholder="Ingrese ID o Nombre" name="materia_buscar">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-search"></i> Buscar
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Mostrar mensaje de error si la materia no se encuentra -->
        <% String mensajeError = (String) request.getAttribute("mensajeError"); %>
        <% if (mensajeError != null) { %>
            <div class="row justify-content-center">
                <div class="col-md-6">
                    <div class="alert alert-danger text-center"><%= mensajeError %></div>
                </div>
            </div>
        <% } %>

        <% Materias materia = (Materias) request.getAttribute("materia"); %>
        <% List<Semestre> listaSemestres = (List<Semestre>) request.getAttribute("listaSemestres"); %>

        <% if (materia != null) { %>
        <div class="row justify-content-center">
            <div class="col-md-6">
                <!-- Formulario de edición -->
                <form action="SvGuardarEdicionMateria" method="POST">
                    <div class="mb-3">
                        <label for="id_materia" class="form-label">ID Materia</label>
                        <input type="text" class="form-control" id="id_materia" name="id_materia" readonly required value="<%= materia.getIdMateria() %>">
                    </div>

                    <div class="mb-3">
                        <label for="nombre_materia" class="form-label">Nombre de la Materia</label>
                        <input type="text" class="form-control" id="nombre_materia" name="nombre_materia" required value="<%= materia.getNombreMateria() %>">
                    </div>

                    <div class="mb-3">
                        <label for="id_semestre" class="form-label">Semestre</label>
                        <select class="form-select" id="id_semestre" name="id_semestre" required>
                            <% for (Semestre semestre : listaSemestres) { %>
                                <option value="<%= semestre.getIdSemestre() %>" <%= (materia.getIdSemestrePertenece() == semestre.getIdSemestre()) ? "selected" : "" %>>
                                    <%= semestre.getNombreSemestre() %>
                                </option>
                            <% } %>
                        </select>
                    </div>

                    <div class="row">
                        <div class="col">
                            <button type="submit" class="btn btn-success w-100">
                                <i class="fas fa-edit"></i> Guardar Edición
                            </button>
                        </div>
                       
                    </div>
                </form>
            </div>
        </div>
        <% } %>
    </div>

    <%@include file="/componentes/bodyDebajo.jsp" %>
</html>
