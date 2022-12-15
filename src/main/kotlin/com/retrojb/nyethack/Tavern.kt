package com.retrojb.nyethack

import java.io.File
import kotlin.random.Random
import kotlin.random.nextInt

private const val TAVERN_MASTER = "Taernyl"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

private val firstNames = setOf("Alex", "Mordoc", "Sophie", "Tariq")
private val lastNames = setOf("Ironfoot", "Fernsworth", "Baggins", "Downstrider")

private val menuData = File("data/tavern-menu-data.txt")
    .readText()
    .split("\n")
    .map { it.split(",")}

private val menuItems = menuData.map{ (_, name, _) -> name }

private val menuItemPrices = menuData.associate {
   (_, name, price) -> name to price.toDouble()
}

private val menuItemTypes = menuData.associate {
    (type, name, _) -> name to type
}

class Tavern : Room(TAVERN_NAME) {
    val patrons: MutableSet<String> = firstNames.shuffled()
        .zip(lastNames.shuffled()) {
                firstName, lastName -> "$firstName $lastName"
        }.toMutableSet()

    val patronGold: MutableMap<String, Double> = mutableMapOf(
        TAVERN_MASTER to 86.00,
        player.name to 5.00,
        *patrons.map { it to 6.00 }.toTypedArray()
    )

    val itemOftheDay = patrons.flatMap { getFavoriteMenuItems(it) }.random()

    override val status = "Busy"
    override val lootBox: LootBox<Key> =
        LootBox(Key("key to Nogartse's evil lair"))
    override fun enterRoom() {
        narrate("${player.name} enters the loud and busy $TAVERN_NAME")
        narrate("There are several items for sale:")
        narrate(menuItems.joinToString())
        narrate("${player.name} sees several patrons in the tavern:")
        narrate(patrons.joinToString())

        narrate("The item of the day is $itemOftheDay")

        repeat(3) {
            placeOrder(patrons.random(), menuItems.random())
        }
    }

    private fun placeOrder(
        patronName: String,
        menuItemName: String,
    ) {
        val itemPrice = menuItemPrices.getValue(menuItemName)
        narrate("$patronName speaks with $TAVERN_MASTER to place an order")
        if (itemPrice <= patronGold.getOrDefault(patronName, 0.0)) {
            val action = when (menuItemTypes[menuItemName]) {
                "shandy", "elixer" -> "pours"
                "meal" -> "serves"
                else -> "hands"
            }
            narrate("$TAVERN_MASTER $action $patronName a $menuItemName")
            patronGold[patronName] = patronGold.getValue(patronName) - itemPrice
            patronGold[TAVERN_MASTER] = patronGold.getValue(TAVERN_MASTER) + itemPrice
        } else {
            narrate("$TAVERN_MASTER says, \"You need more coin for a $menuItemName\"")
        }
    }

}
private val tavernPlayList = mutableListOf<String>("Running with the Devil", "Hells Bells", "Devil is a lie")
//
//private fun displayPatronBalance(patronGold: Map<String, Double>) {
//    narrate("${player.name} intuitively knows how much money each patron has")
//    patronGold.forEach{ (patron, balance) ->
//        narrate("$patron has ${"%.2f".format(balance)} gold")
//
//    }
//}

private fun getFavoriteMenuItems(patron: String): List<String> {
    return when(patron){
        "Alex Ironfoot" -> menuItems.filter { menuItem -> menuItemTypes[menuItem]?.contains("dessert") == true
        } else -> menuItems.shuffled().take(Random.nextInt(1..2))
    }
}

//private fun tavernMusic(music: String): List<String> {
//    return when(music) {
//        "Hells Bells" ->
//    }
//}