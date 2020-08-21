package com.intbyte.bw.gameAPI.utils;
import java.util.HashMap;

public class ID
{
	private static HashMap<String,id> map = new HashMap<String,id>();

	private static int iterator = 0;

	private static class id{
		int maxVal = 0;
		HashMap<String,Integer> idMap = null;
	}


	public static void registredIdGroup(String key, int maxVal){
		id idMap = new id();
		idMap.maxVal = maxVal;
		idMap.idMap = new HashMap<String,Integer>();
		map.put(key, idMap);
	}

	public static int registredId(String key, int id){
		String[] keys = key.split(":");
		if(map.get(keys[0]).maxVal > id)
			map.get(keys[0]).idMap.put(keys[1], id);
		return id;

	}

	public static int registredId(String key){
		iterator = 0;
		String[] keys = key.split(":");
		id idMap = map.get(keys[0]);
		while(iterator < idMap.maxVal){
			if(!idMap.idMap.containsValue(iterator)){
				idMap.idMap.put(keys[1], iterator);
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
		ID.registredIdGroup("land_block", 12000);
		ID.registredIdGroup("item",12000);
		ID.registredIdGroup("block", 12000);
		ID.registredIdGroup("biome", 12000);
		ID.registredIdGroup("entity", 12000);
		ID.registredIdGroup("dimension", 12000);
	}
}
