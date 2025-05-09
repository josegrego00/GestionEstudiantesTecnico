<%@page import="logica.Carrera"%>
<%@page import="logica.NivelCursoEstudiante"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <%@include file="/componentes/head.jsp" %>
    <%@include file="/componentes/bodyArriba.jsp" %>

    <div class="container mt-5">
        <h1 class="text-center mb-4">Promoción de Estudiantes</h1>

        <%
            String mensaje = (String) request.getAttribute("mensaje");
            if (mensaje != null) {
        %>
        <div class="alert alert-info text-center"><%= mensaje%></div>
        <%
            }
        %>

        <!-- Filtros -->
        <form method="GET" action="SvListarEstudiantesPromover" class="row g-3 mb-4">

            <div class="col-md-4">
                <label for="carrera" class="form-label">Filtrar por Carrera:</label>
                <select name="carrera" id="carrera" class="form-select">
                    <option value="">-- Todas las carreras --</option>
                    <%
                        List<Carrera> listaCarreras = (List<Carrera>) request.getAttribute("listaCarreras");
                        String carreraSeleccionada = (String) request.getAttribute("carreraSeleccionada");
                        if (listaCarreras != null) {
                            for (Carrera carrera : listaCarreras) {
                                int idCarrera = carrera.getId(); 
                                System.out.println(idCarrera);// Obtenemos el ID de la carrera
                                String nombreCarrera = carrera.getNombrecarrera();
                    %>
                    <!-- Asignamos el ID de la carrera como valor de la opción -->
                    <option value="<%= idCarrera%>" <%= (String.valueOf(idCarrera).equals(carreraSeleccionada)) ? "selected" : ""%>>
                        <%= nombreCarrera%>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>

            </div>

            <div class="col-md-4">
                <label for="semestre" class="form-label">Filtrar por Semestre:</label>
                <select name="semestre" id="semestre" class="form-select">
                    <option value="">-- Todos los semestres --</option>
                    <%
                        String semestreSeleccionado = (String) request.getAttribute("semestreSeleccionado");
                        for (int i = 1; i <= 3; i++) {
                    %>
                    <option value="<%= i%>" <%= (semestreSeleccionado != null && semestreSeleccionado.equals(String.valueOf(i))) ? "selected" : ""%>>
                        <%= i%>° Semestre
                    </option>
                    <% } %>
                </select>
            </div>

            <div class="col-md-4 d-flex align-items-end gap-2">
                <button type="submit" class="btn btn-primary w-100">Filtrar</button>
                <button type="button" class="btn btn-secondary w-100" onclick="seleccionarTodosCheckboxes()">
                    Seleccionar Todos
                </button>
            </div>
        </form>

        <!-- Formulario para promover estudiantes -->
        <form action="SvPromoverEstudiantes" method="POST" onsubmit="return validarSeleccion()">
            <div class="row border-bottom fw-bold py-3 text-center">
                <div class="col-md-1">
                    <input type="checkbox" id="selectAll" onclick="toggleCheckboxes(this)">
                </div>
                <div class="col-md-2">DNI</div>
                <div class="col-md-2">Nombre</div>
                <div class="col-md-2">Apellido</div>
                <div class="col-md-1">Sem</div>
                <div class="col-md-2">Carrera</div>
                <div class="col-md-1">Activo</div>
            </div>

            <%
                List<NivelCursoEstudiante> listaFiltrada = (List<NivelCursoEstudiante>) request.getAttribute("listaEstudiantesFiltrados");

                if (listaFiltrada != null && !listaFiltrada.isEmpty()) {
                    for (NivelCursoEstudiante nce : listaFiltrada) {
                        String dni = nce.getDniEstudiante().getDniEstudiante();
                        String nombre = nce.getDniEstudiante().getNombreEstudiante();
                        String apellido = nce.getDniEstudiante().getApellidoEstudiante();
                        int semestre = nce.getIdSemestre().getIdSemestre();
                        String carrera = nce.getIdCarrera().getNombrecarrera();
                     
                        boolean activo = nce.getDniEstudiante().getActivo();
            %>
            <div class="row border-bottom py-2 text-center align-items-center">
                <div class="col-md-1">
                   <input type="checkbox" name="dniSeleccionados" value="<%= dni + '|' + nce.getIdCarrera().getId() + '|' + nce.getIdSemestre().getIdSemestre() %>">

                </div>
                <div class="col-md-2"><%= dni%></div>
                <div class="col-md-2"><%= nombre%></div>
                <div class="col-md-2"><%= apellido%></div>
                <div class="col-md-1"><%= semestre%></div>
                <div class="col-md-2"><%= carrera%></div>
                <div class="col-md-1"><%= activo ? "Sí" : "No"%></div>
            </div>
            <%
                }
            } else {
            %>
            <div class="row py-3 text-center">
                <div class="col-12">No hay estudiantes para mostrar.</div>
            </div>
            <% }%>

            <!-- Botón para promover -->
            <div class="row mt-4">
                <div class="col text-center">
                    <button type="submit" class="btn btn-success px-4">
                        <i class="fas fa-arrow-up"></i> Promover seleccionados
                    </button>
                </div>
            </div>
        </form>
    </div>

    <script>
        let todosSeleccionados = false;

        function seleccionarTodosCheckboxes() {
            const checkboxes = document.querySelectorAll('input[name="dniSeleccionados"]');
            todosSeleccionados = !todosSeleccionados;
            checkboxes.forEach(checkbox => checkbox.checked = todosSeleccionados);
        }

        function validarSeleccion() {
            const seleccionados = document.querySelectorAll('input[name="dniSeleccionados"]:checked');
            if (seleccionados.length === 0) {
                alert("Debe seleccionar al menos un estudiante.");
                return false;
            }
            return true;
        }

        function toggleCheckboxes(source) {
            const checkboxes = document.querySelectorAll('input[name="dniSeleccionados"]');
            checkboxes.forEach(cb => cb.checked = source.checked);
        }
    </script>

    <%@include file="/componentes/bodyDebajo.jsp" %>
</html>
