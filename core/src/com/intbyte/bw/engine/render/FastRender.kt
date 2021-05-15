package com.intbyte.bw.engine.render

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera

open class FastRender : Render {
    override val camera = PerspectiveCamera(67f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    override fun render() {

    }

    override fun sceneRender() {

    }

    override fun landRender() {

    }

    override fun resize(width: Int, height: Int) {

    }

    fun cameraUpdate(x: Float, y: Float, z: Float){
        camera.position.set(x,y,z)
        camera.lookAt(x,y,z)
        camera.update()
    }
}