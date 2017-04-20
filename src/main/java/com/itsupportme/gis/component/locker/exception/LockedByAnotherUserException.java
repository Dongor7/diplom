package com.itsupportme.gis.component.locker.exception;


public class LockedByAnotherUserException extends Exception {
    public LockedByAnotherUserException() {
        super("This record is locked by another user.");
    }
}
