package com.intbyte.bw.gameAPI.environment;

import com.intbyte.bw.gameAPI.utils.ExtraData;

public interface BlockExtraData extends ExtraData {
    BlockExtraData NOT_DATA = null;

    int getID();

    int getHealth();

    void setHealth(int health);
}
