package org.afs.pakinglot.domain;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.afs.pakinglot.domain.exception.NoAvailablePositionException;
import org.afs.pakinglot.domain.exception.UnrecognizedTicketException;

public class ParkingLot {
    private int id;
    private String name;
    private final Map<Ticket, Car> tickets = new HashMap<>();

    private static final int DEFAULT_CAPACITY = 10;
    private final int capacity;

    public ParkingLot() {
        this(DEFAULT_CAPACITY);
    }

    public ParkingLot(int capacity) {
        this.capacity = capacity;
    }

    public ParkingLot(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableCapacity() {
        return capacity - tickets.size();
    }

    public Ticket park(Car car) {
        if (isFull()) {
            throw new NoAvailablePositionException();
        }

        Ticket ticket = new Ticket(car.plateNumber(), tickets.size() + 1, this.id);
        tickets.put(ticket, car);
        return ticket;
    }

    public void park(String plateNumber) throws InvalidLicensePlateException {
        if (!LicensePlateValidator.isValid(plateNumber)) {
            throw new InvalidLicensePlateException();
        }
        if (isFull()) {
            throw new NoAvailablePositionException();
        }
        Car car = new Car(plateNumber);
        Ticket ticket = new Ticket(car.plateNumber(), tickets.size() + 1, this.id);
        tickets.put(ticket, car);
    }


    public boolean isFull() {
        return capacity == tickets.size();
    }

    public Car fetch(Ticket ticket) {

        if (!tickets.containsKey(ticket)) {
            throw new UnrecognizedTicketException();
        }

        return tickets.remove(ticket);
    }

    public Car fetch(String plateNumber) throws InvalidLicensePlateException {
        if (!LicensePlateValidator.isValid(plateNumber)) {
            throw new InvalidLicensePlateException();
        }
        Ticket ticket = tickets.keySet().stream()
                .filter(t -> t.plateNumber().equals(plateNumber))
                .findFirst()
                .orElseThrow(UnrecognizedTicketException::new);
        return tickets.remove(ticket);
    }

    public boolean contains(Ticket ticket) {
        return tickets.containsKey(ticket);
    }

    public double getAvailablePositionRate() {
        return getAvailableCapacity() / (double) capacity;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Ticket> getTickets() {
        return tickets.keySet().stream().toList();
    }

    public String getParkingLotStatus() {
        return name + ": " + tickets.values().stream()
                .map(Car::plateNumber)
                .collect(Collectors.toList());
    }
}
