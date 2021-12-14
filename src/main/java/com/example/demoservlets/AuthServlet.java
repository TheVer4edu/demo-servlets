package com.example.demoservlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

@WebServlet(name = "AuthServlet", value = "/AuthServlet", urlPatterns = {"/login", "/signup"})
public class AuthServlet extends HttpServlet {

    private AccountService accountService;

    private void SetService(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.accountService = (AccountService) getServletContext().getAttribute("accountService");
            if(this.accountService == null)
                throw new IllegalStateException("Account now available");
        }
        catch (Exception e) {
            try {
                response.sendRedirect("/");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SetService(request, response);
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
        SetService(request, response);
        String[] uri = request.getRequestURI().split("/");

        if(uri.length == 3 && Arrays.asList(new String[] {"login", "signup"}).contains(uri[2])) {
            String login = request.getParameter("login");
            String pass = request.getParameter("password");
            String email = request.getParameter("email");


            if(Objects.equals(uri[2], "login")){
                if (login == null || pass == null) {
                    response.getWriter().println(new MessagesService("error", "You need to enter all required fields"));
                    return;
                }

                UserProfile profile = accountService.getUserByLogin(login);
                if (profile == null || !profile.getPass().equals(pass)) {
                    response.getWriter().println(new MessagesService("error", "User with that login was not found or password is incorrect"));
                    return;
                }

                accountService.addSession(request.getSession().getId(), profile);
                response.getWriter().println(new MessagesService("redirect", "list"));
                response.sendRedirect("list");

            } else if(Objects.equals(uri[2], "signup")){
                if (login == null || pass == null || email == null) {
                    response.getWriter().println(new MessagesService("error", "You need to enter all required fields"));
                    return;
                }

                if (accountService.getUserByLogin(login) != null) {
                    response.getWriter().println(new MessagesService("error", "User with this login is already registered"));
                    return;
                }

                if (!email.contains("@")) {
                    response.getWriter().println(new MessagesService("error", "Email has bad format"));
                    return;
                }

                UserProfile profile = new UserProfile(login.toLowerCase(Locale.ROOT), pass, email);
                accountService.addNewUser(profile);
                accountService.addSession(request.getSession().getId(), profile);
                response.getWriter().println(new MessagesService("redirect", "list"));
                response.sendRedirect("list");
            }
        }
        else {
            response.getWriter().println("You sent an unknown request to API");
        }
    }
}
