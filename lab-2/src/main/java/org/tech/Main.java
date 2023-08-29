package org.tech;


import org.tech.Hibernate.HibernateCat;
import org.tech.Hibernate.HibernateCatService;
import org.tech.Hibernate.HibernateOwner;
import org.tech.Hibernate.HibernateOwnerService;
import org.tech.JDBC.JDBCCat;
import org.tech.JDBC.JDBCCatService;
import org.tech.JDBC.JDBCOwner;
import org.tech.JDBC.JDBCOwnerService;
import org.tech.MyBatis.MyBatisCat;
import org.tech.MyBatis.MyBatisCatService;
import org.tech.MyBatis.MyBatisOwner;
import org.tech.MyBatis.MyBatisOwnerService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello world!");

        //---------------------------------------JDBC--------------------------------------------
        JDBCOwnerService jdbcOwnerService = new JDBCOwnerService();
        JDBCCatService jdbcCatService = new JDBCCatService();

        JDBCOwner jdbcOwner = new JDBCOwner("jdbcOwner", new Date(1,1,1));
        JDBCCat jdbcCat  = new JDBCCat( "jdbcCat", new Date(1,1,1), "jdbc", CatsColor.BLACK, jdbcOwner);

        //     Delete all
        jdbcCatService.deleteAll();
        jdbcOwnerService.deleteAll();

        //      save
        jdbcOwnerService.save(jdbcOwner);
        jdbcCatService.save(jdbcCat);

        //     update
        jdbcCat.setDateOfBirth(new Date(System.currentTimeMillis()));
        jdbcOwner.setDateOfBirth(new Date(System.currentTimeMillis()));
        jdbcCatService.update(jdbcCat);
        jdbcOwnerService.update(jdbcOwner);

        //     get by ID
        var jdbcCatt = jdbcCatService.getById(jdbcCat.getId());
        System.out.println(jdbcCatt.getName() + " " + jdbcCatt.getOwner().getId());
        var jdbcOwwner = jdbcOwnerService.getById(jdbcOwner.getId());
        System.out.println(jdbcOwwner.getName());

        //      get all
        ArrayList<JDBCCat> jdbcCats = jdbcCatService.getAll();
        System.out.println((long) jdbcCats.size());
        ArrayList<JDBCOwner> jdbcOwners = jdbcOwnerService.getAll();
        System.out.println((long) jdbcOwners.size());


        //--------------------------------------------HIBERNATE----------------------------------------
        HibernateOwnerService hibernateOwnerService = new HibernateOwnerService();
        HibernateCatService hibernateCatService = new HibernateCatService();

        HibernateOwner hibernateOwner = new HibernateOwner("hibernateOwner", new Date(1,1,1));
        HibernateCat hibernateCat  = new HibernateCat( "hibernateCat", new Date(1,1,1), "Hibernate", CatsColor.BLACK, hibernateOwner);

        //     Delete all
        hibernateCatService.deleteAll();
        hibernateOwnerService.deleteAll();
//
        //      save
        hibernateOwnerService.save(hibernateOwner);
        hibernateCatService.save(hibernateCat);

        //     update
        hibernateCat.setDateOfBirth(new Date(System.currentTimeMillis()));
        hibernateOwner.setDateOfBirth(new Date(System.currentTimeMillis()));
        hibernateCatService.update(hibernateCat);
        hibernateOwnerService.update(hibernateOwner);

        //     get by ID
        var HibernateCatt = hibernateCatService.getById(hibernateCat.getId());
        System.out.println(HibernateCatt.getName() + " " + HibernateCatt.getOwner().getId());
        var HibernateOwwner = hibernateOwnerService.getById(hibernateOwner.getId());
        System.out.println(HibernateOwwner.getName());

        //      get all
        List<HibernateCat> cats = hibernateCatService.getAll();
        System.out.println((long) cats.size());
        List<HibernateOwner> owners = hibernateOwnerService.getAll();
        System.out.println((long) owners.size());

        //---------------------------------------------MYBATIS-------------------------------------
        MyBatisOwnerService myBatisOwnerService = new MyBatisOwnerService();
        MyBatisCatService myBatisCatService = new MyBatisCatService();

        MyBatisOwner myBatisOwner = new MyBatisOwner("myBatisOwner", new Date(1,1,1));
        MyBatisCat myBatisCat  = new MyBatisCat( "myBatisCat", new Date(1,1,1), "MyBatis", CatsColor.BLACK, myBatisOwner);

        //     Delete all
        myBatisCatService.deleteAll();
        myBatisOwnerService.deleteAll();

        //      save
        myBatisOwnerService.save(myBatisOwner);
        myBatisCatService.save(myBatisCat);

        //     update
        myBatisCat.setDateOfBirth(new Date(System.currentTimeMillis()));
        myBatisOwner.setDateOfBirth(new Date(System.currentTimeMillis()));
        myBatisCatService.update(myBatisCat);
        myBatisOwnerService.update(myBatisOwner);

        //     get by ID
        var myBatisCatt = myBatisCatService.getById(myBatisCat.getId());
        System.out.println(myBatisCatt.getName() + " " + myBatisCatt.getOwner().getId());
        var myBatisOwwner = myBatisOwnerService.getById(myBatisOwner.getId());
        System.out.println(myBatisOwwner.getName());

        //      get all
        List<MyBatisCat> myBatisCats = myBatisCatService.getAll();
        System.out.println((long) myBatisCats.size());
        List<MyBatisOwner> myBatisOwners = myBatisOwnerService.getAll();
        System.out.println((long) myBatisOwners.size());
    }
}
