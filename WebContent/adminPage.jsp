<%-- 
    Document   : adminPage.jsp
    Created on : Nov 20, 2020, 12:22:08 PM
    Author     : catal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.Elev"%>
<%@ page import="api.ElevDao"%>
<%@ page import="model.Proba"%>
<%@ page import="api.ProbaDao"%>
<%@ page import="model.Profil"%>
<%@ page import="api.ProfilDao"%>
<%@ page import="model.Liceu"%>
<%@ page import="api.LiceuDao"%>
<%@ page import="model.Rezultat"%>
<%@ page import="api.RezultatDao"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Administrator</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">    
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<!-- 	link for jQuery and ajax -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- header band with title and navigation menu -->
<style>
body { 
  margin: 0;
  font-family: Arial, Helvetica, sans-serif;
/* 	background-image: url('https://cdn.asla.org/uploadedImages/CMS/Shop/Bookstore/books.jpg'); */
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
body {
  background-image: url('images/purple_background.jpg');
}
</style>

<% 
	   ProbaDao probaDao = new ProbaDao();
       ProfilDao profilDao = new ProfilDao();
       RezultatDao rezultatDao = new RezultatDao();
       ElevDao elevDao = new ElevDao();
       LiceuDao liceuDao = new LiceuDao();
%>

<div class="header">
  <a href="rezultate.jsp" class="logo">Examenul de Bacalaureat</a>
  <div class="header-right">
  	<a class="active" href="adminpage.jsp">Administrare</a>
  	<a href="databaselog.jsp">Log</a>
    <a href="rezultate.jsp">Rezultate</a>
  	<form action ="AdminServlet" method="post">
		<button type="delogare" name="delogare">Delogare</button>
	</form>
  </div>
</div>

<style>
        body{ font: 14px sans-serif; }
        .wrapper{float:left; width: 350px; padding: 20px; }
		.wrapper2{float:left; width: 500px; height: 570px; overflow-y:auto; padding: 20px; margin-left:15px; margin-right:15px;}
</style>
</head>
<body>
<script>
//fill LICEU combobox when page open
$(document).ready(function(){
    // $(this).css("background-color", "#D6D6FF");
   // $("#profilcombobox").css("background-color", "red");
    //$("#liceucombobox").find('option').remove();
  	$.ajax({
    	type:"get",
        url: 'AdminServlet',
    	data: {
        	liceu: "ok"
        },
    	success: function(response){
        	$("#liceucombobox").append('<option disabled selected value> -- Selecteaza liceul -- </option>' + response);
        }
    });
});    


    
//fill profile combobox when liceu combobox is selected
$(document).ready(function(){
  $("#liceucombobox").change(function(){
  //$("#liceucombobox").click(function(){
    // $(this).css("background-color", "#D6D6FF");
   // $("#profilcombobox").css("background-color", "red");
    $("#profilcombobox").find('option').remove();
  	var selectedLiceu = $('#liceucombobox').val();
        //var selectedLiceu = 'Liceul Teoretic "Dunarea"';
  	$.ajax({
    	type:"get",
        url: 'AdminServlet',
    	data: {
        	selectedLiceuAJAX: selectedLiceu
        },
    	success: function(response){
                //alert("Profil response is:" + response);
        	$("#profilcombobox").append('<option disabled selected value> -- Selecteaza profilul -- </option>' + response);
        }
    });
  });
});

//add elev to database
/*$(document).ready(function(){
  $("#submit").onmousedown(function(e){
  e.preventDefault();
  //e.preventDefault();
    // $(this).css("background-color", "#D6D6FF");
   // $("#profilcombobox").css("background-color", "red");
    //$("#profilcombobox").find('option').remove();
  	//var selectedLiceu = $('#liceucombobox').val();
  	$.ajax({
    	type:"get",
    	data: {
        	adaugaelevindbPHP: 1
        },
    	success: function(response){
        	$("document").html(response);
        }
    });
  //e.preventDefault();
  //this.preventDefault();
  });
});
*/

//add elev to table when adauga elev button is pressed
$(document).ready(function(){
  $("#submit").onmouseup(function(e){
  //e.preventDefault();
  //e.preventDefault();
    // $(this).css("background-color", "#D6D6FF");
   // $("#profilcombobox").css("background-color", "red");
    //$("#profilcombobox").find('option').remove();
  	//var selectedLiceu = $('#liceucombobox').val();
  	var valoare="ok"; 
  	$.ajax({
    	type:"get",
    	data: {
        	adaugaelevPHP: valoare
        },
    	success: function(response){
        	$("#tabel").append(response);
        }
    });
  //e.preventDefault();
  //this.preventDefault();
  });
});

//select elev to update
$(document).ready(function(){
  $("#rezultate").on("click", "tr",function(){
    // $(this).css("background-color", "#D6D6FF");
   // $("#profilcombobox").css("background-color", "red");
    //$("#profilcombobox").find('option').remove();
  //alert($( this ).text());	
  var rowIndex = this.rowIndex;
  var cnp = getCnpElevFromTable(rowIndex);
  //$('#rezultate').find('thead').remove()
  //$('#rezultate').find('tbody').remove()
  //$('#rezultate').find('tfoot').remove()
   // alert('hello '+ cnp);
  	$("#proba3combobox").find('option').remove();
  	$.ajax({
    	type:"get",
    	url: 'AdminServlet',
    	data: {
        	cnptoupdateAJAX: cnp
        },
    	success: function(response){
        	var res = response.split("|");
        	$('#numeUpdate').val(res[1]);
        	$('#initUpdate').val(res[2]);
        	$('#prenumeUpdate').val(res[3]);
        	$('#proba1Update').val(res[4]);
        	$('#proba2Update').val(res[5]);
        	$('#nota1Update').val(res[7]);
        	$('#nota2Update').val(res[8]);
        	$('#nota3Update').val(res[9]);
        	$('#proba3combobox').append('<option disabled value> -- Selecteaza proba3 -- </option><option selected value="'+res[6]+'">'+ res[6] +'</option>'+res[10]);
        	 //alert(res[1]);
        }
    });
   });
});

function getCnpElevFromTable(i) {
  var table, rows, i, cnp;
	//alert('salut');
  table = document.getElementById("rezultate");
    rows = table.rows;
	//cnp is the column at index 1
      cnp = rows[i].getElementsByTagName("TD")[1];
	  //returnez cnp-ul elevului ce trebuie sters
	  //$_GET['cnp']=cnp;
	  //alert(cnp.innerHTML);
	  return cnp.innerHTML;
}

//using only button to delete elev
$(document).ready(function(){
  $("#rezultate").on("click","button",function(){
    // $(this).css("background-color", "#D6D6FF");
   // $("#profilcombobox").css("background-color", "red");
    //$("#profilcombobox").find('option').remove();
  //alert($( this ).text());	
  //alert("vreau numele "+$(this).attr('id'));
  var idButon=$(this).attr('id');
  var rowIndex = idButon.substring(12, idButon.length);
  //alert(rowIndex);
  //alert(rowIndex.substring(1, rowIndex.length-1));
  var intRowIndex = parseInt(rowIndex.substring(1, rowIndex.length-1),10);
  //alert("indexul numar "+intRowIndex);
  var cnp = getCnpElevFromTable(intRowIndex+1);
  //alert('hello '+ cnp);
  $('#rezultate').find('thead').remove();
  $('#rezultate').find('tbody').remove();
  $('#rezultate').find('tfoot').remove();
  $('#tabelcontestatie').find('thead').remove();
  $('#tabelcontestatie').find('tbody').remove();
  $('#tabelcontestatie').find('tfoot').remove();
   //alert('hello2 '+ cnp);
  	$.ajax({
    	type:"get",
    	url: 'AdminServlet',
    	data: {
        	cnptodeleteAJAX: cnp
        },
    	success: function(response){
        	//$(document).html(response);
        	var res = response.split("|");
        	$('#rezultate').append('<thead><tr><th title="Cautare dupa nume" onclick="sortTable(0)"><input type="text" id ="cautare" name="cautare" class="form-control" placeholder="Nume"></th><th onclick="sortTable(1)">CNP</th> <th onclick="sortTable(2)">Liceu</th><th onclick="sortTable(3)">Profil</th><th>Sterge</th></tr></thead><tbody style="height: 100px; overflow-y:auto; ">'+res[0]+'</tbody> <tfoot> <tr> <th>Nume</th> <th>CNP</th>  <th>Liceu</th>  <th>Profil</th> <th>Sterge</th> </tr></tfoot>');
        	//alert(response);
        	$('#numeUpdate').val("");
        	$('#initUpdate').val("");
        	$('#prenumeUpdate').val("");
  			$('#proba1Update').val("");
  			$('#proba2Update').val("");
        	$('#nota1Update').val("");
  			$('#nota2Update').val("");
  			$('#nota3Update').val("");
        	$("#proba3combobox").find('option').remove();
        	$('#tabelcontestatie').append('<thead><tr><th onclick="sortTable(0)">Nume</th><th onclick="sortTable(1)">CNP</th> <th onclick="sortTable(2)">Proba</th><th onclick="sortTable(3)">Nota contestatie</th></tr></thead><tbody style="height: 100px; overflow-y:auto; ">'+res[1]+'</tbody> <tfoot> <tr> <th>Nume</th> <th>CNP</th>  <th>Proba</th>  <th>Nota contestatie</th> </tr></tfoot>');
        }
    });
   });
});

$(document).ready(function(){
  $("#AdaugaContestatie").on("click",function(){
  var trigger="true";
  // var arrayCNPCont=$('#rezultate');
  var tableVar = document.getElementsByName("tabelcontestatie");
  table=tableVar[0];
    rows = table.rows;
  // alert('ajunge aici '+ $("#updateCont"+(1-1)+"").val());
  	var cnpContArray=[];
    var probaContArray=[];
    var notaContArray=[];
	//cnp is the column at index 1
	for(var i=1;i<rows.length-1;i++){
      cnpContArray[i-1] = rows[i].getElementsByTagName("TD")[1].innerHTML;
      probaContArray[i-1] = rows[i].getElementsByTagName("TD")[2].innerHTML;
  	  notaContArray[i-1] = $("#updateCont"+(i-1)+"").val();
    }
	//transform in json
	var jsonStringCnp = JSON.stringify(cnpContArray);
	var jsonStringProba = JSON.stringify(probaContArray);
	var jsonStringNota = JSON.stringify(notaContArray);
  	$.ajax({
    	type:"get",
    	url: 'AdminServlet',
    	data: {
        	triggerContestatie: trigger,
        	cnpContArrayAJAX: jsonStringCnp,
        	probaContArrayAJAX: jsonStringProba,
        	notaContArrayAJAX: jsonStringNota
        },
    	success: function(response){
        	// alert(response);
        	//alert(cnpContArray[1]);
        }
    });
   });
});

//search for elev
$(document).ready(function(){
	$("#rezultate").on("keyup","input",function(){
	var pressed=$('#cautare').val();
  //$('#rezultate').find('thead').remove();
  $('#rezultate').find('tbody').remove();
  $('#rezultate').find('tfoot').remove();
  	$.ajax({
    	type:"post",
    	url: 'AdminServlet',
    	data: {
    		cautareAJAX: pressed
        },
    	success: function(response){
    		$('#rezultate').append('<tbody style="height: 100px; overflow-y:auto; ">'+response+'</tbody> <tfoot> <tr> <th>Nume</th> <th>CNP</th>  <th>Liceu</th>  <th>Profil</th> <th>Sterge</th> </tr></tfoot>');
    		$("#rezultate th input").focus();
    		//var value = $("#rezultate th input").val();
    		//$("#rezultate th input").removeAttr('value').attr('value', value).focus();
    		//$("#rezultate th input").focus().val(value);
    		//$("#rezultate th input").val($("#rezultate th input").val());
    	}
    });
   });
});

</script>
<!-- <form action ="" method="post">
<button type="submit" name="submit">Logout</button>
</form> -->

<!-- css to align multiple divs horizontally -->
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

<!--alert errors-->
<% if(session.getAttribute("error")!=null) {%>
<script>
alert("<%= session.getAttribute("error").toString() %>");
</script>
<%} %>

<div class="divOuter">
<!-- 	<div class="one"> -->
    <div class="wrapper">
    <div class="center" style="background:#debad3">
<!--     <div class="custom-control custom-control-inline"> -->
        <form action="AdminServlet" method="post">
            <div class="form-group">
                <label>Nume</label>
                <input type="text" name="nume" class="form-control" placeholder="Popescu" required pattern="[-A-Za-z]+" title="Se accepta doar litere si '-'">
<!--                 <span class="help-block"></span> -->
            </div>    
            <div class="form-group">
                <label>Initiala tata</label>
                <input type="text" name="init" class="form-control" placeholder="A" required pattern="[A-Za-z]" title="Se accepta o singura litera">
                <!--<span class="help-block"></span>-->
            </div>
            <div class="form-group">
                <label>Prenume</label>
                <input type="text" name="prenume" class="form-control" placeholder="Ion" required pattern="[-A-Za-z]+" title="Se accepta doar litere si '-'">
                <!--<span class="help-block"></span>-->
            </div>
            <div class="form-group">
                <label>CNP</label>
                <!--Put this in class next to form-control if I get an error: <?php if($error) echo "is-invalid";?>-->
                <input type="text" name="cnp" class="form-control <% if(session.getAttribute("error")!=null) {%> is-invalid" <%} else {%> " <%} %> placeholder="1234567891234" required pattern="[0-9]{13}" title="13 cifre">
                <!--<span class="help-block"></span>-->
            </div>
        	<div class="form-group">
				<!--comboBox  liceu-->
            	<label>Liceu</label>
				<select id="liceucombobox" name="liceucombobox" class="form-control" required>
				</select>
            </div>
            <div class="form-group">
				<!--comboBox  profil-->
            	<label>Profil</label>
				<select id="profilcombobox" name="profilcombobox" class="form-control" required>
    				<option disabled selected value> -- Selecteaza profilul -- </option>
				</select>
            </div>
            <div class="form-group">
                &emsp;&emsp;&emsp;&emsp;&emsp;&ensp;&ensp;<input id="submitAdauga" type="submit" name="adauga" class="btn btn-primary" value="Adauga elev">
            </div>
        </form>
    </div>
    </div>
<!--     </div> -->


<div class="wrapper2">
<table id="rezultate" class ="rezultate" name="tabel">
<thead>
  <tr>
    <th title="Cautare dupa nume" onclick="sortTable(0)"><input type="text" id ="cautare" name="cautare" class="form-control" placeholder="Nume"></th>
    <th onclick="sortTable(1)">CNP</th> 
  	<th onclick="sortTable(2)">Liceu</th>
    <th onclick="sortTable(3)">Profil</th> 
    <th>Sterge</th> 
  </tr>
</thead>
<tbody style="height: 100px; overflow-y:auto; ">
  <% 
  List<Elev> eleviList = elevDao.getAllElevi();
  long contor = -1;
  for(Elev elevT : eleviList){
	  contor++;
  %>
	  <tr>
	    <td><%= elevT.getNume() + ' ' + elevT.getInitialaTata() + ' ' + elevT.getPrenume()%></td>
		<td><%= elevT.getCnp() %></td>
		<td><%= elevT.getLiceuProfil().getLiceu().getDenumire() %></td>
		<td><%= elevT.getLiceuProfil().getProfil().getDenumire() %></td>
		<td onclick="getCnpElevFromTable('<%= contor %>')"><button id ="stergebutton'<%= contor %>'" class="btn btn-danger"><i class="fa fa-trash"></i>Sterge</button></td>
	  </tr>
  <% 
}
  %>
</tbody>
  <tfoot>
    <tr>
    <th>Nume</th>
    <th>CNP</th> 
  	<th>Liceu</th>
    <th>Profil</th> 
    <th>Sterge</th> 
    </tr>
  </tfoot>
</table>
</div>
</div>

    <div class="wrapper">
    <div class="center" style="background:#debad3">
<!--     <div class="custom-control custom-control-inline"> -->
        <form action="AdminServlet" method="post">
            <div class="form-group">
                <label>Nume</label>
                <input id ="numeUpdate" type="text" name="numeUpdate" class="form-control" required pattern="[-A-Za-z]+" title="Se accepta doar litere si '-'" value="">
<!--                 <span class="help-block"></span> -->
            </div>    
            <div class="form-group">
                <label>Initiala tata</label>
                <input id ="initUpdate" type="text" name="initUpdate" class="form-control" required pattern="[A-Za-z]" title="Se accepta o singura litera">
                <!--<span class="help-block"></span>-->
            </div>
            <div class="form-group">
                <label>Prenume</label>
                <input id ="prenumeUpdate" type="text" name="prenumeUpdate" class="form-control" required pattern="[-A-Za-z]+" title="Se accepta doar litere si '-'">
                <!--<span class="help-block"></span>-->
            </div>
            <div class="form-group" style="display:inline">
            	<label style="width:110px">Limba si literatura romana</label>
            	&emsp;&ensp;&emsp;
            	<label style="width:120px">Nota initiala/absent</label>
            </div>
            <div class="form-group" style="display:inline">
            	<div style="float:left; width:130px;  margin-right:10px;">
                	<input id ="proba1Update" type="text" name="proba1Update" class="form-control" required readonly>
                </div>
            	<div style="float:left; width:130px;  margin-left:10px;">
                	<input id ="nota1Update" type="number" name="nota1Update" class="form-control" min="0" max="10.00" step="0.01" required>
                </div>
            </div>
        <br>
        <br>
        	<div class="form-group" style="display:inline">
            	<label style="width:130px">Disciplina obligatorie a profilului</label>
            	&emsp;
            	<label style="width:120px">Nota initiala/absent</label>
            </div>
            <div class="form-group" style="display:inline">
            	<div style="float:left; width:130px;  margin-right:10px;">
                	<input id ="proba2Update" type="text" name="proba2Update" class="form-control" required readonly>
                </div>
            	<div style="float:left; width:130px;  margin-left:10px;">
                	<input id ="nota2Update" type="number" name="nota2Update" class="form-control" min="0" max="10.00" step="0.01" required>
                </div>
            </div>
        <div class="form-group" style="display:inline">
            	<label style="width:130px">Disciplina la alegere</label>
            	&emsp;
            	<label style="width:120px">Nota initiala/absent</label>
            </div>
            <div class="form-group" style="display:inline">
            	<div style="float:left; width:130px;  margin-right:10px;">
                	<select id="proba3combobox" name="proba3combobox" class="form-control" required>
    					<option disabled value> -- Selecteaza proba3 -- </option>
					</select>
                </div>
            	<div style="float:left; width:130px;  margin-left:10px;">
                	<input id ="nota3Update" type="number" name="nota3Update" class="form-control" min="0" max="10.00" step="0.01">
                </div>
            </div>
        <br>
        <br>
        <br>
        <br>
            <div class="form-group">
                &emsp;&emsp;&emsp;&emsp;&emsp;&ensp;&ensp;<input id="submitUpdate" type="submit" name="update" class="btn btn-warning" value="Update elev">
            </div>
        </form>
    </div>
    </div>

<!-- contestatie table -->
<div style="display:inline;">
<div style="float:left; align:left; width: 865px; height: 300px; overflow-y:auto; padding: 20px; margin-left:-350px; margin-right:15px;">
<table id="tabelcontestatie" class="rezultate" name="tabelcontestatie">
<thead>
  <tr>
    <th onclick="sortTable(0)">Nume</th>
    <th onclick="sortTable(1)">CNP</th> 
  	<th onclick="sortTable(2)">Proba</th>
    <th onclick="sortTable(3)">Nota contestatie</th> 
  </tr>
</thead>
<tbody style="height: 100px; overflow-y:auto; ">
<% 
  List<Elev> eleviContestatie = elevDao.getEleviContestatieList();
  List<Proba> probeContestatie = probaDao.getProbaListContestatie();
  List<Rezultat> rezultatContestatie = rezultatDao.getRezultatListContestatie();
  for(int i=0;i<eleviContestatie.size();i++){
%>	  
	  <tr>
	  <td><%= eleviContestatie.get(i).getNume() + " " + eleviContestatie.get(i).getInitialaTata() + ' ' + eleviContestatie.get(i).getPrenume() %></td>
	  <td><%= eleviContestatie.get(i).getCnp() + " " %></td>					
	  <td><%= probeContestatie.get(i).getDenumire() %></td>
	  <%
			if(rezultatContestatie.get(i).getNotaContestatie()<0){
				%>
				<td><input id ="updateCont<%= i%>" type='number' name="updateCont<%= i%>" class='form-control' min='0' max='10.00' step='0.01' value=''></td>
			<% }else{
				%>
				<td><input id ="updateCont<%= i%>" type='number' name="updateCont<%= i%>" class='form-control' min='0' max='10.00' step='0.01' value= <%= rezultatContestatie.get(i).getNotaContestatie() %>></td>
				<% } %>
	  </tr>
	<% } %>
     
</tbody>
  <tfoot>
    <tr>
    <th>Nume</th>
    <th>CNP</th> 
  	<th>Proba</th>
    <th>Nota contestatie</th> 
    </tr>
  </tfoot>
</table>
</div>
<div style="margin-left:925px; margin-top:720px; margin-right:15px;">
<button id ="AdaugaContestatie" name ="AdaugaContestatie" class="button button1">Adauga nota contestatie</button>
</div>
</div>
<style>
.button {
  background-color: #ad2183;
  border: none;
  border-radius: 7px;
  color: white;
  padding: 16px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  font-weight: bold;
  margin: 2px 1px;
  transition-duration: 0.4s;
  cursor: pointer;
}

.button1 {
  background-color: white; 
  color: purple; 
  border: 4px solid #ad2183;
}

.button1:hover {
  background-color: #d194bf;
  color: white;
}
</style>

<!-- prevent resubmit on refresh -->
<script>
if ( window.history.replaceState ) {
  window.history.replaceState( null, null, window.location.href );
}
</script>
</body>
</html>
