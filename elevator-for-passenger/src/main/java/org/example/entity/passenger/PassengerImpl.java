package org.example.entity.passenger;

import org.example.entity.Direction;
import org.example.entity.Door;
import org.example.entity.Height;
import org.example.entity.building.RangeOfHeight;
import org.example.entity.elevator.ControlPanel;
import org.example.entity.elevator.NeededState;
import org.example.entity.floor.DoFillExternPanel;

import java.util.List;

public class PassengerImpl implements Passenger {

    private static int NEXT_ID = 0;
    private final int id;
    private final RangeOfHeight building;
    private int heightOfCurrentFloor;
    private int heightOfNextFloor;
    private Direction neededDirection;

    public <E extends ControlPanel, F extends Height & DoFillExternPanel & Door> PassengerImpl
            (RangeOfHeight building, E elevator, List<F> floors) {
        this.id = NEXT_ID++;
        this.building = building;
        this.heightOfCurrentFloor = getRandomNumber(building.getMinHeight(), building.getMaxHeight());
        setRandomHeightOfNextFloor();
        setNeededDirection();
        F floorOfPassenger = floors.stream().filter(floor -> floor.getHeight() == heightOfCurrentFloor).findFirst().get();
        goIntoFloor(floorOfPassenger);
        floorOfPassenger.doPassengersFillExternPanel(elevator);
    }

    @Override
    public void fillExternPanel(ControlPanel elevator) {
        elevator.pushExternButton(heightOfCurrentFloor, neededDirection);
    }

    @Override
    public <E extends NeededState & Door & ControlPanel> void goToElevatorFromFloor(E toElevator, Door fromFloor) {
        if (toElevator.isHasPlace() && toElevator.isDirectionOfMove(neededDirection)) {
            fromFloor.doPassengerGoOut(this);
            toElevator.doPassengerGoInto(this);
            toElevator.pushInternButton(heightOfNextFloor);
        }
    }

    @Override
    public <E extends Height & Door> void goToFloorFromElevator(Door toFloor, E fromElevator) {
        if (fromElevator.getHeight() == heightOfNextFloor) {
            fromElevator.doPassengerGoOut(this);
            goIntoFloor(toFloor);
            heightOfCurrentFloor = heightOfNextFloor;
            setRandomHeightOfNextFloor();
            setNeededDirection();
        }
    }

    @Override
    public int getNextHeight() {
        return heightOfNextFloor;
    }

    @Override
    public int getHeight() {
        return heightOfCurrentFloor;
    }

    @Override
    public int getId() {
        return id;
    }

    private void goIntoFloor(Door floor) {
        floor.doPassengerGoInto(this);
    }

    private void setNeededDirection() {
        neededDirection = heightOfNextFloor - heightOfCurrentFloor > 0? Direction.UP : Direction.DOWN;
    }

    private void setRandomHeightOfNextFloor() {
        do {
            heightOfNextFloor = getRandomNumber(building.getMinHeight(), building.getMaxHeight());
        } while (heightOfNextFloor - heightOfCurrentFloor == 0);
    }

    private int getRandomNumber(int minInclusive, int maxInclusive) {
        return (int) (Math.random() * (maxInclusive - minInclusive + 1)) + minInclusive;
    }
}
