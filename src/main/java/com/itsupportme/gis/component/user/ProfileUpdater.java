package com.itsupportme.gis.component.user;

import com.itsupportme.gis.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProfileUpdater {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
        session.flush();
    }

    @Transactional
    public void update(User source, User target){
        target
                .setFirst(source.getFirst())
                .setLast(source.getLast());

        if(source.getPassword() != null && !source.getPassword().isEmpty()) {
            target.setPassword(passwordEncoder.encode(source.getPassword()));
        }

        this.update(target);
    }
}
