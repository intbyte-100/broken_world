package com.intbyte.bw.game.gameUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Render;
import com.intbyte.bw.gameAPI.graphic.GravityAdapter;
import com.intbyte.bw.gameAPI.graphic.GravityAttribute;
import com.intbyte.bw.gameAPI.graphic.ui.Joystick;
import com.intbyte.bw.gameAPI.graphic.ui.Layer;
import com.intbyte.bw.gameAPI.utils.ExtraData;
import com.intbyte.bw.gameAPI.utils.Resource;

import static com.intbyte.bw.gameAPI.graphic.TypedValue.APIXEL;

public class MainLayerUI extends Layer {
    private static MainLayerUI INSTANCE;

    private final Player player = Player.getPlayer();
    private Label label;
    private GravityAdapter adapter;
    private Joystick joystick;


    private MainLayerUI() {

        CallBack.addCallBack(new Render() {
            @Override
            public void main() {
                label.setText("fps: " + Gdx.graphics.getFramesPerSecond() + "; player position: x = " + (int) player.getXOnBlock() + ", z = " + (int) player.getZOnBlock());
            }
        });


        Group group = new Group();
        label = new Label(" ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        label.setFontScale(2.5f);
        adapter = new GravityAdapter();
        adapter.addActor(label);
        adapter.setGravity(GravityAttribute.TOP, GravityAttribute.LEFT);
        addActor(label);

        joystick = new Joystick(Resource.getTexture("gui/Joystick_0.png"), Resource.getTexture("gui/Joystick_1.png"), 80 * APIXEL);
        joystick.moveBy(APIXEL, 40, 40);
        addActor(joystick);

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
