package com.intbyte.bw.engine.render

import com.badlogic.gdx.graphics.PerspectiveCamera


interface Render {
    fun render()
    fun renderEntity()
    fun resize(width: Int, height: Int)
}