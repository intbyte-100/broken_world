package com.intbyte.bw.gameAPI.graphic.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.intbyte.bw.gameAPI.graphic.Graphic;
import com.intbyte.bw.gameAPI.utils.ExtraData;

import java.util.HashMap;

public class GUI {

    private static final HashMap<String, Layer> layers;
    private static String layerName;
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
        Layer layer = layers.get(key);
        Graphic.STAGE.getActors().removeValue(layers.get(key), true);
        layer.destroy();
        Gdx.app.log("GUI","closed layer \""+key+"\"");
    }

    public static void setLayer(String key, ExtraData extraData) {

        for (Actor i: Graphic.STAGE.getActors()){
            if(i instanceof Layer)
                ((Layer) i).destroy();
        }
        Graphic.STAGE.clear();
        Gdx.app.log("GUI","call \"STAGE.clear()\"");
        openLayer(key, extraData);
        layerName = key;
    }

    public static boolean isOpen(String key){
        return key.equals(layerName);
    }
}
