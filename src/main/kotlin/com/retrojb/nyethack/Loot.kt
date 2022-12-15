package com.retrojb.nyethack

import kotlin.random.Random
import kotlin.random.nextInt

interface Sellable {
    val value: Int
}
abstract class Loot {
    abstract val name: String
}

abstract class Precious : Loot(), Sellable
// This is an example of a producer
class LootBox<out T: Loot>(val contents: T) {
    var isOpen = false
        private set

    fun takeLoot(): T? {
        return contents.takeIf { !isOpen }
            .also { isOpen = true}
    }

    inline fun <reified U> takeLootOfType(): U? {
        return if (contents is U) {
            takeLoot() as U
        } else {
            null
        }
    }

    companion object {
        fun random(): LootBox<Loot> = LootBox(
            contents = when (Random.nextInt(1..100)) {
                in 1..5 -> Clothing("fez of immaculate style", 150)
                in 6..10 -> Clothing("fedora of knowledge", 125)
                in 11..15 -> Clothing("stunning teal fedora", 75)
                in 16..30 -> Clothing("ordinary fez", 15)
                in 31..50 -> Clothing("ordinary fedora", 10)
                else -> Gemstones(Random.nextInt(50..100))
            } )
    }
}

// This is an example of a consumer
class DropOffBox<in T> where T : Loot, T: Sellable {
    fun sellLoot(sellableLoot: T): Int {
        return (sellableLoot.value * 0.7).toInt()
    }
}
/*
* To sell something based on casting
*
*  val clothingDropBox: DropOffBox<Clothing> = DropOffBox()
*  val specificClothingItemDropBox: DropOffBox<SpecificClothing> = DropOffBox()
* */
class Clothing(
    override val name: String,
    override val value: Int
) : Loot(), Sellable

class Gemstones(
    override val value: Int
): Loot(), Sellable {
    override val name = "A sack of gemstones worth $value gold"
}

class Key(
    override val name: String
): Loot()

class Gold(
    override val value: Int
) : Loot(), Sellable {
    override val name = "A sack of gold worth $value"
}

