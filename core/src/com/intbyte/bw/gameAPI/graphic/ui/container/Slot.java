package com.intbyte.bw.gameAPI.graphic.ui.container;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.intbyte.bw.gameAPI.environment.Container;
import com.intbyte.bw.gameAPI.environment.Item;
import com.intbyte.bw.gameAPI.graphic.Graphic;
import com.intbyte.bw.gameAPI.utils.Resource;

public class Slot extends Actor {
    private final static BitmapFont font = new BitmapFont();
    private boolean touchedDown;
    private final static Vector2 touchPosition = new Vector2(), oldPosition = new Vector2();
    private final SlotSkin slotSkin;
    private Container container;
    private float itemSize;
    private final TakenItems takenItems = TakenItems.getInstance();
    private boolean isSelect;
    private boolean drag;

    public Slot(){
        this(64);
    }
    public Slot(int maxCountItems){
        this(new Container(maxCountItems));
    }

    public Slot(Container container){
        this(SlotSkin.DEFAULT,container);
    }
    public Slot(SlotSkin skin, Container container) {
        slotSkin = skin;
        this.container = container;
        addListener(new InputListener() {

            boolean take() {
                if (Slot.this.container.getItems().isEmpty()) {
                    takenItems.isTaken = false;
                    takenItems.isSelect = false;
                    return false;
                }

                takenItems.moveItems(Slot.this.container.getItems());
                return true;
            }

            void put() {
                Slot.this.container.moveItems(takenItems.getItems());
                if (takenItems.getItems().isEmpty()) {
                    takenItems.isTaken = false;
                    takenItems.isSelect = false;
                    isSelect = false;
                }
            }

            void swap() {
                if (takenItems.isSelect && takenItems.selectItems != null && !isSelect) {


                    Slot.this.container.moveItems(takenItems.selectItems);
                    takenItems.selectItems = null;
                    takenItems.clear = true;
                    drag = false;
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                touchedDown = false;
                if (hit(x, y, true) == null) return;
                drag = true;

                swap();

                if(!Slot.this.container.getItems().isEmpty()) {
                    if (isSelect = !isSelect) {
                        takenItems.isSelect = isSelect;
                        takenItems.selectItems = Slot.this.container;
                    }
                    if (takenItems.isTaken || !takenItems.isSelect) {
                        takenItems.isSelect = false;
                        isSelect = false;
                    }
                }

                if (drag && (!isSelect))
                    if (takenItems.getItems().isEmpty()) {
                        if (take()) {
                            takenItems.isTaken = true;
                        }
                    } else
                        put();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(takenItems.isTaken||!takenItems.isSelect) return true;
                touchedDown = true;
                touchPosition.set(x, y);
                oldPosition.set(touchPosition);
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if(touchedDown&&touchPosition.add(-x,-y).len()>getHeight()/2){
                    System.out.println(10000);
                } else
                    touchPosition.set(oldPosition);
            }
        });
    }

    public void setContainer(Container container){
        this.container = container;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (takenItems.isTaken || !takenItems.isSelect || takenItems.clear) {
            isSelect = false;
            takenItems.clear = false;
            takenItems.isSelect = false;
        }
        batch.draw(isSelect ? slotSkin.getSelectedTexture() : slotSkin.getSprite(), getX(), getY(), getWidth(), getHeight());
        if (container.getItems().notEmpty()) {
            container.getItems().get(0).drawIcon(getX() + getWidth() * 0.1f, getY() + getHeight() * 0.1f, itemSize, itemSize);
            font.getData().setScale((18 / (18 / (getHeight() * 0.2f))) / 10);
            font.setColor(slotSkin.getCountTextColor());
            String value = String.valueOf(container.getCountItems());
            font.draw(Graphic.BATCH, value, getX() + getWidth() * 0.8f - (font.getXHeight() * value.length() - font.getXHeight()), getY() + font.getLineHeight() * 0.9f);
        }
    }

    public void addItems(Array<Item> items) {
        container.addItems(items);
    }

    @Override
    @Deprecated
    public void setHeight(float height) {
        throw new RuntimeException("Class Slot don't support the method setHeight");
    }

    @Override
    @Deprecated
    public void setWidth(float width) {
        throw new RuntimeException("Class Slot don't support the method setWidth");
    }

    @Override
    @Deprecated
    public void setSize(float width, float height) {
        throw new RuntimeException("Class Slot don't support the method setSize(float width, float height)");
    }

    public void setSize(float size) {
        super.setSize(size, size);
        itemSize = size * 0.8f;
    }

    public Container getContainer() {
        return container;
    }

    public interface SlotSkin {
        SlotSkin DEFAULT = new SlotSkin() {
            private final Sprite texture = Resource.getSprite("testPanel"),
                    selected = Resource.getSprite("selected_slot.png");

            @Override
            public Sprite getSprite() {
                return texture;
            }

            @Override
            public Color getCountTextColor() {
                return Color.WHITE;
            }

            @Override
            public Sprite getSelectedTexture() {
                return selected;
            }
        };

        Sprite getSprite();

        Color getCountTextColor();

        Sprite getSelectedTexture();
    }
}
