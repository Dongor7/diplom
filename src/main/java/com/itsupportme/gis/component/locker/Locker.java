package com.itsupportme.gis.component.locker;

import com.itsupportme.gis.component.locker.exception.InvalidEntityException;
import com.itsupportme.gis.component.locker.exception.LockedByAnotherUserException;
import com.itsupportme.gis.component.locker.exception.RecordIsLockedException;
import com.itsupportme.gis.component.util.DateUtil;
import com.itsupportme.gis.component.util.ORMUtils;
import com.itsupportme.gis.entity.Lock;
import com.itsupportme.gis.entity.User;
import com.itsupportme.gis.entity.dao.LockDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Component
public class Locker {

    public static final Integer LOCK_TIMEOUT = 180;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private LockDao lockDao;

    @Autowired
    private LockUtil lockUtil;

    @Transactional
    public void lock(Object object, User user)
            throws InvalidEntityException, LockedByAnotherUserException, RecordIsLockedException {

        this.clean();

        object            = ORMUtils.initializeAndUnproxy(object);
        String entityType = object.getClass().getSimpleName();
        Integer entityId  = this.lockUtil.findEntityId(object);

        Lock lock = lockDao.getLock(entityId, entityType);

        if (lock != null) {

            if (!Objects.equals(lock.getUserAdded().getUsername(), user.getUsername())) {
                throw new LockedByAnotherUserException();
            }

            // Update lock on another attempt
            lock.setTimeout(DateUtil.increase(new Date(), LOCK_TIMEOUT));

            Session session = sessionFactory.getCurrentSession();
            session.update(lock);
            session.flush();

            return;
        }

        Date timeout = DateUtil.increase(new Date(), LOCK_TIMEOUT);

        lock = new Lock();
        lock
            .setEntityId(entityId)
            .setEntityType(entityType)
            .setTimeout(timeout)
            .setUserAdded(user);

        Session session = sessionFactory.getCurrentSession();
        session.persist(lock);
        session.flush();
    }

    @Transactional
    public void release(Object object) throws InvalidEntityException {

        object            = ORMUtils.initializeAndUnproxy(object);
        Integer entityId  = this.lockUtil.findEntityId(object);
        String entityType = object.getClass().getSimpleName();

        sessionFactory
                .getCurrentSession()
                .createQuery("delete from Lock where entityId = :entityId and entityType = :entityType")
                .setParameter("entityId", entityId)
                .setParameter("entityType", entityType)
                .executeUpdate();
    }

    @Transactional
    public void clean() {

        sessionFactory
                .getCurrentSession()
                .createQuery("delete from Lock where timeout < :date")
                .setParameter("date", new Date())
                .executeUpdate();
    }

}
