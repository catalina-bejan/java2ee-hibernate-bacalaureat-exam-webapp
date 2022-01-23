/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        // If you have any <init-param> in web.xml, then you could get them
        // here by config.getInitParameter("name") and assign it as field.
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String requestPath = request.getRequestURI();
        
        if(!(requestPath.endsWith("login.jsp") || requestPath.endsWith("LoginServlet") || requestPath.endsWith("crearecont.jsp") || requestPath.endsWith("rezultate.jsp") || requestPath.endsWith("CreareContServlet") || requestPath.endsWith("RezultateServlet") ||requestPath.endsWith("/") || requestPath.endsWith("tryme.html") || requestPath.endsWith("ask.html"))){
            if(session == null || session.getAttribute("functie") == null){
                response.sendRedirect(request.getContextPath() + "/login.jsp"); 
            }
            if(!session.getAttribute("functie").toString().equals("admin") && (requestPath.endsWith("adminPage.jsp") || requestPath.endsWith("databaselog.jsp") || requestPath.endsWith("AdminServlet") || requestPath.endsWith("LogServlet"))){
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
            else if(!session.getAttribute("functie").toString().equals("elev") && (requestPath.endsWith("elevpage.jsp") || requestPath.endsWith("contestatie.jsp") || requestPath.endsWith("ElevServlet") || requestPath.endsWith("ContestatieServlet"))){
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
            else {
            chain.doFilter(req, res); // Logged-in user found, so just continue request.
            }
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
        // If you have assigned any expensive resources as field of
        // this Filter class, then you could clean/close them here.
    }
    
    //basic validation of pages that do not require authentication
    private boolean needsAuthentication(String url) {
        String[] validNonAuthenticationUrls =
            { "login.jsp"};
        for(String validUrl : validNonAuthenticationUrls) {
            if (url.endsWith(validUrl)) {
                return false;
            }
        }
        return true;
    }

}
