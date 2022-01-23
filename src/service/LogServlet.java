package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import api.ElevDao;
import api.LiceuDao;
import api.LogDao;
import model.Elev;
import model.Log;

/**
 * Servlet implementation class LogServlet
 */
@WebServlet("/LogServlet")
public class LogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		LogDao logDao = new LogDao();
		
		try
		{
			if(request.getParameter("rolAJAX").toString()!=null || request.getParameter("dateAJAX").toString()!=null){
				String rolFiltru = request.getParameter("rolAJAX").toString();
		        String dateFiltru = request.getParameter("dateAJAX").toString();
		        if(dateFiltru!="" && rolFiltru.equals("filtreaza dupa rol")){
		        	List<Log> logList = logDao.findAll();
		        	Collections.reverse(logList); 
		        	StringBuilder logFiltrare = new StringBuilder();
		        	for(Log log : logList){
		        		Calendar calendar = Calendar.getInstance();
		        		calendar.setTime(log.getDate());
		        		if(calendar.get(Calendar.DAY_OF_MONTH)==Integer.parseInt(dateFiltru.substring(3, 5)) && (calendar.get(Calendar.MONTH)+1)==Integer.parseInt(dateFiltru.substring(0, 2)) && calendar.get(Calendar.YEAR)==Integer.parseInt(dateFiltru.substring(6, 10))){
		        			logFiltrare.append("<tr> <td>"+ log.getUser() +"</td>");
		        			logFiltrare.append("<td>"+ log.getRol() + "</td>");
		        			logFiltrare.append("<td>"+ log.getDate() + "</td>");
		        			logFiltrare.append("<td>"+ log.getActiune() + "</td>");
		        			logFiltrare.append("<td>"+ log.getDescriere() + "</td></tr>");
		        		}
		        	}
			        out.write(logFiltrare.toString());
			        return;
		        }else if(dateFiltru!="" && !rolFiltru.equals("filtreaza dupa rol")){
		        	List<Log> logList = logDao.findAll();
		        	Collections.reverse(logList); 
		        	StringBuilder logFiltrare = new StringBuilder();
		        	for(Log log : logList){
		        		Calendar calendar = Calendar.getInstance();
		        		calendar.setTime(log.getDate());
		        		if(log.getRol().equals(rolFiltru) && calendar.get(Calendar.DAY_OF_MONTH)==Integer.parseInt(dateFiltru.substring(3, 5)) && (calendar.get(Calendar.MONTH)+1)==Integer.parseInt(dateFiltru.substring(0, 2)) && calendar.get(Calendar.YEAR)==Integer.parseInt(dateFiltru.substring(6, 10))){
		        			logFiltrare.append("<tr> <td>"+ log.getUser() +"</td>");
		        			logFiltrare.append("<td>"+ log.getRol() + "</td>");
		        			logFiltrare.append("<td>"+ log.getDate() + "</td>");
		        			logFiltrare.append("<td>"+ log.getActiune() + "</td>");
		        			logFiltrare.append("<td>"+ log.getDescriere() + "</td></tr>");
		        		}
		        	}
			        out.write(logFiltrare.toString());
			        return;
		        }else if(dateFiltru=="" && !rolFiltru.equals("filtreaza dupa rol")){
		        	List<Log> logList = logDao.findAll();
		        	Collections.reverse(logList); 
		        	StringBuilder logFiltrare = new StringBuilder();
		        	for(Log log : logList){
		        		if(log.getRol().equals(rolFiltru)){
		        			logFiltrare.append("<tr> <td>"+ log.getUser() +"</td>");
		        			logFiltrare.append("<td>"+ log.getRol() + "</td>");
		        			logFiltrare.append("<td>"+ log.getDate() + "</td>");
		        			logFiltrare.append("<td>"+ log.getActiune() + "</td>");
		        			logFiltrare.append("<td>"+ log.getDescriere() + "</td></tr>");
		        		}
		        	}
			        out.write(logFiltrare.toString());
			        return;
		        }else {
		        	List<Log> logList = logDao.findAll();
		        	Collections.reverse(logList); 
		        	StringBuilder logFiltrare = new StringBuilder();
		        	for(Log log : logList){
		        			logFiltrare.append("<tr> <td>"+ log.getUser() +"</td>");
		        			logFiltrare.append("<td>"+ log.getRol() + "</td>");
		        			logFiltrare.append("<td>"+ log.getDate() + "</td>");
		        			logFiltrare.append("<td>"+ log.getActiune() + "</td>");
		        			logFiltrare.append("<td>"+ log.getDescriere() + "</td></tr>");
		        	}
			        out.write(logFiltrare.toString());
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
		HttpSession session = request.getSession(); 
		if(request.getParameter("delogare")!=null){
		        //HttpSession session = request.getSession(true);	    
		          session.invalidate();
		          response.sendRedirect("login.jsp");
		    }
	}

}
