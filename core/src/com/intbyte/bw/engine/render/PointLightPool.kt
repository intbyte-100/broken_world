package com.intbyte.bw.engine.render

import com.badlogic.gdx.utils.Pool

object PointLightPool : Pool<PointLight>() {
    override fun newObject() = with(PointLight()){
        enable()
    }
}