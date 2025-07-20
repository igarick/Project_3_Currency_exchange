<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: Игорь
  Date: 20.07.2025
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>First Jsp</title>
  </head>
  <body>
    <h1>First Jsp</h1>
    <p>
      <% for (int i = 0; i < 2; i++) {
          out.println("<p>" + "hi my friend" + i + "</p>");
          }
      %>
    </p>
  </body>
</html>
