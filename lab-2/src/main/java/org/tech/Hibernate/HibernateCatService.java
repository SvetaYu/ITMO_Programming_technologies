package org.tech.Hibernate;

import org.tech.IDBManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class HibernateCatService implements IDBManager<HibernateCat> {

    private final HibernateConnection connection;

    public HibernateCatService() {
        connection = new HibernateConnection();
    }

    @Override
    public HibernateCat save(HibernateCat entity) {
        try (Session session = connection.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        }
        return entity;
    }

    @Override
    public void deleteById(long id) {
        try (Session session = connection.getSession()) {
            Transaction transaction = session.beginTransaction();
            var cat = session.get(HibernateCat.class, id);
            session.delete(cat);
            transaction.commit();
        }
    }

    @Override
    public void deleteByEntity(HibernateCat entity) {
        try (Session session = connection.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        }
    }

    @Override
    public void deleteAll() {
        try (Session session = connection.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE From Cats").executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public HibernateCat update(HibernateCat entity) {
        try (Session session = connection.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        }
        return entity;
    }

    @Override
    public HibernateCat getById(long id) {
        HibernateCat cat;
        try (Session session = connection.getSession()) {
            cat = session.get(HibernateCat.class, id);
        }
        return cat;
    }

    @Override
    public List<HibernateCat> getAll() {
        List<HibernateCat> cats;
        try (Session session = connection.getSession()) {
            cats = new ArrayList<>(session.createQuery("From Cats", HibernateCat.class).list());
        }
        return cats;
    }
}
