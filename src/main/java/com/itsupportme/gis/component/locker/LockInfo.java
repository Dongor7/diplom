package com.itsupportme.gis.component.locker;

import com.itsupportme.gis.component.locker.exception.InvalidEntityException;
import com.itsupportme.gis.component.util.DateUtil;
import com.itsupportme.gis.component.util.ORMUtils;
import com.itsupportme.gis.entity.Lock;
import com.itsupportme.gis.entity.dao.LockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LockInfo {

    @Autowired
    private LockUtil lockUtil;

    @Autowired
    private LockDao lockDao;

    @Transactional
    public String getLockInfo(Object object) {

        object            = ORMUtils.initializeAndUnproxy(object);
        String entityType = object.getClass().getSimpleName();
        Integer entityId;
        try {
            entityId = this.lockUtil.findEntityId(object);
        } catch (InvalidEntityException e) {
            return "Unable to get lock info: " + e.getMessage();
        }

        Lock lock         = lockDao.getLock(entityId, entityType);

        if (lock == null) {
            return "Record " + entityId + " of type " + entityType + " is not locked";
        }

        String lockedUntil = DateUtil.USDateTime(lock.getTimeout());

        return
                "Record " + entityId + " of type " + entityType + " is locked by " +
                lock.getUserAdded().getUsername() + " (" + lock.getUserAdded().getFirst() + " " +
                lock.getUserAdded().getLast() + ") until " + lockedUntil;
    }
}
