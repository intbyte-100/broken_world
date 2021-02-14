package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

public class Container {
    protected int maxCountItems;
    protected Array<Item> items;
    protected int availableType = -2;

    public Container(int maxCountItems) {
        items = new Array<>();
        this.maxCountItems = maxCountItems;
    }

    public void moveItems(Array<Item> items) {
        if (items.isEmpty()) return;
        if (this.items.isEmpty() || items.get(0).getId() == this.items.get(0).getId()) {

            if (maxCountItems >= items.size + this.items.size && items.get(0).stackSize >= items.size + this.items.size) {
                this.items.addAll(items);
                items.clear();
                return;
            } else if (maxCountItems < items.size) {
                while (this.items.size < maxCountItems)
                    this.items.add(items.pop());
                return;
            }


            if (this.items.size < maxCountItems && this.items.notEmpty() && this.items.get(0).stackSize > this.items.size) {
                while (this.items.size < maxCountItems && this.items.get(0).stackSize > this.items.size)
                    this.items.add(items.pop());
                return;
            }
        }

        if(items.size>this.getMaxCountItems()) return;
        Array<Item> itemArray = new Array<>();
        itemArray.addAll(items);
        items.clear();
        items.addAll(this.items);
        this.items = itemArray;
    }

    public void moveItems(Container container){
        System.out.println(getAvailableType()+" "+container.getAvailableType());
        if((getAvailableType()==container.getAvailableType())||getAvailableType()==container.getType())
        if((container.getCountItems()<=getMaxCountItems()&&
                getCountItems()<=container.getMaxCountItems()||
                getId()==container.getId())||
                getItems().isEmpty())
            moveItems(container.getItems());
    }
    public void addItems(Array<Item> items) {
        moveItems(items);
    }

    public int getMaxCountItems() {
        return maxCountItems;
    }

    public int getId() {
        return items.notEmpty() ? items.get(0).getId() : 0;
    }

    public int getCountItems() {
        return items.size;
    }

    public Item delete() {
        if (items.isEmpty()) {
            Gdx.app.log("CONTAINER", "cannot delete element, because container is empty");
            return null;
        }
        return items.pop();
    }

    public void clear() {
        items.clear();
    }

    public Array<Item> getItems() {
        return items;
    }

    public void setItems(Array<Item> items) {
        if (items.isEmpty()) return;
        this.items.clear();
        if (maxCountItems >= items.size && items.get(0).stackSize >= items.size)
            this.items.addAll(items);
        else
            for (int i = 0; i < maxCountItems; i++) {
                this.items.add(items.get(i));
            }
    }

    public Item getLastElement() {
        return items.get(getCountItems() - 1);
    }

    public int getType(){
        return items.notEmpty() ? items.get(0).getType() : -1;
    }

    public int getAvailableType() {
        return availableType;
    }

    public void setAvailableType(int availableType) {
        this.availableType = availableType;
    }

    public void setMaxCountItems(int maxCountItems) {
        this.maxCountItems = maxCountItems;
    }
}