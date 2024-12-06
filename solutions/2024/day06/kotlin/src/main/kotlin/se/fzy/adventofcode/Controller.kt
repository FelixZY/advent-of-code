package se.fzy.adventofcode

sealed class Controller<T : WorldObject>(val obj: T) {
    abstract fun onTick(accessor: World.Accessor): Unit
}
