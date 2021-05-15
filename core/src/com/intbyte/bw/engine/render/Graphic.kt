package com.intbyte.bw.engine.render

import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider
import com.badlogic.gdx.scenes.scene2d.Stage
import com.intbyte.bw.engine.utils.ShaderContainer


object Graphic {
    private val shadowModelBatch = ModelBatch(DepthShaderProvider())

    @JvmField
    var stage = Stage()
    @JvmField
    val batch = stage.batch!!
    @JvmStatic
    var shadowMod = false
    @JvmStatic
    var modelBatch: ModelBatch
        private set
        get() {
            return if (shadowMod) shadowModelBatch else field
        }

    init {
        val shader = ShaderContainer("default")
        modelBatch = ModelBatch(DefaultShaderProvider(shader.vert, shader.frag))
        (modelBatch.shaderProvider as DefaultShaderProvider).config.numPointLights = 20
    }

}