package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import api.ElevDao;
import api.LiceuDao;
import model.Elev;
import model.Liceu;

/**
 * Servlet implementation class RezultateServlet
 */
@WebServlet("/RezultateServlet")
public class RezultateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RezultateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		LiceuDao liceuDao = new LiceuDao();
		ElevDao elevDao = new ElevDao();
		
		try
		{
			if(request.getParameter("cautareAJAX").toString()!=null || request.getParameter("liceuAJAX").toString()!=null){
				long admisi = 0;
				long respinsi = 0;
				long neprezentati = 0;
				String cautareFiltru = request.getParameter("cautareAJAX").toString();
		        String liceuF = request.getParameter("liceuAJAX").toString();
		        if(cautareFiltru!="" && liceuF.equals("filtreaza dupa liceu")){
		        	List<Elev> eleviRezultate = elevDao.getEleviRezultateList();
		        	StringBuilder eleviFiltrare = new StringBuilder();
		        	for(Elev elev : eleviRezultate){
		        		if((elev.getNume()+' ' + elev.getInitialaTata() + ' ' + elev.getPrenume()).toLowerCase().contains(cautareFiltru.toLowerCase()) ){
		        			eleviFiltrare.append("<tr> <td style='background-color: #debad3;'>"+ elev.getNume() + ' ' + elev.getInitialaTata() + ' ' + elev.getPrenume() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getLiceuProfil().getLiceu().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getLiceuProfil().getProfil().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(0).getProba().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(0).getRezultat().getNotaInitiala() + "</td>");
		        			eleviFiltrare.append("<td>"+ (elev.getExamene().get(0).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(0).getRezultat().getNotaContestatie()) + "</td>");
		        			double notaFinala1 = 0;
		        			if (elev.getExamene().get(0).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(0).getRezultat().getNotaContestatie() == -1) {
		        				notaFinala1 = elev.getExamene().get(0).getRezultat().getNotaInitiala();
		        			}else {
		        				notaFinala1 = elev.getExamene().get(0).getRezultat().getNotaContestatie();
		        			}
		        			eleviFiltrare.append("<td>"+ notaFinala1 + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(1).getProba().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(1).getRezultat().getNotaInitiala() + "</td>");
		        			eleviFiltrare.append("<td>"+ (elev.getExamene().get(1).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(1).getRezultat().getNotaContestatie()) + "</td>");
		        			double notaFinala2 = 0;
		        			if (elev.getExamene().get(1).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(1).getRezultat().getNotaContestatie() == -1) {
		        				notaFinala2 = elev.getExamene().get(1).getRezultat().getNotaInitiala();
		        			}else {
		        				notaFinala2 = elev.getExamene().get(1).getRezultat().getNotaContestatie();
		        			}
		        			eleviFiltrare.append("<td>"+ notaFinala2 + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(2).getProba().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(2).getRezultat().getNotaInitiala() + "</td>");
		        			eleviFiltrare.append("<td>"+ (elev.getExamene().get(2).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(2).getRezultat().getNotaContestatie()) + "</td>");
		        			double notaFinala3 = 0;
		        			if (elev.getExamene().get(2).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(2).getRezultat().getNotaContestatie() == -1) {
		        				notaFinala3 = elev.getExamene().get(2).getRezultat().getNotaInitiala();
		        			}else {
		        				notaFinala3 = elev.getExamene().get(2).getRezultat().getNotaContestatie();
		        			}
		        			eleviFiltrare.append("<td>"+ notaFinala3 + "</td>");
		        			double media = (notaFinala1 + notaFinala2 + notaFinala3)/3.0;
		        			String mediaS = String.format("%.2f", media);
		        			eleviFiltrare.append("<td>"+ mediaS + "</td>");
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
		        			eleviFiltrare.append("<td>"+ rezultat + "</td></tr>");
		        		}
		        	}
			        out.write(eleviFiltrare.toString()+'|'+admisi+'|'+respinsi+'|'+neprezentati);
			        return;
		        }else if(cautareFiltru!="" && !liceuF.equals("filtreaza dupa liceu")){
		        	List<Elev> eleviRezultate = elevDao.getEleviRezultateList();
		        	StringBuilder eleviFiltrare = new StringBuilder();
		        	for(Elev elev : eleviRezultate){
		        		if((elev.getNume()+' ' + elev.getInitialaTata() + ' ' + elev.getPrenume()).toLowerCase().contains(cautareFiltru.toLowerCase()) && elev.getLiceuProfil().getLiceu().getDenumire().equals(liceuF) ){
		        			eleviFiltrare.append("<tr> <td style='background-color: #debad3;'>"+ elev.getNume() + ' ' + elev.getInitialaTata() + ' ' + elev.getPrenume() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getLiceuProfil().getLiceu().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getLiceuProfil().getProfil().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(0).getProba().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(0).getRezultat().getNotaInitiala() + "</td>");
		        			eleviFiltrare.append("<td>"+ (elev.getExamene().get(0).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(0).getRezultat().getNotaContestatie()) + "</td>");
		        			double notaFinala1 = 0;
		        			if (elev.getExamene().get(0).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(0).getRezultat().getNotaContestatie() == -1) {
		        				notaFinala1 = elev.getExamene().get(0).getRezultat().getNotaInitiala();
		        			}else {
		        				notaFinala1 = elev.getExamene().get(0).getRezultat().getNotaContestatie();
		        			}
		        			eleviFiltrare.append("<td>"+ notaFinala1 + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(1).getProba().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(1).getRezultat().getNotaInitiala() + "</td>");
		        			eleviFiltrare.append("<td>"+ (elev.getExamene().get(1).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(1).getRezultat().getNotaContestatie()) + "</td>");
		        			double notaFinala2 = 0;
		        			if (elev.getExamene().get(1).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(1).getRezultat().getNotaContestatie() == -1) {
		        				notaFinala2 = elev.getExamene().get(1).getRezultat().getNotaInitiala();
		        			}else {
		        				notaFinala2 = elev.getExamene().get(1).getRezultat().getNotaContestatie();
		        			}
		        			eleviFiltrare.append("<td>"+ notaFinala2 + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(2).getProba().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(2).getRezultat().getNotaInitiala() + "</td>");
		        			eleviFiltrare.append("<td>"+ (elev.getExamene().get(2).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(2).getRezultat().getNotaContestatie()) + "</td>");
		        			double notaFinala3 = 0;
		        			if (elev.getExamene().get(2).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(2).getRezultat().getNotaContestatie() == -1) {
		        				notaFinala3 = elev.getExamene().get(2).getRezultat().getNotaInitiala();
		        			}else {
		        				notaFinala3 = elev.getExamene().get(2).getRezultat().getNotaContestatie();
		        			}
		        			eleviFiltrare.append("<td>"+ notaFinala3 + "</td>");
		        			double media = (notaFinala1 + notaFinala2 + notaFinala3)/3.0;
		        			String mediaS = String.format("%.2f", media);
		        			eleviFiltrare.append("<td>"+ mediaS + "</td>");
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
		        			eleviFiltrare.append("<td>"+ rezultat + "</td></tr>");
		        		}
		        	}
			        out.write(eleviFiltrare.toString()+'|'+admisi+'|'+respinsi+'|'+neprezentati);
			        return;
		        }else if(cautareFiltru=="" && !liceuF.equals("filtreaza dupa liceu")){
		        	List<Elev> eleviRezultate = elevDao.getEleviRezultateList();
		        	StringBuilder eleviFiltrare = new StringBuilder();
		        	for(Elev elev : eleviRezultate){
		        		if(elev.getLiceuProfil().getLiceu().getDenumire().equals(liceuF)){
		        			eleviFiltrare.append("<tr> <td style='background-color: #debad3;'>"+ elev.getNume() + ' ' + elev.getInitialaTata() + ' ' + elev.getPrenume() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getLiceuProfil().getLiceu().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getLiceuProfil().getProfil().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(0).getProba().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(0).getRezultat().getNotaInitiala() + "</td>");
		        			eleviFiltrare.append("<td>"+ (elev.getExamene().get(0).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(0).getRezultat().getNotaContestatie()) + "</td>");
		        			double notaFinala1 = 0;
		        			if (elev.getExamene().get(0).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(0).getRezultat().getNotaContestatie() == -1) {
		        				notaFinala1 = elev.getExamene().get(0).getRezultat().getNotaInitiala();
		        			}else {
		        				notaFinala1 = elev.getExamene().get(0).getRezultat().getNotaContestatie();
		        			}
		        			eleviFiltrare.append("<td>"+ notaFinala1 + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(1).getProba().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(1).getRezultat().getNotaInitiala() + "</td>");
		        			eleviFiltrare.append("<td>"+ (elev.getExamene().get(1).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(1).getRezultat().getNotaContestatie()) + "</td>");
		        			double notaFinala2 = 0;
		        			if (elev.getExamene().get(1).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(1).getRezultat().getNotaContestatie() == -1) {
		        				notaFinala2 = elev.getExamene().get(1).getRezultat().getNotaInitiala();
		        			}else {
		        				notaFinala2 = elev.getExamene().get(1).getRezultat().getNotaContestatie();
		        			}
		        			eleviFiltrare.append("<td>"+ notaFinala2 + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(2).getProba().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(2).getRezultat().getNotaInitiala() + "</td>");
		        			eleviFiltrare.append("<td>"+ (elev.getExamene().get(2).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(2).getRezultat().getNotaContestatie()) + "</td>");
		        			double notaFinala3 = 0;
		        			if (elev.getExamene().get(2).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(2).getRezultat().getNotaContestatie() == -1) {
		        				notaFinala3 = elev.getExamene().get(2).getRezultat().getNotaInitiala();
		        			}else {
		        				notaFinala3 = elev.getExamene().get(2).getRezultat().getNotaContestatie();
		        			}
		        			eleviFiltrare.append("<td>"+ notaFinala3 + "</td>");
		        			double media = (notaFinala1 + notaFinala2 + notaFinala3)/3.0;
		        			String mediaS = String.format("%.2f", media);
		        			eleviFiltrare.append("<td>"+ mediaS + "</td>");
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
		        			eleviFiltrare.append("<td>"+ rezultat + "</td></tr>");
		        		}
		        	}
			        out.write(eleviFiltrare.toString()+'|'+admisi+'|'+respinsi+'|'+neprezentati);
			        return;
		        }else {
		        	List<Elev> eleviRezultate = elevDao.getEleviRezultateList();
		        	StringBuilder eleviFiltrare = new StringBuilder();
		        	for(Elev elev : eleviRezultate){
		        			eleviFiltrare.append("<tr> <td style='background-color: #debad3;'>"+ elev.getNume() + ' ' + elev.getInitialaTata() + ' ' + elev.getPrenume() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getLiceuProfil().getLiceu().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getLiceuProfil().getProfil().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(0).getProba().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(0).getRezultat().getNotaInitiala() + "</td>");
		        			eleviFiltrare.append("<td>"+ (elev.getExamene().get(0).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(0).getRezultat().getNotaContestatie()) + "</td>");
		        			double notaFinala1 = 0;
		        			if (elev.getExamene().get(0).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(0).getRezultat().getNotaContestatie() == -1) {
		        				notaFinala1 = elev.getExamene().get(0).getRezultat().getNotaInitiala();
		        			}else {
		        				notaFinala1 = elev.getExamene().get(0).getRezultat().getNotaContestatie();
		        			}
		        			eleviFiltrare.append("<td>"+ notaFinala1 + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(1).getProba().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(1).getRezultat().getNotaInitiala() + "</td>");
		        			eleviFiltrare.append("<td>"+ (elev.getExamene().get(1).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(1).getRezultat().getNotaContestatie()) + "</td>");
		        			double notaFinala2 = 0;
		        			if (elev.getExamene().get(1).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(1).getRezultat().getNotaContestatie() == -1) {
		        				notaFinala2 = elev.getExamene().get(1).getRezultat().getNotaInitiala();
		        			}else {
		        				notaFinala2 = elev.getExamene().get(1).getRezultat().getNotaContestatie();
		        			}
		        			eleviFiltrare.append("<td>"+ notaFinala2 + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(2).getProba().getDenumire() + "</td>");
		        			eleviFiltrare.append("<td>"+ elev.getExamene().get(2).getRezultat().getNotaInitiala() + "</td>");
		        			eleviFiltrare.append("<td>"+ (elev.getExamene().get(2).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(2).getRezultat().getNotaContestatie()) + "</td>");
		        			double notaFinala3 = 0;
		        			if (elev.getExamene().get(2).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(2).getRezultat().getNotaContestatie() == -1) {
		        				notaFinala3 = elev.getExamene().get(2).getRezultat().getNotaInitiala();
		        			}else {
		        				notaFinala3 = elev.getExamene().get(2).getRezultat().getNotaContestatie();
		        			}
		        			eleviFiltrare.append("<td>"+ notaFinala3 + "</td>");
		        			double media = (notaFinala1 + notaFinala2 + notaFinala3)/3.0;
		        			String mediaS = String.format("%.2f", media);
		        			eleviFiltrare.append("<td>"+ mediaS + "</td>");
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
		        			eleviFiltrare.append("<td>"+ rezultat + "</td></tr>");
		        	}
			        out.write(eleviFiltrare.toString()+'|'+admisi+'|'+respinsi+'|'+neprezentati);
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
//		HttpSession session = request.getSession();
//		PrintWriter out = response.getWriter();
//		LiceuDao liceuDao = new LiceuDao();
//		ElevDao elevDao = new ElevDao();
//		
//		if(request.getParameter("filtrareAJAX").toString()!=null){
//			String liceuFiltru = request.getParameter("filtrareAJAX").toString();
//	        if(liceuFiltru != null){
//	        	System.out.println(liceuFiltru);
//	        	List<Elev> eleviRezultate = elevDao.getEleviRezultateList();
//	        	StringBuilder eleviFiltrareLiceu = new StringBuilder();
//	        	for(Elev elev : eleviRezultate){
//	        		if(!liceuFiltru.equals("filtreaza dupa liceu")){
//	        		if(elev.getLiceuProfil().getLiceu().getDenumire().equals(liceuFiltru)){
//	        			System.out.println(elev.getNume());
//	        			eleviFiltrareLiceu.append("<tr> <td style='background-color: #debad3;'>"+ elev.getNume() + ' ' + elev.getInitialaTata() + ' ' + elev.getPrenume() + "</td>");
//	        			eleviFiltrareLiceu.append("<td>"+ elev.getLiceuProfil().getLiceu().getDenumire() + "</td>");
//	        			eleviFiltrareLiceu.append("<td>"+ elev.getLiceuProfil().getProfil().getDenumire() + "</td>");
//	        			eleviFiltrareLiceu.append("<td>"+ elev.getExamene().get(0).getProba().getDenumire() + "</td>");
//	        			eleviFiltrareLiceu.append("<td>"+ elev.getExamene().get(0).getRezultat().getNotaInitiala() + "</td>");
//	        			eleviFiltrareLiceu.append("<td>"+ (elev.getExamene().get(0).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(0).getRezultat().getNotaContestatie()) + "</td>");
//	        			double notaFinala1 = 0;
//	        			if (elev.getExamene().get(0).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(0).getRezultat().getNotaContestatie() == -1) {
//	        				notaFinala1 = elev.getExamene().get(0).getRezultat().getNotaInitiala();
//	        			}else {
//	        				notaFinala1 = elev.getExamene().get(0).getRezultat().getNotaContestatie();
//	        			}
//	        			eleviFiltrareLiceu.append("<td>"+ notaFinala1 + "</td>");
//	        			eleviFiltrareLiceu.append("<td>"+ elev.getExamene().get(1).getProba().getDenumire() + "</td>");
//	        			eleviFiltrareLiceu.append("<td>"+ elev.getExamene().get(1).getRezultat().getNotaInitiala() + "</td>");
//	        			eleviFiltrareLiceu.append("<td>"+ (elev.getExamene().get(1).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(1).getRezultat().getNotaContestatie()) + "</td>");
//	        			double notaFinala2 = 0;
//	        			if (elev.getExamene().get(1).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(1).getRezultat().getNotaContestatie() == -1) {
//	        				notaFinala2 = elev.getExamene().get(1).getRezultat().getNotaInitiala();
//	        			}else {
//	        				notaFinala2 = elev.getExamene().get(1).getRezultat().getNotaContestatie();
//	        			}
//	        			eleviFiltrareLiceu.append("<td>"+ notaFinala2 + "</td>");
//	        			eleviFiltrareLiceu.append("<td>"+ elev.getExamene().get(2).getProba().getDenumire() + "</td>");
//	        			eleviFiltrareLiceu.append("<td>"+ elev.getExamene().get(2).getRezultat().getNotaInitiala() + "</td>");
//	        			eleviFiltrareLiceu.append("<td>"+ (elev.getExamene().get(2).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(2).getRezultat().getNotaContestatie()) + "</td>");
//	        			double notaFinala3 = 0;
//	        			if (elev.getExamene().get(2).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(2).getRezultat().getNotaContestatie() == -1) {
//	        				notaFinala3 = elev.getExamene().get(2).getRezultat().getNotaInitiala();
//	        			}else {
//	        				notaFinala3 = elev.getExamene().get(2).getRezultat().getNotaContestatie();
//	        			}
//	        			eleviFiltrareLiceu.append("<td>"+ notaFinala3 + "</td>");
//	        			double media = (notaFinala1 + notaFinala2 + notaFinala3)/3.0;
//	        			String mediaS = String.format("%.2f", media);
//	        			eleviFiltrareLiceu.append("<td>"+ mediaS + "</td>");
//	        			String rezultat;
//	        			if(media>=6 && notaFinala1>=5 && notaFinala2>=5 && notaFinala3>=5){
//	        				rezultat = "ADMIS";
//	        			}else {
//	        				rezultat = "RESPINS";
//	        				}
//	        			eleviFiltrareLiceu.append("<td>"+ rezultat + "</td></tr>");
//	        		}
//	        	}else{
//	        		eleviFiltrareLiceu.append("<tr> <td style='background-color: #debad3;'>"+ elev.getNume() + ' ' + elev.getInitialaTata() + ' ' + elev.getPrenume() + "</td>");
//	    			eleviFiltrareLiceu.append("<td>"+ elev.getLiceuProfil().getLiceu().getDenumire() + "</td>");
//	    			eleviFiltrareLiceu.append("<td>"+ elev.getLiceuProfil().getProfil().getDenumire() + "</td>");
//	    			eleviFiltrareLiceu.append("<td>"+ elev.getExamene().get(0).getProba().getDenumire() + "</td>");
//	    			eleviFiltrareLiceu.append("<td>"+ elev.getExamene().get(0).getRezultat().getNotaInitiala() + "</td>");
//	    			eleviFiltrareLiceu.append("<td>"+ (elev.getExamene().get(0).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(0).getRezultat().getNotaContestatie()) + "</td>");
//	    			double notaFinala1 = 0;
//	    			if (elev.getExamene().get(0).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(0).getRezultat().getNotaContestatie() == -1) {
//	    				notaFinala1 = elev.getExamene().get(0).getRezultat().getNotaInitiala();
//	    			}else {
//	    				notaFinala1 = elev.getExamene().get(0).getRezultat().getNotaContestatie();
//	    			}
//	    			eleviFiltrareLiceu.append("<td>"+ notaFinala1 + "</td>");
//	    			eleviFiltrareLiceu.append("<td>"+ elev.getExamene().get(1).getProba().getDenumire() + "</td>");
//	    			eleviFiltrareLiceu.append("<td>"+ elev.getExamene().get(1).getRezultat().getNotaInitiala() + "</td>");
//	    			eleviFiltrareLiceu.append("<td>"+ (elev.getExamene().get(1).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(1).getRezultat().getNotaContestatie()) + "</td>");
//	    			double notaFinala2 = 0;
//	    			if (elev.getExamene().get(1).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(1).getRezultat().getNotaContestatie() == -1) {
//	    				notaFinala2 = elev.getExamene().get(1).getRezultat().getNotaInitiala();
//	    			}else {
//	    				notaFinala2 = elev.getExamene().get(1).getRezultat().getNotaContestatie();
//	    			}
//	    			eleviFiltrareLiceu.append("<td>"+ notaFinala2 + "</td>");
//	    			eleviFiltrareLiceu.append("<td>"+ elev.getExamene().get(2).getProba().getDenumire() + "</td>");
//	    			eleviFiltrareLiceu.append("<td>"+ elev.getExamene().get(2).getRezultat().getNotaInitiala() + "</td>");
//	    			eleviFiltrareLiceu.append("<td>"+ (elev.getExamene().get(2).getRezultat().getNotaContestatie()<0? "0.00" : elev.getExamene().get(2).getRezultat().getNotaContestatie()) + "</td>");
//	    			double notaFinala3 = 0;
//	    			if (elev.getExamene().get(2).getRezultat().getNotaContestatie() == 0.0 || elev.getExamene().get(2).getRezultat().getNotaContestatie() == -1) {
//	    				notaFinala3 = elev.getExamene().get(2).getRezultat().getNotaInitiala();
//	    			}else {
//	    				notaFinala3 = elev.getExamene().get(2).getRezultat().getNotaContestatie();
//	    			}
//	    			eleviFiltrareLiceu.append("<td>"+ notaFinala3 + "</td>");
//	    			double media = (notaFinala1 + notaFinala2 + notaFinala3)/3.0;
//	    			String mediaS = String.format("%.2f", media);
//	    			eleviFiltrareLiceu.append("<td>"+ mediaS + "</td>");
//	    			String rezultat;
//	    			if(media>=6 && notaFinala1>=5 && notaFinala2>=5 && notaFinala3>=5){
//	    				rezultat = "ADMIS";
//	    			}else {
//	    				rezultat = "RESPINS";
//	    				}
//	    			eleviFiltrareLiceu.append("<td>"+ rezultat + "</td></tr>");
//	        		}
//	        	}
//		        out.write(eleviFiltrareLiceu.toString());
//		        return;
//	        	}
//			}
	}

}
