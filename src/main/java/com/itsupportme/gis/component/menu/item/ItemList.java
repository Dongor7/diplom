package com.itsupportme.gis.component.menu.item;

import java.util.ArrayList;

/**
 * @param <MenuItem>
 */
public class ItemList<MenuItem extends com.itsupportme.gis.component.menu.item.MenuItem> extends ArrayList<MenuItem> {

    public ItemList<MenuItem> addItem(MenuItem item) {
        super.add(item);
        return this;
    }

    @SuppressWarnings("unchecked")
    public MenuItem getActive()
    {
        for (MenuItem item : this) {

            if (item.getActive()) {
                return item;
            }

            if (item.getChildren().size() > 0) {
                MenuItem i = (MenuItem) item.getChildren().getActive();
                if (i != null) {
                    return i;
                }
            }
        }

        return null;
    }
}
