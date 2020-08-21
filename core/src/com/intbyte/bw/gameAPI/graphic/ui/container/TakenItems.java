package com.intbyte.bw.gameAPI.graphic.ui.container;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Render;
import com.intbyte.bw.gameAPI.graphic.Graphic;

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
