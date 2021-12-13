package com.example.demoservlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;

@WebServlet(name = "AuthServlet", value = "/AuthServlet", urlPatterns = {"/login", "/signup"})
public class AuthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        uri = uri.substring(uri.lastIndexOf('/'));
        if(Arrays.asList(new String[] {"/login", "/signup"}).contains(uri)) {
            getServletContext().getRequestDispatcher(uri + ".jsp").forward(request, response);
        }
        else {
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] uri = request.getRequestURI().split("/");

        if(uri.length == 3 && Arrays.asList(new String[] {"login", "signup"}).contains(uri[2])) {
            response.getWriter().println(uri[2]);
        }
        else {
            response.getWriter().println("You sent an unknown request to API");
        }
    }
}
