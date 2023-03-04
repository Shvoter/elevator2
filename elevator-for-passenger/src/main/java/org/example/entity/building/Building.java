package org.example.entity.building;

import org.example.entity.elevator.Elevator;
import org.example.entity.floor.Floor;

import java.util.List;

public interface Building extends RangeOfHeight{

    void doTimeStep();

    List<Floor> getFloors();

    Elevator getElevator();
}
