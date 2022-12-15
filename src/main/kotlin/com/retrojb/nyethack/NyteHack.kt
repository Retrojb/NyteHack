package com.retrojb.nyethack

import kotlin.system.exitProcess

lateinit var player: Player
fun main() {
    if(::player.isInitialized) {
        narrate("Welcome to Dunegon")
    }
    val playerName = promptHeroName()
    player = Player(playerName)
//  changeNarratorMood()
    narrate("${player.name} is ${player.title} of the lands of ${player.homeTown}")
    player.prophesize()
    var currentRoom = TownSquare()
    narrate("${player.name}, ${player.title}, is in ${currentRoom.description()}")
    currentRoom.enterRoom()
    val lootBoxOne: LootBox<Gold> = LootBox(Gold( 20))
    val lootBoxTwo: LootBox<Gemstones> = LootBox(Gemstones(140))
    Game.play()
}



private fun promptHeroName(): String {
    narrate("A hero enters the town of Dragons Keep. What is your name?") {
        message -> // Prints the message in yellow
        "\u001b[33;1m$message\u001b[0m"
    }
    val input = readLine()
    require(input != null && input.isNotEmpty()) {
        "The hero must have a name"
    }
    return input

}

object Game {
    private val worldMap = listOf(
        listOf(TownSquare(), Tavern(), Room("Back Room")),
        listOf(MonsterRoom("Long Corridor"), Room("A Generic Room")),
        listOf(Room("Dungeon Entrance"), MonsterRoom("Dungeon"))
    )
    private var currentRoom: Room = worldMap[0][0]
    private var currentPosition = Coordinate(0, 0)
    init {
        narrate("Welcome, adventurer")
        val mortality = if (player.isImmortal) "an immortal" else "just a mere mortal"
        narrate("${player.name}, $mortality, has ${player.healthPoints} health points")
    }
    fun play() {
        while (true) {
            narrate("${player.name}, ${player.title}, is in ${currentRoom.description()}")
            currentRoom.enterRoom()
            print("> Enter your command: ")
            GameInput(readln()).processCommand()
        }
    }
    fun move(direction: Direction) {
        val newPosition = currentPosition move direction
        val newRoom = worldMap[newPosition].orEmptyRoom()

        narrate("The hero moves ${direction.name}")
        currentPosition = newPosition
        currentRoom = newRoom

    }
    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1) {""}

        fun processCommand() = when (command.lowercase()) {
            "fight" -> fight()
            "take" -> {
                if (argument.equals("loot", ignoreCase = true)) {
                    takeLoot()
                } else {
                    narrate("I guess no loot for you")
                }
            }
            "sell" -> {
                if( argument.equals("loot", ignoreCase = true)) {
                    sellLoot()
                } else {
                    narrate("Can't sell those things")
                }
            }
            "move" -> {
                val direction = Direction.values().firstOrNull{ it.name.equals(argument, ignoreCase = true)}
                if (direction != null) {
                    move(direction)
                } else {
                    narrate("I have no clue what direction that is")
                }
            }
            else -> narrate("I'm not sure what you're trying to do")
        }

        fun fight() {
            val monsterRoom = currentRoom as? MonsterRoom
            val currentMonster = monsterRoom?.creature
            if(currentMonster == null) {
                narrate("There's nothing to fight here")
                return
            }

            var combatRound = 0
            val previousNarrationModifier = narrationModifier
            narrationModifier = { it.addEnthusiasm(enthusiasmLevel = combatRound)}
            while (player.healthPoints > 0 && currentMonster.healthPoints > 0) {
                combatRound++
                player.attack(currentMonster)
                if(currentMonster.healthPoints > 0) {
                    currentMonster.attack(player)
                }
                Thread.sleep(1000)
                narrationModifier = previousNarrationModifier
                if( player.healthPoints <= 0) {
                    narrate("${player.name} has been defeated by ${currentMonster.name}")
                    exitProcess(0)
                } else {
                    narrate("${currentMonster.name} has been slayed")
                    monsterRoom.creature = null
                }
            }
        }

        fun takeLoot() {
            val loot = currentRoom.lootBox.takeLoot()
            if (loot == null) {
                narrate("${player.name} approaches a loot box, but its empty")
            } else {
                narrate("${player.name} now has a ${loot.name}")
                player.inventory += loot
            }
        }

        fun sellLoot() {
            when (val currentRoom = currentRoom) {
                is TownSquare -> {
                    player.inventory.forEach { item ->
                        if (item is Sellable) {
                            val sellPrice = currentRoom.sellLoot(item)
                            narrate("Sold ${item.name} for $sellPrice gold")
                            player.gold += sellPrice
                        } else {
                            narrate("Your ${item.name} can't be sold")
                        }
                    }
                    player.inventory.removeAll { it is Sellable }
                }
                else -> narrate("You can't sell anything here")
            }
        }
    }
}


