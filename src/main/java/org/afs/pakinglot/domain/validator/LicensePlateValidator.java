
package org.afs.pakinglot.domain.validator;

import java.util.regex.Pattern;

public class LicensePlateValidator {
    private static final Pattern LICENSE_PLATE_PATTERN = Pattern.compile("^[A-Z]{2}-\\d{4}$");

    public static boolean isValid(String plateNumber) {
        return plateNumber != null && LICENSE_PLATE_PATTERN.matcher(plateNumber).matches();
    }
}