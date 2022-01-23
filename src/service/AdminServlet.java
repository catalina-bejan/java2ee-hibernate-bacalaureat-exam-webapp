package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import api.ElevDao;
import api.ExamenDao;
import api.LiceuDao;
import api.LiceuProfilDao;
import api.LogDao;
import api.LoginDao;
import api.ProbaDao;
import api.ProfilDao;
import api.RezultatDao;
import api.UserDao;
import model.Elev;
import model.Examen;
import model.Liceu;
import model.LiceuProfil;
import model.Log;
import model.Proba;
import model.Profil;
import model.Rezultat;
import model.User;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	LoginDao loginDao = new LoginDao();
	UserDao userDao = new UserDao();
	LiceuDao liceuDao = new LiceuDao();
	ProfilDao profilDao = new ProfilDao();
	LiceuProfilDao liceuProfilDao= new LiceuProfilDao();
	ElevDao elevDao = new ElevDao();
	ExamenDao examenDao = new ExamenDao();
	ProbaDao probaDao = new ProbaDao();
	RezultatDao rezultatDao = new RezultatDao();
	LogDao logDao = new LogDao();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

	try
	{
		String showLiceuList = request.getParameter("liceu");
        List<Liceu> liceuList= new LinkedList();
        if(showLiceuList != null && showLiceuList.equals("ok")){
        	liceuList = liceuDao.getAllLicee();
        

	        StringBuilder liceuComboBox = new StringBuilder();
	        for(int i=0; i<liceuList.size(); i++){
	        	liceuComboBox.append("<option value='" + liceuList.get(i).getDenumire() + "'>" + liceuList.get(i).getDenumire() + "</option>");
	        }
	        out.write(liceuComboBox.toString());
	        return;
        }
      
        List<Profil> profilList= new LinkedList();
        String selectedLiceu = request.getParameter("selectedLiceuAJAX");
        if(selectedLiceu != null){
        	profilList = profilDao.getProfileByLiceuDen(selectedLiceu);
        

	        StringBuilder profilComboBox = new StringBuilder();
	        for(int i=0; i<profilList.size(); i++){
	        	profilComboBox.append("<option value='" + profilList.get(i).getDenumire() + "'>" + profilList.get(i).getDenumire() + "</option>");
	        }
	        out.write(profilComboBox.toString());
	        return;
        }
        
        //click table to update elev
        String cnpToUpdate = request.getParameter("cnptoupdateAJAX");
        if(cnpToUpdate != null){
        	session.setAttribute("cnpToUpdate", cnpToUpdate);
        	Elev elev = null;
        	elev = elevDao.getElevByCnp(cnpToUpdate);
        	Date date = new Date();
        	Log log = new Log();
        	log.setDate(date);
        	log.setUser(session.getAttribute("nume")+" "+session.getAttribute("prenume"));
        	log.setRol(session.getAttribute("functie").toString());
        	log.setActiune("SELECT");
        	log.setDescriere("A selectat elevul cu cnp-ul: " + cnpToUpdate);
        	logDao.saveLog(log);
        	
        	String proba3 = "nu a ales inca";
        	try{
        		proba3 = (probaDao.getProbaAleasaBy(elev)!=null ? probaDao.getProbaAleasaBy(elev).getDenumire() : "nu a ales inca") + '|';
        		}
        	catch(NullPointerException e){
        		proba3 = "nu a ales inca";
        	}
        	
        	String rezultat3 = "0.00";
        	try{
        		rezultat3 = (rezultatDao.getRezultatBy(elev, 3)!=null? String.valueOf(rezultatDao.getRezultatBy(elev, 3).getNotaInitiala()) : "0.00") + '|';
        	}
        	catch(NullPointerException e){
        		rezultat3 = "0.00";
        	}
        
	        String elevData = "|" + elev.getNume() + '|' + elev.getInitialaTata() + '|' 
	        		+ elev.getPrenume() + '|' 
	        		+ probaDao.getProbaBy(elev, 1).getDenumire() + '|' 
	        		+ probaDao.getProbaBy(elev, 2).getDenumire() + '|' 
	        		+ proba3
	        		+ rezultatDao.getRezultatBy(elev, 1).getNotaInitiala() + '|'
	        		+ rezultatDao.getRezultatBy(elev, 2).getNotaInitiala() + '|'
	        		+ rezultat3;
	    	        StringBuilder elevInfo = new StringBuilder();
	    	        elevInfo.append(elevData);
	        		Profil profil = profilDao.getProfilOfElev(elev);
	    	        long tipProba = 3;
	    	        List<Proba> probaList = probaDao.getProbaListBy(profil, tipProba);
	    	        for(Proba proba : probaList){
	    	        	elevInfo.append("<option value='"+ proba.getDenumire() +"'>"+ proba.getDenumire() +"</option>");
	    	        }
	        		out.write(elevInfo.toString());
	        return;
        }
        
        //delete elev
        String cnpToDelete = request.getParameter("cnptodeleteAJAX");
        if(cnpToDelete != null){
        	Elev elevToDelete = elevDao.getElevByCnp(cnpToDelete);
        	Date date = new Date();
        	elevDao.deleteElev(elevToDelete);
        	Log log = new Log();
        	//SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        	log.setDate(date);
        	log.setUser(session.getAttribute("nume")+" "+session.getAttribute("prenume"));
        	log.setRol(session.getAttribute("functie").toString());
        	log.setActiune("DELETE");
        	log.setDescriere("A sters elevul cu cnp-ul: " + cnpToDelete);
        	logDao.saveLog(log);

		  List<Elev> eleviList = elevDao.getAllElevi();
		  long contor = -1;
		  StringBuilder sb = new StringBuilder();
		  for(Elev elev : eleviList){
			  contor++;
			  sb.append("<tr>");
			  sb.append("<td>").append(elev.getNume() + ' ' + elev.getInitialaTata() + ' ' + elev.getPrenume()).append("</td>");
			  sb.append("<td>").append(elev.getCnp() + ' ').append("</td>");
			  sb.append("<td>").append(elev.getLiceuProfil().getLiceu().getDenumire()).append("</td>");
			  sb.append("<td>").append(elev.getLiceuProfil().getProfil().getDenumire()).append("</td>");
			  sb.append("<td><button id ='stergebutton"+contor+"' class='btn btn-danger'><i class='fa fa-trash'></i>Sterge</button></td>");
			  sb.append("</tr>");
		}
		  //contestatie table next
		  sb.append("|");
		  List<Elev> eleviContestatie = elevDao.getEleviContestatieList();
		  List<Proba> probeContestatie = probaDao.getProbaListContestatie();
		  List<Rezultat> rezultatContestatie = rezultatDao.getRezultatListContestatie();
		  for(int i=0;i<eleviContestatie.size();i++){
			  sb.append("<tr>");
			  sb.append("<td>").append(eleviContestatie.get(i).getNume() + ' ' + eleviContestatie.get(i).getInitialaTata() + ' ' + eleviContestatie.get(i).getPrenume()).append("</td>");
			  sb.append("<td>").append(eleviContestatie.get(i).getCnp() + ' ').append("</td>");					
			  sb.append("<td>").append(probeContestatie.get(i).getDenumire()).append("</td>");
					if(rezultatContestatie.get(i).getNotaContestatie()<0)
						sb.append("<td><input id ='updateCont"+ i +"' type='number' name='updateCont"+ i +"' class='form-control' min='0' max='10.00' step='0.01' value=''></td>");
					else
						sb.append("<td><input id ='updateCont"+ i +"' type='number' name='updateCont"+ i +"' class='form-control' min='0' max='10.00' step='0.01' value='"+rezultatContestatie.get(i).getNotaContestatie()+"'></td>");
			  sb.append("</tr>");
			}
	        out.write(sb.toString());
	        return;
        }
        
        //adauga contestatii
        if(request.getParameter("triggerContestatie").toString()!=null){
        boolean triggerContestatie = Boolean.valueOf(request.getParameter("triggerContestatie").toString());
        if(triggerContestatie == true){
        
	        StringBuilder sb = new StringBuilder();
	        
	        // Processing the JSON string as a string
		    // Turning it into an array of strings
		    String[] cnpContList = request.getParameter("cnpContArrayAJAX").toString()
			    .substring(2, request.getParameter("cnpContArrayAJAX").toString().length() - 2).split("\",\"");
		    String[] notaContList = request.getParameter("notaContArrayAJAX").toString()
			    .substring(2, request.getParameter("notaContArrayAJAX").toString().length() - 2).split("\",\"");
		    String[] probaContList = request.getParameter("probaContArrayAJAX").toString()
				    .substring(2, request.getParameter("probaContArrayAJAX").toString().length() - 2).split("\",\"");
		    
	        for(int i=0; i<cnpContList.length ;i++){
	        	if (notaContList[i].equals("") || notaContList[i].equals(" ")){
	        		notaContList[i] = "-1";
	        	}
	    		if (Double.parseDouble(notaContList[i]) !=0){
	    			Elev elevCont = elevDao.getElevByCnp(cnpContList[i].toString());
	    			List<Examen> examene = elevCont.getExamene();
	    			for(Examen examen : examene){
	    				if(examen.getProba().getDenumire().equals(probaContList[i].toString())){
	    					examen.getRezultat().setNotaContestatie(Double.parseDouble(notaContList[i]));
	    					Date date = new Date();
	    					elevDao.updateElev(elevCont);
	    					Log log = new Log();
	    		        	log.setDate(date);
	    		        	log.setUser(session.getAttribute("nume")+" "+session.getAttribute("prenume"));
	    		        	log.setRol(session.getAttribute("functie").toString());
	    		        	log.setActiune("UPDATE");
	    		        	log.setDescriere("A actualizat contestatia elevului cu cnp-ul: " + cnpContList[i].toString());
	    		        	logDao.saveLog(log);
	    				}
	    			}
	        	}
	    	}
	        return;
        }
        }
        
     
	}catch (Throwable theException) 	    
	{
	     System.out.println(theException); 
	}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(true);
		session.removeAttribute("error");
		try
		{	  
		    if(request.getParameter("delogare")!=null){
		        //HttpSession session = request.getSession(true);	    
		          session.invalidate();
		          response.sendRedirect("login.jsp");
		    }
		    
		    
		    //insert Elev into DB
		    if(request.getParameter("adauga")!=null){
		     Elev elev = new Elev();
		     String nume = request.getParameter("nume");
		     String initialaTata = request.getParameter("init");
		     String prenume = request.getParameter("prenume");
		     String cnp = request.getParameter("cnp");
		     String denumireLiceu = request.getParameter("liceucombobox");
		     String denumireProfil = request.getParameter("profilcombobox");
		     
		     System.out.println("Denumire liceu: " + denumireLiceu);
		     
		     Liceu liceu = liceuDao.getLiceuByDenumire(denumireLiceu);
		     Profil profil = profilDao.getProfilByDenumire(denumireProfil);
		     long codLiceuProfil = liceuProfilDao.getCodLiceuProfilByLiceuAndProfil(liceu, profil);
		     
		     elev.setNume(nume);
			 elev.setInitialaTata(initialaTata);
			 elev.setPrenume(prenume);
			 elev.setCnp(cnp);
			 elev.setLiceuProfil(liceuProfilDao.findById(codLiceuProfil));
			 if(elevDao.getElevByCnp(cnp)==null){
		     Date date = new Date();
		     elevDao.saveElev(elev);
		     Log log = new Log();
        	log.setDate(date);
        	log.setUser(session.getAttribute("nume")+" "+session.getAttribute("prenume"));
        	log.setRol(session.getAttribute("functie").toString());
        	log.setActiune("INSERT");
        	log.setDescriere("A adaugat un elev cu cnp-ul: " + cnp);
        	logDao.saveLog(log);
		     
		     Elev elevDB = elevDao.getElevByCnp(cnp);
		     long tipProba = 1;
		     Proba proba1 = probaDao.getProbaBy(elevDB,tipProba);
		     tipProba = 2;
		     Proba proba2 = probaDao.getProbaBy(elevDB, tipProba);
		     Examen examen1 = new Examen();
		     examen1.setElev(elevDB);
		     examen1.setProba(proba1);
		     examenDao.saveExamen(examen1);
		     Examen examen2 = new Examen();
		     examen2.setElev(elevDB);
		     examen2.setProba(proba2);
		     examenDao.saveExamen(examen2);
		     
		     List<Examen> exameneListForElev = examenDao.getExamenListBy(elevDB);
		     for(Examen examen : exameneListForElev){
		    	 Rezultat rezultat = new Rezultat();
		    	 rezultat.setExamen(examen);
		    	 rezultat.setNotaInitiala(0.00d);
		    	 rezultat.setNotaContestatie(0.00d);
		    	 rezultatDao.saveRezultat(rezultat);
		     }
			 }
			 else {
				 System.out.println("Acest CNP exista deja in baza de date!");
				 session.setAttribute("error","Acest CNP exista deja in baza de date!");
			 }
		     
		     response.sendRedirect("adminPage.jsp");
		    }
		    
		  //update Elev
		    if(request.getParameter("update")!=null){
		     String nume = request.getParameter("numeUpdate");
		     String initialaTata = request.getParameter("initUpdate");
		     String prenume = request.getParameter("prenumeUpdate");
		     String cnp = session.getAttribute("cnpToUpdate").toString();
		     double nota1Update = Double.parseDouble(request.getParameter("nota1Update").toString());
		     double nota2Update = Double.parseDouble(request.getParameter("nota2Update").toString());
		     
		     Elev elev = elevDao.getElevByCnp(cnp);
		     elev.setNume(nume);
			 elev.setInitialaTata(initialaTata);
			 elev.setPrenume(prenume);
		     List<Examen> examene = elev.getExamene();
		     boolean proba3 = false;
		     for(Examen examen : examene){
		    	 if(examen.getProba().getTipProba() == 1){
		    		 examen.getRezultat().setNotaInitiala(nota1Update);
		    	 }
		    	 else if(examen.getProba().getTipProba() == 2){
		    		 examen.getRezultat().setNotaInitiala(nota2Update);
		    	 }
		    	 else if(examen.getProba().getTipProba() == 3){
		    		 proba3 = true;
		    		 double nota3Update = Double.parseDouble(request.getParameter("nota3Update").toString());
		    		 String denumire = request.getParameter("proba3combobox").toString();
		    		 Proba probaAleasa = probaDao.getProbaBy(elev, denumire);
		    		 examen.setProba(probaAleasa);
		    		 examen.getRezultat().setNotaInitiala(nota3Update);
		    	 }
		     }
		     if(!request.getParameter("proba3combobox").toString().equals("nu a ales inca") && proba3 == false){
		    	 Examen examen3 = new Examen();
		    	 String denumire = request.getParameter("proba3combobox").toString();
		    	 examen3.setProba(probaDao.getProbaBy(elev, denumire));
		    	 examen3.setElev(elev);
		    	 examenDao.saveExamen(examen3);
		    	 
		    	 Rezultat rezultat = new Rezultat();
		    	 double nota3Update = Double.parseDouble(request.getParameter("nota3Update").toString());
		    	 rezultat.setNotaInitiala(nota3Update);
		    	 rezultat.setNotaContestatie(0.00);
		    	 rezultat.setExamen(examen3);
		    	 rezultatDao.saveRezultat(rezultat);
		    	 //System.out.println(request.getParameter("proba3combobox").toString());
		     }
		     Date date = new Date();
			 elevDao.updateElev(elev);
			 Log log = new Log();
	         log.setDate(date);
	         log.setUser(session.getAttribute("nume")+" "+session.getAttribute("prenume"));
	         log.setRol(session.getAttribute("functie").toString());
	         log.setActiune("UPDATE");
	         log.setDescriere("A actualizat un elev cu cnp-ul: " + elev.getCnp());
	         logDao.saveLog(log);
		     
		     response.sendRedirect("adminPage.jsp");
		    }
		    
		    //cautare elev
	        if(request.getParameter("cautareAJAX").toString() != null){
	        	StringBuilder sbTable = new StringBuilder();
	        	String cautareFiltru = request.getParameter("cautareAJAX").toString();
	        	if(cautareFiltru!=""){
	        		List<Elev> eleviList = elevDao.getAllElevi();
	      		  long contor = -1;
	      		  for(Elev elev : eleviList){
	      			if((elev.getNume()+' ' + elev.getInitialaTata() + ' ' + elev.getPrenume()).toLowerCase().contains(cautareFiltru.toLowerCase()) ){
	      				Date date = new Date();
	      				 Log log = new Log();
	      		         log.setDate(date);
	      		         log.setUser(session.getAttribute("nume")+" "+session.getAttribute("prenume"));
	      		         log.setRol(session.getAttribute("functie").toString());
	      		         log.setActiune("SELECT");
	      		         log.setDescriere("A cautat studentul dupa nume: '" + cautareFiltru.toLowerCase()+"' ");
	      		         logDao.saveLog(log);
	      				contor++;
	      			sbTable.append("<tr>");
	      			sbTable.append("<td>").append(elev.getNume() + ' ' + elev.getInitialaTata() + ' ' + elev.getPrenume()).append("</td>");
	      			sbTable.append("<td>").append(elev.getCnp() + ' ').append("</td>");
	      			sbTable.append("<td>").append(elev.getLiceuProfil().getLiceu().getDenumire()).append("</td>");
	      			sbTable.append("<td>").append(elev.getLiceuProfil().getProfil().getDenumire()).append("</td>");
	      			sbTable.append("<td><button id ='stergebutton"+contor+"' class='btn btn-danger'><i class='fa fa-trash'></i>Sterge</button></td>");
	      			sbTable.append("</tr>");
	      			}
	      		  }
	        	}else{
	        		List<Elev> eleviList = elevDao.getAllElevi();
		      		  long contor = -1;
		      		  for(Elev elev : eleviList){
		      			  contor++;
		      			sbTable.append("<tr>");
		      			sbTable.append("<td>").append(elev.getNume() + ' ' + elev.getInitialaTata() + ' ' + elev.getPrenume()).append("</td>");
		      			sbTable.append("<td>").append(elev.getCnp() + ' ').append("</td>");
		      			sbTable.append("<td>").append(elev.getLiceuProfil().getLiceu().getDenumire()).append("</td>");
		      			sbTable.append("<td>").append(elev.getLiceuProfil().getProfil().getDenumire()).append("</td>");
		      			sbTable.append("<td><button id ='stergebutton"+contor+"' class='btn btn-danger'><i class='fa fa-trash'></i>Sterge</button></td>");
		      			sbTable.append("</tr>");
		      		}
	        	}
	        	out.write(sbTable.toString());
		        return;
	        }
		}	
		catch (Throwable theException) 	    
		{
		     System.out.println(theException); 
		}
	}

}
