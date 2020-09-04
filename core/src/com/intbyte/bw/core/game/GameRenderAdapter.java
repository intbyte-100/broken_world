package com.intbyte.bw.core.game;

import com.intbyte.bw.gameAPI.callbacks.Render;
import com.intbyte.bw.gameAPI.graphic.Graphic;

import java.util.HashMap;

public class GameRenderAdapter {
    private HashMap<String, Render> renders;
    private Render render;

    public GameRenderAdapter() {
        renders = new HashMap<>();
    }

    public void add(String ratio, Render render) {
        renders.put(ratio, render);
    }

    public void initRender() {
        render = renders.get(Graphic.getWidthRatio() + ":" + Graphic.getHeightRatio());
    }

    public void draw() {
        render.main();
    }
}
