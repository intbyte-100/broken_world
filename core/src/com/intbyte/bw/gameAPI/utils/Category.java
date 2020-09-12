package com.intbyte.bw.gameAPI.utils;

import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

public class Category<T> {
    private final HashMap<String, Array<T>> categories;

    public Category() {
        categories = new HashMap<>();
    }

    public void add(String key, T value) {
        categories.get(key).add(value);
    }

    public T get(String key, int index) {
        return categories.get(key).get(index);
    }

    public Array<T> get(String category) {
        return categories.get(category);
    }

    public void newCategory(String key) {
        categories.put(key, new Array<T>());
    }
}
