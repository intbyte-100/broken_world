package com.intbyte.bw.engine.world

import com.badlogic.gdx.utils.Pool

object TilePool : Pool<Tile>() {
    override fun newObject(): Tile {
        return Tile(0)
    }

}