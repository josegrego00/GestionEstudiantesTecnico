<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="logica.Carrera" %>
<%@ page import="logica.Materias" %>

<%
    Carrera carrera = (Carrera) request.getAttribute("carrera");
%>

<!DOCTYPE html>
<html lang="es">
    <%@include file="/componentes/head.jsp" %>
    <%@include file="/componentes/bodyArriba.jsp" %>

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <h1 class="text-center mb-4">Editar Carrera</h1>

                <form action="SvActualizarCarrera" method="POST" id="formCarrera">

                    <!-- ID oculto -->
                    <input type="hidden" name="idCarrera" value="<%= carrera.getId()%>">

                    <!-- Nombre de la Carrera -->
                    <div class="mb-3">
                        <label for="nombreCarrera" class="form-label">Nombre de la Carrera</label>
                        <input type="text" class="form-control" id="nombreCarrera" name="nombreCarrera" value="<%= carrera.getNombrecarrera()%>" required>
                    </div>

                    <!-- Número de Semestres -->
                    <div class="mb-3">
                        <label class="form-label">Cantidad de Semestres</label><br>
                        <% for (int i = 1; i <= 3; i++) {%>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="cantidadSemestres" id="semestre<%= i%>" value="<%= i%>" <%= (i == carrera.getCantidadSemestres()) ? "checked" : ""%>>
                            <label class="form-check-label" for="semestre<%= i%>"><%= i%></label>
                        </div>
                        <% } %>
                    </div>

                    <!-- Agregar Materias -->
                    <div class="mb-3">
                        <label for="nombreMateria" class="form-label">Nombre de la Materia</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="nombreMateria">
                            <button type="button" class="btn btn-secondary" onclick="agregarMateria()">Agregar Materia</button>
                        </div>
                    </div>

                    <!-- Tabla de Materias -->
                    <div class="mb-3">
                        <label class="form-label">Materias Agregadas</label>
                        <table class="table table-bordered" id="tablaMaterias">
                            <thead>
                                <tr>
                                    <th>Nombre</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (int i = 0; i < carrera.getMateriasList().size(); i++) {
                                        Materias materia = carrera.getMateriasList().get(i);%>
                                <tr>
                                    <td>
                                        <input type="hidden" name="materiaId" value="<%= materia.getIdMateria()%>">
                                        <input type="text" class="form-control" name="materiaNombre" value="<%= materia.getNombreMateria()%>">
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-danger btn-sm" onclick="eliminarMateria(this)">Eliminar</button>
                                    </td>
                                </tr>
                                <% }%>
                            </tbody>


                        </table>
                    </div>

                    <!-- Campos ocultos para nuevas materias -->
                    <div id="materiasHiddenFields"></div>

                    <!-- Botón para enviar formulario -->
                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <%@include file="/componentes/bodyDebajo.jsp" %>

    <script>
        function agregarMateria() {
            const inputMateria = document.getElementById("nombreMateria");
            const nombre = inputMateria.value.trim();
            if (nombre === "") {
                alert("El nombre de la materia no puede estar vacío.");
                return;
            }

            const tabla = document.getElementById("tablaMaterias").querySelector("tbody");
            const fila = document.createElement("tr");

            const celdaNombre = document.createElement("td");
            celdaNombre.textContent = nombre;

            const celdaAcciones = document.createElement("td");
            const botonEliminar = document.createElement("button");
            botonEliminar.type = "button";
            botonEliminar.className = "btn btn-danger btn-sm";
            botonEliminar.textContent = "Eliminar";
            botonEliminar.onclick = function () {
                eliminarMateria(this);
            };

            celdaAcciones.appendChild(botonEliminar);
            fila.appendChild(celdaNombre);
            fila.appendChild(celdaAcciones);
            tabla.appendChild(fila);

            const hiddenField = document.createElement("input");
            hiddenField.type = "hidden";
            hiddenField.name = "materias";
            hiddenField.value = nombre;
            document.getElementById("materiasHiddenFields").appendChild(hiddenField);

            inputMateria.value = "";
        }

        function eliminarMateria(button) {
            const row = button.closest("tr");
            const materiaNombre = row.querySelector("td").textContent;

            const hiddenFields = document.getElementsByName("materias");
            for (let field of hiddenFields) {
                if (field.value === materiaNombre) {
                    field.remove();
                    break;
                }
            }

            row.remove();
        }
    </script>

</html>
