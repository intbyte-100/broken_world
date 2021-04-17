package com.intbyte.bw.game.gameUI;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.intbyte.bw.engine.ui.ProgressBar;
import com.intbyte.bw.engine.utils.Resource;

public class TestProgressBarSkin implements ProgressBar.ProgressBarSkin {
    Sprite sprite = Resource.getSprite("android.jpg");
    @Override
    public Sprite getBaseSprite() {
        return sprite;
    }

    @Override
    public Sprite getProgressSprite() {
        return sprite;
    }
}
