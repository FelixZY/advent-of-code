package se.fzy.adventofcode

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun main() = coroutineScope {
    part1()
    part2()
}

fun part1() {
    val input = Resources.readInput("input.txt")

    val world = World.parse(input)
    world.addRenderer(stdoutRenderer())
    world.run()

    println(world.statisticUniqueGuardLocations)
}

suspend fun part2() = coroutineScope {
    val input = Resources.readInput("input.txt")
    var lastIndex = 0

    val obstructionPositions =
        input
            .filter { it == '.' }
            .map { input.indexOf('.', startIndex = lastIndex).also { lastIndex = it + 1 } }
            .map { replaceIndex ->
                async(Dispatchers.IO) {
                    val world =
                        World.parse(
                            input.substring(0, replaceIndex) +
                                '#' +
                                input.substring(replaceIndex + 1)
                        )
                    world.run()
                    return@async world.guardIsLooping
                }
            }
            .awaitAll()
            .count { it }
    println(obstructionPositions)
}
