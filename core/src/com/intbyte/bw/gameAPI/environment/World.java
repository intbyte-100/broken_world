package com.intbyte.bw.gameAPI.environment;
import com.intbyte.bw.gameAPI.utils.ExtraData;

public final class World{
	static int idDimension;
	static short[][] landBlock;
	static short[][] block;
	static short[][] biome;

	public static void createVoidWorld(int width, int height){
		block = new short[width * 16][height * 16];
		landBlock = new short[width * 16][height * 16];
	}

	public static int getBlock(int x, int z){
		if(x > -1 && z > -1 && x < block.length && z < block.length)
			return block[x][z];
		return 0;
	}

	public static int getLandBlock(int x, int z){
		if(x > -1 && z > -1 && x < block.length && z < block.length)
			return landBlock[x][z];
		return 0;
	}

	public static void setBlock(int x, int z, int id){
		if(x > -1 && z > -1 && x < block.length && z < block.length)
			block[x][z] = (short) id;
	}

	public static void setLandBlock(int x, int z, int id){
		if(x > -1 && z > -1 && x < block.length && z < block.length)
			landBlock[x][z] = (short) id;
	}

	public static <T extends ExtraData> T getBlockData(int x, int y){
		ExtraData data;
		if(Block.blocks[getBlock(x, y)].blockData != null){
			data = Block.blocks[getBlock(x, y)].blockData.get(x * 10000 + y);
			if(data != null)
				return (T) data;
		}
		return null;
	}

	public static <T extends ExtraData> T getLandBlockData(int x, int y){
		ExtraData data;
		if(Block.landBlocks[getLandBlock(x, y)].blockData != null){
			data = Block.landBlocks[getLandBlock(x, y)].blockData.get(x * 10000 + y);
			if(data != null)
				return (T) data;
		}
		return null;
	}
} 
