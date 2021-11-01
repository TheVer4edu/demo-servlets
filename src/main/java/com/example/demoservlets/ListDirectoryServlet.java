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

@WebServlet(name = "ListDirectoryServlet", value = "/list")
public class ListDirectoryServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                    path = "/";
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                request.setAttribute("date", formatter.format(date));
                request.setAttribute("path", path);
                request.setAttribute("baseurl", request.getRequestURI());
                request.setAttribute("directory", Directory.listDirectory(path));
                getServletContext().getRequestDispatcher("/directory.jsp").forward(request, response);
            }
        }

}