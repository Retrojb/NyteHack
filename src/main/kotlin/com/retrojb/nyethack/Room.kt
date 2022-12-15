package com.retrojb.nyethack


// Inheritance Example TownSquare is a subclass of a room
// Think of Room as where the player is on the game map
open class Room(val name: String) {
    open val lootBox: LootBox<Loot> = LootBox.random()
    protected open val status = "calm"
    open fun description() = "$name (Currently: $status)"

    open fun enterRoom() {
        narrate("There is nothing to do in this room")
    }
}

class TownSquare : Room("The Town Square") {
    override val status = "Bustling"
    private var bellSound = "GWONG!!!"
    private val preciousDropBox = DropOffBox<Precious>()
    private val clothingDropBox = DropOffBox<Clothing>()
    override fun enterRoom() {
        narrate("The Villagers cheer as the hero enters")
        ringBell(3)
    }
    open fun ringBell(n: Int?) {
        narrate("The high bell tower at the center of the square makes a loud ${n?.let { bellSound.repeat(it) }}")
    }

    fun <T> sellLoot(
        loot: T
    ): Int where T : Loot, T : Sellable {
        return when (loot) {
            is Precious -> preciousDropBox.sellLoot(loot)
            is Clothing -> clothingDropBox.sellLoot(loot)
            else -> 0
        }
    }
}


class BackRoom : Room("Back Room") {

    override fun enterRoom() {
        narrate("What are you doing back here hero")
    }
}

open class MonsterRoom(
    name: String,
    var creature: Monster? = Goblin()
) : Room(name) {
    override fun description(): String {
        return super.description() + "(Creature: ${creature?.description ?: "None"}"
    }

    override fun enterRoom() {
        if (creature == null) {
            super.enterRoom()
        } else {
            narrate("Danger is lurking in $name")
        }
    }
}