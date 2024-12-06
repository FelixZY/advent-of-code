package se.fzy.adventofcode

fun interface Action {
    fun perform(mutator: World.Mutator)
}
