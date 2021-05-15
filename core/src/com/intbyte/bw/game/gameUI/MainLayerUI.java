package com.intbyte.bw.game.gameUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.intbyte.bw.engine.callbacks.CallBack;
import com.intbyte.bw.engine.callbacks.Render;
import com.intbyte.bw.engine.entity.Player;
import com.intbyte.bw.engine.graphic.GravityAdapter;
import com.intbyte.bw.engine.graphic.GravityAttribute;
import com.intbyte.bw.engine.input.InteractionOfItems;
import com.intbyte.bw.engine.item.Container;
import com.intbyte.bw.engine.item.Item;
import com.intbyte.bw.engine.ui.GUI;
import com.intbyte.bw.engine.ui.Joystick;
import com.intbyte.bw.engine.ui.Layer;
import com.intbyte.bw.engine.ui.ProgressBar;
import com.intbyte.bw.engine.ui.container.Slot;
import com.intbyte.bw.engine.ui.container.TakenItemsRender;
import com.intbyte.bw.engine.utils.Debug;
import com.intbyte.bw.engine.utils.ExtraData;
import com.intbyte.bw.engine.utils.Resource;
import com.intbyte.bw.engine.world.World;

import static com.intbyte.bw.engine.graphic.TypedValue.APIXEL;
import static com.intbyte.bw.engine.graphic.TypedValue.HHPIXEL;

public class MainLayerUI extends Layer {


    private final Player player = Player.getPlayer();
    private final Label label;


    public MainLayerUI() {
        final GravityAdapter adapter = new GravityAdapter();
        final StringBuilder builder = new StringBuilder();

        CallBack.addCallBack(new Render() {
            @Override
            public void main() {

                for (int i = 0; i < Debug.getDebugMessages().size(); i++) {
                    builder.append(Debug.getDebugMessages().get(i));
                    builder.append("\n");
                }
                label.setText(builder);
                builder.setLength(0);
                adapter.addActor(label);
                adapter.setHeight(label.getPrefHeight());
                adapter.setGravity(GravityAttribute.TOP, GravityAttribute.LEFT);
                adapter.margin(10 * APIXEL, 0);
                adapter.apply();
            }
        });


        Slot slot = new Slot(Slot.SlotSkin.DEFAULT, Player.getPlayer().getCarriedItem());
        slot.setSize(160 * APIXEL);
        adapter.addActor(slot);
        adapter.setGravity(GravityAttribute.BOTTOM, GravityAttribute.RIGHT);
        addActor(slot);

        Slot slot2 = new Slot(Slot.SlotSkin.DEFAULT, new Container(1000));
        slot2.setSize(160 * APIXEL);
        slot2.addItems(Item.newItems("pickaxe", 1));
        adapter.addActor(slot2);
        adapter.tiedTo(GravityAttribute.RIGHT, slot);
        adapter.setGravity(GravityAttribute.BOTTOM, GravityAttribute.RIGHT);
        addActor(slot2);


        Slot slot3 = new Slot(Slot.SlotSkin.DEFAULT, new Container(1000));
        slot3.setSize(160 * APIXEL);
        adapter.addActor(slot3);
        adapter.tiedTo(GravityAttribute.RIGHT, slot2);
        adapter.setGravity(GravityAttribute.BOTTOM, GravityAttribute.RIGHT);
        //addActor(slot3);


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/jb_mono.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        label = new Label(" ", new Label.LabelStyle(font12, Color.WHITE));
        label.setFontScale(HHPIXEL / 50 * 10 * 0.8f);
        adapter.addActor(label);
        adapter.setHeight(label.getPrefHeight());
        adapter.setGravity(GravityAttribute.TOP, GravityAttribute.LEFT);
        addActor(label);

        Joystick joystick = new Joystick(Resource.getSprite("gui/Joystick_0.png"), Resource.getSprite("gui/Joystick_1.png"), 80 * APIXEL);
        joystick.moveBy(APIXEL, 40, 40);
        addActor(joystick);


        final ProgressBar bar = new ProgressBar(new TestProgressBarSkin());
        bar.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                GUI.setLayer("inventory", null);
            }
        });
        bar.setSize(100 * APIXEL, 20 * APIXEL);
        adapter.addActor(bar);
        adapter.setGravity(GravityAttribute.TOP, GravityAttribute.RIGHT);
        addActor(bar);

        CallBack.addCallBack(new Render() {
            @Override
            public void main() {
                bar.setState(player.getEndurance());
            }
        });
        TakenItemsRender.initInstance();
        TakenItemsRender.setRendering(true);
        adapter.apply();

    }

    @Override
    public Layer onCreate(ExtraData data) {
        InteractionOfItems.setInteraction(true);
        if (!World.getConfig().isDebug())
            removeActor(label);
        else
            addActor(label);
        return this;
    }

    @Override
    public void destroy() {
        InteractionOfItems.setInteraction(false);
    }
}
