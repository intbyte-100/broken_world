package com.intbyte.bw.engine.ui.container

import com.intbyte.bw.engine.item.Container

object SlotAllocateController {

    private var slotUID: Int = 0
    private val allocateContainers = HashSet<Container>()
    private val takenItems = TakenItems.getInstance()
    private val items = Container(0)

    @JvmStatic
    var isAllocate = false
    set(value){
        if (!value) allocateContainers.clear()
        field = value
    }
    @JvmStatic
    var isLockScroll = false



    @JvmStatic
    fun allocate(currentContainer: Container) {

        if (isAllocate&&slotUID != System.identityHashCode(currentContainer)) {
            slotUID = System.identityHashCode(currentContainer)


            val container = takenItems.selectItems

            if (currentContainer.items.notEmpty()&&currentContainer.id != container.id) return

            allocateContainers.add(container)
            allocateContainers.add(currentContainer)


            if (currentContainer.items.isEmpty&&container.countItems>1)
                equalization(container)
            else {
                allocateContainers.clear()
                allocateContainers.add(container)
                items.maxCountItems = 1;
                items.moveItems(container)
                currentContainer.moveItems(items)
            }
        }
    }

    private fun equalization(selectContainer: Container) {
        val container = Container(Int.MAX_VALUE)
        for (i in allocateContainers)
            container.moveItems(i)
        items.maxCountItems = (container.countItems / allocateContainers.size.toDouble()).toInt()
        for (i in allocateContainers) {
            items.moveItems(container)
            i.moveItems(items)
            if(items.items.notEmpty())
                container.moveItems(items)
        }
        if (container.items.notEmpty())
            selectContainer.moveItems(container)
    }
}