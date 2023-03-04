package org.example;

import org.example.entity.Direction;
import org.example.entity.building.Building;
import org.example.entity.elevator.Elevator;
import org.example.entity.floor.Floor;
import org.example.entity.passenger.Passenger;

import java.util.List;

public class ViewAsString {
    private int numberOfStep;
    Building building;

    public ViewAsString() {
        numberOfStep = 1;
    }

    public String getViewOfBuilding(Building building) {
        StringBuilder viewBuilder = new StringBuilder();
        viewBuilder
                .append("Step ")
                .append(numberOfStep)
                .append(" -----------------------------------------------------------\n\n");
        numberOfStep++;

        building.getFloors().forEach(floor -> addViewOfFloor(viewBuilder, floor, building.getElevator()));



        return viewBuilder.toString();
    }

    private void addViewOfFloor(StringBuilder stringBuilder, Floor floor, Elevator elevator) {
        stringBuilder.append(floor.getHeight()).append(" ");
        addViewOfDirection(
                stringBuilder,
                elevator.getExternalPanelRequestByHeightOfFloor(floor.getHeight())
        );

        stringBuilder.append(" [ ");

        addViewOfPassengers(stringBuilder, floor.getPassengers());
        stringBuilder.append(" ]");


        if (floor.getHeight() == elevator.getHeight()) {
            addViewOfElevator(stringBuilder, elevator);
        }
        stringBuilder.append("\n");
    }

    private void addViewOfDirection(StringBuilder stringBuilder, Direction direction) {
        if (direction == null) {
            return;
        }
        switch (direction) {
            case UP:
                stringBuilder.append("v");
                break;
            case DOWN:
                stringBuilder.append("^");
                break;
            case BOTH:
                stringBuilder.append("^v");

        }
    }

    private void addViewOfPassengers(StringBuilder stringBuilder, List<Passenger> passengers) {
        passengers.forEach(passenger -> addViewOfPassenger(stringBuilder, passenger));
    }

    private void addViewOfPassenger(StringBuilder stringBuilder, Passenger passenger) {
        stringBuilder
                .append(passenger.getId())
                .append("_(")
                .append(passenger.getNextHeight())
                .append(") ");
    }

    private void addViewOfElevator(StringBuilder stringBuilder, Elevator elevator) {
        stringBuilder
                .append("{");
        addViewOfPassengers(stringBuilder, elevator.getPassengers());
        stringBuilder.append("}");
        addViewOfDirection(stringBuilder, elevator.getDirection());
    }


}
