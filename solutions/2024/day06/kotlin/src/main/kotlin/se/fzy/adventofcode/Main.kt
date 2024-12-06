package se.fzy.adventofcode

fun main() {
    part1()
}

fun part1() {
    val input = Resources.readInput("input.txt")

    val world = World.parse(input)
    var frame = 0
    world.addRenderer {
        frame++
        println("=== Frame $frame")
        for (y in 0..<it.worldHeight) {
            for (x in 0..<it.worldWidth) {
                print(
                    when (val obj = it.look(Position(x, y)).singleOrNull()) {
                        null -> '.'
                        else -> obj.repr()
                    }
                )
            }
            println()
        }
        println()
        Thread.sleep(500)
    }
    world.run()

    println(world.statisticUniqueGuardLocations)
}
