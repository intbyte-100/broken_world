package com.intbyte.bw.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.IntMap
import com.badlogic.gdx.utils.IntSet


open class PrimaryView<T : PrimaryView<T>> : View<T>, Actor() {
    override val property = IntSet()
    override val tiedTo = IntMap<View<*>>()
    override var rawWeight = 0f
    override var rawHeight = 0f
    override var heightUnit = 0
    override var weightUnit = 0
}