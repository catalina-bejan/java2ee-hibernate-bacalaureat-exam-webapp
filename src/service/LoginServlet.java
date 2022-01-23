package service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import api.LoginDao;
import api.UserDao;
import model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		try
		{	   
			LoginDao loginDao = new LoginDao();
			UserDao userDao = new UserDao();
		    if(request.getParameter("delogare")!=null){
		        HttpSession session = request.getSession(true);	    
		          session.invalidate();
		          response.sendRedirect("login.jsp");
		    }
		    
		    if(request.getParameter("rezultate")!=null){
		          response.sendRedirect("rezultate.jsp");
		    }
		    
		     User user = new User();
		     String username = request.getParameter("username");
		     String password = request.getParameter("password");
		     
		     boolean isValid = loginDao.validate(username,password);
			   		    
		     if (isValid)
		     {  
		          user = userDao.getUserBy(username, password);
		          HttpSession session = request.getSession(true);	    
		          session.setAttribute("nume",user.getNume()); 
		          session.setAttribute("prenume",user.getPrenume()); 
		          session.setAttribute("functie", user.getFunctie());
		          
		      
		          if(user.getFunctie().equals("admin")){
		              response.sendRedirect("adminPage.jsp");
		          }
		          else if(user.getFunctie().equals("elev")){
		          session.setAttribute("cnpElev", user.getCnp());
		          session.setAttribute("nume", user.getNume());
		          session.setAttribute("prenume", user.getPrenume());
		          response.sendRedirect("elevpage.jsp"); //logged-in page    
		          }  
		          response.sendRedirect("login.jsp");
		     }
			        
		     else 
		          response.sendRedirect("login.jsp"); //error page 
		} 
				
				
		catch (Throwable theException) 	    
		{
		     System.out.println(theException); 
		}
	}

}
