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
    <title>Elev</title>
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
       
<!--alert errors-->
<% if(session.getAttribute("errorOptiune")!=null) {%>
<script>
alert("<%= session.getAttribute("errorOptiune").toString() %>");
</script>
<%} %>

<!--alert succes-->
<% if(session.getAttribute("succes")!=null) {%>
<script>
alert("<%= session.getAttribute("succes").toString() %>");
</script>
<%} %>

<div class="header">
  <a href="rezultate.jsp" class="logo">Examenul de Bacalaureat</a>
  <div class="header-right">
  	<a class="active" href="elevpage.jsp"><%= session.getAttribute("nume")+" "+session.getAttribute("prenume") %></a>
  	<a href="contestatie.jsp">Contestatie</a>
    <a href="rezultate.jsp">Rezultate</a>
  	<form action ="ElevServlet" method="post">
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
<!--     <p>Te rog sa introduci disciplina la alegere.</p> -->
        <form action="ElevServlet" method="post">
            <div class="form-group">
                <label>Liceu</label>
                <input type="text" name="liceu" class="form-control" value='<%= liceu.getDenumire() %>' readonly>
<!--                 <span class="help-block"></span> -->
            </div>    
            <div class="form-group">
                <label>Profil</label>
                <input type="text" name="profil" class="form-control" value='<%= profil.getDenumire() %>' readonly>
                <!--<span class="help-block"></span>-->
            </div>
        	<div class="form-group">
				<!--comboBox  liceu-->
            	<label>Disciplina la alegere</label>
				<select id="disciplinacombobox" name="disciplinacombobox" class="form-control">
                	<% 
	    	        long tipProba = 3;
	    	        if(probaDao.getProbaAleasaBy(elev)==null){
	    	        %>
						<option disabled selected value>Selecteaza disciplina la alegere</option>
	    	        <% 
	    	        List<Proba> probaList = probaDao.getProbaListBy(profil, tipProba);
		    	        for(Proba proba : probaList){
		    	        %>
		    	        	<option value=<%= proba.getDenumire() %>><%= proba.getDenumire() %></option>
		    	        <% 
		    	        }
					}else {
					%>
	    	        	<option selected value><%= probaDao.getProbaAleasaBy(elev).getDenumire() %></option>
	    	        <% 
					}
	    	        %>
				</select>
            </div>
            <div class="form-group">
            <center>
                <input id="submitAdauga" type="submit" name="adauga" class="btn btn-primary" value="Adauga disciplina la alegere">
            </center>
            </div>
        </form>
    </div>
    </div>

<% session.removeAttribute("errorOptiune"); %>
<% session.removeAttribute("succes"); %>

<!-- prevent resubmit on refresh -->
<script>
if ( window.history.replaceState ) {
  window.history.replaceState( null, null, window.location.href );
}
</script>
</body>
</html>