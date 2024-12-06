package se.fzy.adventofcode

class World
private constructor(
    private val width: Int,
    private val height: Int,
    private val objectsByPosition: MutableMap<Position, MutableList<WorldObject>>,
    private val positionByObject: MutableMap<WorldObject, Position>,
    private val controllers: MutableList<Controller<*>>,
) {
    private val actionQueue = ArrayDeque<Action>()
    private val renderers = mutableListOf<Renderer>()
    private val accessor = Accessor()
    private val mutator = Mutator()
    private var isGameOver: Boolean = false

    fun addRenderer(renderer: Renderer) {
        renderers.add(renderer)
    }

    fun removeRenderer(renderer: Renderer) {
        renderers.remove(renderer)
    }

    fun run() {
        while (!isGameOver) {
            tick()
        }
    }

    fun tick() {
        if (isGameOver) return
        controllers.forEach { it.onTick(accessor) }
        val action = actionQueue.removeFirstOrNull() ?: return
        action.perform(mutator)
        drawFrame()
    }

    fun drawFrame() {
        renderers.forEach { it.render(accessor) }
    }

    open inner class Accessor {
        val worldWidth: Int = width
        val worldHeight: Int = height

        fun look(position: Position): List<WorldObject> =
            objectsByPosition.getOrElse(position, ::emptyList)

        fun locate(obj: WorldObject) = positionByObject[obj]

        fun queueAction(action: Action) {
            actionQueue.add(action)
        }
    }

    inner class Mutator : Accessor() {
        fun gameOver() {
            isGameOver = true
        }

        fun move(obj: WorldObject, to: Position) {
            positionByObject
                .getOrPut(obj) { to }
                .let { from ->
                    objectsByPosition[from]?.remove(obj)
                    if (objectsByPosition[from]?.isEmpty() == true) {
                        objectsByPosition.remove(from)
                    }
                }

            positionByObject[obj] = to
            objectsByPosition.getOrPut(to, ::mutableListOf).add(obj)
        }
    }

    companion object {
        fun parse(input: String): World {
            val objectsByPosition = mutableMapOf<Position, MutableList<WorldObject>>()
            val positionByObject = mutableMapOf<WorldObject, Position>()
            val controllers = mutableListOf<Controller<*>>()
            var x = 0
            var y = 0
            input.trim().forEach {
                when (it) {
                    '\n' -> {
                        x = 0
                        y++
                        return@forEach
                    }

                    '#' -> Obstacle()
                    '^' -> Guard(Guard.Direction.UP)
                    '<' -> Guard(Guard.Direction.LEFT)
                    '>' -> Guard(Guard.Direction.RIGHT)
                    'v' -> Guard(Guard.Direction.DOWN)
                    else -> null
                }?.let { toAdd ->
                    if (toAdd is Guard) {
                        controllers.add(Guard.Controller(toAdd))
                    }
                    objectsByPosition.getOrPut(Position(x, y), ::mutableListOf).add(toAdd)
                    positionByObject[toAdd] = Position(x, y)
                }
                x++
            }
            return World(x, y + 1, objectsByPosition, positionByObject, controllers)
        }
    }
}
