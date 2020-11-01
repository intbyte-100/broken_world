package com.intbyte.bw.gameAPI.graphic.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.intbyte.bw.gameAPI.utils.ExtraData;

public abstract class Layer extends Group {

    public abstract Layer onCreate(ExtraData data);

    public abstract void destroy();

}
