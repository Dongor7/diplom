package com.itsupportme.gis.entity.dao;


import com.itsupportme.gis.entity.Customer;
import com.itsupportme.gis.entity.User;

import java.util.List;

public interface CustomerDao {
    List<Customer> findAll();
    Customer findById(Integer id);

    void save(Customer customer, User user);
    void update(Customer customer, User user);
}
