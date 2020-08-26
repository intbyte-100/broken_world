package com.intbyte.bw.game.gameUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Initialization;
import com.intbyte.bw.gameAPI.callbacks.Render;
import com.intbyte.bw.gameAPI.environment.Item;
import com.intbyte.bw.gameAPI.graphic.Graphic;
import com.intbyte.bw.gameAPI.graphic.GravityAdapter;
import com.intbyte.bw.gameAPI.graphic.GravityAttribute;
import com.intbyte.bw.gameAPI.graphic.ui.Joystick;
import com.intbyte.bw.gameAPI.graphic.ui.container.Container;
import com.intbyte.bw.gameAPI.graphic.ui.container.TakenItemsRender;
import com.intbyte.bw.gameAPI.graphic.ui.container.Slot;
import com.intbyte.bw.gameAPI.utils.ID;

import static com.intbyte.bw.gameAPI.graphic.TypedValue.APIXEL;

public class GameGUI {
    private Label label;
    private int timer;
    private GravityAdapter adapter;
    private final Player player = Player.getPlayer();
    private Joystick joystick;
    private Array<Item> items = new Array<>();
    private Slot slot, slot2;


    public void main() {

        CallBack.addCallBack(new Initialization() {
            @Override
            public void main() {

                slot = new Slot(Slot.SlotSkin.DEFAULT,new Container(128));
                slot.setSize(100);
                slot.setPosition(1000,500);
                slot2 = new Slot(Slot.SlotSkin.DEFAULT,new Container(128));
                slot2.setSize(100);;
                slot2.setPosition(1000+100*APIXEL,500);

                class Item1 extends Item{
                    public Item1(){
                        super(0, 64);
                    }
                    {
                        icon = new Texture(Gdx.files.internal("textures/grass.jpg"));
                    }
                    @Override
                    public byte[] getBytes() {
                        return new byte[0];
                    }

                    @Override
                    public void readBytes(byte[] bytes) {

                    }

                    @Override
                    public Item1 create() {
                       return new Item1();
                    }
                }
                Item.addItem("test", new Item1());

                Item.addItem("test2", new Item1());
                label = new Label(" ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
                label.setFontScale(2.5f);
                adapter = new GravityAdapter(label);
                adapter.setGravity(GravityAttribute.TOP, GravityAttribute.LEFT);
                adapter.setHeight(1, label.getPrefHeight());
                adapter.setWidth(1, label.getPrefWidth());
                Graphic.STAGE.addActor(adapter.apply());


                joystick = new Joystick(new Texture(Gdx.files.internal("gui/Joystick_0.png")), new Texture(Gdx.files.internal("gui/Joystick_1.png")), 80 * APIXEL);
                joystick.moveBy(APIXEL, 40, 40);

                Graphic.STAGE.addActor(joystick);

                items.add(Item.getItems()[ID.get("item:test")]);
                slot.addItems(Item.newItems("test",50));
                slot2.addItems(Item.newItems("test",60));
                Graphic.STAGE.addActor(slot);
                Graphic.STAGE.addActor(slot2);
                TakenItemsRender.setRendering(true);


            }
        });

        CallBack.addCallBack(new Render() {
            @Override
            public void main() {
                label.setText("fps: " + Gdx.graphics.getFramesPerSecond() + "; player position: x = " + (int) player.getXOnBlock() + ", z = " + (int) player.getZOnBlock());
                timer++;
                if (timer == 60) {
                    items.clear();
                    items.add(Item.getItems()[ID.get("item:test2")]);
                    player.getCarriedItem().moveItems(items);
                    timer = -60;
                } else if (timer == 0) {
                    items.clear();
                    items.add(Item.getItems()[ID.get("item:test")]);
                    player.getCarriedItem().moveItems(items);
                }
            }
        });
    }
}
