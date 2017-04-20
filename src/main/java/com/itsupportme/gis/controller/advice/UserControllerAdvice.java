package com.itsupportme.gis.controller.advice;

import com.itsupportme.gis.component.user.UserBuilder;
import com.itsupportme.gis.entity.User;
import com.itsupportme.gis.entity.UserDetails;
import com.itsupportme.gis.entity.dao.UserDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.transaction.Transactional;

@ControllerAdvice
public class UserControllerAdvice {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserBuilder userBuilder;

    @ModelAttribute("User")
    @Transactional
    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        String username = authentication.getName();
        User user       = userDao.findByUsername(username);

        if (user == null) {
            return null;
        }

        if (user.getUserDetails() == null) {

            UserDetails userDetails = userBuilder.getDefaultUserDetails(user);

            Session session = sessionFactory.getCurrentSession();
            session.persist(userDetails);
            session.flush();
        }

        return user;
    }
}
