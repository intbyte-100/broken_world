package com.intbyte.bw.game.gameUI;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.intbyte.bw.gameAPI.graphic.GravityAdapter;
import com.intbyte.bw.gameAPI.graphic.GravityAttribute;
import com.intbyte.bw.gameAPI.graphic.ui.GUI;
import com.intbyte.bw.gameAPI.graphic.ui.Layer;
import com.intbyte.bw.gameAPI.graphic.ui.button.Button;
import com.intbyte.bw.gameAPI.graphic.ui.button.RoundButton;
import com.intbyte.bw.gameAPI.graphic.ui.button.TriangleButton;
import com.intbyte.bw.gameAPI.utils.ExtraData;

import static com.intbyte.bw.gameAPI.graphic.TypedValue.APIXEL;

public class InventoryLayerUI extends Layer {

    public InventoryLayerUI(){
        GravityAdapter adapter = new GravityAdapter();
        Button button = new Button(new RoundButton());
        button.setSize(APIXEL * 100, APIXEL * 100);

        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                GUI.setLayer("main", null);
            }
        });

        adapter.addActor(button);
        adapter.setGravity(GravityAttribute.BOTTOM, GravityAttribute.RIGHT);
        addActor(button);

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
