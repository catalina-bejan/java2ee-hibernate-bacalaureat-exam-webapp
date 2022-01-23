package api;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import model.Elev;
import model.Examen;
import util.HibernateUtil;

public class ExamenDao {
	
	public void saveExamen(Examen examen) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the examen object
            session.save(examen);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
	
	public List<Examen> getExamenListBy(Elev elev) {
    	Transaction transaction = null;
    	List<Examen> allExamene = new LinkedList();
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an examen list of objects
            Query query = session.createQuery("FROM Examen E WHERE E.elev.codElev = :codElev");
            query.setParameter("codElev", elev.getCodElev());
            allExamene = (List<Examen>)query.getResultList();
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
        return allExamene;    
    }	
}
