package com.example.demoservlets;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "CoreServlet", value = "/CoreServlet", urlPatterns = {"/"})
public class CoreServlet extends HttpServlet {
    private final AccountService accountService;
    private String message;

    public CoreServlet() {
        this.accountService = new AccountService();
    }

    public CoreServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    public void init() {
        message = "Hello World";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sessionId = request.getSession().getId();
        UserProfile profile = accountService.getUserBySessionId(sessionId);
        getServletContext().setAttribute("accountService", accountService);
        response.sendRedirect(profile == null ? "login" : "list");

/*        response.setContentType("text/html");
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("<p>Для листинга файлов перейдите <a href=\"list\">сюда</a></p>");
        out.println("</body></html>");*/
    }

    public void destroy() {
    }
}