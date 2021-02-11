package com.intbyte.bw.game.gameUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.gameAPI.graphic.GravityAdapter;
import com.intbyte.bw.gameAPI.graphic.GravityAttribute;
import com.intbyte.bw.gameAPI.graphic.TypedValue;
import com.intbyte.bw.gameAPI.ui.Inventory;
import com.intbyte.bw.gameAPI.ui.Layer;
import com.intbyte.bw.gameAPI.ui.Panel;
import com.intbyte.bw.gameAPI.ui.container.Slot;
import com.intbyte.bw.gameAPI.utils.ExtraData;
import com.intbyte.bw.gameAPI.utils.Resource;

public class InventoryLayerUI extends Layer {

    Inventory inventory = new Inventory();

    public InventoryLayerUI() {
        final float padding = Gdx.graphics.getHeight() * 0.06f;
        float width = (Gdx.graphics.getWidth() - padding * 2 - padding) / 2;
        float height = Gdx.graphics.getHeight() - padding * 2;

        Sprite sprite = Panel.getDrawRoundedPanel((int) (width), (int) (height), (int) (TypedValue.APIXEL * 4), 0.1f, 0.1f, 0.1f, 0.7f);
        Resource.addSprite(sprite, "inventory");


        Panel panel = new Panel("inventory");
        panel.setSize(width, height);
        panel.setPosition(padding, padding);
        addActor(panel);

        GravityAdapter adapter = new GravityAdapter();
        Panel panel2 = new Panel("inventory");
        panel2.setSize(width, height);
        adapter.addActor(panel2);
        adapter.tiedTo(GravityAttribute.LEFT, panel);
        adapter.margin(padding, padding);
        addActor(panel2);


        inventory.setContainers(Player.getPlayer().getInventory());
        inventory.setSize(width - padding * 2, height - padding * 2);
        inventory.setElementsPerLine(5);
        inventory.setPosition(padding, padding);
        panel.addActor(inventory);

        Slot firstItem = new Slot(Player.getPlayer().getCarriedItem()), secondItem = new Slot(1), thirdItem = new Slot(),
                helmet = new Slot(1), armor = new Slot(1), leggings = new Slot(1);

        final float slotSize = (panel2.getWidth() - padding * 4) / 3f * 0.86f;
        firstItem.setSize(slotSize);
        secondItem.setSize(slotSize);
        thirdItem.setSize(slotSize);

        panel2.addActor(firstItem);
        panel2.addActor(secondItem);
        panel2.addActor(thirdItem);

        firstItem.setPosition(padding, panel2.getHeight() - padding - firstItem.getHeight());

        adapter.addActor(secondItem);
        adapter.tiedTo(GravityAttribute.BOTTOM, firstItem);
        adapter.margin(padding, 0);

        adapter.addActor(thirdItem);
        adapter.tiedTo(GravityAttribute.BOTTOM, secondItem);
        adapter.margin(padding, 0);
        adapter.apply();

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
