package org.tech.Hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class HibernateConnection {

    private final SessionFactory factory;
     public HibernateConnection()
     {
         org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure("hibernate.cfg.xml");
         configuration.addAnnotatedClass(HibernateCat.class);
         configuration.addAnnotatedClass(HibernateOwner.class);
         StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
         builder.applySettings(configuration.getProperties());
         ServiceRegistry serviceRegistry = builder.build();
         factory = configuration.buildSessionFactory(serviceRegistry);
     }

     public Session getSession()
     {
         return factory.openSession();
     }
}
