package com.intbyte.bw.engine.callbacks;

import com.badlogic.gdx.utils.Array;

public interface BlockHit {
    Array<BlockHit> callBacks = new Array<>();

    void main(int x, int z, int itemID);
}
