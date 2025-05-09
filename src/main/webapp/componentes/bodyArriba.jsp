
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
                    <li><a class="dropdown-item" href="#">Cambiar Contrase�a</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="#">Cerrar Sesi�n</a></li>
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
                        <!-- Men� Principal -->
                        <div class="sb-sidenav-menu-heading">Men�</div>
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