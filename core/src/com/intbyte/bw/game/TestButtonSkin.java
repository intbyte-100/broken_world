package com.intbyte.bw.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.intbyte.bw.engine.ui.button.Button;
import com.intbyte.bw.engine.utils.Resource;

public class TestButtonSkin implements Button.ButtonSkin {
    Sprite base = Resource.getSprite("gui/Joystick_0.png"), touch = Resource.getSprite("gui/Joystick_1.png");
    @Override
    public Sprite getBaseSprite() {
        return base;
    }

    @Override
    public Sprite getTouchedSprite() {
        return touch;
    }
}
