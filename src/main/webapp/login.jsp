<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login - Sistema de Carreras</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

    <div class="container">
        <div class="row justify-content-center align-items-center" style="height: 100vh;">
            <div class="col-md-4">
                <div class="card shadow">
                    <div class="card-body">
                        <h4 class="text-center mb-4">Iniciar Sesión</h4>

                        <!-- Mostrar mensaje de error -->
                        <%
                            String mensajeError = (String) session.getAttribute("mensajeError");
                            if (mensajeError != null) {
                        %>
                            <div class="alert alert-danger">
                                <%= mensajeError %>
                            </div>
                        <%
                                session.removeAttribute("mensajeError");
                            }
                        %>

                        <form action="SvLogin" method="POST">
                            <div class="mb-3">
                                <label for="usuario" class="form-label">Usuario</label>
                                <input type="text" class="form-control" id="usuario" name="usuario" required>
                            </div>
                            <div class="mb-3">
                                <label for="password" class="form-label">Contraseña</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>
                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary">Ingresar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
