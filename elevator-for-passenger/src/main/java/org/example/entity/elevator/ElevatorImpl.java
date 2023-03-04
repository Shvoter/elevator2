package org.example.entity.elevator;

import org.example.entity.Direction;
import org.example.entity.Door;
import org.example.entity.building.RangeOfHeight;
import org.example.entity.passenger.Passenger;

import java.util.*;

public class ElevatorImpl implements Elevator {
    private final int size;
    private int height;
    private List<Passenger> passengers;
    NavigableSet<Integer> internalPanelRequests;
    NavigableMap<Integer, Direction> externalPanelRequests;
    private Direction direction;

    public ElevatorImpl(int size, RangeOfHeight building) {
        this.size = size;
        this.height = building.getMinHeight();
        this.passengers = new ArrayList<>();
        this.internalPanelRequests = new TreeSet<>();
        this.externalPanelRequests = new TreeMap<>();
        this.direction = Direction.NONE;
    }

    @Override
    public void doPassengersGoToFloor(Door toFloor) {
        List<Passenger> passengersToInvoke = new ArrayList<>(this.passengers);
        for (Passenger passenger : passengersToInvoke) {
            passenger.goToFloorFromElevator(toFloor, this);
        }
        internalPanelRequests.remove(height);
    }

    @Override
    public void move() {
        externalPanelRequests.remove(height);
        if (internalPanelRequests.isEmpty() && externalPanelRequests.isEmpty()) {
            return;
        }

        if (internalPanelRequests.isEmpty() && externalPanelRequests.get(height) == null) {
            if (externalPanelRequests.lastKey() < height) {
                direction = Direction.DOWN;
            } else {
                direction = Direction.UP;
            }
        }

        if (direction == Direction.UP) {
            moveUp();
        } else if (direction == Direction.DOWN) {
            moveDown();
        } else if (direction == Direction.NONE) {
            direction = Direction.UP;
        }
    }

    private void moveUp() {
        height++;
    }

    private void moveDown() {
        height--;
    }

    @Override
    public void pushInternButton(int height) {
        if (internalPanelRequests.isEmpty()) {
            direction = height > this.height ? Direction.UP : Direction.DOWN;
        }
        internalPanelRequests.add(height);
    }

    @Override
    public void pushExternButton(int from, Direction direction) {
        if (externalPanelRequests.get(from) == null) {
            externalPanelRequests.put(from, direction);
        } else if (externalPanelRequests.get(from) != direction) {
            externalPanelRequests.put(from, Direction.BOTH);
        }
    }

    @Override
    public boolean isDirectionOfMove(Direction direction) {
        if (this.direction == direction || this.direction == Direction.NONE) {
            return true;
        }

        if (internalPanelRequests.isEmpty()) {
            if (this.direction == Direction.UP) {
                return externalPanelRequests.lastKey() == height;
            } else if (this.direction == Direction.DOWN) {
                return externalPanelRequests.firstKey() == height;
            }
        }
        return false;

    }

    @Override
    public boolean isHasPlace() {
        return size > passengers.size();
    }

    @Override
    public void doPassengerGoInto(Passenger passenger) {
        passengers.add(passenger);
    }

    @Override
    public void doPassengerGoOut(Passenger passenger) {
        passengers.remove(passenger);
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public List<Passenger> getPassengers() {
        return passengers;
    }

    @Override
    public Direction getExternalPanelRequestByHeightOfFloor(int height) {
        return externalPanelRequests.get(height);
    }

    @Override
    public int getSize() {
        return size;
    }
}
