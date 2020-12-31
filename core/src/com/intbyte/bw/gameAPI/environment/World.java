package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.Gdx;
import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.gameAPI.physic.PhysicBlockObject;

public final class World {

    public static Chunck[][] world = new Chunck[16][16];
    public static int playerX, playerZ, playerChunkX, playerChunkZ;
    static int idDimension;
    static short[][] landBlock;
    static short[][] block;
    static short[][] biome;
    static Player player = Player.getPlayer();
    static boolean isChangePosition;
    static boolean movedUp, movedRight;

    static {

        int j = 0;
        for (Chunck[] chuncks : world) {
            j++;
            for (int i = 0; i < chuncks.length; i++) {
                chuncks[i] = new Chunck();
                chuncks[i].x = i;
                chuncks[i].z = j;
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
        int chunckX = ((int) (player.getX() / 2) - (int) (player.getX() / 32) * 16);
        int chunckZ = ((int) (player.getZ() / 2) - (int) (player.getZ() / 32) * 16);
        isChangePosition = chunckX != playerX || chunckZ != playerZ;
        if (isChangePosition) {
            movedUp = playerZ < chunckZ;
            movedRight = playerX > chunckX;
        } else return;
        playerX = chunckX;
        playerZ = chunckZ;
    }


    public static int fixedIndex(int index) {
        return index >= 0 && index < 16 ? index : index >= 16 ? index - (index / 16) * 16 : index + (-index / 16 + 1) * 16;
    }


    public static Chunck getChunck(float x, float z) {
        x = (x / 2 - (int) (player.getX() / 2));
        z = (z / 2 - (int) (player.getZ() / 2));
        return world[fixedIndex((int) (x + playerX))][fixedIndex((int) (z + playerZ))];
    }

    public static boolean isCollision(float x, float z) {
        return getIntersectedTile(x, z) != null;
    }


    public static Tile getIntersectedTile(float x, float z){
        for (int x1 = -2; x1 < 5; x1++)
            for (int z1 = -2; z1 < 5; z1++) {
                Chunck chunck = getChunck(x+x1, z+z1);
                for (Tile tile :
                        chunck.getTiles()) {
                    if(tile.getID() == 0){
                        Gdx.app.log("ERROR","can't check is intersected tile, because blockID = 0");
                    }
                    PhysicBlockObject physicEntity = Block.getBlocks()[tile.getID()].getPhysicEntity();
                    physicEntity.setPosition(tile.getPosition());
                    if (physicEntity.containsXZ(x * 10, z * 10))
                        return tile;
                }
            }
        return null;
    }
    public static void setBlockToChunk(float x, float z, Tile tile) {
        Chunck chunck = getChunck(x, z);
        tile.setPosition(x * 10, 0, z * 10);
        chunck.setTile(tile);
    }
} 
