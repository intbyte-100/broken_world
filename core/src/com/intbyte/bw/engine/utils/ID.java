package com.intbyte.bw.engine.utils;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;

public class ID {
    private static final HashMap<String, id> map = new HashMap<>();

    private static class id {
        int maxVal = 0;
        HashMap<String, Integer> idMap = null;
    }


    public static void registeredIdGroup(String key, int maxVal) {
        id idMap = new id();
        idMap.maxVal = maxVal;
        idMap.idMap = new HashMap<>();
        map.put(key, idMap);
        Gdx.app.log("ID MANAGER", "registered id group " + key);
    }

    public static int registeredId(String key, int id) {
        String[] keys = key.split(":");
        if (map.get(keys[0]).maxVal > id)
            map.get(keys[0]).idMap.put(keys[1], id);
        Gdx.app.log("ID MANAGER", "registered string id " + key + ", with integer id " + id);
        return id;

    }

    public static int registeredId(String key) {
        int iterator = 0;
        String[] keys = key.split(":");
        id idMap = map.get(keys[0]);
        while (iterator < idMap.maxVal) {
            if (!idMap.idMap.containsValue(iterator)) {
                idMap.idMap.put(keys[1], iterator);
                Gdx.app.log("ID MANAGER", "registered string id " + key + ", with integer id " + iterator);
                return iterator;
            } else iterator++;
        }
        return 0;
    }


    public static int get(String key) {
        Integer integer = null;
        String[] keys = key.split(":");
        id idHashMap = map.get(keys[0]);
        if (idHashMap == null||(integer = idHashMap.idMap.get(keys[1])) == null)
            throw new RuntimeException("Cannot found id "+key);
        return integer;
    }

    static {
        ID.registeredIdGroup("land_block", 1200);
        ID.registeredIdGroup("item", 1200);
        ID.registeredId("item:void");
        ID.registeredIdGroup("block", 1200);
        ID.registeredIdGroup("biome", 1200);
        ID.registeredIdGroup("entity", 1200);
        ID.registeredIdGroup("dimension", 1200);
        ID.registeredIdGroup("2dBody", 1200);
    }
}
