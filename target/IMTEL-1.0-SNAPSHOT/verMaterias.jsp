<%@page import="logica.Materias"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <%@include file="/componentes/head.jsp" %>
    <%@include file="/componentes/bodyArriba.jsp" %>

    <div class="container mt-5">
        <h1 class="text-center mb-4">Lista de Materias</h1>

        <!-- Filtros -->
        <div class="d-flex justify-content-end mb-3">
            <form method="GET" action="SvListarMaterias" class="d-flex gap-2">
                <label for="filtroSemestre" class="form-label align-self-center mb-0">Filtrar por Semestre:</label>
                <select class="form-select" id="filtroSemestre" name="semestre">
                    <option value="">Todos</option>
                    <option value="1">1er Semestre</option>
                    <option value="2">2do Semestre</option>
                    <option value="3">3er Semestre</option>
                    <!-- Agregar más semestres si es necesario -->
                </select>
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-filter"></i> Aplicar Filtro
                </button>
            </form>
        </div>

        <!-- Encabezados de la tabla -->
        <div class="row border-bottom fw-bold py-3 text-center">
            <div class="col-md-3">ID</div>
            <div class="col-md-6">Nombre de la Materia</div>
            <div class="col-md-3">Semestre</div>
            
        </div>

        <!-- Lista de Materias -->
        <%
            List<Materias> listaMaterias = (List<Materias>) request.getAttribute("listaMaterias");

            if (listaMaterias != null && !listaMaterias.isEmpty()) {
                for (Materias materia : listaMaterias) {
        %>
        <div class="row border-bottom py-3 text-center">
            <div class="col-md-3"><%= materia.getIdMateria() %></div>
            <div class="col-md-6"><%= materia.getNombreMateria() %></div>
            <div class="col-md-3"><%= materia.getIdSemestrePertenece() %></div>
            
        </div>
        <%
                }
            } else {
        %>
        <div class="row border-bottom py-3 text-center">
            <div class="col-md-12">No hay materias registradas</div>
        </div>
        <% } %>
    </div>

    <%@include file="/componentes/bodyDebajo.jsp" %>
</html>
