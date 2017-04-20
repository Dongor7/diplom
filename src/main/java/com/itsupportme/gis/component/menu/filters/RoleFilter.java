package com.itsupportme.gis.component.menu.filters;


import com.itsupportme.gis.component.menu.item.ItemList;
import com.itsupportme.gis.component.menu.item.MenuItem;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class RoleFilter implements FilterInterface {

    private Collection<?extends GrantedAuthority> authorities;
    private String                                defaultRole;

    public RoleFilter(Collection<?extends GrantedAuthority> authorities, String defaultRole) {
        this.authorities = authorities;
        this.defaultRole = defaultRole;
    }

    public RoleFilter(Collection<?extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public ItemList<MenuItem> filter(ItemList<MenuItem> menu) {

        ItemList<MenuItem> filteredMenu = new ItemList<>();

        for (MenuItem item : menu) {
            ArrayList<String> roles = item.getRoles();
            if (defaultRole != null && roles.size() == 0) {
                roles.add(defaultRole);
            }

            Boolean hasRole = false;

            for (GrantedAuthority authority : this.authorities) {
                String actualAuthority = authority.getAuthority();
                if (roles.contains(actualAuthority)) {
                    hasRole = true;
                }
            }

            if (hasRole) {
                filteredMenu.addItem(item);
            }

            if (item.getChildren().size() > 0 && filteredMenu.size() > 0) {
                MenuItem filteredItem = filteredMenu.get(filteredMenu.size() - 1);
                filteredItem.setChildren(this.filter(filteredItem.getChildren()));
            }
        }

        return filteredMenu;
    }
}
