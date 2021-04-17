package com.intbyte.bw.engine.callbacks;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public interface Touch {
    Array<Touch> callBacks = new Array<>();

    void main(Vector3 position);
}
