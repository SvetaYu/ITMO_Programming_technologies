package org.tech.MyBatis;

import org.tech.CatsColor;

import java.sql.Date;

public class MyBatisCat {
    private Long id;
    private String name;
    private Date dateOfBirth;
    private String breed;
    private CatsColor color;
    private MyBatisOwner owner;

    public MyBatisCat(String name, Date dateOfBirth, String breed, CatsColor color, MyBatisOwner owner) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.breed = breed;
        this.color = color;
        this.owner = owner;
    }

    public MyBatisCat() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public String getBreed() {
        return breed;
    }
    public CatsColor getColor() {
        return color;
    }

    public MyBatisOwner getOwner() {
        return owner;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public void setBreed(String breed) {
        this.breed = breed;
    }
    public void setColor(CatsColor color) {
        this.color = color;
    }

    public void setOwner(MyBatisOwner owner) {
        this.owner = owner;
    }
}
