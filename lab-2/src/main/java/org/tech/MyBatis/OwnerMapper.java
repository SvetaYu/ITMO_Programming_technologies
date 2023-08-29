package org.tech.MyBatis;

import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

public interface OwnerMapper {

    @Select("Insert into owners(name, dateofbirth) values (#{name}, #{dateOfBirth}) returning id")
    long save(MyBatisOwner owner);

    @Delete("Delete from owners where id=#{id}")
    void deleteById(long id);

    @Delete("Delete from owners")
    void deleteAll();

    @Update("Update owners set name=#{name}, dateofbirth=#{dateOfBirth} where id=#{id}")
    long update(MyBatisOwner owner);

    @Select("Select * from owners where id=#{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "dateOfBirth", column = "dateofbirth")})
    MyBatisOwner getById(long id);

    @Select("Select * from owners")
    ArrayList<MyBatisOwner> getAll();
}
