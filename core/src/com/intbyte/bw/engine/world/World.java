package com.intbyte.bw.engine.world;

import com.badlogic.gdx.Gdx;
import com.intbyte.bw.engine.entity.Player;
import com.intbyte.bw.engine.physic.PhysicBlockObject;
import com.intbyte.bw.engine.block.Block;
import com.intbyte.bw.engine.utils.Debug;

public final class World {

    public static Chunk[][] world = new Chunk[16][16];
    public static ChunkHandler handler;
    public static int playerX, playerZ;
    static short[][] landBlock;
    static short[][] block;
    static Player player = Player.getPlayer();
    static boolean isChangePosition;
    static boolean movedUp, movedRight;
    static WorldConfig config;
    static {
        int j = 0;
        for (Chunk[] chunks : world) {
            j++;
            for (int i = 0; i < chunks.length; i++) {
                chunks[i] = new Chunk();
                chunks[i].x = i;
                chunks[i].z = j;
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
        handler.setTile(id, x, z);
    }



    public static void update() {
        Debug.valueInfo("tiles in pool", TilePool.INSTANCE.getFree());
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


    public static Chunk getChunk(float x, float z) {
        return handler.getChunk(x, z);
    }

    public static boolean isCollision(float x, float z) {
        return getIntersectedTile(x, z) != null;
    }


    public static Tile getIntersectedTile(float x, float z){
        for (int x1 = -2; x1 < 5; x1++)
            for (int z1 = -2; z1 < 5; z1++) {
                Chunk chunk = getChunk(x+x1, z+z1);
                for (Tile tile :
                        chunk.getTiles()) {
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
} 
