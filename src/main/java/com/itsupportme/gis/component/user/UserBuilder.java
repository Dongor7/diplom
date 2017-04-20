package com.itsupportme.gis.component.user;

import com.itsupportme.gis.component.layout.AvatarsRepository;
import com.itsupportme.gis.component.layout.ThemesRepository;
import com.itsupportme.gis.entity.User;
import com.itsupportme.gis.entity.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserBuilder {

    @Autowired
    PasswordEncoder passwordEncoder;

    public User build(
            String username,
            String firstName,
            String lastName,
            Boolean isEnabled,
            String password
    ) {
        User user = new User();

        if (password == null || password.isEmpty()) {
            password = User.DEFAULT_PASSWORD;
        }

        user
            .setUsername(username)
            .setFirst(firstName)
            .setLast(lastName)
            .setIsEnabled(isEnabled)
            .setPassword(passwordEncoder.encode(password));

        return user;
    }

    public User build(User user, String password) {
        return this.build(user.getUsername(), user.getFirst(), user.getLast(), user.getIsEnabled(), password);
    }

    public UserDetails getDefaultUserDetails(User user) {

        UserDetails userDetails = new UserDetails();

        userDetails.setImage(AvatarsRepository.AVATAR_1);
        userDetails.setTheme(ThemesRepository.THEME_BLUE);
        userDetails.setUser(user);

        user.setUserDetails(userDetails);

        return userDetails;
    }
}
