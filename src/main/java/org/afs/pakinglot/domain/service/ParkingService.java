// ParkingService.java
package org.afs.pakinglot.domain.service;

import org.afs.pakinglot.domain.Car;
import org.afs.pakinglot.domain.ParkingManager;
import org.afs.pakinglot.domain.exception.InvalidLicensePlateException;
import org.afs.pakinglot.domain.validator.LicensePlateValidator;
import org.springframework.stereotype.Service;

@Service
public class ParkingService {
    private final ParkingManager parkingManager;

    public ParkingService(ParkingManager parkingManager) {
        this.parkingManager = parkingManager;
    }

    public void parkCar(String strategy, String plateNumber) throws InvalidLicensePlateException {
        if (!LicensePlateValidator.isValid(plateNumber)) {
            throw new InvalidLicensePlateException();
        }
        parkingManager.park(strategy, plateNumber);
    }

    public Car fetchCar(String plateNumber) throws InvalidLicensePlateException {
        if (!LicensePlateValidator.isValid(plateNumber)) {
            throw new InvalidLicensePlateException();
        }
        return parkingManager.fetch(plateNumber);
    }

    public String getParkingLotStatus() {
        return parkingManager.getParkingLotStatus();
    }
}