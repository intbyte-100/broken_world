package com.intbyte.bw.gameAPI.graphic.ui.button;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.intbyte.bw.gameAPI.utils.Resource;

public class Button extends Actor {

    ButtonShape shape;

    public Button(ButtonShape shape) {
        this.shape = shape;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(Resource.getSprite("gui/Joystick_0.png"),getX(),getY(),getWidth(),getHeight());
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        return super.hit(x, y, touchable) != null&&(shape==null||shape.hit(this, x, y))  ? this : null;
    }
}
