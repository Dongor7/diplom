package com.itsupportme.gis.component.converter;

import com.itsupportme.gis.entity.User;
import com.itsupportme.gis.entity.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserConverter implements Converter<String, User> {

    @Autowired
    UserDao userDao;

    @Override
    public User convert(String id){
        return Objects.equals(id, "") ? null : userDao.findUser(Integer.parseInt(id));
    }

}
