package de.willnecker.personservice.app.person.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "person" ,schema = "public")
public class PersonEntity {

    @Id
    @Column(name = "idcardnumber", unique = true)
    private int idCardNumber;

    @Column(name = "salutation")
    private String salutation = "";

    @Column(name = "firstname")
    private String firstName = "";

    @Column(name = "surname")
    private String surName = "";

    @Column(name = "city")
    private String city = "";

    @Column(name = "place")
    private int place = 0;

    @Column(name = "street")
    private String street = "";

    @Column(name = "birthday")
    private Date birthday;
}
