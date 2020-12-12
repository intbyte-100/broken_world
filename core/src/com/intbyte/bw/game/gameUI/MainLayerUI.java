package com.intbyte.bw.game.gameUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.intbyte.bw.core.game.GameThread;
import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Render;
import com.intbyte.bw.gameAPI.environment.Item;
import com.intbyte.bw.gameAPI.environment.World;
import com.intbyte.bw.gameAPI.graphic.GravityAdapter;
import com.intbyte.bw.gameAPI.graphic.GravityAttribute;
import com.intbyte.bw.gameAPI.graphic.ui.Joystick;
import com.intbyte.bw.gameAPI.graphic.ui.Layer;
import com.intbyte.bw.gameAPI.graphic.ui.ProgressBar;
import com.intbyte.bw.gameAPI.graphic.ui.container.Container;
import com.intbyte.bw.gameAPI.graphic.ui.container.Slot;
import com.intbyte.bw.gameAPI.graphic.ui.container.TakenItemsRender;
import com.intbyte.bw.gameAPI.utils.ExtraData;
import com.intbyte.bw.gameAPI.utils.Resource;

import static com.intbyte.bw.gameAPI.graphic.TypedValue.APIXEL;

public class MainLayerUI extends Layer {


    private final Player player = Player.getPlayer();
    private final Label label;

    Skin sk =new Skin(){

    };
    public MainLayerUI() {

        CallBack.addCallBack(new Render() {
            @Override
            public void main() {
                label.setText("fps: " + Gdx.graphics.getFramesPerSecond() + "; player position: x = " + (int) player.getXOnBlock() + ", z = " + (int) player.getZOnBlock() + "; visible models = " + GameThread.visible+" "+ World.getChunck((float) player.getXOnBlock(),(float) player.getZOnBlock()).x);
            }
        });


        GravityAdapter adapter = new GravityAdapter();

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


        label = new Label(" ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        label.setFontScale(2.5f);
        adapter.addActor(label);
        adapter.setGravity(GravityAttribute.TOP, GravityAttribute.LEFT);
        addActor(label);

        Joystick joystick = new Joystick(Resource.getSprite("gui/Joystick_0.png"), Resource.getSprite("gui/Joystick_1.png"), 80 * APIXEL);
        joystick.moveBy(APIXEL, 40, 40);
        addActor(joystick);


        final ProgressBar bar = new ProgressBar(new TestProgressBarSkin());
        bar.setSize(100*APIXEL,20*APIXEL);
        adapter.addActor(bar);
        adapter.setGravity(GravityAttribute.TOP,GravityAttribute.RIGHT);
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
        return this;
    }

    @Override
    public void destroy() {

    }
}
