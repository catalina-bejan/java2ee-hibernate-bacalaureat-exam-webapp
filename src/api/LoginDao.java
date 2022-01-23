/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

//import DatabaseConnection;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.User;
import util.HibernateUtil;

/**
 *
 * @author catal
 */
public class LoginDao {
	public boolean validate(String username, String password) {

        Transaction transaction = null;
        User user = null;
        System.out.println("Inainte de try");
    	Session session = HibernateUtil.getSessionFactory().openSession();
        try {
        	System.out.println("In try");
            // start a transaction
            transaction = session.beginTransaction();
            // get an user object
            user = (User) session.createQuery("FROM User U WHERE U.username = :username").setParameter("username", username)
                .uniqueResult();

            if (user != null && user.getParola().equals(password)) {
                System.out.println("It is VALID!!!");
            	return true;
            }
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
        return false;
    }
}
