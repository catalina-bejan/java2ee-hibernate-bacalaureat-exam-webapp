package api;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import model.Elev;
import model.Examen;
import model.Proba;
import model.Rezultat;
import util.HibernateUtil;

public class RezultatDao {
	
	public void saveRezultat(Rezultat rezultat) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the rezultat object
            session.save(rezultat);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
	
	//getNotaInitiala si notaContestatie
	public Rezultat getRezultatBy(Elev elev, long tipProba) {
    	Transaction transaction = null;
    	Rezultat allRezultat = null;
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an object
            Query query = session.createNativeQuery("SELECT rezultat.* FROM rezultat , examen , elev , proba  "
            		+ "WHERE proba.codpr = examen.codpr "
            		+ "AND examen.code = elev.code "
            		+ "AND rezultat.codex = examen.codex "
            		+ "AND proba.tip_proba = :tipProba "
            		+ "AND elev.cnp = :cnp ");
            query.setParameter("tipProba", tipProba);
            query.setParameter("cnp", elev.getCnp());
            ((NativeQuery) query).addEntity(Rezultat.class);
            allRezultat = (Rezultat)query.uniqueResult();
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
        return allRezultat;    
    }	
	
	public List<Rezultat> getRezultatListContestatie() {
    	Transaction transaction = null;
    	List<Rezultat> allRezultat = new LinkedList();
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an object
            Query query = session.createNativeQuery("select rezultat.* "
                    + "from elev,proba,examen,rezultat "
                    + "where elev.code = examen.code "
                    + "and proba.codpr=examen.codpr "
                    + "and rezultat.codex=examen.codex "
                    + "and nota_cont <> 0.0 "
                    + "order by elev.code,proba.tip_proba");
            ((NativeQuery) query).addEntity(Rezultat.class);
            allRezultat = (List<Rezultat>)query.getResultList();
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
        return allRezultat;    
    }	
}
