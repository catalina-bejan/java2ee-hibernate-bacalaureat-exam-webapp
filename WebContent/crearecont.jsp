<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Examenul de Bacalaureat</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">    

<!-- header band with title and navigation menu -->
<style>
body { 
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
}

.header {
  overflow: hidden;
  background-color: #380237;
  padding: 10px 10px;
}

.header a {
  float: left;
  color: white;
  text-align: center;
  padding: 5px;
  text-decoration: none;
  font-size: 18px; 
  line-height: 25px;
  border-radius: 4px;
}

.header a.logo {
  font-size: 25px;
  font-weight: bold;
}

.header a:hover {
  background-color: #d194bf;
  color: black;
  transition-duration: 0.4s;
}

.header a.active {
  background-color: #d194bf;
  color: white;
}

.header-right {
  float: right;
}

@media screen and (max-width: 500px) {
  .header a {
    float: none;
    display: block;
    text-align: left;
  }
  
  .header-right {
    float: none;
  }
}
</style>
<div class="header">
  <a href="rezultate.jsp" class="logo">Examenul de Bacalaureat</a>
  <div class="header-right">
    <a href="rezultate.jsp">Rezultate</a>
    <a href="login.jsp">Logare</a>
    <a class="active" href="crearecont.jsp">Creare cont</a>
  </div>
</div>
<style type="text/css">
        body{ font: 14px sans-serif; }
        .wrapper{ width: 350px; padding: 20px; }
</style>
</head>
<style>
.centeredborder {
  margin: auto;
  width: 90%;
/*   border: 3px solid #ad2183; */
  background: #d194bf;
  background-clip: border-box;  
  padding: 10px;
}
.centered {
  margin: auto;
  width: 70%;
  padding: 10px;
}
.center {
  margin: auto;
  width: 100%;
  border: 5px solid #ad2183;
  border-radius: 20px;
  padding: 10px;
}
body {
  background-image: url('images/purple_background.jpg');
}
</style>

<!--alert errors-->
<% if(session.getAttribute("errorCNP")!=null) {%>
<script>
alert("<%= session.getAttribute("errorCNP").toString() %>");
</script>
<%} %>

<!--alert errors-->
<% if(session.getAttribute("errorUsername")!=null) {%>
<script>
alert("<%= session.getAttribute("errorUsername").toString() %>");
</script>
<%} %>

<body>
<br>
<center>
    <div class="wrapper">
    <div class="center" style="background:#debad3">
        <h2 style="color:#ad2183">Creare cont</h2>
        <p>Va rog sa completati datele pentru crearea contului.</p>
        <form action="CreareContServlet" method="post">
        	<div class="form-group">
                <label>CNP</label>
                <input type="text" name="cnp" class="form-control <% if(session.getAttribute("errorCNP")!=null) {%> is-invalid" <%} else {%> " <%} %> placeholder="1234567891234" required pattern="[0-9]{13}" title="13 cifre">
<!--                 <span class="help-block"></span> -->
            </div>   
        	<div class="form-group">
                <label>Nume de utilizator</label>
                <input type="text" name="username" class="form-control  <% if(session.getAttribute("errorUsername")!=null) {%> is-invalid" <%} else {%> " <%} %> required pattern="[A-Za-z0-9]+" title="Se accepta doar litere si cifre">
<!--                 <span class="help-block"></span> -->
            </div>   
            <div class="form-group">
                <label>Parola</label>
                <input type="password" name="password" class="form-control" required pattern="[A-Za-z0-9]+" title="Se accepta doar litere si cifre">
                <!--<span class="help-block"></span>-->
            </div>
            <div class="form-group">
                <input type="submit" name="submit" class="btn btn-primary" value="Creare cont">
            </div>
        </form>
    </div>
    </div>
</center>
</body>
<% session.removeAttribute("errorCNP"); %>
<% session.removeAttribute("errorUsername"); %>
</html>