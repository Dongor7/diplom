package com.itsupportme.gis.entity.dao;

import com.itsupportme.gis.entity.Customer;
import com.itsupportme.gis.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class CustomerDaoImpl implements CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Customer> findAll() {

        return sessionFactory
                .getCurrentSession()
                .createQuery("from Customer")
                .list()
        ;
    }

    @Override
    public void save(Customer customer, User user) {

        Date date = new Date();

        customer
                .setDateAdded(date)
                .setDateModified(date)
                .setUserAdded(user)

        ;

        sessionFactory.getCurrentSession().save(customer);
    }
}
