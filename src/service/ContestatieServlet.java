package service;

import java.io.IOException;
import java.util.Date;

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
import model.Liceu;
import model.Log;
import model.Profil;

/**
 * Servlet implementation class ContestatieServlet
 */
@WebServlet("/ContestatieServlet")
public class ContestatieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContestatieServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("alert1"); 
		session.removeAttribute("alert2"); 
		session.removeAttribute("alert3"); 
		
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
				if(request.getParameter("proba1")!=null){
					if(elev.getExamene().get(0).getRezultat().getNotaContestatie()==0.0){
				    	if(elev.getExamene().get(0).getRezultat().getNotaInitiala()!=0.0){
							elev.getExamene().get(0).getRezultat().setNotaContestatie(-1.0);
				    		Date date = new Date();
				    		elevDao.updateElev(elev);
					    	Log log = new Log();
					    	log.setDate(date);
					    	log.setUser(session.getAttribute("nume")+" "+session.getAttribute("prenume"));
					    	log.setRol(session.getAttribute("functie").toString());
					    	log.setActiune("UPDATE");
					    	log.setDescriere("A contestat proba 1 ");
					    	logDao.saveLog(log);
				    		System.out.println("Ati contestat cu succes proba 1!");
							 session.setAttribute("alert1","Ati contestat cu succes proba 1!");
							 response.sendRedirect("contestatie.jsp");
				        }
				    	else{
				    		System.out.println("Deocamdata nu aveti o nota initiala la proba 1, deci nu puteti contesta nota!");
						 	session.setAttribute("alert1","Deocamdata nu aveti o nota initiala la proba 1, deci nu puteti contesta nota!");
						 	response.sendRedirect("contestatie.jsp");
				    	}
					}else {
						System.out.println("Ati facut deja contestatie la proba 1, va rog sa aflati nota din pagina Rezultate!");
					 	session.setAttribute("alert1","Ati facut deja contestatie la proba 1, va rog sa aflati nota din pagina Rezultate!");
					 	response.sendRedirect("contestatie.jsp");
					}
				}
				
				if(request.getParameter("proba2")!=null){
					if(elev.getExamene().get(1).getRezultat().getNotaContestatie()==0.0){
				    	if(elev.getExamene().get(1).getRezultat().getNotaInitiala()!=0.0){
							elev.getExamene().get(1).getRezultat().setNotaContestatie(-1.0);
							Date date = new Date();
				    		elevDao.updateElev(elev);
					    	Log log = new Log();
					    	log.setDate(date);
					    	log.setUser(session.getAttribute("nume")+" "+session.getAttribute("prenume"));
					    	log.setRol(session.getAttribute("functie").toString());
					    	log.setActiune("UPDATE");
					    	log.setDescriere("A contestat proba 2 ");
					    	logDao.saveLog(log);
				    		System.out.println("Ati contestat cu succes proba 2!");
							 session.setAttribute("alert2","Ati contestat cu succes proba 2!");
							 response.sendRedirect("contestatie.jsp");
				        }
				    	else{
				    		System.out.println("Deocamdata nu aveti o nota initiala la proba 2, deci nu puteti contesta nota!");
						 	session.setAttribute("alert2","Deocamdata nu aveti o nota initiala la proba 2, deci nu puteti contesta nota!");
						 	response.sendRedirect("contestatie.jsp");
				    	}
					}else {
						System.out.println("Ati facut deja contestatie la proba 2, va rog sa aflati nota din pagina Rezultate!");
					 	session.setAttribute("alert2","Ati facut deja contestatie la proba 2, va rog sa aflati nota din pagina Rezultate!");
					 	response.sendRedirect("contestatie.jsp");
					}
				}
				
				if(request.getParameter("proba3")!=null){
					if(elev.getExamene().get(2).getRezultat().getNotaContestatie()==0.0){
				    	if(elev.getExamene().get(2).getRezultat().getNotaInitiala()!=0.0){
							elev.getExamene().get(2).getRezultat().setNotaContestatie(-1.0);
							Date date = new Date();
				    		elevDao.updateElev(elev);
					    	Log log = new Log();
					    	log.setDate(date);
					    	log.setUser(session.getAttribute("nume")+" "+session.getAttribute("prenume"));
					    	log.setRol(session.getAttribute("functie").toString());
					    	log.setActiune("UPDATE");
					    	log.setDescriere("A contestat proba 3 ");
					    	logDao.saveLog(log);
				    		System.out.println("Ati contestat cu succes proba 3!");
							 session.setAttribute("alert3","Ati contestat cu succes proba 3!");
							 response.sendRedirect("contestatie.jsp");
				        }
				    	else{
				    		System.out.println("Deocamdata nu aveti o nota initiala la proba 3, deci nu puteti contesta nota!");
						 	session.setAttribute("alert3","Deocamdata nu aveti o nota initiala la proba 3, deci nu puteti contesta nota!");
						 	response.sendRedirect("contestatie.jsp");
				    	}
					}else {
						System.out.println("Ati facut deja contestatie la proba 3, va rog sa aflati nota din pagina Rezultate!");
					 	session.setAttribute("alert3","Ati facut deja contestatie la proba 3, va rog sa aflati nota din pagina Rezultate!");
					 	response.sendRedirect("contestatie.jsp");
					}
				}
			}
	}catch (Throwable theException) 	    
		{
    	System.out.println(theException); 
		}

	}
}
