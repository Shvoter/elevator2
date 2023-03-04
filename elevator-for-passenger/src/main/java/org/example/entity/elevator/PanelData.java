package org.example.entity.elevator;

import org.example.entity.Direction;

public interface PanelData {

    Direction getExternalPanelRequestByHeightOfFloor(int height);
}
