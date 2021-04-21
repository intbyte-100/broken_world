package com.intbyte.bw.engine.utils.gdx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g3d.ModelBatch

inline fun ModelBatch.render(camera: PerspectiveCamera, render: () -> Unit){
    clear()
    begin(camera)
    render()
    end()
}

inline fun SpriteBatch.render(render: () -> Unit){
    begin()
    render()
    end()
}

fun clear(){
    Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
}