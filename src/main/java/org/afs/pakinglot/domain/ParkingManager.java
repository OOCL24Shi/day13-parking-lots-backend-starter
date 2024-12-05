package org.afs.pakinglot.domain;

import org.afs.pakinglot.domain.strategies.*;

import java.util.List;
import java.util.stream.Collectors;

public class ParkingManager {
    private List<ParkingLot> parkingLots;
    private ParkingBoy standardParkingBoy;
    private ParkingBoy smartParkingBoy;
    private ParkingBoy superSmartParkingBoy;

    public ParkingManager(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
        this.standardParkingBoy = new ParkingBoy(parkingLots, new SequentiallyStrategy());
        this.smartParkingBoy = new ParkingBoy(parkingLots, new MaxAvailableStrategy());
        this.superSmartParkingBoy = new ParkingBoy(parkingLots, new AvailableRateStrategy());
    }

    public Ticket park(String strategy, String plateNumber) {
        Car car = new Car(plateNumber);
        switch (strategy) {
            case "STANDARD":
                return standardParkingBoy.park(car);
            case "SMART":
                return smartParkingBoy.park(car);
            case "SUPER":
                return superSmartParkingBoy.park(car);
            default:
                throw new IllegalArgumentException("Invalid parking strategy: " + strategy);
        }
    }

    public String getParkingLotStatus() {
        return parkingLots.stream()
                .map(ParkingLot::getParkingLotStatus)
                .collect(Collectors.joining("\n"));
    }
}