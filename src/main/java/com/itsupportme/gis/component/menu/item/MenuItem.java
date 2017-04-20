package com.itsupportme.gis.component.menu.item;


import java.util.ArrayList;

public class MenuItem {

    private Integer id;

    private String title;

    private String comment;

    private String alias;

    private String icon;

    private ArrayList<String> roles;

    private ItemList<MenuItem> children;

    private Boolean isSeparator = false;

    private Boolean isActive    = false;

    public MenuItem() {
        this.children = new ItemList<>();
        this.roles    = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public MenuItem setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MenuItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getComment() {
        return this.comment;
    }

    public MenuItem setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public MenuItem setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public MenuItem setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public MenuItem setRoles(ArrayList<String> roles) {
        this.roles = roles;
        return this;
    }

    public MenuItem addRole(String role) {
        this.roles.add(role);
        return this;
    }

    public ItemList<MenuItem> getChildren() {
        return children;
    }

    public MenuItem setChildren(ItemList<MenuItem> children) {
        this.children = children;
        return this;
    }

    public Boolean getSeparator() {
        return isSeparator;
    }

    public MenuItem setSeparator(Boolean separator) {
        this.isSeparator = separator;
        return this;
    }

    public Boolean getActive() {
        return isActive;
    }

    public MenuItem setActive(Boolean active) {
        isActive = active;
        return this;
    }

    public boolean hasActive() {

        for (MenuItem item : this.getChildren()) {
            if (item.getActive()) {
                return true;
            }
            if (item.getChildren().size() > 0) {
                Boolean result = item.hasActive();
                if (result) {
                    return item.hasActive();
                }
            }
        }

        return false;
    }
}
