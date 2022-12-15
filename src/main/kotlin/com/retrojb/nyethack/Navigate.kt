package com.retrojb.nyethack

data class Coordinate(val x: Int, val y: Int) {
    operator fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)
}

enum class Direction ( private val directionCoordinate: Coordinate) {
    NORTH(Coordinate(0, -1)),
    NORTH_EAST(Coordinate(1, -1)),
    EAST(Coordinate(1, 0)),
    SOUTH_EAST(Coordinate(1, 1)),
    SOUTH(Coordinate(0, 1)),
    SOUTH_WEST(Coordinate(-1, 1)),
    WEST(Coordinate(-1, 0)),
    NORTH_WEST(Coordinate(-1, -1));

    fun updateCoordinate(coordinate: Coordinate) =
        coordinate + directionCoordinate
}