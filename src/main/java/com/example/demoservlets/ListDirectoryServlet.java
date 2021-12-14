package com.example.demoservlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "ListDirectoryServlet", value = "/ListDirectoryServlet", urlPatterns = {"/list"})
public class ListDirectoryServlet extends HttpServlet {

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

        UserProfile userProfile = accountService.getUserBySessionId(request.getSession().getId());
        if( userProfile == null ){
            response.sendRedirect("/");
        }
        String homeDirectory = userProfile.getFullPath();
        String filePathRepr = request.getParameter("path") == null ? homeDirectory : request.getParameter("path");


        if (!filePathRepr.startsWith(homeDirectory) || filePathRepr.contains("..") ){
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(new MessagesService("Ошибка", "Вы собираетесь зайти слишком далеко. Не пущу!"));
            return;
        }

        response.setContentType("text/html");
        String path = request.getParameter("path");
        String download = request.getParameter("download");
        if (download != null && download.equals("true")) {
            Path filePath = Paths.get(path);
            try (FileChannel fileChannel = FileChannel.open(filePath)) {
                String mimeType = "application/octet-stream";

                response.setContentType(mimeType);
                response.setContentLength((int) fileChannel.size());

                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", filePath.getFileName());
                response.setHeader(headerKey, headerValue);

                OutputStream outStream = response.getOutputStream();

                ByteBuffer buffer = ByteBuffer.allocate(1024);

                while (fileChannel.read(buffer) >= 0 || buffer.position() != 0) {
                    buffer.flip();
                    outStream.write(buffer.array(), buffer.position(), buffer.remaining());
                    buffer.clear();
                }

                fileChannel.close();
                outStream.close();
            }
        }
        else {
            if (path == null)
                path = filePathRepr;
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            request.setAttribute("date", formatter.format(date));
            request.setAttribute("path", path);
            request.setAttribute("baseurl", request.getRequestURL().toString());
            request.setAttribute("directory", Directory.listDirectory(path));
            getServletContext().getRequestDispatcher("/directory.jsp").forward(request, response);
        }
    }

}
