package com.itsupportme.gis.component.locker.exception;


public class InvalidEntityException extends Exception {
    public InvalidEntityException() {
        super("Unable to invoke getId on object provided.");
    }
}
