package com.intbyte.bw.engine.callbacks;

import com.badlogic.gdx.utils.Array;

public interface TouchOnBlock {
    Array<TouchOnBlock> callBacks = new Array<>();

    void main(float x, float z);
}
