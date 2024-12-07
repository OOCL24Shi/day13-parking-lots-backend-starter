// ParkingController.java
package org.afs.pakinglot.domain.controller;

import org.afs.pakinglot.domain.Car;
import org.afs.pakinglot.domain.service.ParkingService;
import org.afs.pakinglot.domain.exception.InvalidLicensePlateException;
import org.afs.pakinglot.domain.service.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parking")
@CrossOrigin(origins = "http://localhost:3000")
public class ParkingController {
    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @PostMapping("/park")
    public ResponseEntity<String> parkCar(@RequestParam String strategy, @RequestParam String plateNumber) {
        try {
            parkingService.parkCar(strategy, plateNumber);
            return ResponseEntity.ok("Car parked successfully.");
        } catch (InvalidLicensePlateException e) {
            return ResponseEntity.badRequest().body("Invalid license plate.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while parking the car.");
        }
    }

    @PostMapping("/fetch")
    public ResponseEntity<Car> fetchCar(@RequestParam String plateNumber) {
        try {
            Car car = parkingService.fetchCar(plateNumber);
            return ResponseEntity.ok(car);
        } catch (InvalidLicensePlateException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> getParkingLotStatus() {
        try {
            String status = parkingService.getParkingLotStatus();
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching the parking lot status.");
        }
    }
}