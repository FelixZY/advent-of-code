package se.fzy.adventofcode

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class Obstacle private constructor(private val id: Uuid) : WorldObject() {
    constructor() : this(Uuid.random())

    override fun repr(): Char = '#'

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Obstacle

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
