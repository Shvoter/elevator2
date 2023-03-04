package org.example;

import org.example.entity.building.Building;
import org.example.entity.building.BuildingImpl;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        Building building = new BuildingImpl(1, 5, 5, 15);
        View view = new View();
        for (int i = 0; i < 1000; i++) {
            System.out.println(view.getView(building));
            building.doTimeStep();
        }
        System.out.println(view.getView(building));
    }
}
