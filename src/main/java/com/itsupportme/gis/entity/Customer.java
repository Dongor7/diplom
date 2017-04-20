package com.itsupportme.gis.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(
        name = "Customer"
)
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

    @Column(name = "Cst_AddressLine1", length = 80)
    private String addressLine1;

    @Column(name = "Cst_AddressLine2", length = 80)
    private String addressLine2;

    @Column(name = "Cst_City", length = 60)
    private String city;

    @Column(name = "Cst_State", length = 60)
    private String state;

    @Column(name = "Cst_Zip", length = 9)
    private String zip;

    @Column(name = "Cst_Country", length = 50)
    private String country;

    @Column(name = "Cst_Lat", length = 12)
    private String lat;

    @Column(name = "Cst_Lng", length = 12)
    private String lng;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cst_Usr_Added", nullable = false)
    private User userAdded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cst_Usr_Modified")
    private User userModified;

    @Column(name = "Cst_Date_Added", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdded;

    @Column(name = "Cst_Date_Modified", columnDefinition="DATETIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;

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

    public String getAddressLine1() {
        return addressLine1;
    }

    public Customer setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public Customer setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Customer setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public Customer setState(String state) {
        this.state = state;
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

    public String getLat() {
        return lat;
    }

    public Customer setLat(String lat) {
        this.lat = lat;
        return this;
    }

    public String getLng() {
        return lng;
    }

    public Customer setLng(String lng) {
        this.lng = lng;
        return this;
    }

    public User getUserAdded() {
        return userAdded;
    }

    public Customer setUserAdded(User userAdded) {
        this.userAdded = userAdded;
        return this;
    }

    public User getUserModified() {
        return userModified;
    }

    public Customer setUserModified(User userModified) {
        this.userModified = userModified;
        return this;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public Customer setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public Customer setDateModified(Date dateModified) {
        this.dateModified = dateModified;
        return this;
    }
}
