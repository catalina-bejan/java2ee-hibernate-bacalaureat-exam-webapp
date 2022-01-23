package api;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import model.Elev;
import model.Liceu;
import model.LiceuProfil;
import model.Profil;
import model.User;
import util.HibernateUtil;

public class LiceuDao {

	public List<Liceu> getAllLicee() {
    	Transaction transaction = null;
    	List<Liceu> allLiceu = new LinkedList();
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an user object
            //List<Liceu> liceuList = new LinkedList();
            allLiceu = (List<Liceu>)session.createQuery("FROM Liceu").getResultList();
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
        return allLiceu;    
    }	
	
	public Liceu findById(long codLiceu) {
    	Transaction transaction = null;
    	Liceu liceu = null;
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an object
            liceu = session.find(Liceu.class, codLiceu);
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
        return liceu;    
    }
	
	public Liceu getLiceuByDenumire(String denumire) {
    	Transaction transaction = null;
    	Liceu allLiceu = null;
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an object
            Query query = session.createQuery(" FROM Liceu L WHERE L.denumire = :denumire ");
            query.setParameter("denumire", denumire);
            allLiceu = (Liceu)query.uniqueResult();
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
        return allLiceu;    
    }	
	
	public Liceu getLiceuByElev(Elev elev) {
    	Transaction transaction = null;
    	Liceu allLiceu = null;
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an object
            Query query = session.createNativeQuery("select * from liceu where codl = (select codl from liceu_profil, elev where elev.codlp = liceu_profil.codlp and elev.cnp = :cnp) ");
            query.setParameter("cnp", elev.getCnp());
            ((NativeQuery) query).addEntity(Liceu.class);
            allLiceu = (Liceu)query.uniqueResult();
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
        return allLiceu;    
    }	
}
