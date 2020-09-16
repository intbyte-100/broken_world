package com.intbyte.bw.gameAPI.callbacks;

import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public abstract class Initialization {
    static Array<Initialization> callBacks = new Array<>();
    static Iterator<Initialization> iterator = null;

    abstract public void main();
}
