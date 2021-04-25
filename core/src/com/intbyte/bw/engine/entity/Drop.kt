package com.intbyte.bw.engine.entity

import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.intbyte.bw.engine.block.Block
import com.intbyte.bw.engine.graphic.Graphic
import com.intbyte.bw.engine.item.Container
import com.intbyte.bw.engine.physic.PhysicData
import com.intbyte.bw.engine.utils.gdx.renderWithTransform

open class Drop(blockID: Int, dropData: DropData) : Entity() {
    private var modelInstance: ModelInstance
    private var dropData: DropData
    override fun spawn() {
        val data = getBody().userData as PhysicData
        data.setObject(this)
        data.type = PhysicData.DROP
    }

    override fun getEntityModel(): ModelInstance {
        return modelInstance
    }

    override fun render() {
        renderWithTransform(0f,0f, 0f, Block.getBlock(bodyID).scale * 0.3f) {
            Graphic.getModelBatch().render(modelInstance, Graphic.getEnvironment(false))
        }
    }

    val drop: Container
        get() = dropData.get()

    init {
        rotate = Math.random().toFloat() * 360
        modelInstance = Block.getBlock(blockID).modelInstance
        this.dropData = dropData
    }
}