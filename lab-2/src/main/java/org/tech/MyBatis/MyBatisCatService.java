package org.tech.MyBatis;

import org.apache.ibatis.session.SqlSession;
import org.tech.IDBManager;

import java.io.IOException;
import java.util.ArrayList;

public class MyBatisCatService implements IDBManager<MyBatisCat> {

    private final MyBatisConnection connection;

    public MyBatisCatService() {
        connection = new MyBatisConnection();
    }

    @Override
    public MyBatisCat save(MyBatisCat entity) {
        try (SqlSession session = connection.getSession()) {
            var mapper = session.getMapper(CatMapper.class);
            entity.setId(mapper.save(entity));
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public void deleteById(long id) {
        try (SqlSession session = connection.getSession()) {
            var mapper = session.getMapper(CatMapper.class);
            mapper.deleteById(id);
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByEntity(MyBatisCat entity) {
        try (SqlSession session = connection.getSession()) {
            var mapper = session.getMapper(CatMapper.class);
            mapper.deleteById(entity.getId());
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteAll() {
        try (SqlSession session = connection.getSession()) {
            var mapper = session.getMapper(CatMapper.class);
            mapper.deleteAll();
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MyBatisCat update(MyBatisCat entity) {
        try (SqlSession session = connection.getSession()) {
            var mapper = session.getMapper(CatMapper.class);
            mapper.update(entity);
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public MyBatisCat getById(long id) {
        MyBatisCat cat = null;
        try (SqlSession session = connection.getSession()) {
            var mapper = session.getMapper(CatMapper.class);
            cat = mapper.getById(id);
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cat;
    }

    @Override
    public ArrayList<MyBatisCat> getAll() {
        ArrayList<MyBatisCat> cats = null;
        try (SqlSession session = connection.getSession()) {
            var mapper = session.getMapper(CatMapper.class);
            cats = mapper.getAll();
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cats;
    }
}
