package com.itsupportme.gis.component.menu;

import com.itsupportme.gis.component.RoleRepository;
import com.itsupportme.gis.component.menu.item.ItemList;
import com.itsupportme.gis.component.menu.item.MenuItem;

public class MenuBuilder {

    public static ItemList<MenuItem> build() {

        ItemList<MenuItem> menu = new ItemList<>();

        menu
            .addItem(
                (new MenuItem())
                    .setId(0).setTitle("Main Section").setSeparator(true)
            )
            .addItem(
                    (new MenuItem())
                            .setId(1).setTitle("Home").setAlias("/")
                            .setComment("Overall information and service status").setIcon("home")
            )
            /*.addItem(
                    (new MenuItem())
                            .setId(2).setTitle("Customers").setAlias("/customers/view")
                            .setComment("Customers list")
                            .setIcon("list-alt")
            )*/
            .addItem(
                    (new MenuItem())
                            .setId(4).setTitle("Management").setSeparator(true)
                            .addRole(RoleRepository.ROLE_MANAGER)
                            .addRole(RoleRepository.ROLE_DEVELOPER)
            )
            .addItem(
                (new MenuItem())
                    .setId(5).setTitle("Configuration").setIcon("wrench")
                    .addRole(RoleRepository.ROLE_MANAGER)
                    .addRole(RoleRepository.ROLE_DEVELOPER)
                    .setChildren(
                        (new ItemList<>())
                            .addItem(
                                (new MenuItem())
                                    .setTitle("User Management").setAlias("/configuration/user_management")
                                    .setIcon("users")
                                    .addRole(RoleRepository.ROLE_DEVELOPER)
                            )
                    )
            );

        return menu;
    }
}
