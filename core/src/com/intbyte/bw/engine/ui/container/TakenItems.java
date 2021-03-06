package com.intbyte.bw.engine.ui.container;

import com.intbyte.bw.engine.item.Container;

public final class TakenItems extends Container {

    private static TakenItems instance;
    Container selectItems;
    boolean isTaken;
    boolean isSelect;
    boolean clear;

    private TakenItems() {
        super(9999);
    }

    public static TakenItems getInstance() {
        if (instance == null)
            instance = new TakenItems();
        return instance;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public boolean isSelect() {
        return isSelect;
    }
}
