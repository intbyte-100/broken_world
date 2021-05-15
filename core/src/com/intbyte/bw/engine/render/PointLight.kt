package com.intbyte.bw.engine.render

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.graphics.g3d.environment.PointLight as GdxPointLight
import com.badlogic.gdx.utils.Pool
import com.intbyte.bw.engine.GameThread
import com.intbyte.bw.engine.entity.Player
import com.intbyte.bw.engine.utils.gdx.each

private val pointLights = Array<PointLight>()
private val player = Player.getPlayer()

fun updatePointLights() {
    pointLights.each {
        it.pointLight.position.set((it.position.x - player.x) * 10f + GameThread.xDraw, it.pointLight.position.y, (it.position.y - player.z) * 10f + GameThread.zDraw)
        it.landPointLight.position.set((it.position.x - player.x) * 10f + GameThread.xDraw, 30f, (it.position.y - player.z) * 10f + GameThread.zDraw)

    }
}

class PointLight : Pool.Poolable {
    val position = Vector2()
    val landPointLight = GdxPointLight()
    val pointLight = GdxPointLight()

    fun set(r: Float, g: Float, b: Float, x: Float, y: Float, z: Float) {
        landPointLight.position.set(x, y, z)
        landPointLight.color.set(r, g, b, 1f)
        pointLight.position.set(x, y, z)
        pointLight.color.set(r, g, b, 1f)
    }

    fun setPosition(x: Float, z: Float) {
        position.set(x, z)
    }

    fun enable(): PointLight {
        GlobalEnvironment.getEnvironment(true).add(landPointLight)
        GlobalEnvironment.getEnvironment(false).add(pointLight)
        pointLights.add(this)
        return this
    }

    fun disable(): PointLight {
        GlobalEnvironment.getEnvironment(true).remove(landPointLight)
        GlobalEnvironment.getEnvironment(false).remove(pointLight)
        pointLights.removeValue(this, true)
        return this
    }

    override fun reset() {

    }

    fun remove() {
        disable()
        PointLightPool.free(this)
    }


    fun copy(pointLight: PointLight): PointLight {
        pointLight.pointLight.set(this.pointLight)
        pointLight.landPointLight.set(landPointLight)
        return pointLight
    }

}