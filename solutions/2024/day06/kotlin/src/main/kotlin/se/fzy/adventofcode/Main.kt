package se.fzy.adventofcode

fun main() {
    val input = Resources.readInput("part1_sample.txt")

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
}
