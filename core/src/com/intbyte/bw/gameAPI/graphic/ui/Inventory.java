package com.intbyte.bw.gameAPI.graphic.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.intbyte.bw.gameAPI.environment.Container;
import com.intbyte.bw.gameAPI.graphic.ui.container.Slot;

public class Inventory extends Group {
    protected int elementsPerLine;
    protected Array<Container> containers;
    protected Array<Slot> slots;
    protected Table table;

    protected int defaultSize = 12;

    public  Inventory(){
        this.table = new Table();
        this.slots = new Array<>();
        addActor(table);
    }


    @Override
    public void setPosition(float x, float y) {
        table.setPosition(x, y);
    }

    public void setDefaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public void setContainers(Array<Container> containers) {
        this.containers = containers;
    }

    public Inventory(Array<Container> containers){
        this.containers = containers;
        this.table = new Table();
        this.slots = new Array<>();
        addActor(table);
    }

    public void setElementsPerLine(int elementsPerLine) {
        this.elementsPerLine = elementsPerLine;
    }

    public void apply(){
        table.setSize(getWidth(),getHeight());
        table.clear();
        int i = 0;
        while (slots.size<containers.size) {
            slots.add(new Slot(Slot.SlotSkin.DEFAULT, containers.get(i++)));
        }

        for(i = 0; i < containers.size; i++){
            slots.get(i).setSize(getWidth()/elementsPerLine);
            slots.get(i).setContainer(containers.get(i));
            table.add(slots.get(i));
            if((i+1)%elementsPerLine==0)
                table.row();
        }

    }
}
