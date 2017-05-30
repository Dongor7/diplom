package com.itsupportme.gis.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Cst_Id")
    private Integer id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "Cst_Name", length = 100)
    private String name;

    @Column(name = "Cst_Phone", length = 13)
    private String phone;

    @Column(name = "Cst_Fax", length = 13)
    private String fax;

    @Column(name = "Cst_City", length = 60)
    private String city;

    @Column(name = "Cst_Zip", length = 9)
    private String zip;

    @Column(name = "Cst_Country", length = 50)
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cst_Usr_Added", nullable = false)
    private User userAdded;

    public Integer getId() {
        return id;
    }

    public Customer setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Customer setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getFax() {
        return fax;
    }

    public Customer setFax(String fax) {
        this.fax = fax;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Customer setCity(String city) {
        this.city = city;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public Customer setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Customer setCountry(String country) {
        this.country = country;
        return this;
    }

    public User getUserAdded() {
        return userAdded;
    }

    public Customer setUserAdded(User userAdded) {
        this.userAdded = userAdded;
        return this;
    }

}
