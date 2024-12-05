// ParkingControllerTest.java
package org.afs.pakinglot.domain;

import org.afs.pakinglot.domain.Car;
import org.afs.pakinglot.domain.controller.ParkingController;
import org.afs.pakinglot.domain.service.ParkingService;
import org.afs.pakinglot.domain.exception.InvalidLicensePlateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

class ParkingControllerTest {

    private ParkingController parkingController;
    private ParkingService parkingService;

    @BeforeEach
    void setUp() {
        parkingService = mock(ParkingService.class);
        parkingController = new ParkingController(parkingService);
    }

    @Test
    void should_park_car_with_valid_plate_number() throws InvalidLicensePlateException {
        String strategy = "STANDARD";
        String plateNumber = "AB-1234";

        ResponseEntity<String> response = parkingController.parkCar(strategy, plateNumber);

        assertEquals(OK, response.getStatusCode());
        assertEquals("Car parked successfully.", response.getBody());
        verify(parkingService, times(1)).parkCar(strategy, plateNumber);
    }

    @Test
    void should_return_bad_request_for_invalid_plate_number_when_parking() throws InvalidLicensePlateException {
        String strategy = "STANDARD";
        String plateNumber = "INVALID";
        doThrow(new InvalidLicensePlateException()).when(parkingService).parkCar(strategy, plateNumber);

        ResponseEntity<String> response = parkingController.parkCar(strategy, plateNumber);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid license plate.", response.getBody());
    }

    @Test
    void should_fetch_car_with_valid_plate_number() throws InvalidLicensePlateException {
        String plateNumber = "AB-1234";
        Car car = new Car(plateNumber);
        when(parkingService.fetchCar(plateNumber)).thenReturn(car);

        ResponseEntity<Car> response = parkingController.fetchCar(plateNumber);

        assertEquals(OK, response.getStatusCode());
        assertEquals(car, response.getBody());
        verify(parkingService, times(1)).fetchCar(plateNumber);
    }

    @Test
    void should_return_bad_request_for_invalid_plate_number_when_fetching() throws InvalidLicensePlateException {
        String plateNumber = "INVALID";
        doThrow(new InvalidLicensePlateException()).when(parkingService).fetchCar(plateNumber);

        ResponseEntity<Car> response = parkingController.fetchCar(plateNumber);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void should_return_parking_lot_status() {
        String status = "Plaza Park: [AB-1234]";
        when(parkingService.getParkingLotStatus()).thenReturn(status);

        ResponseEntity<String> response = parkingController.getParkingLotStatus();

        assertEquals(OK, response.getStatusCode());
        assertEquals(status, response.getBody());
        verify(parkingService, times(1)).getParkingLotStatus();
    }
}