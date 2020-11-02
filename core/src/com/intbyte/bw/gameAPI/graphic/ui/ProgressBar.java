package com.intbyte.bw.gameAPI.graphic.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ProgressBar extends Actor {
    ProgressBarSkin skin;
    private float state = 0;

    public ProgressBar(ProgressBarSkin skin) {
        this.skin = skin;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(skin.getBaseSprite(), getX(), getY(), getWidth(), getHeight());

        batch.setColor(1, 0, 0, 1);
        skin.getProgressSprite().setRegion(0f, 0f, getState() / 100, 1f);
        if (getState() > 0)
            batch.draw(skin.getProgressSprite(), getX(), getY(), getWidth() * getState() / 100, getHeight());
        batch.setColor(1, 1, 1, 1);
        skin.getProgressSprite().setRegion(0f, 0f, 1, 1f);
    }

    public float getState() {
        return state;
    }

    public void setState(float state) {
        this.state = state;
    }

    public static interface ProgressBarSkin {
        Sprite getBaseSprite();

        Sprite getProgressSprite();
    }
}
