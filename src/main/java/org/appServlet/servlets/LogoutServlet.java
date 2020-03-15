package org.appServlet.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet(description = "Logout Servlet", urlPatterns = "/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        Cookie loginCookie = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("JSESSIONID")){
                    System.out.println("JSESSIONID="+cookie.getValue());
                    loginCookie = cookie;
                    break;
                }
            }
            if(loginCookie != null){
                loginCookie.setMaxAge(0);
                response.addCookie(loginCookie);
            }
            //invalidate the session if exists
            HttpSession session = request.getSession(false);
            System.out.println("User="+session.getAttribute("user"));
            if(session != null){
                session.invalidate();
            }
            response.sendRedirect("login.html");
        }
    }
}
