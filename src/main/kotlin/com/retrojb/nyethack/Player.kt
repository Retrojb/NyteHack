package com.retrojb.nyethack

import java.io.File

val weapon = Weapon("Crystal Sword", "sword")
var inventory = Inventory()
class Player (
    initialName: String,
    val homeTown: String = "Dayton",
    override var healthPoints: Int,
    val isImmortal: Boolean,
) : Fightable {
    override var name = initialName
        get() = field.replaceFirstChar { it.uppercase() }
        private set(value) { field = value.trim()}

    override val diceCount = 3

    override val diceSides = 4

    override fun takeDamage(damage: Int) {
        if( !isImmortal) {
            healthPoints -= damage
        }
    }

    var playerClass: String = "Warrior"
    val title: String
        get() = when {
            name.all { it.isDigit() } -> "The Identifiable"
            name.none { it.isLetter() } -> "The Witness Protectionor"
            name.numVowels > 4 -> "The Vowel Master"
            else -> "The Renowned Hero"
        }
    init {
        require(healthPoints > 0) { "healthPoints must be greater than zero"}
        require(name.isNotBlank()) { "Player must have a name"}
        val baseInventory = listOf("torch", "waterskin")
        val playerClass: String = "archer"
        val classInventory = when (playerClass) {
            "archer" -> listOf("arrows")
            "wizard" -> listOf("spells")
            else -> emptyList()
        }
//        inventory = baseInventory + classInventory

    }
    constructor(name: String) : this(
        initialName = name,
        healthPoints = 100,
        isImmortal = false
    ) {
        if (name.equals("John", ignoreCase = true)) {
            healthPoints = 500
        }
    }

//    constructor(saveFileBytes: ByteArray) : this()
//    companion object{
//        private const val SAVE_FILE_NAME = "player.dat"
//        fun fromSaveFile() = Player(File(SAVE_FILE_NAME).readBytes())
//    }
    fun castFireBall(numFireballs: Int = 2) {
        narrate("A roaring hot ball of fire (x$numFireballs)")
    }
    val prophecy by lazy {
        narrate("$name embarks on an arduous quest to locate the Wizard of Charm")
        Thread.sleep(3000)
        narrate("The Wizard Quark bestows a prophecy upon $name")

        "An intrepid hero from $homeTown shall some day" + listOf(
            "form an unlikely bond with two warring factions",
            "take possesion of the either blade",
            "bring gifts back to the people of $homeTown",
            "become the world eater"
        ).random()
    }
    val inventory = mutableListOf<Loot>()
    var gold = 0
    fun changeName(newName: String) {
        narrate("$name legally changes their name to $newName")
        name = newName
    }

    fun prophesize() {
        narrate("$name thinks about their future")
        narrate("The Wizard tells $name, \"$prophecy\"")


    }

    fun useWeapon() {
        Weapon.quickMeleeAttack("Mjolnir", 3);
    }
}