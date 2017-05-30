package com.itsupportme.gis.entity.dao;

import com.itsupportme.gis.entity.Customer;
import com.itsupportme.gis.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
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
    public Customer findById(Integer id) {

        Session session = sessionFactory.getCurrentSession();
        Query query = session
            .createQuery("from Customer where id = :id")
            .setParameter("id", id)
        ;

        Customer customer = (Customer) query.uniqueResult();

        return customer;
    }

    @Override
    public void save(Customer customer, User user) {

        Date date = new Date();

        customer
                .setUserAdded(user)

        ;

        sessionFactory.getCurrentSession().save(customer);
    }

    @Override
    public void update(Customer customer, User user) {

        Date date = new Date();

        customer
                .setUserAdded(user)

        ;
        sessionFactory.getCurrentSession().persist(customer);
        sessionFactory.getCurrentSession().saveOrUpdate(customer);
    }


}
