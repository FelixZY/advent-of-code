package se.fzy.adventofcode

fun interface Renderer {
    fun render(worldAccessor: World.Accessor)
}
