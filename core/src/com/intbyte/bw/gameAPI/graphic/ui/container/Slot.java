package com.intbyte.bw.gameAPI.graphic.ui.container;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.intbyte.bw.gameAPI.environment.Item;
import com.intbyte.bw.gameAPI.graphic.Graphic;

public class Slot extends Actor {
    private final SlotSkin slotSkin;
    private final Container container;
    private final static BitmapFont font = new BitmapFont();
    private float itemSize;

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
        if(container.items.notEmpty()) {
            container.items.get(0).drawIcon(getX()+getWidth()*0.1f,getY()+getHeight()*0.1f,itemSize,itemSize);
            font.getData().setScale((18/(18/(getHeight()*0.2f)))/10);
            font.setColor(slotSkin.getCountTextColor());
            String value = String.valueOf(container.getCountItems());
            font.draw(Graphic.BATCH, value, getX() + getWidth() * 0.8f - (font.getXHeight() * value.length() - font.getXHeight()), getY() + font.getLineHeight() * 0.9f);
        }
    }

    public void addItems(Item... items){
        container.addItems(items);
    }
    @Override
    @Deprecated
    public void setHeight(float height) {
        throw new RuntimeException("Class Slot don't support a method setHeight");
    }

    @Override
    @Deprecated
    public void setWidth(float width) {
        throw new RuntimeException("Class Slot don't support a method setWidth");
    }

    @Override
    @Deprecated
    public void setSize(float width, float height) {
        throw new RuntimeException("Class Slot don't support a method setSize(float width, float height)");
    }

    public void setSize(float size){
        super.setSize(size,size);
        itemSize = size*0.8f;
    }
    public Container getContainer() {
        return container;
    }

    public static interface SlotSkin {
        SlotSkin DEFAULT = new SlotSkin() {
            private final Texture texture = new Texture(Gdx.files.internal("android.jpg"));

            @Override
            public Texture getTexture() {
                return texture;
            }

            @Override
            public Color getCountTextColor() {
                return Color.WHITE;
            }
        };

        Texture getTexture();

        Color getCountTextColor();
    }
}
