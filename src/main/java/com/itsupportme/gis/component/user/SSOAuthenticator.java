package com.itsupportme.gis.component.user;

import com.itsupportme.gis.component.PropertyLoader;
import com.itsupportme.gis.component.RoleRepository;
import com.itsupportme.gis.component.util.UrlParser;
import com.itsupportme.gis.entity.User;
import com.itsupportme.gis.entity.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

@Component
public class SSOAuthenticator {

    public static final Integer SSO_FAILED           = 0;
    public static final Integer SSO_SUCCESS          = 1;
    public static final Integer SSO_SUCCESS_NEW_USER = 2;

    @Autowired
    private PropertyLoader propertyLoader;

    @Autowired
    private DbUserDetailsService dbUserDetailsService;

    @Autowired
    private UserBuilder userBuilder;

    @Autowired
    private SessionFactory sessionFactory;

    public Integer doSSO(String ticket) throws UnsupportedEncodingException {

        Integer ssoResult     = SSOAuthenticator.SSO_FAILED;
        Properties properties = propertyLoader.load("application.properties");

        String url     = (String) properties.get("sso.service.url");
        String service = (String) properties.get("sso.service.id");

        MultiValueMap<String, String> uriVars = new LinkedMultiValueMap<>();

        uriVars.add("service", service);
        uriVars.add("ticket",  ticket);

        RestTemplate restTemplate = new RestTemplate();
        String result             = restTemplate.postForObject(url, uriVars, String.class);

        HashMap<String, List<String>> decoded = (HashMap<String, List<String>>) UrlParser.splitQuery(result);

        if (!(
                decoded.containsKey("AUTHENTICATED") &&
                Boolean.valueOf(decoded.get("AUTHENTICATED").get(0).toLowerCase()) &&
                decoded.containsKey("USERID") &&
                !decoded.get("USERID").get(0).isEmpty()
        )) {
            return ssoResult;
        }

        String  username  = decoded.get("USERID").get(0);
        UserDetails userDetails;
        try {
            userDetails = dbUserDetailsService.loadUserByUsername(username);
            ssoResult   = SSOAuthenticator.SSO_SUCCESS;
        } catch (UsernameNotFoundException e) {

            User user = userBuilder.build(
                    username,
                    "First",
                    "Last",
                    true,
                    new BigInteger(130, new SecureRandom()).toString(32)
            );

            Session session = sessionFactory.getCurrentSession();
            session.persist(user);

            UserRole userRole = new UserRole();
            userRole
                    .setRole(RoleRepository.ROLE_USER)
                    .setUser(user)
            ;

            session.persist(userRole);
            user.addUserRole(userRole);

            com.itsupportme.gis.entity.UserDetails dbUserDetails
                    = userBuilder.getDefaultUserDetails(user);

            session.persist(dbUserDetails);
            user.setUserDetails(dbUserDetails);

            session.flush();

            userDetails = dbUserDetailsService.loadUserByUsername(username);
            ssoResult   = SSOAuthenticator.SSO_SUCCESS_NEW_USER;
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ssoResult;
    }
}
