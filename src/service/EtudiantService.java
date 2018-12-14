package service;

import classe.Etudiant;
import dao.IDao;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class EtudiantService implements IDao<Etudiant> {

    @Override
    public boolean create(Etudiant o) {
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
    public boolean update(Etudiant o) {
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
    public boolean delete(Etudiant o) {
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
    public List<Etudiant> findAll() {
        List<Etudiant> etudients = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        etudients = session.createQuery("from Etudiant").list();
        session.getTransaction().commit();
        session.close();
        return etudients;
    }

    @Override
    public Etudiant findById(int id) {
        Etudiant etudiant = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        etudiant = (Etudiant) session.get(Etudiant.class, id);
        session.getTransaction().commit();
        session.close();
        return etudiant;
    }
    
    public int checkEtudiant(String num,String nom){
        int nbr = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        nbr = Integer.parseInt(session.createQuery("Select Count(*) From Etudiant Where numInscription=:num and nom=:nom").setParameter("num", num).setParameter("nom", nom).uniqueResult().toString());
        session.getTransaction().commit();
        session.close();
        return nbr;
    }
    
    public int countEtudiant(){
        int nbr = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        nbr = Integer.parseInt(session.createQuery("Select Count(*) From Etudiant").uniqueResult().toString());
        session.getTransaction().commit();
        session.close();
        return nbr;
    }
    
    public List<Object[]> etudiantParDecision(){
        List<Object[]> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        list = session.createSQLQuery("Select decision,Count(*) AS 'nbrEtud' FROM Etudiant GROUP BY decision").list();
        session.getTransaction().commit();        
        session.close();
        return list;
    }

}

