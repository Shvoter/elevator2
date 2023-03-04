package org.example.entity.floor;

import org.example.entity.Door;
import org.example.entity.elevator.ControlPanel;
import org.example.entity.Height;
import org.example.entity.PassengerContainer;
import org.example.entity.elevator.NeededState;

public interface Floor extends Height, PassengerContainer, Door, DoFillExternPanel {

    <E extends NeededState & Door & ControlPanel> void doPassengersGoToElevator(E elevator);
}
