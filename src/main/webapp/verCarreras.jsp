<%@page import="logica.Materias"%>
<%@page import="logica.Carrera"%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>


<!DOCTYPE html>
<html lang="es">
    <%@include file="/componentes/head.jsp" %>
    <%@include file="/componentes/bodyArriba.jsp" %>

    <div class="container mt-5">
        <h1 class="text-center mb-4">Listado de Carreras</h1>
        <%-- Mostrar mensajes de éxito o error --%>
        <div class="row justify-content-center mb-3">
            <div class="col-md-8">
                <% if (session.getAttribute("mensajeExito") != null) {%>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <%= session.getAttribute("mensajeExito")%>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% session.removeAttribute("mensajeExito"); %>
                <% } %>
                <% if (session.getAttribute("mensajeError") != null) {%>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <%= session.getAttribute("mensajeError")%>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% session.removeAttribute("mensajeError"); %>
                <% } %>
            </div>
        </div>

        <%
            List<Carrera> listaCarreras = (List<Carrera>) request.getAttribute("listaCarreras");
            if (listaCarreras != null && !listaCarreras.isEmpty()) {
        %>
        <div class="accordion" id="accordionCarreras">
            <% for (int i = 0; i < listaCarreras.size(); i++) {
                    Carrera carrera = listaCarreras.get(i);
            %>
            <div class="accordion-item">
                <h2 class="accordion-header" id="heading<%=i%>">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse<%=i%>" aria-expanded="false" aria-controls="collapse<%=i%>">
                        <%= carrera.getNombrecarrera()%> - <%= carrera.getCantidadSemestres()%> semestre(s)
                    </button>
                </h2>
                <div id="collapse<%=i%>" class="accordion-collapse collapse" aria-labelledby="heading<%=i%>" data-bs-parent="#accordionCarreras">
                    <div class="accordion-body">
                        <h6>Materias:</h6>
                        <ul>
                            <% for (Materias materia : carrera.getMateriasList()) {%>
                            <li><%= materia.getNombreMateria()%></li>
                                <% }%>
                        </ul>
                        <!-- Botón de edición al final -->
                        <div class="mt-3 text-end">
                            <form action="SvEditarCarrera" method="GET">
                                <input type="hidden" name="idCarrera" value="<%= carrera.getId()%>">
                                <button type="submit" class="btn btn-warning btn-sm">Editar</button>
                            </form>

                        </div>

                    </div>
                </div>
            </div>
            <% } %>
        </div>
        <% } else { %>
        <div class="alert alert-info">No hay carreras registradas.</div>
        <% }%>
    </div>

    <%@include file="/componentes/bodyDebajo.jsp" %>
</html>
