package com.itsupportme.gis.entity.dao;


import com.itsupportme.gis.entity.Customer;
import com.itsupportme.gis.entity.User;

import java.util.List;

public interface CustomerDao {

    public List<Customer> findAll();

    public void save(Customer customer, User user);

}
