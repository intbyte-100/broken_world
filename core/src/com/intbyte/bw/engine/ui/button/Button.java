package com.intbyte.bw.engine.ui.button;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Button extends Actor {

    public static interface ButtonSkin{
        Sprite getBaseSprite();
        Sprite getTouchedSprite();
    }
    protected ButtonShape shape;
    protected ButtonSkin skin;
    protected boolean isTouched;
    public Button(ButtonShape shape, ButtonSkin skin) {
        this.shape = shape;
        this.skin = skin;
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isTouched = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isTouched = false;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(!isTouched ? skin.getBaseSprite():skin.getTouchedSprite(),getX(),getY(),getWidth(),getHeight());
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        return super.hit(x, y, touchable) != null&&(shape==null||shape.hit(this, x, y))  ? this : null;
    }
}
