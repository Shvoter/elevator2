package org.example.entity.elevator;

import org.example.entity.Direction;

public interface NeededState {

    boolean isDirectionOfMove(Direction direction);

    boolean isHasPlace();
}
