package org.afs.pakinglot.domain.config;

import org.afs.pakinglot.domain.ParkingLot;
import org.afs.pakinglot.domain.ParkingManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ParkingManagerConfig {

    @Bean
    public ParkingManager parkingManager() {
        ParkingLot plazaPark = new ParkingLot(1, "Plaza Park", 9);
        ParkingLot cityMallGarage = new ParkingLot(2, "City Mall Garage", 12);
        ParkingLot officeTowerParking = new ParkingLot(3, "Office Tower Parking", 9);

        return new ParkingManager(List.of(plazaPark, cityMallGarage, officeTowerParking));
    }
}