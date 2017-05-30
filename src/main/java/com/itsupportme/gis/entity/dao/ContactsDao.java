package com.itsupportme.gis.entity.dao;


import com.itsupportme.gis.entity.Contacts;
import com.itsupportme.gis.entity.User;

import java.util.List;

public interface ContactsDao {
    List<Contacts> findAll();
    Contacts findById(Integer id);

    void save(Contacts contacts, User user);
    void update(Contacts contacts, User user);
}
