package service;

import classe.Employe;
import dao.IDao;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class EmployeService implements IDao<Employe> {

    @Override
    public boolean create(Employe o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            session.close();
            return true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            session.close();
            return false;
        }
    }

    @Override
    public boolean update(Employe o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            session.close();
            return true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            session.close();
            return false;
        }
    }

    @Override
    public boolean delete(Employe o) {
          Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            session.close();
            return true;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            session.close();
            return false;
        }

    }

    @Override
    public List<Employe> findAll() {
        List<Employe> employes = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            employes = session.createQuery("from Employe").list();
            tx.commit();
            session.close();
            return employes;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            session.close();
            return employes;
        }

    }

    @Override
    public Employe findById(int id) {
         Employe f = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            f = (Employe) session.get(Employe.class, id);
            tx.commit();
            session.close();
            return f;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            session.close();
            return f;
        }
    }
    
    public Employe findByEmail(String email,String pass) {
        Employe e = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        e = (Employe) session.createQuery("from Employe where email=:email and password=:pass").setParameter("email", email).setParameter("pass", util.Util.md5(pass)).uniqueResult();
        session.getTransaction().commit();
        return e;
    }
    
    
    public int countEmploye(){
        int nbr = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        nbr = Integer.parseInt(session.createQuery("Select Count(*) From Employe").uniqueResult().toString());
        session.getTransaction().commit();
        session.close();
        return nbr;
    }
    
    public List<Object[]> employeParProfil(){
        List<Object[]> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        list = session.createSQLQuery("Select p.libelle,Count(*) AS 'nbrEmp' FROM Employe e INNER JOIN Profil p ON e.profil_id = p.id GROUP BY  p.libelle").list();
        session.getTransaction().commit();        
        session.close();
        return list;
    }
    

}

