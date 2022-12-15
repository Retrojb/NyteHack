package com.retrojb.nyethack

abstract class Monster(
    override val name: String,
    val description: String,
    override var healthPoints: Int
) : Fightable {
    override fun takeDamage(damage: Int) {
        healthPoints -= damage
    }
}

class Goblin(
    name: String = "Goblin",
    description: String = "A nasty little bastard",
    healthPoints: Int = 20
) : Monster(name, description, healthPoints) {
    override val diceSides = 8
    override val diceCount = 2
}

class Dragon(
    name: String = "Dragon",
    description: String = "A giant scaly lizard",
    healthPoints: Int = 60
) : Monster(name, description, healthPoints) {
    override val diceSides = 12
    override val diceCount = 3
}

class Draugr(
    name: String = "Draugr",
    description: String = "An undead",
    healthPoints: Int = 25
) : Monster(name, description, healthPoints) {
    override val diceSides = 8
    override val diceCount = 2
}

class Werewolf(
    name: String = "Werewolf",
    description: String = "Wasn't that just a person",
    healthPoints: Int = 40
) : Monster(name, description, healthPoints) {
    override val diceSides = 8
    override val diceCount = 2
}