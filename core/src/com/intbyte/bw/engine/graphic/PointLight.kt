package com.intbyte.bw.engine.graphic

import com.badlogic.gdx.graphics.g3d.environment.PointLight
import com.badlogic.gdx.utils.Pool

class PointLight : Pool.Poolable,PointLight() {
    fun update(x: Float, y: Float, z: Float, intensity: Float){
        setPosition(x,y,z)

    }

    override fun reset() {
        TODO("Not yet implemented")
    }
}