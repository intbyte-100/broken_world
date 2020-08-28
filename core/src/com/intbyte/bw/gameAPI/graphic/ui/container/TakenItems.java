package com.intbyte.bw.gameAPI.graphic.ui.container;

public final class TakenItems extends Container {

    private static TakenItems instance;
    boolean isTaken;

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


}
