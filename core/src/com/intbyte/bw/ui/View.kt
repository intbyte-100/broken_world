package com.intbyte.bw.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.IntMap
import com.badlogic.gdx.utils.IntSet


const val CENTER = 0
const val TOP = 1
const val BOTTOM = 2
const val LEFT = 3
const val RIGHT = 4

//auto corroded according to the height of the parent
const val HP = 0;

//auto corroded according to the weight of the parent
const val WP = 1

//auto corrects the height by the height of the parent, and the width by the width of the parent
const val WHP = 2

@Suppress("UNCHECKED_CAST")
interface View<T> {

    val property: IntSet
    val tiedTo: IntMap<View<*>>
    var rawWeight: Float
    var rawHeight: Float
    var heightUnit: Int
    var weightUnit: Int

    fun center(): T {
        property.add(CENTER)
        return this as T
    }

    fun right(): T {
        property.add(RIGHT)
        return this as T
    }

    fun left(): T {
        property.add(LEFT)
        return this as T
    }

    fun bottom(): T {
        property.add(BOTTOM)
        return this as T
    }

    fun top(): T {
        property.add(TOP)
        return this as T
    }

    fun tied(side: Int, view: View<*>): T {
        tiedTo.put(side, view)
        return this as T
    }

    fun resetLayout(): T {
        tiedTo.clear()
        property.clear()
        return this as T
    }

    fun size(sizeUnit: Int, weight: Float, height: Float): T {
        return size(sizeUnit, sizeUnit, weight, height)
    }

    fun size(weightUnit: Int, heightUnit: Int, weight: Float, height: Float): T {
        this.weightUnit = weightUnit
        this.heightUnit = heightUnit
        rawWeight = weight
        rawHeight = height
        return this as T
    }


    fun updateLayout() {

    }

    fun onClick(acton: (x: Float, y: Float) -> Unit): T {
        return this as T
    }


    fun getY(): Float
    fun getX(): Float
}