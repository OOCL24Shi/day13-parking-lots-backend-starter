package org.afs.pakinglot.domain.exception;

public class InvalidLicensePlateException extends Throwable {
    public InvalidLicensePlateException() {
        super("Invalid license plate.");
    }
}
