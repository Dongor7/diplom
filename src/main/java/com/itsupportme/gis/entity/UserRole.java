package com.itsupportme.gis.entity;


import javax.persistence.*;

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"Ur_Role", "Ur_Usr_Id"}
    ),
    name = "User_Role"
)
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Ur_Id")
    private Integer id;

    @Column(name = "Ur_Role", length = 12)
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Ur_Usr_Id", nullable = false)
    private User user;

    public Integer getId()
    {
        return id;
    }

    public String getRole()
    {
        return role;
    }

    public UserRole setRole(String role)
    {
        this.role = role;
        return this;
    }

    public User getUser()
    {
        return user;
    }

    public UserRole setUser(User user)
    {
        this.user = user;
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
