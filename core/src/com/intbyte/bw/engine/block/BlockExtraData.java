package com.intbyte.bw.engine.block;

import com.intbyte.bw.engine.utils.ExtraData;

public interface BlockExtraData extends ExtraData {
    BlockExtraData NOT_DATA = null;

    int getID();

    int getHealth();

    void setHealth(int health);
}
