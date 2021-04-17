package com.intbyte.bw.engine.callbacks;

import com.badlogic.gdx.utils.Array;

public interface Render {
    Array<Render> callBacks = new Array<>();

    void main();
}
