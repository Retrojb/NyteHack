package com.retrojb.nyethack

class Villager(val name: String, val homeTown: String) {

    val personality:String
    val race = "Dwarf"
    var age = 50
        private set

    init {
        println("Creatinga  new villager")
        personality = "Outgoing"
    }
    constructor(name: String) : this (name, "Bavaria") {
        age = 99
    }
}