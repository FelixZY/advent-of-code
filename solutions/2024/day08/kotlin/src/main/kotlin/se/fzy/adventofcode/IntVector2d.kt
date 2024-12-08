package se.fzy.adventofcode

data class IntVector2d(val x: Int, val y: Int) {

    operator fun plus(other: IntVector2d) = IntVector2d(x + other.x, y + other.y)

    operator fun minus(other: IntVector2d) = IntVector2d(x - other.x, y - other.y)

    operator fun times(scalar: Int) = IntVector2d(x * scalar, y * scalar)

    operator fun unaryMinus() = IntVector2d(-x, -y)
}
