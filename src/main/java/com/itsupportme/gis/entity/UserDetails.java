package com.itsupportme.gis.entity;

import javax.persistence.*;

@Entity
@Table(name = "User_Details")
public class UserDetails {

    @Id
    @Column(name = "Uds_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Uds_Usr_Id")
    private User user;

    @Column(name = "Uds_Image")
    private Integer image;

    @Column(name = "Uds_Theme")
    private Integer theme;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public Integer getTheme() {
        return theme;
    }

    public void setTheme(Integer theme) {
        this.theme = theme;
    }
}
