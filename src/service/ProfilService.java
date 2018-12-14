package service;

import classe.Profil;
import dao.IDao;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class ProfilService implements IDao<Profil> {

    @Override
    public boolean create(Profil o) {
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
    public boolean update(Profil o) {
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
    public boolean delete(Profil o) {
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
    public List<Profil> findAll() {
        List<Profil> profils = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            profils = session.createQuery("from Profil").list();
            tx.commit();
            session.close();
            return profils;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            session.close();
            return profils;
        }

    }

    @Override
    public Profil findById(int id) {
        Profil m = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            m = (Profil) session.get(Profil.class, id);
            tx.commit();
            session.close();
            return m;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            session.close();
            return m;
        }

    }
    
    public int countProfil(){
        int nbr = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        nbr = Integer.parseInt(session.createQuery("Select Count(*) From Profil").uniqueResult().toString());
        session.getTransaction().commit();
        session.close();
        return nbr;
    }
    
    public Profil findProfilByLibelle(String libelle){
        Profil p = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        p = (Profil) session.createQuery("From Profil where libelle=:libelle").setParameter("libelle", libelle).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return p;
    }

}
