package com.itsupportme.gis.component.menu.filters;

import com.itsupportme.gis.component.menu.item.ItemList;
import com.itsupportme.gis.component.menu.item.MenuItem;

public interface FilterInterface {
     ItemList<MenuItem> filter(ItemList<MenuItem> menu);
}
