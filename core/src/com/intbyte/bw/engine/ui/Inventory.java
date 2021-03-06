package com.intbyte.bw.engine.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.intbyte.bw.engine.item.Container;
import com.intbyte.bw.engine.ui.container.Slot;
import com.intbyte.bw.engine.ui.container.SlotAllocateController;

public class Inventory extends Group {
    protected int elementsPerLine;
    protected Array<Container> containers;
    protected Array<Slot> slots;
    protected Table scrollTable;
    protected Table layout;
    protected ScrollPane scrollPane;

    protected int defaultSize = 12;

    public  Inventory(){
        this(null);
    }


    @Override
    public void setPosition(float x, float y) {
        layout.setPosition(x, y);
    }

    public void setDefaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public void setContainers(Array<Container> containers) {
        this.containers = containers;
    }

    public Inventory(Array<Container> containers){
        this.containers = containers;
        this.scrollTable = new Table();
        this.slots = new Array<>();
        this.scrollPane = new ScrollPane(scrollTable);
        scrollPane.setOverscroll(false,true);
        scrollTable.top().left();
        layout = new Table();
        layout.setFillParent(true);
        layout.add(scrollPane).expand().fill();
        layout.setSize(getWidth(),getHeight());
        addActor(layout);
    }

    public void setElementsPerLine(int elementsPerLine) {
        this.elementsPerLine = elementsPerLine;
    }

    public void apply(){
        scrollTable.clear();
        int i = 0;
        while (slots.size<containers.size) {
            slots.add(new Slot(Slot.SlotSkin.DEFAULT, containers.get(i++)));
        }

        for(i = 0; i < containers.size; i++){
            slots.get(i).setSize(getWidth()/elementsPerLine);
            slots.get(i).setContainer(containers.get(i));
            scrollTable.add(slots.get(i));
            if((i+1)%elementsPerLine==0)
                scrollTable.row();
        }
    }


    public float getSlotSize(){
        return getWidth()/elementsPerLine;
    }
    boolean scrollLock;
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setColor(1,1,1,1);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(SlotAllocateController.isAllocate()||SlotAllocateController.isLockScroll())
            scrollPane.cancel();

    }
}
