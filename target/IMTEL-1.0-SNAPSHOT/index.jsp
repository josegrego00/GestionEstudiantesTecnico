<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="Sistema de gestión académica">
        <meta name="author" content="TuNombre">
        <title>Sistema IMTEL</title>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome Icons -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link href="css/styles.css" rel="stylesheet">
    </head>
    <body class="sb-nav-fixed">
        <!-- Navbar -->
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <a class="navbar-brand ps-3" href="index.jsp">
                <i class="fas fa-book"></i> Sistema IMTEL
            </a>
            <!-- Sidebar Toggle -->
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle">
                <i class="fas fa-bars"></i>
            </button>
            <!-- Navbar -->
            <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown">
                        <i class="fas fa-user fa-fw"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a class="dropdown-item" href="#">Cambiar Contraseña</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="#">Cerrar Sesión</a></li>
                    </ul>
                </li>
            </ul>
        </nav>

        <!-- Sidebar -->
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <!-- Menú Principal -->
                            <div class="sb-sidenav-menu-heading">Menú</div>
                            <a class="nav-link" href="index.jsp">
                                <div class="sb-nav-link-icon"><i class="fas fa-home"></i></div>
                                Inicio
                            </a>

                            <!-- Estudiantes -->
                            <div class="sb-sidenav-menu-heading">Estudiantes</div>
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseEstudiantes">
                                <div class="sb-nav-link-icon"><i class="fas fa-users"></i></div>
                                Estudiantes
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseEstudiantes">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="SvListarEstudiantes">Ver Estudiantes</a>
                                    <a class="nav-link" href="SvCrearEstudiante">Crear Estudiante</a>
                                    <a class="nav-link" href="editarEstudiante.jsp">Editar Estudiante</a>

                                </nav>
                            </div>

                            <!-- Materias -->
                            <div class="sb-sidenav-menu-heading">Carreras y Materias</div>
                            <!-- carreras-->
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseCarreras">
                                <div class="sb-nav-link-icon"><i class="fas fa-book"></i></div>
                                Carreras
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseCarreras">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="SvVerCarreras">Ver Carreras/Editar</a>
                                    <a class="nav-link" href="crearCarrera.jsp">Crear Carreras</a>
                                </nav>
                            </div>

                            <!-- Relaciones -->
                            <div class="sb-sidenav-menu-heading">Acciones</div>
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseRelaciones">
                                <div class="sb-nav-link-icon"><i class="fas fa-link"></i></div>
                                Varios
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseRelaciones">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="SvListarEstudiantesFiltro">Ver Materias de estudiantes</a>
                                    <a class="nav-link" href="addCarrera.jsp">Add. Carrera Estudiante</a>
                                    <a class="nav-link" href="cargarLoteEstudiantes.jsp">Crear Estudiantes por Lotes</a>
                                    <a class="nav-link" href="SvListarEstudiantesPromover">Promover Estudiantes</a>

                                </nav>
                            </div>

                            <!-- Reportes -->
                            <div class="sb-sidenav-menu-heading">Reportes</div>
                            <a class="nav-link" href="SvFiltrarReportesCarreras">
                                <div class="sb-nav-link-icon"><i class="fas fa-chart-bar"></i></div>
                                Generar Filtros
                            </a>
                        </div>
                    </div>
                    <!-- Footer del Sidebar -->
                    <div class="sb-sidenav-footer">
                        <div class="small">Usuario:</div>
                        Nombre del Usuario
                    </div>
                </nav>
            </div>

            <!-- Contenido Principal -->
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Bienvenido al Sistema IMTEL</h1>
                        <p>Gestiona estudiantes, materias, semestres y sus relaciones de manera eficiente.</p>

                        <!-- Imagen de bienvenida -->
                        <div class="text-center mt-4">
                            <img src="imagenes/imtel.png" alt="Imagen de bienvenida" class="img-fluid rounded" style="max-width: 100%; height: auto;">
                        </div>
                    </div>
                </main>


                <!-- Footer -->
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright &copy; Sistema IMTEL 2025</div>
                            <div>
                                <a href="#">Política de Privacidad</a> &middot;
                                <a href="#">Términos y Condiciones</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>


        </div>

        <!-- Bootstrap JS y dependencias -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Custom Scripts -->
        <script src="js/scripts.js"></script>
    </body>
</html>