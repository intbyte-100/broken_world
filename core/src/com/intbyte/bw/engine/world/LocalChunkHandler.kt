package com.intbyte.bw.engine.world

import com.intbyte.bw.engine.entity.Player
import com.intbyte.bw.engine.render.PointLight
import com.intbyte.bw.engine.render.PointLightPool

@Suppress("NAME_SHADOWING")
class LocalChunkHandler(val player: Player) : ChunkHandler {
    private val world = Array(16) { Array(16) { Chunk() } }


    override fun setTile(id: Int, x: Float, z: Float) {
        val tile = TilePool.obtain()
        tile.setBlockID(id)
        if (tile.block.isGlowing) {
            tile.pointLight = PointLight()
            tile.block.pointLight.copy(tile.pointLight)
            tile.pointLight.enable()
            tile.pointLight.setPosition(x, z)

        }

        val chunk = World.getChunk(x, z)
        tile.setPosition(x * 10, 0f, z * 10)
        chunk.setTile(tile)

    }

    private fun fixedIndex(index: Int): Int {
        return if (index in 0..15) index else if (index >= 16) index - index / 16 * 16 else index + (-index / 16 + 1) * 16
    }

    override fun getChunk(x: Float, z: Float): Chunk {
        return World.world.get(World.fixedIndex(((x / 2 - (World.player.getX() / 2).toInt()) + World.playerX).toInt())).get(World.fixedIndex(((z / 2 - (World.player.getZ() / 2).toInt()) + World.playerZ).toInt()))}
}