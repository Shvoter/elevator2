package org.example.entity.elevator;

import org.example.entity.Direction;

public interface ControlPanel {

    void pushInternButton(int height);

    void pushExternButton(int from, Direction direction);
}
