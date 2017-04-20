package com.itsupportme.gis.component.locker;

import com.itsupportme.gis.component.locker.exception.InvalidEntityException;
import com.itsupportme.gis.component.util.ORMUtils;
import com.itsupportme.gis.entity.Lock;
import com.itsupportme.gis.entity.User;
import com.itsupportme.gis.entity.dao.LockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
public class LockChecker {

    @Autowired
    private LockDao lockDao;

    @Autowired
    private LockUtil lockUtil;

    @Autowired
    private Locker locker;

    @Transactional
    public Boolean isEditable(Object object, User user) throws InvalidEntityException {

        locker.clean();

        object            = ORMUtils.initializeAndUnproxy(object);
        String entityType = object.getClass().getSimpleName();
        Integer entityId  = this.lockUtil.findEntityId(object);
        Lock lock         = lockDao.getLock(entityId, entityType);

        return lock == null || Objects.equals(user.getUsername(), lock.getUserAdded().getUsername());
    }
}
