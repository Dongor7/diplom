package com.itsupportme.gis.component.form;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserManagementForm extends PaginationForm {

    @Size(max = 45)
    private String username;

    @Size(max = 30)
    @Pattern(regexp = "^[a-zA-Z]*$",
            message = "First name must contains only letters.")
    private String first;


    @Size(max = 30)
    @Pattern(regexp = "^[a-zA-Z]*$",
            message = "Last name must contains only letters.")
    private String last;

    public UserManagementForm() {

        this.page         = 1;
        this.itemsPerPage = 15;
    }

    public String getUsername() {
        return username;
    }

    public UserManagementForm setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirst() {
        return first;
    }

    public UserManagementForm setFirst(String first) {
        this.first = first;
        return this;
    }

    public String getLast() {
        return last;
    }

    public UserManagementForm setLast(String last) {
        this.last = last;
        return this;
    }
}
