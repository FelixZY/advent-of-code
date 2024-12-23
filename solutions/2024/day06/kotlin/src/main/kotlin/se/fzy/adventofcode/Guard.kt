package se.fzy.adventofcode

class Guard(private var movementDirection: Direction) : WorldObject() {

    override fun repr(): Char =
        when (movementDirection) {
            Direction.UP -> '^'
            Direction.DOWN -> 'v'
            Direction.LEFT -> '<'
            Direction.RIGHT -> '>'
        }

    class Controller(guard: Guard, position: Position) :
        se.fzy.adventofcode.Controller<Guard>(guard) {
        private val trail: MutableMap<Position, MutableSet<Direction>> = mutableMapOf()
        private var reVisitStart: Position = position
        private var reVisitDirection: Direction = guard.movementDirection

        override fun onTick(accessor: World.Accessor) {
            val position = accessor.locate(obj)!!

            if (position.x !in 0..<accessor.worldWidth || position.y !in 0..<accessor.worldHeight) {
                accessor.queueAction { it.gameOver() }
                return
            }

            trail
                .getOrPut(position) {
                    accessor.incStatUniqGuardLocations()
                    mutableSetOf()
                }
                .also {
                    if (it.contains(obj.movementDirection)) {
                        accessor.queueAction { mutator ->
                            mutator.notifyGuardIsLooping()
                            mutator.gameOver()
                        }
                        return
                    } else {
                        it.add(obj.movementDirection)
                        reVisitStart = position
                        reVisitDirection = obj.movementDirection
                    }
                }

            when (obj.movementDirection) {
                Direction.UP ->
                    when {
                        accessor.look(position.copy(y = position.y - 1)).isNotEmpty() -> {
                            obj.movementDirection = Direction.RIGHT
                        }

                        else ->
                            accessor.queueAction { it.move(obj, position.copy(y = position.y - 1)) }
                    }

                Direction.DOWN ->
                    when {
                        accessor.look(position.copy(y = position.y + 1)).isNotEmpty() -> {
                            obj.movementDirection = Direction.LEFT
                        }

                        else ->
                            accessor.queueAction { it.move(obj, position.copy(y = position.y + 1)) }
                    }

                Direction.LEFT ->
                    when {
                        accessor.look(position.copy(x = position.x - 1)).isNotEmpty() -> {
                            obj.movementDirection = Direction.UP
                        }

                        else ->
                            accessor.queueAction { it.move(obj, position.copy(x = position.x - 1)) }
                    }

                Direction.RIGHT ->
                    when {
                        accessor.look(position.copy(x = position.x + 1)).isNotEmpty() -> {
                            obj.movementDirection = Direction.DOWN
                        }

                        else ->
                            accessor.queueAction { it.move(obj, position.copy(x = position.x + 1)) }
                    }
            }
        }
    }

    enum class Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }
}
