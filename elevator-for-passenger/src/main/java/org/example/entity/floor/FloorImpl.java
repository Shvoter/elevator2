package org.example.entity.floor;

import org.example.entity.Door;
import org.example.entity.elevator.ControlPanel;
import org.example.entity.elevator.NeededState;
import org.example.entity.passenger.Passenger;

import java.util.ArrayList;
import java.util.List;

public class FloorImpl implements Floor {
    private final int height;
    private List<Passenger> passengers;
    private List<Passenger> newPassengers;

    public FloorImpl(int height) {
        this.height = height;
        passengers = new ArrayList<>();
        newPassengers = new ArrayList<>();
    }

    @Override
    public <E extends NeededState & Door & ControlPanel> void doPassengersGoToElevator(E elevator) {
        List<Passenger> passengersToInvoke = new ArrayList<>(this.passengers);
        for (Passenger passenger : passengersToInvoke) {
            passenger.goToElevatorFromFloor(elevator, this);
        }
    }

    @Override
    public void doPassengersFillExternPanel(ControlPanel controlPanel) {
        passengers.addAll(newPassengers);
        passengers.forEach(passenger -> passenger.fillExternPanel(controlPanel));
        newPassengers.clear();
    }

    @Override
    public void doPassengerGoInto(Passenger passenger) {
        newPassengers.add(passenger);
    }

    @Override
    public void doPassengerGoOut(Passenger passenger) {
        passengers.remove(passenger);
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public List<Passenger> getPassengers() {
        return passengers;
    }
}
