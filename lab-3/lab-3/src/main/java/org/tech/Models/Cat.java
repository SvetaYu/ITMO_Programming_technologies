package org.tech.Models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter

@Entity
@Table(name = "Cats")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "dateofbirth")
    private Date dateOfBirth;
    @Column(name = "breed")
    private String breed;
    @Enumerated(EnumType.STRING)
    private CatsColor color;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ownerid")
    private Owner owner;
    @Column(name = "taillength")
    private int tailLength;

    public Cat(String name, Date dateOfBirth, String breed, CatsColor color, Owner owner, Integer tailLength) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.breed = breed;
        this.color = color;
        this.owner = owner;
        this.tailLength = tailLength;
    }

    public Cat() {

    }
}
