package com.intbyte.bw.game.gameUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Render;
import com.intbyte.bw.gameAPI.environment.Item;
import com.intbyte.bw.gameAPI.graphic.GravityAdapter;
import com.intbyte.bw.gameAPI.graphic.GravityAttribute;
import com.intbyte.bw.gameAPI.graphic.ui.Joystick;
import com.intbyte.bw.gameAPI.graphic.ui.Layer;
import com.intbyte.bw.gameAPI.graphic.ui.container.Container;
import com.intbyte.bw.gameAPI.graphic.ui.container.Slot;
import com.intbyte.bw.gameAPI.graphic.ui.container.TakenItemsRender;
import com.intbyte.bw.gameAPI.utils.ExtraData;
import com.intbyte.bw.gameAPI.utils.Resource;

import static com.intbyte.bw.gameAPI.graphic.TypedValue.APIXEL;

public class MainLayerUI extends Layer {
    private static MainLayerUI INSTANCE;

    private final Player player = Player.getPlayer();
    private Label label;
    private GravityAdapter adapter;
    private Joystick joystick;
    private Slot slot;


    private MainLayerUI() {

        CallBack.addCallBack(new Render() {
            @Override
            public void main() {
                label.setText("fps: " + Gdx.graphics.getFramesPerSecond() + "; player position: x = " + (int) player.getXOnBlock() + ", z = " + (int) player.getZOnBlock());
            }
        });


        adapter = new GravityAdapter();

        slot = new Slot(Slot.SlotSkin.DEFAULT, Player.getPlayer().getCarriedItem());
        slot.setSize(160 * APIXEL);
        adapter.addActor(slot);
        adapter.setGravity(GravityAttribute.BOTTOM, GravityAttribute.RIGHT);
        addActor(slot);


        label = new Label(" ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        label.setFontScale(2.5f);
        adapter.addActor(label);
        adapter.setGravity(GravityAttribute.TOP, GravityAttribute.LEFT);
        addActor(label);

        joystick = new Joystick(Resource.getTexture("gui/Joystick_0.png"), Resource.getTexture("gui/Joystick_1.png"), 80 * APIXEL);
        joystick.moveBy(APIXEL, 40, 40);
        addActor(joystick);

        TakenItemsRender.initInstance();
        TakenItemsRender.setRendering(true);
        adapter.apply();

    }

    public static MainLayerUI getInstance() {
        if (INSTANCE == null) INSTANCE = new MainLayerUI();
        return INSTANCE;
    }

    @Override
    public Layer onCreate(ExtraData data) {
        return this;
    }

    @Override
    public void destroy() {

    }
}
