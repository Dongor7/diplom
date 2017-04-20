package com.itsupportme.gis.component.locker;

import com.itsupportme.gis.component.locker.exception.InvalidEntityException;
import com.itsupportme.gis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LockManager {

    @Autowired
    private Locker locker;

    @Autowired
    private LockChecker lockChecker;

    @Autowired
    private LockInfo lockInfo;

    public boolean lock(Object object, User user) {

        try {
            this.locker.lock(object, user);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean release(Object object) {
        try {
            this.locker.release(object);
        } catch (InvalidEntityException e) {
            return false;
        }

        return true;
    }

    public boolean isEditable(Object object, User user) {

        Boolean result = false;
        try {
            result = this.lockChecker.isEditable(object, user);
        } catch (InvalidEntityException e) {
            // Do nothing
        }

        return result;
    }

    public String getLockInfo(Object object) {

        return this.lockInfo.getLockInfo(object);
    }
}
