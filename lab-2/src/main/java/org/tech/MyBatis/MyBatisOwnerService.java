package org.tech.MyBatis;

import org.apache.ibatis.session.SqlSession;
import org.tech.IDBManager;

import java.io.IOException;
import java.util.ArrayList;

public class MyBatisOwnerService implements IDBManager<MyBatisOwner> {

    private final MyBatisConnection connection;

    public MyBatisOwnerService() {
        connection = new MyBatisConnection();
    }

    @Override
    public MyBatisOwner save(MyBatisOwner entity) {
        try (SqlSession session = connection.getSession()) {
            var mapper = session.getMapper(OwnerMapper.class);
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
            var mapper = session.getMapper(OwnerMapper.class);
            mapper.deleteById(id);
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByEntity(MyBatisOwner entity) {
        try (SqlSession session = connection.getSession()) {
            var mapper = session.getMapper(OwnerMapper.class);
            mapper.deleteById(entity.getId());
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteAll() {
        try (SqlSession session = connection.getSession()) {
            var mapper = session.getMapper(OwnerMapper.class);
            mapper.deleteAll();
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MyBatisOwner update(MyBatisOwner entity) {
        try (SqlSession session = connection.getSession()) {
            var mapper = session.getMapper(OwnerMapper.class);
            mapper.update(entity);
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public MyBatisOwner getById(long id) {
        MyBatisOwner Owner = null;
        try (SqlSession session = connection.getSession()) {
            var mapper = session.getMapper(OwnerMapper.class);
            Owner = mapper.getById(id);
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Owner;
    }

    @Override
    public ArrayList<MyBatisOwner> getAll() {
        ArrayList<MyBatisOwner> Owners = null;
        try (SqlSession session = connection.getSession()) {
            var mapper = session.getMapper(OwnerMapper.class);
            Owners = mapper.getAll();
            session.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Owners;
    }
}
