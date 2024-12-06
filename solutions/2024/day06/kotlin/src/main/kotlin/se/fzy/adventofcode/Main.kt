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


    println("Part 1: ${part1(ruleSet, jobs)}")
    println("Part 2: ${part2(ruleSet, jobs)}")
}

fun part1(ruleSet: Map<Int, Set<Int>>, jobs: List<List<Int>>): Int = jobs.filter { job ->
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

fun part2(ruleSet: Map<Int, Set<Int>>, jobs: List<List<Int>>): Int = jobs.mapNotNull { job ->
    val remainingPageSet = job.toMutableSet()
    val completed = mutableSetOf<Int>()
    var modifiedList: MutableList<Int>? = null

    var i = 0
    while (i < job.size) {
        val pages = (modifiedList ?: job)

        val page = pages[i]
        val requiredPages = ruleSet[page]
        if (requiredPages == null) {
            completed.add(page)
            i++
            continue
        }

        val unfulfilledDependencies = (requiredPages - completed).intersect(remainingPageSet)
        if (unfulfilledDependencies.isEmpty()) {
            completed.add(page)
            i++
            continue
        }

        modifiedList = modifiedList ?: job.toMutableList()

        val moveTo = pages.indexOfLast { unfulfilledDependencies.contains(it) }
        require(moveTo > i)

        modifiedList.add(moveTo, modifiedList.removeAt(i))
    }

    modifiedList
}.sumOf { it[it.size / 2] }
