package com.intbyte.bw.game.gameUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.gameAPI.graphic.GravityAdapter;
import com.intbyte.bw.gameAPI.graphic.GravityAttribute;
import com.intbyte.bw.gameAPI.graphic.TypedValue;
import com.intbyte.bw.gameAPI.graphic.ui.Inventory;
import com.intbyte.bw.gameAPI.graphic.ui.Layer;
import com.intbyte.bw.gameAPI.graphic.ui.Panel;
import com.intbyte.bw.gameAPI.utils.ExtraData;
import com.intbyte.bw.gameAPI.utils.Resource;

public class InventoryLayerUI extends Layer {

    Inventory inventory = new Inventory();
    public InventoryLayerUI(){
        float width = (Gdx.graphics.getWidth() - Gdx.graphics.getHeight()*0.06f*2-Gdx.graphics.getHeight()*0.06f)/2;
        float height = Gdx.graphics.getHeight()-Gdx.graphics.getHeight()*0.06f*2;

        Sprite sprite = Panel.getDrawRoundedPanel((int) (width),(int)(height), (int) (TypedValue.APIXEL*4),0.1f,0.1f,0.1f,0.7f);
        Resource.addSprite(sprite,"inventory");

        Table table = new Table();

        Panel panel = new Panel("inventory");
        panel.setSize(width,height);
        panel.setPosition(Gdx.graphics.getHeight()*0.06f,Gdx.graphics.getHeight()*0.06f);
        addActor(panel);

        GravityAdapter adapter = new GravityAdapter();
        Panel panel2 = new Panel("inventory");
        panel2.setSize(width,height);
        adapter.addActor(panel2);
        adapter.tiedTo(GravityAttribute.LEFT,panel);
        adapter.margin(Gdx.graphics.getHeight()*0.06f,Gdx.graphics.getHeight()*0.06f);
        addActor(panel2);
        adapter.apply();


        inventory.setContainers(Player.getPlayer().getInventory());
        inventory.setSize(width-Gdx.graphics.getHeight()*0.12f,height);
        inventory.setElementsPerLine(5);

        inventory.setPosition(Gdx.graphics.getHeight()*0.12f,Gdx.graphics.getHeight()*0.06f);
        addActor(inventory);
    }
    @Override
    public Layer onCreate(ExtraData data) {
        inventory.apply();
        return this;
    }

    @Override
    public void destroy() {

    }
}
