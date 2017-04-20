package com.itsupportme.gis.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
    name = "Lock_Data"
)
public class Lock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Lck_Id")
    private Integer id;

    @Column(name = "Lck_Timeout", columnDefinition="DATETIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Lck_Usr_Added", nullable = false)
    private User userAdded;

    @Column(name = "Lck_Entity_Id")
    private Integer entityId;

    @Column(name = "Lck_Entity_Type", length = 64)
    private String entityType;

    public Integer getId() {
        return id;
    }

    public Lock setId(Integer id) {
        this.id = id;
        return this;
    }

    public Date getTimeout() {
        return timeout;
    }

    public Lock setTimeout(Date timeout) {
        this.timeout = timeout;
        return this;
    }

    public User getUserAdded() {
        return userAdded;
    }

    public Lock setUserAdded(User userAdded) {
        this.userAdded = userAdded;
        return this;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public Lock setEntityId(Integer entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getEntityType() {
        return entityType;
    }

    public Lock setEntityType(String entityType) {
        this.entityType = entityType;
        return this;
    }
}
