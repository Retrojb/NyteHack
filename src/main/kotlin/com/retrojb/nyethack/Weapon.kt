package com.retrojb.nyethack

open class Weapon(val name: String, val type: String) {
     var weaponName = name
        get() = "The Legendary $field"
         set(value) {
             field = value.lowercase().reversed().capitalize()
         }
    var weaponType = type
        get() = "A $field"
    init {
        this.weaponName = name
        this.weaponType = type
    }
    companion object {
        fun quickMeleeAttack(weaponName: String ,i: Int) {
            narrate("${player.name} swings $weaponName for $i damage");
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Weapon) return false

        other as Weapon

        if (name != other.name) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}

class Sword : Weapon("", "Swords") {

}

class ShortBows: Weapon("", "Bows") {

}

class LongBows: Weapon("", "Bows") {

}

class Wand: Weapon("", "Wands/Staffs") {

}

class ShortStaff: Weapon("", "Wands/Staffs"){

}

class LongStaff: Weapon("", "Wands/Staffs") {

}

class Flail: Weapon ("", "Maces") {

}

class Mace: Weapon ("", "Maces") {

}
