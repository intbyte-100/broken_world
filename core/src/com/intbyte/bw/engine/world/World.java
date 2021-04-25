package com.intbyte.bw.engine.world;

import com.badlogic.gdx.Gdx;
import com.intbyte.bw.engine.entity.Player;
import com.intbyte.bw.engine.physic.PhysicBlockObject;
import com.intbyte.bw.engine.block.Block;

public final class World {

    public static Chunck[][] world = new Chunck[16][16];
    public static int playerX, playerZ;
    static short[][] landBlock;
    static short[][] block;
    static Player player = Player.getPlayer();
    static boolean isChangePosition;
    static boolean movedUp, movedRight;
    static WorldConfig config;
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


    public static WorldConfig getConfig() {
        return config;
    }

    public static void createVoidWorld(int width, int height, WorldConfig settings) {
        block = new short[width * 32][height * 32];
        landBlock = new short[width * 32][height * 32];
        World.config = settings;
    }


    public static int getLandBlock(int x, int z) {
        if (x > -1 && z > -1 && x < block.length && z < block.length)
            return landBlock[x][z];
        return 0;
    }

    public static void setBlock(float x, float z, int id) {
        setBlockToChunk(x,z,new Tile(id));
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
        if (index >= 0 && index < 16)
            return index;
        else if (index >= 16)
            return index - (index / 16) * 16;
        return index + (-index / 16 + 1) * 16;
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
                    if (physicEntity.containsXZ(x, z))
                        return tile;
                }
            }
        return null;
    }
    public static void setBlockToChunk(float x, float z, Tile tile) {
        Chunck chunck = getChunck(x, z);
        tile.setPosition(x, 0, z);
        chunck.setTile(tile);
    }
} 
