package com.intbyte.bw.gameAPI.utils;
import com.badlogic.gdx.Gdx;

import java.util.HashMap;

public class ID
{
	private static final HashMap<String,id> map = new HashMap<>();

	private static int iterator = 0;

	private static class id{
		int maxVal = 0;
		HashMap<String,Integer> idMap = null;
	}


	public static void registeredIdGroup(String key, int maxVal){
		id idMap = new id();
		idMap.maxVal = maxVal;
		idMap.idMap = new HashMap<>();
		map.put(key, idMap);
		Gdx.app.log("ID MANAGER","registered id group "+key);
	}

	public static int registeredId(String key, int id){
		String[] keys = key.split(":");
		if(map.get(keys[0]).maxVal > id)
			map.get(keys[0]).idMap.put(keys[1], id);
		Gdx.app.log("ID MANAGER","registered string id "+key+", with integer id "+id);
		return id;

	}

	public static int registeredId(String key){
		iterator = 0;
		String[] keys = key.split(":");
		id idMap = map.get(keys[0]);
		while(iterator < idMap.maxVal){
			if(!idMap.idMap.containsValue(iterator)){
				idMap.idMap.put(keys[1], iterator);
				Gdx.app.log("ID MANAGER","registered string id "+key+", with integer id "+iterator);
				return iterator;
			}
			else iterator++;
		}
		iterator = 0;
		return 0;
	}


	public static int get(String key){
		String[] keys = key.split(":");
		if (!map.containsKey(keys[0]))
			throw new GroupNotFoundException(keys[0]);
		if (!map.get(keys[0]).idMap.containsKey(keys[1]))
			throw new IdNotFoundException(keys[1]);
		return map.get(keys[0]).idMap.get(keys[1]);
	}

	static {
		ID.registeredIdGroup("land_block", 12000);
		ID.registeredIdGroup("item",12000);
		ID.registeredId("item:void");
		ID.registeredIdGroup("block", 12000);
		ID.registeredIdGroup("biome", 12000);
		ID.registeredIdGroup("entity", 12000);
		ID.registeredIdGroup("dimension", 12000);
	}
}
