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

public class ProfilDao {
	
	public List<Profil> getProfileByLiceuDen(String denumireLiceu) {
    	Transaction transaction = null;
    	List<Profil> allProfile = new LinkedList();
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            
            Query nativeQuery = session.createNativeQuery("SELECT * FROM profil P, liceu_profil LP WHERE P.codp = LP.codp AND LP.codl = (select codl FROM liceu WHERE denl = :denumireLiceu)", Profil.class);
            nativeQuery.setParameter("denumireLiceu", denumireLiceu);
            allProfile = (List<Profil>)nativeQuery.getResultList();

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
        return allProfile;    
    }	
	
	public Profil getProfilByDenumire(String denumire) {
    	Transaction transaction = null;
    	Profil allProfil = null;
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an object
            Query query = session.createQuery(" FROM Profil P WHERE P.denumire = :denumire ");
            query.setParameter("denumire", denumire);
            allProfil = (Profil)query.uniqueResult();
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
        return allProfil;    
    }	
	
	public Profil getProfilOfElev(Elev elev) {
    	Transaction transaction = null;
    	Profil allProfil = null;
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an object
            Query query = session.createNativeQuery("select * from profil where codp = (select codp from liceu_profil, elev where elev.codlp = liceu_profil.codlp and elev.cnp = :cnp) ");
            query.setParameter("cnp", elev.getCnp());
            ((NativeQuery) query).addEntity(Profil.class);
            allProfil = (Profil)query.uniqueResult();
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
        return allProfil;    
    }
}
