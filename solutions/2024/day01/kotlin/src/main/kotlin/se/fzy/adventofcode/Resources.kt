package se.fzy.adventofcode

object Resources {
    fun readInput(fileName: String): String =
        javaClass.getResource("/$fileName").readText()
}
