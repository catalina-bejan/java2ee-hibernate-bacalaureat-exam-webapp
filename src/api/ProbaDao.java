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
import util.HibernateUtil;

public class ProbaDao {

	//only for tip proba 1 or tip proba 2
	public Proba getProbaBy(Elev elev, long tipProba) {
    	Transaction transaction = null;
    	Proba allProba = null;
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an object
            Query query = session.createNativeQuery("select * from proba where tip_proba = :tipProba and proba.codp = (select codp from liceu_profil,elev where liceu_profil.codlp = elev.codlp and elev.cnp = :cnp) ");
            query.setParameter("tipProba", tipProba);
            query.setParameter("cnp", elev.getCnp());
            ((NativeQuery) query).addEntity(Proba.class);
            allProba = (Proba)query.uniqueResult();
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
        return allProba;    
    }	
	
	public Proba getProbaBy(Elev elev, String denumire) {
    	Transaction transaction = null;
    	Proba allProba = null;
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an object
            Query query = session.createNativeQuery("select proba.* from proba,elev,liceu_profil where proba.codp = liceu_profil.codp and liceu_profil.codlp = elev.codlp and elev.cnp = :cnp and proba.denpr = :denumireProba");
            query.setParameter("denumireProba", denumire);
            query.setParameter("cnp", elev.getCnp());
            ((NativeQuery) query).addEntity(Proba.class);
            allProba = (Proba)query.uniqueResult();
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
        return allProba;    
    }	
	
	public Proba getProbaAleasaBy(Elev elev) {
    	Transaction transaction = null;
    	Proba allProba = null;
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an object
            Query query = session.createNativeQuery("select * from proba where proba.tip_proba = 3 and proba.codpr = (select examen.codpr from examen, elev,proba where examen.code = elev.code and examen.codpr = proba.codpr and proba.tip_proba = 3 and elev.cnp = :cnp) ");
            query.setParameter("cnp", elev.getCnp());
            ((NativeQuery) query).addEntity(Proba.class);
            allProba = (Proba)query.uniqueResult();
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
        return allProba;    
    }	
	
	//toate probele specifice unui profil si a unui tip de proba
	public List<Proba> getProbaListBy(Profil profil, long tipProba) {
    	Transaction transaction = null;
    	List<Proba> allProba = new LinkedList();
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an object
            Query query = session.createNativeQuery("select * "
            		+ "from proba "
            		+ "where tip_proba = :tipProba "
            		+ "and codp = "
            		+ "(select codp "
            		+ "from profil "
            		+ "where denp= :profil)");
            query.setParameter("tipProba", tipProba);
            query.setParameter("profil", profil.getDenumire());
            ((NativeQuery) query).addEntity(Proba.class);
            allProba = (List<Proba>)query.getResultList();
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
        return allProba;    
    }	
	
	public List<Proba> getProbaListContestatie() {
    	Transaction transaction = null;
    	List<Proba> allProba = new LinkedList();
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an object
            Query query = session.createNativeQuery("select proba.* "
                    + "from elev,proba,examen,rezultat "
                    + "where elev.code = examen.code "
                    + "and proba.codpr=examen.codpr "
                    + "and rezultat.codex=examen.codex "
                    + "and nota_cont <> 0.0 "
                    + "order by elev.code,proba.tip_proba");
            ((NativeQuery) query).addEntity(Proba.class);
            allProba = (List<Proba>)query.getResultList();
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
        return allProba;    
    }
}
