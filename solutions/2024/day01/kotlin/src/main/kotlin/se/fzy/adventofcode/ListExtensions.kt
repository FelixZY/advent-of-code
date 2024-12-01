package se.fzy.adventofcode

fun <T> List<List<T>>.transpose(): Sequence<List<T>> {
    if (isEmpty()) return emptySequence()
    require(all { it.size == first().size }) { "Cannot transpose lists of non-uniform size" }

    return sequence {
        first().indices.forEach { i ->
            yield(this@transpose.indices.map { j -> get(j)[i] }.toList())
        }
    }
}
