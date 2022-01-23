package api;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import model.Elev;
import model.User;
import util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author catal
 */
public class UserDao {
	
	public void saveUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student object
            session.save(user);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
	
    public User getUserBy(String username, String password) {
    	Transaction transaction = null;
        User user = null;
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an user object
            Query query = session.createQuery("FROM User U WHERE U.username = :username AND U.parola = :password");
            query.setParameter("username", username);
            query.setParameter("password", password);
            user = (User)query.uniqueResult();
            
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
        return user;
	
    }	
    
    public User getUserBy(String username) {
    	Transaction transaction = null;
        User user = null;
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an user object
            Query query = session.createQuery("FROM User U WHERE U.username = :username");
            query.setParameter("username", username);
            user = (User)query.uniqueResult();
            
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
        return user;
    }
    
    public User getUserByCnp(String cnp) {
    	Transaction transaction = null;
        User user = null;
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an user object
            Query query = session.createQuery("FROM User U WHERE U.cnp = :cnp");
            query.setParameter("cnp", cnp);
            user = (User)query.uniqueResult();
            
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
        return user;
    }
    
    public List<String> getFunctii() {
    	Transaction transaction = null;
        List<String> functii = new LinkedList();
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // start a transaction
            transaction = session.beginTransaction();
            // get an user object
            Query query = session.createNativeQuery("SELECT DISTINCT(functie) from users ");
            functii = (List<String>)query.getResultList();
            ((NativeQuery) query).addEntity(Elev.class);
            
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
        return functii;
    }
         
}
