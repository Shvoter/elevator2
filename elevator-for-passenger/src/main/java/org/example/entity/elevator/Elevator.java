package org.example.entity.elevator;

import org.example.entity.Door;
import org.example.entity.Height;
import org.example.entity.PassengerContainer;

public interface Elevator extends Height, PassengerContainer, Door, ControlPanel, PanelData, DirectionData, NeededState {

    void move();

    void doPassengersGoToFloor(Door toFloor);

    int getSize();

}
