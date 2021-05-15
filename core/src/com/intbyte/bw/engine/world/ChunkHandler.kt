package com.intbyte.bw.engine.world

interface ChunkHandler {
    fun getChunk(x: Float, z: Float): Chunk
    fun setTile(id: Int, x: Float, z: Float)
}