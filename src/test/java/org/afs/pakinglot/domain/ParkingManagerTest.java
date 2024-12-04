package org.afs.pakinglot.domain;

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
            "SMART, STU-5678",
            "SUPER, VWX-9012",
            "STANDARD, ABC-1234"
    })
    void should_park_car_in_lot_based_on_strategy_given_parking_lots(String strategy, String plateNumber) {
        // Given
        cityMallGarage.park(new Car("CAR-1"));
        plazaPark.park(new Car("CAR-2"));
        officeTowerParking.park(new Car("CAR-3"));
        cityMallGarage.park(new Car("CAR-4"));
        plazaPark.park(new Car("CAR-5"));

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
        String plateNumber = "JKL-3456";

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> parkingManager.park(strategy, plateNumber));
        assertEquals("Invalid parking strategy: INVALID", exception.getMessage());
    }
}