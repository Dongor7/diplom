package com.itsupportme.gis.component.user;

import com.itsupportme.gis.entity.UserRole;
import com.itsupportme.gis.entity.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class DbUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(final String username) {
        com.itsupportme.gis.entity.User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Unable to find user with specified username.");
        }

        List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());
        return buildUserForAuthentication(user, authorities);
    }

    private User buildUserForAuthentication(
            com.itsupportme.gis.entity.User user,
            List<GrantedAuthority> authorities
    ) {
        return new User(
                user.getUsername(),
                user.getPassword(),
                user.getIsEnabled(),
                true,
                true,
                true,
                authorities
        );
    }

    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
        Set<GrantedAuthority> setAuth = new HashSet<>();

        // Build user's authorities
        for (UserRole userRole : userRoles) {
            setAuth.add(new SimpleGrantedAuthority(userRole.getRole()));
        }

        return new ArrayList<>(setAuth);
    }
}
