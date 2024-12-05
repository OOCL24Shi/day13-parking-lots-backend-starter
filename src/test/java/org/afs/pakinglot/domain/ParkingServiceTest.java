// ParkingServiceTest.java
package org.afs.pakinglot.domain;

import org.afs.pakinglot.domain.Car;
import org.afs.pakinglot.domain.ParkingManager;
import org.afs.pakinglot.domain.exception.InvalidLicensePlateException;
import org.afs.pakinglot.domain.service.ParkingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingServiceTest {

    private ParkingService parkingService;
    private ParkingManager parkingManager;

    @BeforeEach
    void setUp() {
        parkingManager = mock(ParkingManager.class);
        parkingService = new ParkingService(parkingManager);
    }

    @Test
    void should_park_car_with_valid_plate_number() throws InvalidLicensePlateException {
        String strategy = "STANDARD";
        String plateNumber = "AB-1234";

        parkingService.parkCar(strategy, plateNumber);

        verify(parkingManager, times(1)).park(strategy, plateNumber);
    }

    @Test
    void should_throw_exception_for_invalid_plate_number_when_parking() {
        String strategy = "STANDARD";
        String plateNumber = "INVALID";

        assertThrows(InvalidLicensePlateException.class, () -> parkingService.parkCar(strategy, plateNumber));
    }

    @Test
    void should_fetch_car_with_valid_plate_number() throws InvalidLicensePlateException {
        String plateNumber = "AB-1234";
        Car car = new Car(plateNumber);
        when(parkingManager.fetch(plateNumber)).thenReturn(car);

        Car fetchedCar = parkingService.fetchCar(plateNumber);

        assertEquals(plateNumber, fetchedCar.plateNumber());
        verify(parkingManager, times(1)).fetch(plateNumber);
    }

    @Test
    void should_throw_exception_for_invalid_plate_number_when_fetching() {
        String plateNumber = "INVALID";

        assertThrows(InvalidLicensePlateException.class, () -> parkingService.fetchCar(plateNumber));
    }

    @Test
    void should_return_parking_lot_status() {
        String status = "Plaza Park: [AB-1234]";
        when(parkingManager.getParkingLotStatus()).thenReturn(status);

        String result = parkingService.getParkingLotStatus();

        assertEquals(status, result);
        verify(parkingManager, times(1)).getParkingLotStatus();
    }
}