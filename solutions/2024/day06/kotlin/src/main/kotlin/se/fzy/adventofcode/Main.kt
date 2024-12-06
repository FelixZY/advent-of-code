package se.fzy.adventofcode

fun main() {
    part1()
}

fun part1() {
    val input = Resources.readInput("input.txt")

    val world = World.parse(input)
    world.addRenderer(stdoutRenderer())
    world.run()

    println(world.statisticUniqueGuardLocations)
}
