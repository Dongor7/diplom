package com.itsupportme.gis.component.menu.filters;


import com.itsupportme.gis.component.menu.item.ItemList;
import com.itsupportme.gis.component.menu.item.MenuItem;
import com.itsupportme.gis.component.util.UrlParser;

import javax.servlet.http.HttpServletRequest;

public class ActiveItemFilter implements FilterInterface{

    private HttpServletRequest request;

    public ActiveItemFilter(HttpServletRequest request) {
        this.request = request;
    }

    public ItemList<MenuItem> filter(ItemList<MenuItem> menu) {

        if (this.request == null) {
            return menu;
        }

        String toReplace    = UrlParser.getRootUrl(this.request);
        String actualUrl    = this.request.getRequestURL().toString();
        String valueToCheck = actualUrl.replace(toReplace, "");

        for (MenuItem item : menu) {

            if (item.getChildren().size() > 0) {
                this.filter(item.getChildren());
            }

            if (item.getAlias() == null) {
                continue;
            }

            if (item.getAlias().equals(valueToCheck)) {
                item.setActive(true);
                break;
            }
        }

        return menu;
    }
}
