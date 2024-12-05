package org.afs.pakinglot.domain;

import org.afs.pakinglot.domain.exception.InvalidLicensePlateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParkingManagerTest {

    private ParkingManager parkingManager;
    private ParkingLot plazaPark;
    private ParkingLot cityMallGarage;
    private ParkingLot officeTowerParking;

    @BeforeEach
    void setUp() {
        plazaPark = new ParkingLot(1, "Plaza Park", 9);
        cityMallGarage = new ParkingLot(2, "City Mall Garage", 12);
        officeTowerParking = new ParkingLot(3, "Office Tower Parking", 9);

        parkingManager = new ParkingManager(
                List.of(plazaPark, cityMallGarage, officeTowerParking)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "SMART, ST-5678",
            "SUPER, VW-9012",
            "STANDARD, AB-1234"
    })
    void should_park_car_in_lot_based_on_strategy_given_parking_lots(String strategy, String plateNumber) throws InvalidLicensePlateException {
        // Given
        cityMallGarage.park(new Car("CA-1999"));
        plazaPark.park(new Car("CA-2888"));
        officeTowerParking.park(new Car("CA-3888"));
        cityMallGarage.park(new Car("CA-4888"));
        plazaPark.park(new Car("CA-5888"));

        // When
        Ticket ticket = parkingManager.park(strategy, plateNumber);

        // Then
        if (strategy.equals("SMART")) {
            assertTrue(cityMallGarage.contains(ticket));
        } else if (strategy.equals("SUPER")) {
            assertTrue(officeTowerParking.contains(ticket));
        } else if (strategy.equals("STANDARD")) {
            assertTrue(plazaPark.contains(ticket));
        }
    }

    @Test
    void should_throw_exception_when_invalid_strategy_given_parking_lots() {
        // Given
        String strategy = "INVALID";
        String plateNumber = "JK-3456";

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> parkingManager.park(strategy, plateNumber));
        assertEquals("Invalid parking strategy: INVALID", exception.getMessage());
    }

    @Test
    void should_park_car_in_first_lot_when_standard_strategy_given_first_lot_not_full() throws InvalidLicensePlateException {
        // Given
        String strategy = "STANDARD";
        String plateNumber = "AB-1234";

        // When
        Ticket ticket = parkingManager.park(strategy, plateNumber);

        // Then
        assertTrue(plazaPark.contains(ticket));
    }

    @Test
    void should_park_car_in_second_lot_when_standard_strategy_given_first_lot_full() throws InvalidLicensePlateException {
        // Given
        String strategy = "STANDARD";
        String plateNumber = "PQ-1234";
        for (int i = 0; i < plazaPark.getCapacity(); i++) {
            plazaPark.park(new Car("CAR-" + i));
        }

        // When
        Ticket ticket = parkingManager.park(strategy, plateNumber);

        // Then
        assertTrue(cityMallGarage.contains(ticket));
    }

    @Test
    void should_display_aggregated_status_of_all_parking_lots_when_park_to_3_lots_given_three_lots() {
        // Given
        plazaPark.park(new Car("AB-1234"));
        cityMallGarage.park(new Car("DE-5678"));
        officeTowerParking.park(new Car("GH-9012"));

        // When
        String status = parkingManager.getParkingLotStatus();

        // Then
        String expectedStatus = "Plaza Park: [AB-1234]\nCity Mall Garage: [DE-5678]\nOffice Tower Parking: [GH-9012]";
        assertEquals(expectedStatus, status);
    }

    //requirement2
    @Test
    void should_park_car_in_first_available_lot_and_update_status_when_park_given_plate_number() throws InvalidLicensePlateException {
        // Given
        String plateNumber = "AB-1234";

        // When
        parkingManager.park("STANDARD", plateNumber);
        String status = parkingManager.getParkingLotStatus();

        // Then
        assertTrue(status.contains("AB-1234"));
        assertTrue(plazaPark.getParkingLotStatus().contains("AB-1234"));
    }

    @Test
    void should_park_car_in_second_lot_when_first_is_full_and_update_status_when_park_given_plate_number() throws InvalidLicensePlateException {
        // Given
        for (int i = 0; i < plazaPark.getCapacity(); i++) {
            parkingManager.park("STANDARD", "CA-" + ((100 + (int)(Math.random() * 900)) * 10+i));
        }
        String plateNumber = "XY-5678";

        // When
        parkingManager.park("STANDARD", plateNumber);
        String status = parkingManager.getParkingLotStatus();

        // Then
        assertTrue(status.contains("XY-5678"));
        assertTrue(cityMallGarage.getParkingLotStatus().contains("XY-5678"));
    }

    @Test
    void should_park_car_in_third_lot_when_first_two_are_full_and_update_status_given_plate_number() throws InvalidLicensePlateException {
        // Given
        for (int i = 0; i < plazaPark.getCapacity(); i++) {
            parkingManager.park("STANDARD", "CA-" + ((100 + (int)(Math.random() * 900)) * 10+i));
        }
        for (int i = 0; i < cityMallGarage.getCapacity(); i++) {
            parkingManager.park("STANDARD", "CA-" + ((100 + (int)(Math.random() * 900)) * 10+i));
        }
        String plateNumber = "LM-9012";

        // When
        parkingManager.park("STANDARD", plateNumber);
        String status = parkingManager.getParkingLotStatus();

        // Then
        assertTrue(status.contains("LM-9012"));
        assertTrue(officeTowerParking.getParkingLotStatus().contains("LM-9012"));
    }

    @Test
    void should_fetch_car_from_first_lot_and_update_status_when_fetch_given_plate_number() throws InvalidLicensePlateException {
        // Given
        String plateNumber = "AB-1234";
        parkingManager.park("STANDARD", plateNumber);

        // When
        Car fetchedCar = parkingManager.fetch(plateNumber);
        String status = parkingManager.getParkingLotStatus();

        // Then
        assertEquals(plateNumber, fetchedCar.plateNumber());
        assertFalse(status.contains("AB-1234"));
        assertFalse(plazaPark.getParkingLotStatus().contains("AB-1234"));
    }

    @Test
    void should_fetch_car_from_second_lot_and_update_status_when_fetch_given_plate_number() throws InvalidLicensePlateException {
        // Given
        for (int i = 0; i < plazaPark.getCapacity(); i++) {
            parkingManager.park("STANDARD", "CA-" + ((100 + (int)(Math.random() * 900)) * 10+i));
        }
        String plateNumber = "XY-5678";
        parkingManager.park("STANDARD", plateNumber);

        // When
        Car fetchedCar = parkingManager.fetch(plateNumber);
        String status = parkingManager.getParkingLotStatus();

        // Then
        assertEquals(plateNumber, fetchedCar.plateNumber());
        assertFalse(status.contains("XY-5678"));
        assertFalse(cityMallGarage.getParkingLotStatus().contains("XY-5678"));
    }

    @Test
    void should_fetch_car_from_third_lot_and_update_status_when_fetch_given_plate_number() throws InvalidLicensePlateException {
        // Given
        for (int i = 0; i < plazaPark.getCapacity(); i++) {
            parkingManager.park("STANDARD", "CA-" + ((100 + (int)(Math.random() * 900)) * 10+i));
        }
        for (int i = 0; i < cityMallGarage.getCapacity(); i++) {
            parkingManager.park("STANDARD", "CA-" + ((100 + (int)(Math.random() * 900)) * 10+i));
        }
        String plateNumber = "LM-9012";
        parkingManager.park("STANDARD", plateNumber);

        // When
        Car fetchedCar = parkingManager.fetch(plateNumber);
        String status = parkingManager.getParkingLotStatus();

        // Then
        assertEquals(plateNumber, fetchedCar.plateNumber());
        assertFalse(status.contains("LM-9012"));
        assertFalse(officeTowerParking.getParkingLotStatus().contains("LM-9012"));
    }
    //requirement4

    @Test
    void should_park_car_with_valid_plate_number_and_update_status_when_park_given_valid_plate() throws InvalidLicensePlateException {
        // Given
        String validPlateNumber = "AB-1234";

        // When
        parkingManager.park("STANDARD", validPlateNumber);
        String status = parkingManager.getParkingLotStatus();

        // Then
        assertTrue(status.contains("AB-1234"));
        assertTrue(plazaPark.getParkingLotStatus().contains("AB-1234"));
    }

    @Test
    void should_throw_exception_for_invalid_plate_number_when_parking_given_invalid_plate() {
        // Given
        String invalidPlateNumber = "INV-1234";

        // When & Then
        assertThrows(InvalidLicensePlateException.class, () -> parkingManager.park("STANDARD", invalidPlateNumber));
    }

    @Test
    void should_fetch_car_with_valid_plate_number_and_update_status_when_fetch_given_valid_plate() throws InvalidLicensePlateException {
        // Given
        String validPlateNumber = "AB-1234";
        parkingManager.park("STANDARD", validPlateNumber);

        // When
        Car fetchedCar = parkingManager.fetch(validPlateNumber);
        String status = parkingManager.getParkingLotStatus();

        // Then
        assertEquals(validPlateNumber, fetchedCar.plateNumber());
        assertFalse(status.contains("AB-1234"));
        assertFalse(plazaPark.getParkingLotStatus().contains("AB-1234"));
    }

    @Test
    void should_throw_exception_for_invalid_plate_number_when_fetching_given_invalid_plate() {
        // Given
        String invalidPlateNumber = "INV-222222";

        // When & Then
        assertThrows(InvalidLicensePlateException.class, () -> parkingManager.fetch(invalidPlateNumber));
    }

}