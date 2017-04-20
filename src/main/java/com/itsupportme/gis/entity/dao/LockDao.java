package com.itsupportme.gis.entity.dao;

import com.itsupportme.gis.entity.Lock;
import com.itsupportme.gis.entity.User;

import java.util.List;


public interface LockDao {

    Lock getLock(Integer entityId, String entityType);
    List<Lock> findAllPatientsLockedBy(User user);
}
