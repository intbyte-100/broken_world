package com.intbyte.bw.engine.render


import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight
import com.badlogic.gdx.utils.Pool
import com.intbyte.bw.engine.graphic.PointLight


object GlobalEnvironment {
    private val pointLightsPool = PointLightsPool()
    private val landEnvironment = Environment()
    private val environment = Environment()
    private val landLight = DirectionalLight()
    private val environmentAmbientLight = ColorAttribute(ColorAttribute.AmbientLight, 0.1f, 0.1f, 0.1f, 1f)
    private val landEnvironmentAmbientLight = ColorAttribute(ColorAttribute.AmbientLight, 0.2f, 0.2f, 0.2f, 1f)
    val shadowLight = DirectionalShadowLight(2000, 2000, 150f, 100f, 1f, 200f)
    @JvmStatic
    var intensity = 0.999999f
        set(value) {
            field = if (value >= 1f) 0.99f else value
        }
    var pointLightIntensity = 1f; private set

    init {
        environment.set(environmentAmbientLight)
        environment.shadowMap = shadowLight
        environment.add(DirectionalLight().set(0.1f, 0.1f, 0.2f, 0f, -20f, 0f))
        environment.add(DirectionalLight().set(0.1f, 0.1f, 0.1f, 0f, -20f, 0f))
        environment.add(shadowLight)

        landEnvironment.set(landEnvironmentAmbientLight);
        landEnvironment.shadowMap = shadowLight
        landEnvironment.add(landLight)
    }


    @JvmStatic
    fun getEnvironment(isLandBlock: Boolean) = if (isLandBlock) landEnvironment else environment

    @JvmStatic
    fun update() {
        val landIntensity = 1 / (-(intensity - 1) * 10)
        var ambient = (intensity - 0.5f) / (0.5f / 4) / 10
        if (ambient <= 0.1f) ambient = 0.1f
        environmentAmbientLight.color.set(ambient, ambient, ambient, 1f)
        environment.set(environmentAmbientLight)
        intensity *=0.6f;
        shadowLight.set(intensity, intensity, intensity, -1f, -1f, 1f)
        landLight.set(landIntensity, landIntensity, landIntensity, -1f, -1f, 1f)
    }
}

private class PointLightsPool : Pool<PointLight>() {
    override fun newObject() = PointLight()
}