package api;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import model.Elev;
import model.Liceu;
import model.Proba;
import model.Profil;
import model.User;
import util.HibernateUtil;

public class ElevDao {
	
	public String saveElev(Elev elev) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.save(elev);
            // commit transaction
            transaction.commit();
            return "Tranzactie efectuata cu succes";
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return "Tranzactie esuata";
        }
        finally {
        	session.close();
        }
    }
	
	public String deleteElev(Elev elev) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            //find the object
            Query query = session.createQuery("FROM Elev E WHERE E.cnp = :cnp");
            query.setParameter("cnp", elev.getCnp());
            Elev elevToDelete = (Elev) query.uniqueResult();
            // delete the student object
            session.delete(elevToDelete);
            // commit transaction
            transaction.commit();
            return "Tranzactie efectuata cu succes";
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return "Tranzactie esuata";
        }
        finally {
        	session.close();
        }
    }
	
	public String updateElev(Elev elev) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // update the student object
            session.update(elev);
            // commit transaction
            transaction.commit();
            return "Tranzactie efectuata cu succes";
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return "Tranzactie esuata";
        }
        finally {
        	session.close();
        }
    }
	
	public List<Elev> getAllElevi() {
    	Transaction transaction = null;
    	List<Elev> allElevi = new LinkedList();
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an user object
            allElevi = (List<Elev>)session.createQuery("FROM Elev").getResultList();
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        finally {
        	session.close();
        }
        return allElevi;    
    }	
	
	public Elev getElevByCnp(String cnp) {
    	Transaction transaction = null;
    	Elev allElev = null;
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an user object
            Query query = session.createQuery("FROM Elev E WHERE E.cnp = :cnp");
            query.setParameter("cnp", cnp);
            allElev = (Elev)query.uniqueResult();
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        finally {
        	session.close();
        }
        return allElev;    
    }	
	
	public List<Elev> getEleviContestatieList() {
    	Transaction transaction = null;
    	List<Elev> allElev = new LinkedList();
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an elev contestatie list
            Query query = session.createNativeQuery("select elev.* "
                + "from elev,proba,examen,rezultat "
                + "where elev.code = examen.code "
                + "and proba.codpr=examen.codpr "
                + "and rezultat.codex=examen.codex "
                + "and nota_cont <> 0.0 "
                + "order by elev.code,proba.tip_proba");
            ((NativeQuery) query).addEntity(Elev.class);
            allElev = (List<Elev>)query.getResultList();
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        finally {
        	session.close();
        }
        return allElev;    
    }	
	
	public List<Elev> getEleviRezultateList() {
    	Transaction transaction = null;
    	List<Elev> allElev = new LinkedList();
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an elev contestatie list
            Query query = session.createNativeQuery("select elev.* "
            		+ "from rezultat r1, rezultat r2, rezultat r3, examen e1, examen e2, examen e3, elev, proba p1, proba p2, proba p3, liceu, profil, liceu_profil "
            		+ "where profil.codp= liceu_profil.codp "
            		+ "and liceu.codl=liceu_profil.codl "
            		+ "and elev.codlp=liceu_profil.codlp "
            		+ "and p1.tip_proba = 1 "
            		+ "and p2.tip_proba = 2 "
            		+ "and p3.tip_proba = 3 "
            		+ "and r1.codex = e1.codex "
            		+ "and e1.code = elev.code "
            		+ "and e1.codpr = p1.codpr "
            		+ "and r2.codex = e2.codex "
            		+ "and e2.code = elev.code "
            		+ "and e2.codpr = p2.codpr "
            		+ "and r3.codex = e3.codex "
            		+ "and e3.code = elev.code "
            		+ "and e3.codpr = p3.codpr "
            		+ "and elev.code in "
            		+ "(select elev.code from examen,elev "
            		+ "where elev.code=examen.code "
            		+ "group by elev.code "
            		+ "having count(examen.code)=3) "
            		+ "order by elev.nume,elev.init_tata,elev.prenume ");
            ((NativeQuery) query).addEntity(Elev.class);
            allElev = (List<Elev>)query.getResultList();
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        finally {
        	session.close();
        }
        return allElev;    
    }	
}
