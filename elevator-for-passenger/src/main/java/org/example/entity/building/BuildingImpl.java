package org.example.entity.building;

import org.example.entity.elevator.Elevator;
import org.example.entity.elevator.ElevatorImpl;
import org.example.entity.floor.Floor;
import org.example.entity.floor.FloorImpl;
import org.example.entity.passenger.PassengerImpl;

import java.util.ArrayList;
import java.util.List;

public class BuildingImpl implements Building {
    private final int minHeight;
    private final int maxHeight;
    private Elevator elevator;
    private List<Floor> floors;

    public BuildingImpl(int minHeight, int maxHeight, int sizeOfElevator, int numberOfPassenger) {
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        floors = new ArrayList<>();

        for (int i = minHeight; i <= maxHeight; i++) {
            floors.add(new FloorImpl(i));
        }

        elevator = new ElevatorImpl(sizeOfElevator, this);
        for (int i = 0; i < numberOfPassenger; i++) {
            new PassengerImpl(this, elevator, floors);
        }
    }


    @Override
    public void doTimeStep() {
        int heightOfElevator = elevator.getHeight();
        Floor currentFloor = floors.stream().filter(floor -> floor.getHeight() == heightOfElevator).findFirst().get();
        elevator.doPassengersGoToFloor(currentFloor);
        currentFloor.doPassengersGoToElevator(elevator);
        elevator.move();
        currentFloor.doPassengersFillExternPanel(elevator);
    }

    @Override
    public List<Floor> getFloors() {
        return floors;
    }

    @Override
    public Elevator getElevator() {
        return elevator;
    }

    @Override
    public int getMinHeight() {
        return minHeight;
    }

    @Override
    public int getMaxHeight() {
        return maxHeight;
    }
}
