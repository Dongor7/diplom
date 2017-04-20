package com.itsupportme.gis.component.menu;


import com.itsupportme.gis.component.menu.filters.FilterInterface;
import com.itsupportme.gis.component.menu.item.ItemList;
import com.itsupportme.gis.component.menu.item.MenuItem;

import java.util.ArrayList;

public class MenuService {

    private ArrayList<FilterInterface> filters;

    public MenuService(ArrayList<FilterInterface> filters) {
        this.filters = filters;
    }

    public ItemList<MenuItem> build(ItemList<MenuItem> menu) {

        for (FilterInterface filter : filters) {
            menu = filter.filter(menu);
        }

        return menu;
    }
}
