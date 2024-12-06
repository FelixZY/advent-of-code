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
        var frame: String? = null

        world.addRenderer(stringRenderer { frame = it })
        world.drawFrame()

        expectThat(frame?.trim()).isEqualTo(worldInput)
    }
}
