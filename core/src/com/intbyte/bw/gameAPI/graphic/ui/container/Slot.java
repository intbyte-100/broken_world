package com.intbyte.bw.gameAPI.graphic.ui.container;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Slot extends Actor {
    private final SlotSkin slotSkin;
    private final Container container;


    public Slot(SlotSkin skin, final Container container) {
        slotSkin = skin;
        this.container = container;
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                TakenItems takenItems = TakenItems.getInstance();
                if (takenItems.items.isEmpty()) {
                    if (container.items.isEmpty()) {
                        takenItems.isTaken = false;
                        return true;
                    }
                    takenItems.moveItems(container.items);
                    takenItems.isTaken = true;
                }
                else if (takenItems.isTaken){
                    container.moveItems(takenItems.items);
                    if(takenItems.items.isEmpty())
                        takenItems.isTaken = false;
                }
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(slotSkin.getTexture(), getX(), getY(), getWidth(), getHeight());
        if(container.items.notEmpty())
            container.items.get(0).drawIcon(getX()+getWidth()*0.1f,getY()+getHeight()*0.1f,getWidth()*0.8f,getHeight()*0.8f);
    }

    public Container getContainer() {
        return container;
    }

    public static interface SlotSkin {
        SlotSkin DEFAULT = new SlotSkin() {
            private Texture texture = new Texture(Gdx.files.internal("android.jpg"));

            @Override
            public Texture getTexture() {
                return texture;
            }

            @Override
            public Color getCountTextColor() {
                return null;
            }
        };

        Texture getTexture();

        Color getCountTextColor();
    }
}
