<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="model.Proba"%>
<%@ page import="api.ProbaDao"%>
<%@ page import="model.Elev"%>
<%@ page import="api.ElevDao"%>
<%@ page import="model.Profil"%>
<%@ page import="api.ProfilDao"%>
<%@ page import="model.Liceu"%>
<%@ page import="api.LiceuDao"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Contestatie</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">    
<!-- 	link for jQuery and ajax -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- header band with title and navigation menu -->
<style>
body { 
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
	background-image: url('images/elev_background.gif');
	background-position: right top;
	background-size: cover;
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

.header form {
  float: left;
  color: white;
  text-align: center;
  padding: 5px;
  text-decoration: none;
  font-size: 18px; 
  line-height: 25px;
  border-radius: 4px;
}

.header form.logo {
  font-size: 25px;
  font-weight: bold;
}

.header form:hover {
  background-color: #d194bf;
  color: black;
  transition-duration: 0.4s;
}

.header form.active {
  background-color: #d194bf;
  color: white;
}

.header button {
  float: left;
  color: white;
  text-align: center;
  padding: 5px;
  text-decoration: none;
  font-size: 18px; 
  line-height: 10px;
  border-radius: 4px;
  background-color: #380237;
}

.header button.logo {
  font-size: 25px;
  font-weight: bold;
}

.header button:hover {
  background-color: #d194bf;
  color: black;
  transition-duration: 0.4s;
}

.header button.active {
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
.center {
  margin: auto;
  max-width: 100%;
  border: 5px solid #ad2183;
  border-radius: 20px;
  padding: 10px;
}

.rezultate {
  font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

.rezultate td, .rezultate th {
  border: 1px solid #d194bf;
  padding: 8px;
}

.rezultate tr:nth-child(even){background-color: #debad3;}
.rezultate tr:nth-child(odd){background-color: white;}

.rezultate tr:hover {background-color: #d194bf;}

.rezultate th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #ad2183;
  color: white;
  cursor: pointer;
}
</style>

	   <% 
	   ProbaDao probaDao = new ProbaDao();
       ProfilDao profilDao = new ProfilDao();
       ElevDao elevDao = new ElevDao();
       LiceuDao liceuDao = new LiceuDao();
       Elev elev = elevDao.getElevByCnp(session.getAttribute("cnpElev").toString());
       Profil profil = profilDao.getProfilOfElev(elev);
       Liceu liceu = liceuDao.getLiceuByElev(elev);
       %>

<!--alert alert1-->
<% if(session.getAttribute("alert1")!=null) {%>
<script>
alert("<%= session.getAttribute("alert1").toString() %>");
</script>
<%} %>

<!--alert alert2-->
<% if(session.getAttribute("alert2")!=null) {%>
<script>
alert("<%= session.getAttribute("alert2").toString() %>");
</script>
<%} %>

<!--alert alert3-->
<% if(session.getAttribute("alert3")!=null) {%>
<script>
alert("<%= session.getAttribute("alert3").toString() %>");
</script>
<%} %>

<div class="header">
  <a href="rezultate.jsp" class="logo">Examenul de Bacalaureat</a>
  <div class="header-right">
  	<a href="elevpage.jsp"><%= session.getAttribute("nume")+" "+session.getAttribute("prenume") %></a>
  	<a class="active" href="contestatie.jsp">Contestatie</a>
    <a href="rezultate.jsp">Rezultate</a>
  	<form action ="ContestatieServlet" method="post">
		<button type="delogare" name="delogare">Delogare</button>
	</form>
  </div>
</div>
<style>
        body{ font: 14px sans-serif; }
        .wrapper{float:left; width: 450px; padding: 20px; }
		.wrapper2{float:left; width: 500px; height: 570px; overflow-y:auto; padding: 20px; margin-left:15px; margin-right:15px;}
</style>
</head>
<body>
<style type="text/css">
    .divOuter{
        display:inline;
    }

    .divInner1, .divInner2, .divInner3{
        float:left;
        width:150px;
        height:150px;
        margin-left:3px;
        margin-right:3px;
    }
</style>

<!-- 	<div class="one"> -->
    <div class="wrapper">
    <div class="center" style="background:#debad3">
<!--     <div class="custom-control custom-control-inline"> -->
    <h5>Bifeaza probele la care doresti sa faci contestatie.</h5>
    <br>
        <form action="ContestatieServlet" method="post">
            <div class="form-group">
                
                <input type="checkbox" name="proba1" value="Proba1">
            	<label style="font-size: 130%"><%= probaDao.getProbaBy(elev, 1).getDenumire() %></label>
<!--                 <span class="help-block"></span> -->
            </div>    
            <div class="form-group">
                
               	<input type="checkbox" name="proba2" value="Proba2">
            	<label style="font-size: 130%"><%= probaDao.getProbaBy(elev, 2).getDenumire() %></label>
                <!--<span class="help-block"></span>-->
            </div>
        	<div class="form-group">
				<input type="checkbox" name="proba3" value="Proba3">
            	<label style="font-size: 130%"><%= probaDao.getProbaAleasaBy(elev).getDenumire() %></label>
            </div>
            <div class="form-group">
            <center>
                <input id="submitAdauga" type="submit" name="adauga" class="btn btn-primary" value="Trimite contestatie">
            </center>
            </div>
        </form>
    </div>
    </div>

<% session.removeAttribute("alert1"); %>
<% session.removeAttribute("alert2"); %>
<% session.removeAttribute("alert3"); %>

<!-- prevent resubmit on refresh -->
<script>
if ( window.history.replaceState ) {
  window.history.replaceState( null, null, window.location.href );
}
</script>
</body>
</html>