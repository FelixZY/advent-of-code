package se.fzy.adventofcode

fun main() {
    val input = Resources.readInput("input.txt")

    val ruleSet: Map<Int, Set<Int>> =
        input.lineSequence().takeWhile { it.isNotBlank() }.takeWhile { it.isNotBlank() }
            .map { line -> line.split("|").let { it[0].toInt() to it[1].toInt() } }
            .fold(mutableMapOf<Int, MutableSet<Int>>()) { map, (dependsOn, key) ->
                map.apply {
                    getOrPut(key) { mutableSetOf() }.add(dependsOn)

                }
            }
    val jobs: List<List<Int>> =
        input.lineSequence().dropWhile { it.isNotBlank() }.drop(1).takeWhile { it.isNotBlank() }
            .map { it.split(',').map(String::toInt) }.toList()

    val sumOfMiddle = jobs.filter { job ->
        val remainingPageSet = job.toMutableSet()
        val completed = mutableSetOf<Int>()
        job.forEach { page ->
            remainingPageSet.remove(page)
            if (ruleSet[page]?.let {
                    (it - completed).intersect(remainingPageSet).isEmpty()
                } == false) {
                return@filter false
            }
            completed.add(page)
        }
        true
    }.sumOf { it[it.size / 2] }

    println(sumOfMiddle)
}
