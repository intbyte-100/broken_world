package com.intbyte.bw.engine.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.intbyte.bw.engine.utils.ExtraData;

public abstract class Layer extends Group {

    public abstract Layer onCreate(ExtraData data);

    public abstract void destroy();

}
