package se.fzy.adventofcode

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class WorldTest {
    @Test
    fun `Can render parsed world`() {
        val worldInput = """
            ....#.
            .#..##
            .#.#..
            ...<..
            ######
        """.trimIndent()
        val world = World.parse(worldInput)
        val frame = StringBuilder()
        world.addRenderer {
            for (y in 0..<it.worldHeight) {
                for (x in 0..<it.worldWidth) {
                    frame.append(
                        when (val obj = it.look(Position(x, y)).singleOrNull()) {
                            null -> '.'
                            else -> obj.repr()
                        },
                    )
                }
                frame.append('\n')
            }
        }
        world.drawFrame()

        expectThat(frame.toString().trim()).isEqualTo(worldInput)
    }
}
