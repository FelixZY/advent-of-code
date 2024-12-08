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
            .mapValues { (_, antennas) -> calculateAntiNodes(antennas, mapBounds) }
            .values
            .asSequence()
            .flatten()
            .distinct()
            .count()

    println(antiNodeCount)
}

fun calculateAntiNodes(antennas: List<IntVector2d>, mapBounds: IntVector2d): Sequence<IntVector2d> =
    sequence {
        fun isInBounds(vector: IntVector2d): Boolean =
            vector.x >= 0 && vector.x < mapBounds.x && vector.y >= 0 && vector.y < mapBounds.y

        antennas.indices.forEach { i ->
            for (j in i + 1..antennas.lastIndex) {
                val betweenVector = antennas[j] - antennas[i]

                var aInBounds = true
                var bInBounds = true
                for (step in 0..Int.MAX_VALUE) {
                    if (aInBounds) {
                        (antennas[i] - betweenVector * step)
                            .takeIf { isInBounds(it).also { inBounds -> aInBounds = inBounds } }
                            ?.let { yield(it) }
                    }

                    if (bInBounds) {
                        (antennas[j] + betweenVector * step)
                            .takeIf { isInBounds(it).also { inBounds -> bInBounds = inBounds } }
                            ?.let { yield(it) }
                    }

                    if (!(aInBounds || bInBounds)) {
                        break
                    }
                }
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
