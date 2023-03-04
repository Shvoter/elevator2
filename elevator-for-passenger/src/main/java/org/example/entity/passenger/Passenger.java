package org.example.entity.passenger;

import org.example.entity.Door;
import org.example.entity.Height;
import org.example.entity.elevator.ControlPanel;
import org.example.entity.elevator.NeededState;

public interface Passenger extends Height {

    void fillExternPanel(ControlPanel controlPanel);

    <E extends NeededState & Door & ControlPanel> void goToElevatorFromFloor(E toElevator, Door fromFloor);

    <E extends Height & Door> void goToFloorFromElevator(Door toFloor, E fromElevator);

    int getNextHeight();

    int getId();
}
