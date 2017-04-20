package com.itsupportme.gis.component.locker.exception;

public class RecordIsLockedException extends Exception {
    public RecordIsLockedException() {
        super("The record is already locked.");
    }
}
