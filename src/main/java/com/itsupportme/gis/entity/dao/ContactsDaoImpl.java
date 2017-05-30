package com.itsupportme.gis.entity.dao;

import com.itsupportme.gis.entity.Contacts;
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
public class ContactsDaoImpl implements ContactsDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Contacts> findAll() {

        return sessionFactory
                .getCurrentSession()
                .createQuery("from Contacts")
                .list()
        ;
    }

    @Override
    public Contacts findById(Integer id) {

        Session session = sessionFactory.getCurrentSession();
        Query query = session
            .createQuery("from Contacts where id = :id")
            .setParameter("id", id)
        ;

        Contacts contacts = (Contacts) query.uniqueResult();

        return contacts;
    }

    @Override
    public void save(Contacts contacts, User user) {

        contacts
                .setUserAdded(user)

        ;

        sessionFactory.getCurrentSession().save(contacts);
    }

    @Override
    public void update(Contacts contacts, User user) {


        contacts
                .setUserAdded(user)

        ;
        sessionFactory.getCurrentSession().persist(contacts);
        sessionFactory.getCurrentSession().saveOrUpdate(contacts);
    }


}
