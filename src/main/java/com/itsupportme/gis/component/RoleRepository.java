package com.itsupportme.gis.component;

import java.util.HashMap;

public class RoleRepository {
    public static final String ROLE_MANAGER   = "ROLE_MANAGER";
    public static final String ROLE_USER      = "ROLE_USER";
    public static final String ROLE_DEVELOPER = "ROLE_DEVELOP";
    public static final String ROLE_API       = "ROLE_API";

    public static String hasRole(String role) {

        return "hasRole('" +   role + "')";
    }

    public static HashMap<String, String> getRoles() {

        HashMap<String, String> roles = new HashMap<>();

        roles.put(RoleRepository.ROLE_MANAGER,   "Manager");
        roles.put(RoleRepository.ROLE_USER,      "User");
        roles.put(RoleRepository.ROLE_DEVELOPER, "Developer");
        roles.put(RoleRepository.ROLE_API,       "Api");

        return roles;
    }
}
