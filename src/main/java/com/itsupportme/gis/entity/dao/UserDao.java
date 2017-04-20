package com.itsupportme.gis.entity.dao;


import com.itsupportme.gis.entity.User;

import java.util.List;

public interface UserDao
{
    User findByUsername(String username);
    User findUser(Integer id, String username);
    User findUser(Integer id);
    List<User> findAll();
    List<User> findAll(String username, String first, String last, Integer limitStart, Integer limitEnd);
    Integer countAll(String username, String first, String last);
}
