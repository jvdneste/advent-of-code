package day03

import java.io.File

data class Slope(val xStep: Int, val yStep: Int)
data class Position(val x: Int, val y: Int) {
    infix operator fun plus(slope: Slope) = Position(x + slope.xStep, y + slope.yStep)
}

data class Mountain(val map: List<String>) {

    fun hasTreeAt(position: Position) = map[position.y][position.x % width] == '#'

    private val width: Int
        get() = map[0].length
    val height: Int
        get() = map.size
}

fun countTrees(mountain: Mountain, slope: Slope): Int {
    return trajectory(mountain, slope)
        .filter { mountain.hasTreeAt(it) }
        .count()
}

fun trajectory(mountain: Mountain, slope: Slope): Sequence<Position> {
    val seed = Position(0, 0)
    return generateSequence(seed, { it plus slope })
        .takeWhile { it.y < mountain.height };
}

fun main() {
    val slopes = listOf(
        Slope(1, 1),
        Slope(3, 1),
        Slope(5, 1),
        Slope(7, 1),
        Slope(1, 2)
    )
    val input = File("part1.txt").readLines()
    val mountain = Mountain(input)
    val result = slopes.map { countTrees(mountain, it).toLong() }.reduce(Long::times)
    println(result)
}

main()
