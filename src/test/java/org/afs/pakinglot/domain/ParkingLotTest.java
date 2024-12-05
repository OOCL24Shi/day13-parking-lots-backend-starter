package org.afs.pakinglot.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.afs.pakinglot.domain.exception.NoAvailablePositionException;
import org.afs.pakinglot.domain.exception.UnrecognizedTicketException;
import org.junit.jupiter.api.Test;

class ParkingLotTest {
    @Test
    void should_return_ticket_when_park_given_a_parking_lot_and_a_car() {
        // Given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car(CarPlateGenerator.generatePlate());
        // When
        Ticket ticket = parkingLot.park(car);
        // Then
        assertNotNull(ticket);
    }

    @Test
    void should_return_car_when_fetch_given_a_parking_lot_and_a_ticket() {
        // Given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car(CarPlateGenerator.generatePlate());
        Ticket ticket = parkingLot.park(car);
        // When
        Car fetchedCar = parkingLot.fetch(ticket);
        // Then
        assertEquals(car, fetchedCar);
    }

    @Test
    void should_return_correct_car_when_fetch_twice_given_a_parking_lot_and_two_tickets() {
        // Given
        ParkingLot parkingLot = new ParkingLot();
        Car car1 = new Car(CarPlateGenerator.generatePlate());
        Car car2 = new Car(CarPlateGenerator.generatePlate());
        Ticket ticket1 = parkingLot.park(car1);
        Ticket ticket2 = parkingLot.park(car2);
        // When
        Car fetchedCar1 = parkingLot.fetch(ticket1);
        Car fetchedCar2 = parkingLot.fetch(ticket2);
        // Then
        assertEquals(car1, fetchedCar1);
        assertEquals(car2, fetchedCar2);
    }

    @Test
    void should_return_nothing_with_error_message_when_park_given_a_parking_lot_and_a_car_and_parking_lot_is_full() {
        // Given
        ParkingLot parkingLot = new ParkingLot();
        for (int i = 0; i < parkingLot.getCapacity(); i++) {
            parkingLot.park(new Car(CarPlateGenerator.generatePlate()));
        }
        Car car = new Car(CarPlateGenerator.generatePlate());
        // When
        // Then
        NoAvailablePositionException exception =
            assertThrows(NoAvailablePositionException.class, () -> parkingLot.park(car));
        assertEquals("No available position.", exception.getMessage());
    }

    @Test
    void should_return_nothing_with_error_message_when_fetch_given_a_parking_lot_and_an_unrecognized_ticket() {
        // Given
        ParkingLot parkingLot = new ParkingLot();
        Ticket unrecognizedTicket = new Ticket(CarPlateGenerator.generatePlate(), 1, 1);
        // When
        // Then
        UnrecognizedTicketException exception =
            assertThrows(UnrecognizedTicketException.class, () -> parkingLot.fetch(unrecognizedTicket));
        assertEquals("Unrecognized parking ticket.", exception.getMessage());
    }

    @Test
    void should_return_nothing_with_error_message_when_fetch_given_a_parking_lot_and_a_used_ticket() {
        // Given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car(CarPlateGenerator.generatePlate());
        Ticket ticket = parkingLot.park(car);
        parkingLot.fetch(ticket);
        // When
        // Then
        UnrecognizedTicketException exception =
            assertThrows(UnrecognizedTicketException.class, () -> parkingLot.fetch(ticket));
        assertEquals("Unrecognized parking ticket.", exception.getMessage());
    }


    @Test
    void should_return_ticks_list_when_getTickets_given_a_some_parked_cars() {
        // Given
        ParkingLot parkingLot = new ParkingLot();
        Car car1 = new Car(CarPlateGenerator.generatePlate());
        Car car2 = new Car(CarPlateGenerator.generatePlate());
        Ticket ticket1 = parkingLot.park(car1);
        Ticket ticket2 = parkingLot.park(car2);
        List<Ticket> expectedTickets = List.of(ticket1, ticket2);
        // When
        List<Ticket> tickets = parkingLot.getTickets();
        // Then
        assertNotNull(tickets);
        assertTrue(expectedTickets.containsAll(tickets));
    }

    @Test
    void should_display_status_of_empty_parking_lot() {
        // Given
        ParkingLot parkingLot = new ParkingLot(1, "Plaza Park", 9);

        // When
        String status = parkingLot.getParkingLotStatus();

        // Then
        assertEquals("Plaza Park: []", status);
    }

    @Test
    void should_display_status_of_partially_filled_parking_lot_when_park_given_a_partially_full_park() {
        // Given
        ParkingLot parkingLot = new ParkingLot(1, "Plaza Park", 9);
        parkingLot.park(new Car("AB-1234"));
        parkingLot.park(new Car("DE-5678"));

        // When
        String status = parkingLot.getParkingLotStatus();

        // Then
        assertEquals("Plaza Park: [AB-1234, DE-5678]", status);
    }

    @Test
    void should_display_status_of_fully_filled_parking_lot_when_park_given_one_full_parking_lot() {
        // Given
        ParkingLot parkingLot = new ParkingLot(1, "Plaza Park", 3);
        parkingLot.park(new Car("GH-9012"));
        parkingLot.park(new Car("AB-1234"));
        parkingLot.park(new Car("DE-5678"));

        // When
        String status = parkingLot.getParkingLotStatus();

        // Then
        assertEquals("Plaza Park: [AB-1234, GH-9012, DE-5678]", status);
    }
    //requriment 2

    @Test
    void should_park_car_when_park_given_plate_number() throws InvalidLicensePlateException {
        // Given
        ParkingLot parkingLot = new ParkingLot(1, "Plaza Park", 9);
        String plateNumber = "AB-1234";

        // When
        parkingLot.park(plateNumber);
        String status = parkingLot.getParkingLotStatus();

        // Then
        assertEquals("Plaza Park: [AB-1234]", status);
    }

    @Test
    void should_fetch_correct_car_when_fetch_given_plate_number() throws InvalidLicensePlateException {
        // Given
        ParkingLot parkingLot = new ParkingLot(1, "Plaza Park", 9);
        String plateNumber = "AB-1234";
        parkingLot.park(plateNumber);

        // When
        Car fetchedCar = parkingLot.fetch(plateNumber);
        String status = parkingLot.getParkingLotStatus();

        // Then
        assertEquals(plateNumber, fetchedCar.plateNumber());
        assertEquals("Plaza Park: []", status);
    }

    //requirement4
    @Test
    void should_park_car_with_valid_plate_number_and_update_status_park_given_valid_license_plate() throws InvalidLicensePlateException {
        // Given
        ParkingLot parkingLot = new ParkingLot(1, "Plaza Park", 9);
        String validPlateNumber = "AB-1234";

        // When
        parkingLot.park(validPlateNumber);
        String status = parkingLot.getParkingLotStatus();

        // Then
        assertEquals("Plaza Park: [AB-1234]", status);
    }

    @Test
    void should_throw_exception_for_invalid_plate_number_when_parking_given_lisence_plate_with_invalid_format() {
        // Given
        ParkingLot parkingLot = new ParkingLot(1, "Plaza Park", 9);
        String invalidPlateNumber = "INVALID";

        // When & Then
        assertThrows(InvalidLicensePlateException.class, () -> parkingLot.park(invalidPlateNumber));
    }

    @Test
    void should_fetch_car_and_update_status_when_fetch_given_plate_number_with_valid_format() throws InvalidLicensePlateException {
        // Given
        ParkingLot parkingLot = new ParkingLot(1, "Plaza Park", 9);
        String validPlateNumber = "AB-1234";
        parkingLot.park(validPlateNumber);

        // When
        Car fetchedCar = parkingLot.fetch(validPlateNumber);
        String status = parkingLot.getParkingLotStatus();

        // Then
        assertEquals(validPlateNumber, fetchedCar.plateNumber());
        assertEquals("Plaza Park: []", status);
    }

    @Test
    void should_throw_exception_for_invalid_plate_number_when_fetching_given_plate_with_invalid_format() {
        // Given
        ParkingLot parkingLot = new ParkingLot(1, "Plaza Park", 9);
        String invalidPlateNumber = "INVALID";

        // When & Then
        assertThrows(InvalidLicensePlateException.class, () -> parkingLot.fetch(invalidPlateNumber));
    }
}
