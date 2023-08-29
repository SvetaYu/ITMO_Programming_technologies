package org.tech.MyBatis;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.ArrayList;

public interface CatMapper {

    @Select("Insert into cats(name, ownerid, dateofbirth, breed, color) values (#{name}, #{owner.id}, #{dateOfBirth}, #{breed}, #{color}) returning id")
    long save(MyBatisCat cat);

    @Delete("Delete from cats where id=#{id}")
    void deleteById(long id);

    @Delete("Delete from cats")
    void deleteAll();

    @Update("Update cats set name=#{name}, ownerid=#{owner.id}, dateofbirth=#{dateOfBirth}, breed=#{breed}, color=#{color} where id=#{id}")
    long update(MyBatisCat cat);

    @Select("Select * from cats where id=#{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "owner", column = "ownerid",
                    one = @One(select = "org.tech.MyBatis.OwnerMapper.getById",
                            fetchType = FetchType.LAZY)),
            @Result(property = "dateOfBirth", column = "dateofbirth"),
            @Result(property = "breed", column = "breed"),
            @Result(property = "color", column = "color")})
    MyBatisCat getById(long id);

    @Select("Select * from cats")
    ArrayList<MyBatisCat> getAll();
}
