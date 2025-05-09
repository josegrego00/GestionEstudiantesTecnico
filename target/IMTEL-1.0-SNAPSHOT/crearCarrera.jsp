<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="es">
    <%@include file="/componentes/head.jsp" %>
    <%@include file="/componentes/bodyArriba.jsp" %>
    <%@ page import="java.util.List" %>
    <%@ page import="java.util.Arrays" %>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <h1 class="text-center mb-4">Crear Carrera</h1>

                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <%-- Mensajes de éxito/error --%>
                        <% if (request.getSession().getAttribute("mensajeExito") != null) {%>
                        <div class="alert alert-success alert-dismissible fade show" role="alert">
                            <%= request.getSession().getAttribute("mensajeExito")%>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                        <% request.getSession().removeAttribute("mensajeExito"); %>
                        <% } %>

                        <% if (request.getSession().getAttribute("mensajeError") != null) {%>
                        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            <%= request.getSession().getAttribute("mensajeError")%>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                        <% request.getSession().removeAttribute("mensajeError"); %>
                        <% } %>
                    </div>
                </div>
                <form action="SvCrearCarreras" method="POST" id="formCarrera">

                    <!-- Nombre de la Carrera -->
                    <div class="mb-3">
                        <label for="nombreCarrera" class="form-label">Nombre de la Carrera</label>
                        <input type="text" class="form-control" id="nombreCarrera" name="nombreCarrera" required>
                    </div>

                    <!-- Número de Semestres -->
                    <div class="mb-3">
                        <label class="form-label">Cantidad de Semestres</label><br>
                        <% for (int i = 1; i <= 3; i++) {%>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="cantidadSemestres" id="semestre<%= i%>" value="<%= i%>" <%= (i == 1) ? "checked" : ""%>>
                            <label class="form-check-label" for="semestre<%= i%>"><%= i%></label>
                        </div>
                        <% }%>
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
                                <!-- Las materias se agregan aquí dinámicamente -->
                            </tbody>
                        </table>
                    </div>

                    <!-- Campos ocultos para enviar las materias con el formulario -->
                    <div id="materiasHiddenFields"></div>

                    <!-- Botón para enviar formulario -->
                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary">Guardar Carrera</button>
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

            // Crear una fila para la tabla
            const tabla = document.getElementById("tablaMaterias").querySelector("tbody");
            const fila = document.createElement("tr");

            // Celda para el nombre
            const celdaNombre = document.createElement("td");
            celdaNombre.textContent = nombre;

            // Celda para el botón de eliminar
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

            // Crear un campo oculto para enviar la materia con el formulario
            const hiddenField = document.createElement("input");
            hiddenField.type = "hidden";
            hiddenField.name = "materias";
            hiddenField.value = nombre;

            // Agregar el campo oculto al formulario
            document.getElementById("materiasHiddenFields").appendChild(hiddenField);

            // Limpiar el campo de entrada
            inputMateria.value = "";
        }
        // Función para eliminar una materia de la tabla y los campos ocultos
        function eliminarMateria(button) {
            const row = button.closest("tr");
            const materiaNombre = row.querySelector("td").textContent;

            // Eliminar el campo oculto correspondiente
            const hiddenFields = document.getElementsByName("materias");
            for (let field of hiddenFields) {
                if (field.value === materiaNombre) {
                    field.remove();
                    break;
                }
            }

            // Eliminar la fila de la tabla
            row.remove();
        }
    </script>

</html>
