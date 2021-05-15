package com.intbyte.bw.engine.render

import com.badlogic.gdx.graphics.PerspectiveCamera


interface Render {
    val camera: PerspectiveCamera
    fun render()
    fun sceneRender()
    fun landRender()
    fun resize(width: Int, height: Int)
}