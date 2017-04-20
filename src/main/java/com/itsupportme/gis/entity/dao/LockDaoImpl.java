package com.itsupportme.gis.entity.dao;

import com.itsupportme.gis.entity.Lock;
import com.itsupportme.gis.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class LockDaoImpl implements LockDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public Lock getLock(Integer entityId, String entityType) {

        List<Lock> locks;

        locks = sessionFactory.getCurrentSession()
                .createQuery("from Lock where entityId = :entityId and entityType = :entityType")
                .setParameter("entityId", entityId)
                .setParameter("entityType", entityType)
                .list();

        if (locks.size() == 0) {
            return null;
        } else {
            return locks.get(0);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Lock> findAllPatientsLockedBy(User user) {

        List<Lock> patients;

        patients = sessionFactory.getCurrentSession()
                .createQuery("from Lock where entityType = 'Patient' and userAdded = :user")
                .setParameter("user", user)
                .list();

        return patients;
    }
}
