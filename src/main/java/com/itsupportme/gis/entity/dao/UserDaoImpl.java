package com.itsupportme.gis.entity.dao;

import com.itsupportme.gis.entity.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao
{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public User findByUsername(String username)
    {
        List<User> users;

        users = sessionFactory.getCurrentSession()
                .createQuery("from User where username = :username")
                .setParameter("username", username)
                .list();

        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public User findUser(Integer id) {

        List<User> users;

        users = sessionFactory.getCurrentSession()
                .createQuery("from User where id = :id")
                .setParameter("id", id)
                .list();

        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public User findUser(Integer id, String username) {

        List<User> users;

        users = sessionFactory.getCurrentSession()
                .createQuery("from User where id != :id and username = :username")
                .setParameter("username", username)
                .setParameter("id", id)
                .list();

        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll(){

        List<User> users;

        users = sessionFactory.getCurrentSession()
                .createQuery("from User")
                .list();

        return users;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll(String username, String first, String last, Integer limitStart, Integer limitEnd) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("from User where 1=1 ");

        if (username != null && !username.isEmpty()) {
            queryBuilder.append("and username like :username ");
        }

        if (first != null && !first.isEmpty()) {
            queryBuilder.append("and first like :first ");
        }

        if (last != null && !last.isEmpty()) {
            queryBuilder.append("and last like :last ");
        }

        queryBuilder.append("order by id asc");

        Query query = sessionFactory.getCurrentSession().createQuery(queryBuilder.toString());

        if (username != null && !username.isEmpty()) {
            query.setParameter("username", username + "%");
        }

        if (first != null && !first.isEmpty()) {
            query.setParameter("first", first + "%");
        }
        if (last != null && !last.isEmpty()) {
            query.setParameter("last", last + "%");
        }

        query.setFirstResult(limitStart);
        query.setMaxResults(limitEnd);

        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Integer countAll(String username, String first, String last) {

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("select count(*) from User where 1=1 ");

        if (username != null && !username.isEmpty()) {
            queryBuilder.append("and username like :username ");
        }

        if (first != null && !first.isEmpty()) {
            queryBuilder.append("and first like :first ");
        }

        if (last != null && !last.isEmpty()) {
            queryBuilder.append("and last like :last ");
        }

        Query query = sessionFactory.getCurrentSession().createQuery(queryBuilder.toString());

        if (username != null && !username.isEmpty()) {
            query.setParameter("username", username + "%");
        }

        if (first != null && !first.isEmpty()) {
            query.setParameter("first", first + "%");
        }
        if (last != null && !last.isEmpty()) {
            query.setParameter("last", last + "%");
        }

        return ((Number)query.uniqueResult()).intValue();
    }

}
