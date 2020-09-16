package com.intbyte.bw.gameAPI.callbacks;

import com.badlogic.gdx.utils.Array;

public interface TouchOnBlock {
    Array<TouchOnBlock> callBacks = new Array<>();

    void main(int x, int z);
}
