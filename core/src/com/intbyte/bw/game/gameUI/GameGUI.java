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
import com.intbyte.bw.gameAPI.graphic.ui.container.Slot;
import com.intbyte.bw.gameAPI.graphic.ui.container.TakenItemsRender;
import com.intbyte.bw.gameAPI.utils.ID;

import static com.intbyte.bw.gameAPI.graphic.TypedValue.APIXEL;

public class GameGUI {
    private final Player player = Player.getPlayer();
    private Label label;
    private int timer;
    private GravityAdapter adapter;
    private Joystick joystick;
    private Array<Item> items = new Array<>();
    private Slot slot, slot2;


    public void main() {

        CallBack.addCallBack(new Initialization() {
            @Override
            public void main() {


                label = new Label(" ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
                label.setFontScale(2.5f);
                adapter = new GravityAdapter();
                adapter.addActor(label);
                adapter.setGravity(GravityAttribute.TOP, GravityAttribute.LEFT);

                Graphic.STAGE.addActor(label);

                slot = new Slot(Slot.SlotSkin.DEFAULT, new Container(128));
                slot.setSize(60 * APIXEL);
                adapter.addActor(slot);
                adapter.setGravity(GravityAttribute.TOP, GravityAttribute.RIGHT);

                Graphic.STAGE.addActor(slot);

                slot2 = new Slot(Slot.SlotSkin.DEFAULT, new Container(128));
                slot2.setSize(60 * APIXEL);
                adapter.addActor(slot2);
                adapter.setGravity(GravityAttribute.TOP, GravityAttribute.RIGHT);
                adapter.tiedTo(GravityAttribute.RIGHT, slot);

                Graphic.STAGE.addActor(slot2);

                joystick = new Joystick(new Texture(Gdx.files.internal("gui/Joystick_0.png")), new Texture(Gdx.files.internal("gui/Joystick_1.png")), 80 * APIXEL);
                joystick.moveBy(APIXEL, 40, 40);

                Graphic.STAGE.addActor(joystick);


                Item.addItem("test", new Item1());
                Item.addItem("test2", new Item1());


                items.add(Item.getItems()[ID.get("item:test")]);

                slot.addItems(Item.newItems("test", 50));
                slot2.addItems(Item.newItems("test", 50));
                TakenItemsRender.setRendering(true);

                adapter.apply();
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

class Item1 extends Item {
    {
        icon = new Texture(Gdx.files.internal("textures/grass.jpg"));
    }

    public Item1() {
        super(0, 64);
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
        return this;
    }
}