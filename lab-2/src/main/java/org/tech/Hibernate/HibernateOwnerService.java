package org.tech.Hibernate;

import org.tech.IDBManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class HibernateOwnerService implements IDBManager<HibernateOwner> {
    private final HibernateConnection configuration;

    public HibernateOwnerService() {
        configuration = new HibernateConnection();
    }

    @Override
    public HibernateOwner save(HibernateOwner entity) {
        try (Session session = configuration.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        }
        return entity;
    }

    @Override
    public void deleteById(long id) {
        try (Session session = configuration.getSession()) {
            Transaction transaction = session.beginTransaction();
            var Owner = session.get(HibernateOwner.class, id);
            session.delete(Owner);
            transaction.commit();
        }
    }

    @Override
    public void deleteByEntity(HibernateOwner entity) {
        try (Session session = configuration.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        }
    }

    @Override
    public void deleteAll() {
        try (Session session = configuration.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE From Owners").executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public HibernateOwner update(HibernateOwner entity) {
        try (Session session = configuration.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        }
        return entity;
    }

    @Override
    public HibernateOwner getById(long id) {
        HibernateOwner owner;
        try (Session session = configuration.getSession()) {
            owner = session.get(HibernateOwner.class, id);
        }
        return owner;
    }

    @Override
    public ArrayList<HibernateOwner> getAll() {
        ArrayList<HibernateOwner> owners;
        try (Session session = configuration.getSession()) {
            owners = new ArrayList<>(session.createQuery("From Owners", HibernateOwner.class).getResultList());
        }
        return owners;
    }

}
