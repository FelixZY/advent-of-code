package se.fzy.adventofcode

fun interface Renderer {
    fun render(worldAccessor: World.Accessor)
}

fun stringRenderer(onFrame: (String) -> Unit) = Renderer {
    val frame = StringBuilder()
    for (y in 0..<it.worldHeight) {
        for (x in 0..<it.worldWidth) {
            frame.append(
                when (val obj = it.look(Position(x, y)).singleOrNull()) {
                    null -> '.'
                    else -> obj.repr()
                }
            )
        }
        frame.append('\n')
    }
    onFrame(frame.toString())
}

fun stdoutRenderer(): Renderer {
    var frame = 0
    val innerRenderer = stringRenderer { println(it) }
    return Renderer {
        frame++
        println("=== Frame $frame")
        innerRenderer.render(it)
    }
}
