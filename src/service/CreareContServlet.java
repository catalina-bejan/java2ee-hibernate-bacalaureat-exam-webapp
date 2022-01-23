package service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import api.ElevDao;
import api.LogDao;
import api.UserDao;
import model.Elev;
import model.Examen;
import model.Liceu;
import model.Log;
import model.Proba;
import model.Profil;
import model.Rezultat;
import model.User;

/**
 * Servlet implementation class CreareContServlet
 */
@WebServlet("/CreareContServlet")
public class CreareContServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ElevDao elevDao = new ElevDao();
	UserDao userDao = new UserDao();
	LogDao logDao = new LogDao();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreareContServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//create account
		HttpSession session = request.getSession();
		session.removeAttribute("errorCNP"); 
		session.removeAttribute("errorUsername"); 
		session.removeAttribute("errorUser"); 
	    if(request.getParameter("submit")!=null){
	    	if(userDao.getUserByCnp(request.getParameter("cnp").toString())==null){
		    	if(elevDao.getElevByCnp(request.getParameter("cnp").toString())!=null){
		    		if(userDao.getUserBy(request.getParameter("username").toString())==null){
		    			User user = new User();
		    			user.setCnp(request.getParameter("cnp").toString());
		    			Elev elev = elevDao.getElevByCnp(request.getParameter("cnp").toString());
		    			user.setNume(elev.getNume());
		    			user.setPrenume(elev.getPrenume());
		    			user.setUsername(request.getParameter("username").toString());
		    			user.setParola(request.getParameter("password").toString());
		    			user.setFunctie("elev");
		    			userDao.saveUser(user);
		    			
		    			response.sendRedirect("login.jsp");
		    		}else {
		    			System.out.println("Acest username este deja utilizat!");
						 session.setAttribute("errorUsername","Acest username este deja utilizat!");
						 response.sendRedirect("crearecont.jsp");
		    		}
		    	}else {
					 System.out.println("Nu exista un elev cu acest CNP in baza de date!");
					 session.setAttribute("errorCNP","Nu exista un elev cu acest CNP in baza de date!");
					 response.sendRedirect("crearecont.jsp");
				 }
	    	}else {
	    		System.out.println("Acest elev are deja cont de utilizator, username-ul este: " + userDao.getUserByCnp(request.getParameter("cnp").toString()).getUsername());
				session.setAttribute("errorUser","Acest elev are deja cont de utilizator, username-ul este: " + userDao.getUserByCnp(request.getParameter("cnp").toString()).getUsername());
				response.sendRedirect("login.jsp");
	    	}
	    }
	}
}
