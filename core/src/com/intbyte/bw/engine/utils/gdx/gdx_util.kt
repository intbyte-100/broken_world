package com.intbyte.bw.engine.utils.gdx

import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g3d.ModelBatch

inline fun ModelBatch.render(camera: PerspectiveCamera, render: () -> Unit){
    begin(camera)
    render()
    end()
}

inline fun SpriteBatch.render(render: () -> Unit){
    begin()
    render()
    end()
}