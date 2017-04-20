package com.itsupportme.gis.component.validator;

import com.itsupportme.gis.entity.*;
import com.itsupportme.gis.entity.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueValidatorModel {


    @Autowired
    UserDao userDao;

    public boolean validateUser(User user) {

        Boolean isValid = true;

        User remoteUser;

        if(user.getId() != null){
            //edit
            remoteUser = userDao.findUser(user.getId(), user.getUsername());

        } else {
            //new
            remoteUser = userDao.findByUsername(user.getUsername());
        }

        if (remoteUser != null) {
            isValid = false;
        }

        return isValid;
    }
}

