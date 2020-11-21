package com.intbyte.bw.gameAPI.environment;

import com.intbyte.bw.core.game.Player;

public final class World {
    static int idDimension;
    static short[][] landBlock;
    static short[][] block;
    static short[][] biome;
    static Chunck[][] world = new Chunck[32][32];
    static int playerX, playerZ;
    static Player player = Player.getPlayer();
    static boolean isChangePosition;
    static boolean movedUp, movedRight;
    static {
        main(null);
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

    public static void main(String[] args) {
        for (Chunck[] chunks :
                world) {
            for (int i = 0; i < chunks.length; i++) {
                chunks[i] = new Chunck();
            }
        }
        player.setZOnBlock(22);
        player.setXOnBlock(100);
        System.out.println(fixedIndex((int) (22-player.getZOnBlock()))+" "+fixedIndex((int) (100-player.getXOnBlock())));
        World.setBlockToChunk(1002,22,new Tile());
        System.out.println(getChunk(102,22).getTiles());
    }

    public static void update() {
        int chunckX = ((int) (player.getXOnBlock() / 2) - (int) (player.getXOnBlock() / 32) * 16);
        int chunckZ = ((int) (player.getZOnBlock() / 2) - (int) (player.getZOnBlock() / 32) * 16);
        isChangePosition = chunckX != playerX || chunckZ != playerZ;
        if (isChangePosition) {
            movedUp = playerZ < chunckZ;
            movedRight = playerX > chunckX;
        }
        playerX = chunckX;
        playerZ = chunckZ;
    }

    public static int fixedIndex(int index) {
        return index >= 0 && index < 32 ? index : index >= 32 ? index - 32 : index + 32;
    }

    public static Chunck getChunk(int x, int z) {
        return world[fixedIndex((int) (x-player.getXOnBlock()))][fixedIndex((int) (z- player.getZOnBlock()))];
    }


    public static void setBlockToChunk(double x, double z, Tile tile) {
        Chunck chunck;
        x = (x - player.getXOnBlock());
        z = (z - player.getZOnBlock());
        if (z > -32 && z < 32&&x > -32 && x < 32)
            chunck = world[fixedIndex((int) x)][fixedIndex(((int) z))];
        else return;
        tile.setPosition((float) (x/2-Math.floor(x/2))*2,(float) (z/2-Math.floor(z/2))*2);
        chunck.setTile(tile);
    }
} 
