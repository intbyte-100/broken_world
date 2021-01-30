package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.intbyte.bw.gameAPI.environment.Item;

public class Container {
    protected int maxCountItems;
    protected Array<Item> items;


    public Container(int maxCountItems) {
        items = new Array<>();
        this.maxCountItems = maxCountItems;
    }

    public void moveItems(Array<Item> items) {
        if (items.isEmpty()) return;
        if (this.items.isEmpty() || items.get(0).getId() == this.items.get(0).getId()) {

            if (maxCountItems >= items.size + this.items.size && items.get(0).STACK_SIZE >= items.size + this.items.size) {
                this.items.addAll(items);
                items.clear();
                return;
            } else if (maxCountItems < items.size) {
                while (this.items.size < maxCountItems)
                    this.items.add(items.pop());
                return;
            }


            if (this.items.size < maxCountItems && this.items.notEmpty() && this.items.get(0).STACK_SIZE > this.items.size) {
                while (this.items.size < maxCountItems && this.items.get(0).STACK_SIZE > this.items.size)
                    this.items.add(items.pop());
                return;
            }
        }

        Array<Item> itemArray = new Array<>();
        itemArray.addAll(items);
        items.clear();
        items.addAll(this.items);
        this.items = itemArray;
    }

    public void addItems(Array<Item> items) {
        Array<Item> array = new Array<>();
        array.addAll(items);
        moveItems(array);
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
        if (maxCountItems >= items.size && items.get(0).STACK_SIZE >= items.size)
            this.items.addAll(items);
        else
            for (int i = 0; i < maxCountItems; i++) {
                this.items.add(items.get(i));
            }
    }

    public Item getLastElement() {
        return items.get(getCountItems() - 1);
    }
}