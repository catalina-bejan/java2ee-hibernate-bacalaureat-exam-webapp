package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import api.ElevDao;
import api.ExamenDao;
import api.LiceuDao;
import api.LogDao;
import api.ProbaDao;
import api.ProfilDao;
import api.RezultatDao;
import model.Elev;
import model.Examen;
import model.Liceu;
import model.Log;
import model.Proba;
import model.Profil;
import model.Rezultat;

/**
 * Servlet implementation class ElevServlet
 */
@WebServlet("/ElevServlet")
public class ElevServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ElevServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//folosita la fiabilitate software pentru tema 2 pentru test JUnit si Mockito ElevServletTest, nu are functionalitate in acest proiect de fapt

//		if(request.getParameter("AflareNumeElev")!=null){ 
//			HttpSession session = request.getSession();
//			if(session.getAttribute("prenume") == null || session.getAttribute("nume") == null){
//				response.getWriter().write("Atributele prenume sau nume nu sunt setate in sesiune");
//			}else{
//				String prenume = session.getAttribute("prenume").toString();
//				String nume = session.getAttribute("nume").toString();
//				response.getWriter().write("Numele elevului: " + prenume + " " + nume);
//			}
//		}else{
//			response.getWriter().write("Nu se doreste aflarea numelui elevului.");
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("errorOptiune"); 
		session.removeAttribute("succes"); 
		
		try
		{	
			if(request.getParameter("delogare")!=null){   
		          session.invalidate();
		          response.sendRedirect("login.jsp");
		    }
			
		   ProbaDao probaDao = new ProbaDao();
	       ProfilDao profilDao = new ProfilDao();
	       ElevDao elevDao = new ElevDao();
	       LiceuDao liceuDao = new LiceuDao();
	       ExamenDao examenDao = new ExamenDao();
	       RezultatDao rezultatDao = new RezultatDao();
	       LogDao logDao = new LogDao();
	       Elev elev = elevDao.getElevByCnp(session.getAttribute("cnpElev").toString());
	       Profil profil = profilDao.getProfilOfElev(elev);
	       Liceu liceu = liceuDao.getLiceuByElev(elev);
	       //adauga proba la alegere
			if(request.getParameter("adauga")!=null){
				if(probaDao.getProbaAleasaBy(elev)==null){
					String denumireProba = request.getParameter("disciplinacombobox").toString();
					Proba proba = probaDao.getProbaBy(elev, denumireProba);
					Examen examen3 = new Examen();
			    	examen3.setProba(proba);
			    	examen3.setElev(elev);
			    	examenDao.saveExamen(examen3);
			    	 
			    	Rezultat rezultat = new Rezultat();
			    	rezultat.setNotaInitiala(0.00);
			    	rezultat.setNotaContestatie(0.00);
			    	rezultat.setExamen(examen3);
			    	rezultatDao.saveRezultat(rezultat);
			    	
			    	Date date = new Date();
			    	Log log = new Log();
			    	log.setDate(date);
			    	log.setUser(session.getAttribute("nume")+" "+session.getAttribute("prenume"));
			    	log.setRol(session.getAttribute("functie").toString());
			    	log.setActiune("UPDATE");
			    	log.setDescriere("A adaugat proba la alegere: " + proba.getDenumire());
			    	logDao.saveLog(log);
			    	
			    	System.out.println("Succes la examenul de Bacalaureat!");
					 session.setAttribute("succes","Succes la examenul de Bacalaureat!");
					 response.sendRedirect("elevpage.jsp");
			}else {
				System.out.println("Optiunea elevului exista deja in baza de date!");
				 session.setAttribute("errorOptiune","Optiunea elevului exista deja in baza de date!");
				 response.sendRedirect("elevpage.jsp");
			}
		}

		}catch (Throwable theException) 	    
			{
	    	System.out.println(theException); 
			}
}
}
