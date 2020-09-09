package com.intbyte.bw.gameAPI.graphic.ui;

import com.badlogic.gdx.Gdx;
import com.intbyte.bw.gameAPI.graphic.Graphic;
import com.intbyte.bw.gameAPI.utils.ExtraData;

import java.util.HashMap;

public class GUI {

    private static final HashMap<String, Layer> layers;

    static {
        layers = new HashMap<>();
    }

    public static void putLayer(String key, Layer layer) {
        layers.put(key, layer);
    }

    public static void openLayer(String key, ExtraData extraData) {
        Graphic.STAGE.addActor(layers.get(key).onCreate(extraData));
        Gdx.app.log("GUI","opened layer \""+key+"\"");
    }

    public static void closeLayer(String key) {
        Graphic.STAGE.getActors().removeValue(layers.get(key), true);
        Gdx.app.log("GUI","closed layer \""+key+"\"");
    }

    public static void setLayer(String key, ExtraData extraData) {
        Graphic.STAGE.clear();
        Gdx.app.log("GUI","call \"STAGE.clear()\"");
        openLayer(key, extraData);
    }
}
