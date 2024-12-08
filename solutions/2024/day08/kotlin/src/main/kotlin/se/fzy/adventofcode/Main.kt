package se.fzy.adventofcode

fun main() {
    val input = Resources.readInput("input.txt").trim()
    val mapBounds =
        IntVector2d(
            x = input.lineSequence().take(1).single().length,
            y = input.lineSequence().count(),
        )
    val antennasByFrequency = parseAntennaMap(input)

    val antiNodeCount =
        antennasByFrequency
            .mapValues { (_, antennas) -> calculateAntiNodes(antennas) }
            .values
            .asSequence()
            .flatten()
            .distinct()
            .filter { it.x >= 0 && it.y >= 0 && it.x < mapBounds.x && it.y < mapBounds.y }
            .count()

    println(antiNodeCount)
}

fun calculateAntiNodes(antennas: List<IntVector2d>): Sequence<IntVector2d> = sequence {
    antennas.indices.forEach { i ->
        for (j in i + 1..antennas.lastIndex) {
            val betweenVector = antennas[j] - antennas[i]

            yield(antennas[i] - betweenVector)
            yield(antennas[j] + betweenVector)
        }
    }
}

fun parseAntennaMap(antennaMap: String): Map<Char, List<IntVector2d>> {
    val antennasByFrequency = mutableMapOf<Char, MutableList<IntVector2d>>()

    antennaMap.lineSequence().forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            if (char != '.') {
                antennasByFrequency.getOrPut(char, ::mutableListOf).add(IntVector2d(x, y))
            }
        }
    }

    return antennasByFrequency
}
