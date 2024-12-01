package se.fzy.adventofcode

import kotlin.math.absoluteValue

fun main() {
    val (a, b) = Resources.readInput("puzzle_input.txt")
        .lineSequence()
        .filter { it.isNotBlank() }
        .map { line -> line.split("""\s+""".toRegex()).map(String::toInt) }
        .toList()
        .transpose()
        .map { it.sorted() }
        .toList()

    val distance = a.zip(b).sumOf { (a, b) -> (a - b).absoluteValue }
    println(distance)
}
