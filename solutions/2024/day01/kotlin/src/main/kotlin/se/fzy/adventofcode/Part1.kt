package se.fzy.adventofcode

import kotlin.math.absoluteValue

fun main() {
    val (a, b) = Resources.readInput("input_part1.txt")
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

fun <T> List<List<T>>.transpose(): Sequence<List<T>> {
    if (isEmpty()) return emptySequence()
    require(all { it.size == first().size }) { "Cannot transpose lists of non-uniform size" }

    return sequence {
        first().indices.forEach { i ->
            yield(this@transpose.indices.map { j -> get(j)[i] }.toList())
        }
    }
}
