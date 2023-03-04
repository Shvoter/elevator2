package org.example;

import org.example.entity.Direction;
import org.example.entity.building.Building;
import org.example.entity.elevator.Elevator;
import org.example.entity.floor.Floor;
import org.example.entity.passenger.Passenger;

import java.util.List;
import java.util.ListIterator;

public class View {
    int numberOfStep;

    public View() {
        this.numberOfStep = 1;
    }

    public String getView(Building building) {
        StringBuilder viewBuilder = new StringBuilder();
        viewBuilder
                .append("Step ")
                .append(numberOfStep)
                .append(" -----------------------------------------------------------\n\n");
        numberOfStep++;

        ListIterator<Floor> iterator = building.getFloors().listIterator(building.getFloors().size());
        while (iterator.hasPrevious()) {
            Floor floor = iterator.previous();
            viewBuilder
                    .append(getViewOfFloor(
                            floor,
                            getMaxCountOfPeopleOnFloor(building.getFloors()),
                            building.getElevator().getExternalPanelRequestByHeightOfFloor(floor.getHeight())
                    ));
            if (floor.getHeight() == building.getElevator().getHeight()) {
                viewBuilder.append(getViewOfElevator(building.getElevator()));
            }
            viewBuilder.append("\n");
        }
        return viewBuilder.toString();
    }

    private StringBuilder getViewOfElevator(Elevator elevator) {
        return new StringBuilder()
                .append("{")
                .append(getViewOfAllPassengers(elevator.getPassengers(), elevator.getSize()))
                .append("}")
                .append(getViewOfDirection(elevator.getDirection()));
    }

    private StringBuilder getViewOfFloor(Floor floor, int maxCountOfPeopleOnFloor, Direction direction) {
        StringBuilder viewOfFloorBuilder = new StringBuilder();

        viewOfFloorBuilder
                .append(floor.getHeight())
                .append(" ")
                .append(getViewOfDirection(direction))
                .append("[")
                .append(getViewOfAllPassengers(floor.getPassengers(), maxCountOfPeopleOnFloor))
                .append("]");

        return viewOfFloorBuilder;
    }

    private StringBuilder getViewOfAllPassengers(List<Passenger> passengers, int maxCountOfPeople) {
        int maxSizeOfViewOfPassenger = 7;
        int maxSizeOfViewOfAllPassengers = maxSizeOfViewOfPassenger * maxCountOfPeople;

        StringBuilder viewOfAllPassengers = new StringBuilder();

        ListIterator<Passenger> iterator = passengers.listIterator(passengers.size());
        while (iterator.hasPrevious()) {
            viewOfAllPassengers
                    .append(getViewOfPassenger(iterator.previous()))
                    .append(" ");
        }
        return getViewCastedToMaxSize(viewOfAllPassengers, maxSizeOfViewOfAllPassengers);
    }

    private StringBuilder getViewCastedToMaxSize(StringBuilder view, int maxSizeOfViewOfAllPassengers) {
        for (int i = view.length(); i < maxSizeOfViewOfAllPassengers; i++) {
            view.append(" ");
        }
        return view;
    }

    private StringBuilder getViewOfPassenger(Passenger passenger) {
        return new StringBuilder()
                .append(passenger.getId())
                .append("_(")
                .append(passenger.getNextHeight())
                .append(")");
    }

    private StringBuilder getViewOfDirection(Direction direction) {
        StringBuilder viewOfDirectionBuilder = new StringBuilder("  ");

        if (direction != null) {
            switch (direction) {
                case UP:
                    viewOfDirectionBuilder = new StringBuilder("^ ");
                    break;
                case DOWN:
                    viewOfDirectionBuilder = new StringBuilder(" v");
                    break;
                case BOTH:
                    viewOfDirectionBuilder = new StringBuilder("^v");
                    break;
            }
        }
        return viewOfDirectionBuilder;
    }

    private int getMaxCountOfPeopleOnFloor(List<Floor> floors) {
        int maxSizeOfFloor = 0;

        for (Floor floor : floors) {
            int sizeCurrentFloor = floor.getPassengers().size();
            if (maxSizeOfFloor < sizeCurrentFloor) maxSizeOfFloor = sizeCurrentFloor;
        }
        return maxSizeOfFloor;
    }
}
