<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<%@ page import="java.lang.Math"%>
    
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Rezultate Bacalaureat</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous"> 
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">  
<!-- 	link for jQuery and ajax -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.min.js"></script>
<script src="jspdf.plugin.autotable.min.js"></script>

<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.3/jspdf.plugin.autotable.js"></script> -->
</head>
<body style="background-image: url('images/purple_background.jpg');">
<div class="header">
  <a href="rezultate.jsp" class="logo">Examenul de Bacalaureat</a>
  <div class="header-right">
    <a class="active" href="rezultate.jsp">Rezultate</a>
    <a href="login.jsp">Logare</a>
    <a href="crearecont.jsp">Creare cont</a>
  </div>
</div>

<table id="my-table"></table>

<style>
* {box-sizing: border-box;}

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

#rezultate {
  font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

#rezultate td, #rezultate th {
  border: 1px solid #d194bf;
  padding: 8px;
}

#rezultate tr:nth-child(even){background-color: #debad3;}
#rezultate tr:nth-child(odd){background-color: white;}

#rezultate tr:hover {background-color: #d194bf;}

#rezultate th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #ad2183;
  color: white;
  cursor: pointer;
}

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

<script>
$(document).ready(function(){
  $("#cautare").keyup(function(){
  var pressed=$('#cautare').val();
  var liceu=$('#liceu').val();
  $('#rezultate').find('thead').remove();
  $('#rezultate').find('tbody').remove();
  $('#rezultate').find('tfoot').remove();
  	$.ajax({
    	type:"get",
    	url: 'RezultateServlet',
    	data: {
        	cautareAJAX: pressed,
        	liceuAJAX: liceu
        },
    	success: function(response){
    	var res = response.split("|");
        $('#rezultate').append('<thead><tr><th onclick="sortTable(0)">Nume</th><th onclick="sortTable(1)">Liceu</th> <th onclick="sortTable(2)">Profil</th><th onclick="sortTable(3)">Proba1</th> <th onclick="sortTable(4)">Nota initiala 1</th><th onclick="sortTable(5)">Nota contestatie 1</th> <th onclick="sortTable(6)">Nota finala 1</th><th onclick="sortTable(7)">Proba2</th> <th onclick="sortTable(8)">Nota initiala 2</th> <th onclick="sortTable(9)">Nota contestatie 2</th><th onclick="sortTable(10)">Nota finala 2</th> <th onclick="sortTable(11)">Proba3</th> <th onclick="sortTable(12)">Nota initiala 3</th><th onclick="sortTable(13)">Nota contestatie 3</th> <th onclick="sortTable(14)">Nota finala 3</th> <th onclick="sortTable(15)">Media</th> <th onclick="sortTable(16)">Rezultatul final</th> </tr></thead><tbody style="height: 100px; overflow-y:auto; ">'+res[0]+'</tbody><tfoot><tr><th>Nume</th><th>Liceu</th> <th>Profil</th><th>Proba1</th> <th>Nota initiala 1</th><th>Nota contestatie 1</th> <th>Nota finala 1</th><th>Proba2</th> <th>Nota initiala 2</th> <th>Nota contestatie 2</th><th>Nota finala 2</th> <th>Proba3</th> <th>Nota initiala 3</th><th>Nota contestatie 3</th> <th>Nota finala 3</th> <th>Media</th> <th>Rezultatul final</th> </tr></tfoot>');
        var admisi = res[1];
        var respinsi = res[2];
        var neprezentati = res[3];
        var ctx=$("#piechart").get(0).getContext("2d");
        var canvas = document.getElementsByTagName('canvas')[0];
        canvas.width  = 50;
        canvas.height = 10; 
        canvas.style.width  = '800px';
        canvas.style.height = '600px';
        Chart.defaults.global.defaultFontColor = '#ccff00';
        var data = {
             labels: ["Elevi admisi", "Elevi respinsi", "Elevi neprezentati"],
             datasets: [
                 {
                 		data: [admisi, respinsi, neprezentati],
                     // data: [20, 40, 10],
                     backgroundColor: [
                          "#ad2183", 
                          "#d194bf", 
                          "#debad3", 
                     ]  
                 }]
        };

        var myPieChart = new Chart(ctx, {
          type: 'pie',
          data: data

        });
    	}
    });
   });
});

$(document).ready(function(){
  $("#liceu").change(function(){
  var chosen=$('#liceu').val();
  var cautare=$('#cautare').val();
  $('#rezultate').find('thead').remove();
  $('#rezultate').find('tbody').remove();
  $('#rezultate').find('tfoot').remove();
  	$.ajax({
    	type:"get",
    	url: 'RezultateServlet',
    	data: {
    		liceuAJAX: chosen,
        	cautareAJAX : cautare
        },
    	success: function(response){
    		var res = response.split("|");
            $('#rezultate').append('<thead><tr><th onclick="sortTable(0)">Nume</th><th onclick="sortTable(1)">Liceu</th> <th onclick="sortTable(2)">Profil</th><th onclick="sortTable(3)">Proba1</th> <th onclick="sortTable(4)">Nota initiala 1</th><th onclick="sortTable(5)">Nota contestatie 1</th> <th onclick="sortTable(6)">Nota finala 1</th><th onclick="sortTable(7)">Proba2</th> <th onclick="sortTable(8)">Nota initiala 2</th> <th onclick="sortTable(9)">Nota contestatie 2</th><th onclick="sortTable(10)">Nota finala 2</th> <th onclick="sortTable(11)">Proba3</th> <th onclick="sortTable(12)">Nota initiala 3</th><th onclick="sortTable(13)">Nota contestatie 3</th> <th onclick="sortTable(14)">Nota finala 3</th> <th onclick="sortTable(15)">Media</th> <th onclick="sortTable(16)">Rezultatul final</th> </tr></thead><tbody style="height: 100px; overflow-y:auto; ">'+res[0]+'</tbody><tfoot><tr><th>Nume</th><th>Liceu</th> <th>Profil</th><th>Proba1</th> <th>Nota initiala 1</th><th>Nota contestatie 1</th> <th>Nota finala 1</th><th>Proba2</th> <th>Nota initiala 2</th> <th>Nota contestatie 2</th><th>Nota finala 2</th> <th>Proba3</th> <th>Nota initiala 3</th><th>Nota contestatie 3</th> <th>Nota finala 3</th> <th>Media</th> <th>Rezultatul final</th> </tr></tfoot>');
            var admisi = res[1];
            var respinsi = res[2];
            var neprezentati = res[3];
            var ctx=$("#piechart").get(0).getContext("2d");
    	     var canvas = document.getElementsByTagName('canvas')[0];
    	     canvas.width  = 50;
    	     canvas.height = 10; 
    	     canvas.style.width  = '800px';
    	     canvas.style.height = '600px';
    	     Chart.defaults.global.defaultFontColor = '#ccff00';
    	     var data = {
    	          labels: ["Elevi admisi", "Elevi respinsi", "Elevi neprezentati"],
    	          datasets: [
    	              {
    	            	  data: [admisi, respinsi, neprezentati],    	                  // data: [20, 40, 10],
    	                  backgroundColor: [
    	                       "#ad2183", 
    	                       "#d194bf", 
    	                       "#debad3", 
    	                  ]  
    	              }]
    	     };

    	     var myPieChart = new Chart(ctx, {
    	       type: 'pie',
    	       data: data

    	     });        }
    });
   });
});
</script>

<% 
	   ProbaDao probaDao = new ProbaDao();
       ProfilDao profilDao = new ProfilDao();
       RezultatDao rezultatDao = new RezultatDao();
       ElevDao elevDao = new ElevDao();
       LiceuDao liceuDao = new LiceuDao();
%>

<br>
<div class="custom-control custom-control-inline">
	<input type="text" id ="cautare" name="cautare" class="form-control" placeholder="Cautare dupa nume">&ensp;

<!--comboBox filtrare dupa liceu-->
<select id="liceu" name="liceu" class="form-control">
	<option selected value="filtreaza dupa liceu">--filtrare dupa liceu--</option>
<% 
   	        List<Liceu> liceuList = liceuDao.getAllLicee();
    	        for(Liceu liceu : liceuList){
    	        %>
    	        	<option value='<%= liceu.getDenumire() %>'><%= liceu.getDenumire() %></option>
    	        <% 
    	        }
%>
</select>
</div>
<br>
<br>

<!--tabel rezultate-->
<!-- <div style="overflow-x:auto; overflow-y: auto; height: 230px;"> -->
<div style="overflow-x:auto; height:340px;">
<table id="rezultate">
<thead>
  <tr>
    <th onclick="sortTable(0)">Nume</th>
    <th onclick="sortTable(1)">Liceu</th> 
  	<th onclick="sortTable(2)">Profil</th>
    <th onclick="sortTable(3)">Proba1</th> 
  	<th onclick="sortTable(4)">Nota initiala 1</th>
    <th onclick="sortTable(5)">Nota contestatie 1</th> 
  	<th onclick="sortTable(6)">Nota finala 1</th>
    <th onclick="sortTable(7)">Proba2</th> 
    <th onclick="sortTable(8)">Nota initiala 2</th> 
  	<th onclick="sortTable(9)">Nota contestatie 2</th>
  	<th onclick="sortTable(10)">Nota finala 2</th> 
    <th onclick="sortTable(11)">Proba3</th> 
  	<th onclick="sortTable(12)">Nota initiala 3</th>
    <th onclick="sortTable(13)">Nota contestatie 3</th> 
  	<th onclick="sortTable(14)">Nota finala 3</th> 
    <th onclick="sortTable(15)">Media</th> 
  	<th onclick="sortTable(16)">Rezultatul final</th> 
  </tr>
</thead>
<tbody style="height: 100px; overflow-y:auto; ">
<% 
	long admisi = 0;
	long respinsi = 0;
	long neprezentati = 0;
  List<Elev> eleviRezultateList = elevDao.getEleviRezultateList();
  for(Elev elev : eleviRezultateList){
  %>
	  <tr>
	    <td style="background-color: #debad3;"><%= elev.getNume() + ' ' + elev.getInitialaTata() + ' ' + elev.getPrenume()%></td>
		<td><%= elev.getLiceuProfil().getLiceu().getDenumire() %></td>
		<td><%= elev.getLiceuProfil().getProfil().getDenumire() %></td>
		<td><%= elev.getExamene().get(0).getProba().getDenumire() %></td>
		<td><%= elev.getExamene().get(0).getRezultat().getNotaInitiala() %></td>
		<td><%= elev.getExamene().get(0).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(0).getRezultat().getNotaContestatie() %></td>
		<%
		double notaFinala1 = 0;
		if (elev.getExamene().get(0).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(0).getRezultat().getNotaContestatie() == -1) {
			notaFinala1 = elev.getExamene().get(0).getRezultat().getNotaInitiala();
		}else {
			notaFinala1 = elev.getExamene().get(0).getRezultat().getNotaContestatie();
		}
		%>
		<td><%= notaFinala1 %></td>
		<td><%= elev.getExamene().get(1).getProba().getDenumire() %></td>
		<td><%= elev.getExamene().get(1).getRezultat().getNotaInitiala() %></td>
		<td><%= elev.getExamene().get(1).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(1).getRezultat().getNotaContestatie() %></td>
		<%
		double notaFinala2 = 0;
		if (elev.getExamene().get(1).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(1).getRezultat().getNotaContestatie() == -1) {
			notaFinala2 = elev.getExamene().get(1).getRezultat().getNotaInitiala();
		}else {
			notaFinala2 = elev.getExamene().get(1).getRezultat().getNotaContestatie();
		}
		%>
		<td><%= notaFinala2 %></td>
		<td><%= elev.getExamene().get(2).getProba().getDenumire() %></td>
		<td><%= elev.getExamene().get(2).getRezultat().getNotaInitiala() %></td>
		<td><%= elev.getExamene().get(2).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(2).getRezultat().getNotaContestatie() %></td>
		<%
		double notaFinala3 = 0;
		if (elev.getExamene().get(2).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(2).getRezultat().getNotaContestatie() == -1) {
			notaFinala3 = elev.getExamene().get(2).getRezultat().getNotaInitiala();
		}else {
			notaFinala3 = elev.getExamene().get(2).getRezultat().getNotaContestatie();
		}
		%>
		<td><%= notaFinala3 %></td>
		<%
		double media = (notaFinala1 + notaFinala2 + notaFinala3)/3.0;
		String mediaS = String.format("%.2f", media);
		%>
		<td><%= mediaS %></td>
		<%
		String rezultat;
		if(media>=6 && notaFinala1>=5 && notaFinala2>=5 && notaFinala3>=5){
			rezultat = "ADMIS";
			admisi++;
		}else if (notaFinala1 == 0 || notaFinala2 == 0 || notaFinala3 == 0) {
			rezultat="NEPREZENTAT";
			neprezentati++;
		}else {
			rezultat = "RESPINS";
			respinsi++;
		}
		%>
		<td><%= rezultat %></td>
	  </tr>
  <% 
}
  %>
</tbody>
  <tfoot>
    <tr>
    <th>Nume</th>
    <th>Liceu</th> 
  	<th>Profil</th>
    <th>Proba1</th> 
  	<th>Nota initiala 1</th>
    <th>Nota contestatie 1</th> 
  	<th>Nota finala 1</th>
    <th>Proba2</th> 
    <th>Nota initiala 2</th> 
  	<th>Nota contestatie 2</th>
  	<th>Nota finala 2</th> 
    <th>Proba3</th> 
  	<th>Nota initiala 3</th>
    <th>Nota contestatie 3</th> 
  	<th>Nota finala 3</th> 
    <th>Media</th> 
  	<th>Rezultatul final</th> 
    </tr>
  </tfoot>
</table>
</div>
<br>

<form action="" method="post">
<center>
<!-- <button type="export" id="export" name="export" class="button button1" onclick="Export('rezultate')">Export</button> -->
<button type="export" id="pdf" name="export1" class="button button1">Export PDF</button>
<button type="export" id="csv" name="export2" class="button button1">Export CSV</button>
<button type="export" id="json" name="export3" class="button button1">Export JSON</button>
</center>
</form>

<!-- <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
<script src="export/tableHTMLExport.js"></script>
<script>
  $('#json').on('click',function(){
    $("#rezultate").tableHTMLExport({type:'json',filename:'RezultateBacalaureat.json'});
  })
  $('#csv').on('click',function(){
    $("#rezultate").tableHTMLExport({type:'csv',filename:'RezultateBacalaureat.csv'});
  })
  $('#pdf').on('click',function(){
		var doc = new jsPDF('l', 'mm', 'a3');	
		doc.autoTable({ html: '#rezultate' , theme:'grid', headStyles: { fillColor:  '#ad2183' }, footStyles: { fillColor: '#ad2183' }})
  		doc.text(185, 10, "Rezultate Bacalaureat");
		doc.save('RezultateBacalaureat.pdf')
  })
  </script>

<!-- <script src="https://cdn.anychart.com/js/8.0.1/anychart-core.min.js"></script> -->
<!-- <script src="https://cdn.anychart.com/js/8.0.1/anychart-pie.min.js"></script> -->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.js"></script> -->
 <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.js" type="text/javascript"></script> 
<canvas id="piechart"> 
<script>

$(document).ready(function(){
 var ctx=$("#piechart").get(0).getContext("2d");
var canvas = document.getElementsByTagName('canvas')[0];
canvas.width  = 50;
canvas.height = 10; 
canvas.style.width  = '800px';
canvas.style.height = '600px';
Chart.defaults.global.defaultFontColor = '#ccff00';
var data = {
     labels: ["Elevi admisi", "Elevi respinsi", "Elevi neprezentati"],
     datasets: [
         {
         		//data: [<?php echo $admisi; ?>, <?php echo $respinsi; ?>, <?php echo $neprezentati; ?>],
              data: [<%= admisi %>, <%= respinsi %>, <%= neprezentati %>],
             //data: [1, 1, 2],
              backgroundColor: [
                  "#ad2183", 
                  "#d194bf", 
                  "#debad3", 
             ]  
         }]
};

var myPieChart = new Chart(ctx, {
  type: 'pie',
  data: data

});
});

</script>


<script>
function sortTable(n) {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("rezultate");
  switching = true;
  //Set the sorting direction to ascending:
  dir = "asc"; 
  /*Make a loop that will continue until
  no switching has been done:*/
  while (switching) {
    //start by saying: no switching is done:
    switching = false;
    rows = table.rows;
    /*Loop through all table rows (except the
    first, which contains table headers):*/
    for (i = 1; i < (rows.length - 1); i++) {
      //start by saying there should be no switching:
      shouldSwitch = false;
      /*Get the two elements you want to compare,
      one from current row and one from the next:*/
      x = rows[i].getElementsByTagName("TD")[n];
      y = rows[i + 1].getElementsByTagName("TD")[n];
      /*check if the two rows should switch place,
      based on the direction, asc or desc:*/
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          //if so, mark as a switch and break the loop:
          shouldSwitch= true;
          break;
        }
      } else if (dir == "desc") {
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
          //if so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      /*If a switch has been marked, make the switch
      and mark that a switch has been done:*/
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      //Each time a switch is done, increase this count by 1:
      switchcount ++;      
    } else {
      /*If no switching has been done AND the direction is "asc",
      set the direction to "desc" and run the while loop again.*/
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}
</script>

</body>
</html>