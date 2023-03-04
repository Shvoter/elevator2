package org.example.entity;

import org.example.entity.passenger.Passenger;

public interface Door {

    void doPassengerGoInto(Passenger passenger);

    void doPassengerGoOut(Passenger passenger);
}
