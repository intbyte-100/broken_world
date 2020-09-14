package com.intbyte.bw.gameAPI.environment;

public final class World {
    static int idDimension;
    static short[][] landBlock;
    static short[][] block;
    static short[][] biome;

    public static void createVoidWorld(int width, int height) {
        block = new short[width * 16][height * 16];
        landBlock = new short[width * 16][height * 16];
    }

    public static int getBlock(int x, int z) {
        if (x > -1 && z > -1 && x < block.length && z < block.length)
            return block[x][z];
        return 0;
    }

    public static int getLandBlock(int x, int z) {
        if (x > -1 && z > -1 && x < block.length && z < block.length)
            return landBlock[x][z];
        return 0;
    }

    public static void setBlock(int x, int z, int id) {
        if (x > -1 && z > -1 && x < block.length && z < block.length)
            block[x][z] = (short) id;
    }

    public static void setLandBlock(int x, int z, int id) {
        if (x > -1 && z > -1 && x < block.length && z < block.length)
            landBlock[x][z] = (short) id;
    }

} 
