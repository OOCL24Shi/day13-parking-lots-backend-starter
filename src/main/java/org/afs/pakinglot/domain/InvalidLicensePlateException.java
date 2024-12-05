package org.afs.pakinglot.domain;

public class InvalidLicensePlateException extends Throwable {
    public InvalidLicensePlateException() {
        super("Invalid license plate.");
    }
}
