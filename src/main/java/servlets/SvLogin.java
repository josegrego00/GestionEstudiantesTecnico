package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import logica.ControladoraLogica;
import logica.Usuario;

@WebServlet(name = "SvLogin", urlPatterns = {"/SvLogin"})
public class SvLogin extends HttpServlet {

    ControladoraLogica control = new ControladoraLogica();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener usuario y contraseña desde el formulario
        String nombreUsuario = request.getParameter("usuario");
        String password = request.getParameter("password");

        // Buscar usuario por nombre
        Usuario usuario = control.validarUsuario(nombreUsuario, password);

        if (usuario != null) {
            // Usuario válido -> guardar en sesión y redirigir
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuario);
            response.sendRedirect("index.jsp");  // o la página principal
        } else {
            // Usuario inválido -> volver al login con mensaje
            request.getSession().setAttribute("mensajeError", "Usuario o contraseña incorrectos.");
            response.sendRedirect("login.jsp");
        }
    }
}
