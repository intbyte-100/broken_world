package com.intbyte.bw.gameAPI.graphic.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.intbyte.bw.gameAPI.utils.ExtraData;

public abstract class Layer<T extends ExtraData> extends Group {

    public abstract Layer onCreate(T data);

    public abstract void destroy();

}
