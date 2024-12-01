package se.fzy.adventofcode

fun main() {
    val (a, b) =
        Resources.readInput("puzzle_input.txt")
            .lineSequence()
            .filter { it.isNotBlank() }
            .map { line -> line.split("""\s+""".toRegex()).map(String::toInt) }
            .toList()
            .transpose()
            .map { it.sorted() }
            .toList()

    val similarity = a.sumOf { key -> key * b.count { key == it } }
    println(similarity)
}
