package com.itsupportme.gis.controller.advice;

import com.itsupportme.gis.component.RoleRepository;
import com.itsupportme.gis.component.menu.MenuBuilder;
import com.itsupportme.gis.component.menu.MenuService;
import com.itsupportme.gis.component.menu.filters.ActiveItemFilter;
import com.itsupportme.gis.component.menu.filters.FilterInterface;
import com.itsupportme.gis.component.menu.filters.RoleFilter;
import com.itsupportme.gis.component.menu.item.ItemList;
import com.itsupportme.gis.component.menu.item.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@ControllerAdvice
public class MenuControllerAdvice {

    @Autowired
    private HttpServletRequest request;

    @ModelAttribute("Menu")
    public ItemList<MenuItem> getMenu() {

        // Prepare filters
        ArrayList<FilterInterface> filters = new ArrayList<>();
        filters.add(new ActiveItemFilter(request));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            filters.add(new RoleFilter(authentication.getAuthorities(), RoleRepository.ROLE_USER));
        }

        // Create menu using default website builder
        ItemList<MenuItem> menu = MenuBuilder.build();

        // Create menu service with filters
        MenuService service = new MenuService(filters);

        return service.build(menu);
    }
}
