package service;

import classe.Etablissement;
import dao.IDao;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class EtablissementService implements IDao<Etablissement> {

    @Override
    public boolean create(Etablissement o) {
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
    public boolean update(Etablissement o) {
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
    public boolean delete(Etablissement o) {
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
    public List<Etablissement> findAll() {
        List<Etablissement> etablissements = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            etablissements = session.createQuery("from Etablissement").list();
            tx.commit();
            session.close();
            return etablissements;
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            session.close();
            return etablissements;
        }

    }

    @Override
    public Etablissement findById(int id) {
         Etablissement f = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            f = (Etablissement) session.get(Etablissement.class, id);
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

    public int countEtablissement(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        int e = Integer.parseInt(session.createQuery("Select COUNT(*) From Etablissement").uniqueResult().toString());
        session.getTransaction().commit();
        session.close();
        return e;        
    }
}

