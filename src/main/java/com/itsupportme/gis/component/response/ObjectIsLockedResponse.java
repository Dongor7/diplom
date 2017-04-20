package com.itsupportme.gis.component.response;


public class ObjectIsLockedResponse extends AjaxResponse {
    public ObjectIsLockedResponse() {
        super(3, "Object is locked by other user");
    }
}
