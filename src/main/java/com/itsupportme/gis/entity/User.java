package com.itsupportme.gis.entity;

import com.itsupportme.gis.component.validator.constraint.UniqueEntity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "User",
    uniqueConstraints = @UniqueConstraint(
            columnNames = {"Usr_Username"}
    )
)
@UniqueEntity(field = "username", message = "User with the same username already exists")
public class User {

    public static final String DEFAULT_PASSWORD = "password#1";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Usr_Id")
    private Integer id;


    @Size(min = 3, max = 45)
    @Column(name = "Usr_Username", length = 45)
    private String username;

    @Column(name = "Usr_Password", length = 60)
    private String password;

    @Column(name = "Usr_Is_Enabled")
    private Boolean isEnabled;

    @Pattern(
            regexp = "^[A-Z][a-z]{1,29}$",
            message = "First letter must be capital. Size must be between 2 and 30 "
    )
    @Column(name = "Usr_First", length = 30)
    private String first;

    @Pattern(
            regexp = "^[A-Z][a-z]{1,29}$",
            message = "First letter must be capital. Size must be between 2 and 30 "
    )
    @Column(name = "Usr_Last", length = 30)
    private String last;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private UserDetails userDetails;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<UserRole> userRole = new HashSet<>(0);

    public Integer getId() {
        return id;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public User setId(Integer id){
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public User setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public String getFirst() {
        return first;
    }

    public User setFirst(String first) {
        this.first = first;

        return this;
    }

    public String getLast() {
        return last;
    }

    public User setLast(String last) {
        this.last = last;
        return this;
    }

    public Set<UserRole> getUserRole() {
        return userRole;
    }

    public User setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
        return this;
    }

    public User addUserRole(UserRole role) {
        this.userRole.add(role);
        return this;
    }

    public Boolean hasRole(String role) {
        for (UserRole userRole : this.getUserRole()) {
            if (role.equals(userRole.getRole())) {
                return true;
            }
        }

        return false;
    }
}
