package api;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.Liceu;
import model.LiceuProfil;
import model.Profil;
import util.HibernateUtil;

public class LiceuProfilDao {

	public long getCodLiceuProfilByLiceuAndProfil(Liceu liceu, Profil profil) {
    	Transaction transaction = null;
    	long codLiceuProfil = -1;
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an object
            Query query = session.createNativeQuery("SELECT codlp FROM liceu_profil LP WHERE LP.codl = :codLiceu AND LP.codp = :codProfil ");
            query.setParameter("codLiceu", liceu.getCodLiceu());
            query.setParameter("codProfil", profil.getCodProfil());
            codLiceuProfil = ((Number)query.uniqueResult()).longValue();
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
        return codLiceuProfil;    
    }	
	
	
	public LiceuProfil findById(long codlp) {
    	Transaction transaction = null;
    	LiceuProfil liceuProfil = null;
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an object
            liceuProfil = session.find(LiceuProfil.class, codlp);
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
        return liceuProfil;    
    }	
}
