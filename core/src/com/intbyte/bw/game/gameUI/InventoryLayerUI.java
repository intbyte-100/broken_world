package com.intbyte.bw.game.gameUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.intbyte.bw.gameAPI.graphic.GravityAdapter;
import com.intbyte.bw.gameAPI.graphic.GravityAttribute;
import com.intbyte.bw.gameAPI.graphic.TypedValue;
import com.intbyte.bw.gameAPI.graphic.ui.Layer;
import com.intbyte.bw.gameAPI.graphic.ui.Panel;
import com.intbyte.bw.gameAPI.utils.ExtraData;
import com.intbyte.bw.gameAPI.utils.Resource;

public class InventoryLayerUI extends Layer {


    public InventoryLayerUI(){
        float width = (Gdx.graphics.getWidth() - Gdx.graphics.getHeight()*0.06f*2-Gdx.graphics.getHeight()*0.06f)/2;
        float height = Gdx.graphics.getHeight()-Gdx.graphics.getHeight()*0.06f*2;
        GravityAdapter adapter = new GravityAdapter();
        Sprite sprite = Panel.getDrawRoundedPanel((int) (width),(int)(height), (int) (TypedValue.APIXEL*4),0.1f,0.1f,0.1f,0.7f);
        Resource.addSprite(sprite,"inventory");
        Panel panel = new Panel("inventory");
        panel.setSize(width,height);
        panel.setPosition(Gdx.graphics.getHeight()*0.06f,Gdx.graphics.getHeight()*0.06f);
        addActor(panel);

        Panel panel2 = new Panel("inventory");
        panel2.setSize(width,height);
        adapter.addActor(panel2);
        adapter.tiedTo(GravityAttribute.LEFT,panel);
        adapter.margin(Gdx.graphics.getHeight()*0.06f,Gdx.graphics.getHeight()*0.06f);
        addActor(panel2);
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
