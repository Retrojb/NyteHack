package com.retrojb.nyethack

open class Armor(var name: String, val armorRating: Int) {
    var baseArmorRating = armorRating
        get() = 3
        set(value) {
            if( player.playerClass == "Warrior") {
                field = value.plus(3)
            }
        }
}

class BodyArmor: Armor("", 6) {}

class ArmArmor: Armor("", 3){}

class LegArmor: Armor("", 4) {}

class Belt: Armor("", 2){}

class Helm: Armor("", 4){}

class Gloves: Armor("", 3){}

class Boots: Armor("", 3){}