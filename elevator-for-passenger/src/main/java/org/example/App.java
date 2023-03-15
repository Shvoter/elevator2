package org.example;

import org.example.entity.building.Building;
import org.example.entity.building.BuildingImpl;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        final int FIRST_FLOOR = 5;
        final int LAST_FLOOR = 15;
        final int SIZE_OF_ELEVATOR = 5;
        final int NUMBER_OF_PASSENGERS = 15;
        final int NUMBER_OF_TIME_STEPS = 200;

        Building building = new BuildingImpl(FIRST_FLOOR, LAST_FLOOR, SIZE_OF_ELEVATOR, NUMBER_OF_PASSENGERS);
        View view = new View();

        for (int i = 0; i < NUMBER_OF_TIME_STEPS; i++) {
            System.out.println(view.getView(building));
            building.doTimeStep();
        }
    }
}
