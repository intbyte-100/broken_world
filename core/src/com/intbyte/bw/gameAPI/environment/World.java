package com.intbyte.bw.gameAPI.environment;

import com.intbyte.bw.core.game.Player;

public final class World {

    static int idDimension;
    static short[][] landBlock;
    static short[][] block;
    static short[][] biome;
    static Chunck[][] world = new Chunck[16][16];
    static int playerX, playerZ, playerChunkX, playerChunkZ;
    static Player player = Player.getPlayer();
    static boolean isChangePosition;
    static boolean movedUp, movedRight;
    static {
        for (Chunck[] chuncks: world){
            for (int i = 0; i < chuncks.length; i++) {
                chuncks[i] = new Chunck();
                chuncks[i].x = i;
            }
        }
    }

    public static void createVoidWorld(int width, int height) {
        block = new short[width * 32][height * 32];
        landBlock = new short[width * 32][height * 32];
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
        if (x > -1 && z > -1 && x < block.length && z < block.length) {
            Block.CustomBlock customBlock = Block.getBlocks()[getBlock(x, z)];
            if (customBlock != null && customBlock.blockData.containsKey(x * 1000 + z))
                customBlock.blockData.remove(x * 1000 + z);
            block[x][z] = (short) id;
        }
    }

    public static void setLandBlock(int x, int z, int id) {
        if (x > -1 && z > -1 && x < block.length && z < block.length)
            landBlock[x][z] = (short) id;
    }

    public static BlockExtraData getBlockData(int x, int z) {
        Block.CustomBlock customBlock = Block.getBlocks()[getBlock(x, z)];
        if (customBlock == null) return BlockExtraData.NOT_DATA;
        return customBlock.blockData.get(x * 1000 + z);
    }

    public static void setBlockData(int x, int z, BlockExtraData data) {
        Block.CustomBlock customBlock = Block.getBlocks()[getBlock(x, z)];
        if (customBlock == null) return;
        customBlock.blockData.put(x * 1000 + z, data);
    }


    public static void update() {
        int chunckX = ((int) (player.getXOnBlock() / 2) - (int) (player.getXOnBlock() / 32) * 16);
        int chunckZ = ((int) (player.getZOnBlock() / 2) - (int) (player.getZOnBlock() / 32) * 16);
        isChangePosition = chunckX != playerX || chunckZ != playerZ;
        if (isChangePosition) {
            movedUp = playerZ < chunckZ;
            movedRight = playerX > chunckX;
        } else return;
        playerX = chunckX;
        playerZ = chunckZ;
    }

    public static int fixedIndex(int index) {
        index = index >= 0 && index < 16 ? index : index >= 16 ? index - 16 : index+16;
        return index >= 0 && index < 16 ? index : fixedIndex(index);
    }


    public static Chunck getChunck(float x, float z){
        x = (x/2 - (int) (player.getXOnBlock()/2));
        z = (z/2- (int) (player.getZOnBlock()/2));
        return world[fixedIndex((int) (x+playerX))][fixedIndex((int) (z+playerZ))];
    }

    public static void setBlockToChunk(float x, float z, Tile tile) {
        Chunck chunck = getChunck(x,z);
        System.err.println(chunck.x);
        tile.setPosition((float) (x-(Math.floor(x/2f)*2f)),0,(float) (z-(Math.floor(z/2f)*2f)));
        System.err.println("set block"+tile.position+" "+x+" "+z);
        chunck.setTile(tile);
        System.err.println(chunck.getTiles());
    }
} 
