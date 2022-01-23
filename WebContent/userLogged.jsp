<%-- 
    Document   : userLogged
    Created on : Nov 20, 2020, 10:28:11 AM
    Author     : catal
--%>

<%@page language="java" contentType="text/html" pageEncoding="UTF-8" import="service.User"%>
<!DOCTYPE html>
<html>
      <head>
          <meta charset="UTF-8">
         <title>   User Logged Successfully   </title>
      </head>
	
      <body>
        <center>	
            Welcome <%= session.getAttribute("nume") + " " + session.getAttribute("prenume") + " functie= " + session.getAttribute("functie")%>
        </center>
      </body>
	
   </html>

