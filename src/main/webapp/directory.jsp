<%--
  Created by IntelliJ IDEA.
  User: thever4
  Date: 02.11.2021
  Time: 1:11
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page import="com.example.demoservlets.Directory" %>--%>
<%@ page import="com.example.demoservlets.FileInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demoservlets.Directory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<FileInfo> dir = (List<FileInfo>) request.getAttribute("directory"); %>
<% String path = (String) request.getAttribute("path"); %>
<% String date = (String) request.getAttribute("date"); %>
<% String baseurl = (String) request.getAttribute("baseurl"); %>
<html>
<head>
    <title>Directory</title>
</head>
<body>
<%=date%>
<h1><%=path%></h1>
<hr/>
<% if(!path.equals("/")) {%>
<img src="https://img.icons8.com/color/24/000000/folder-tree.png"/>
<a href="<%=String.format("%s?path=%s",
        baseurl,
        Directory.getParent(path))%>">Вверх</a>
<br/>
<% } %>
<table>
    <tr><th></th><th>Filename</th><th>Size</th><th>Last change</th></tr>
<% if (dir != null)
    for (FileInfo info : dir)   {%>
    <%String url = String.format("%s?path=%s%s%s",
            baseurl,
            path,
            info.getName(),
            info.isDirectory() ? "/" : "&download=true");%>
    <tr>
        <td><a href="<%=url%>"><img src=<%=info.isDirectory()
                ? "https://img.icons8.com/color/24/000000/folder-invoices--v1.png"
                : "https://img.icons8.com/color/24/000000/document--v1.png"%>
        /></a></td>
        <td><a href="<%=url%>">
            <%=info.getName()%>
        </a></td>
        <td><%=info.isDirectory() ? "" : info.getSize()%></td>
        <td><%=info.getLastChanged()%></td>
    </tr>
<%}%>
</table>
</body>
</html>
