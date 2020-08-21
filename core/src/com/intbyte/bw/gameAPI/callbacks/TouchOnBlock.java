package com.intbyte.bw.gameAPI.callbacks;

import java.util.ArrayList;

public abstract class TouchOnBlock {
    static ArrayList<TouchOnBlock> callBacks = new ArrayList<>();
    abstract public void main(int x, int z);
}
