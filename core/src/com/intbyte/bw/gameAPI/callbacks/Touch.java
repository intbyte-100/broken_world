package com.intbyte.bw.gameAPI.callbacks;

import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public abstract class Touch {
    static ArrayList<Touch> callBacks = new ArrayList<>();
    abstract public void main(Vector3 position);
}
