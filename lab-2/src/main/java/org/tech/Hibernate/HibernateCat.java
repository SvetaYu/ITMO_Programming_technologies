package org.tech.Hibernate;

import jakarta.persistence.*;
import org.tech.CatsColor;

import java.sql.Date;


@Entity(name = "Cats")
@Table(name = "Cats")
public class HibernateCat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date dateOfBirth;
    private String breed;
    @Enumerated(EnumType.STRING)
    private CatsColor color;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerid")
    private HibernateOwner owner;

    public HibernateCat(String name, Date dateOfBirth, String breed, CatsColor color, HibernateOwner owner) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.breed = breed;
        this.color = color;
        this.owner = owner;
    }

    public HibernateCat() {

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

    public HibernateOwner getOwner() {
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

    public void setOwner(HibernateOwner owner) {
        this.owner = owner;
    }
}
