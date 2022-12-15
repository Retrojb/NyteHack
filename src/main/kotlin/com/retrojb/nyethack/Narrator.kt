package com.retrojb.nyethack

import kotlin.random.Random
import kotlin.random.nextInt

inline fun narrate(
    message: String,
    modifier: (String) -> String = { narrationModifier(it) }
){
    println(narrationModifier(message))
}
var narrationModifier: (String) -> String = { it }

fun changeNarratorMood() {
    val mood: String
    val modifier: (String) -> String

    when (Random.nextInt(1..4)) {
        1 -> {
            mood = "loud"
            modifier = { message ->
                val numExclamationPoints = 3
                message.uppercase()+"!".repeat(numExclamationPoints)
            }
        }
        2 -> {
            mood = "tired"
            modifier = { message ->
                message.lowercase().replace(" ", "... ")
            }
        }
        3 -> {
            mood = "unsure"
            modifier = { message ->
                "$message?"
            }
        }
        else -> {
            mood = "professional"
            modifier = { message ->
                "$message."
            }
        }
    }

    narrationModifier = modifier
    narrate("The narrator beings to feel $mood")
}
