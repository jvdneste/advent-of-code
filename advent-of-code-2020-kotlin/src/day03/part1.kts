package day03

import java.io.File

class TobogganRide(input: List<String>) {
    private val mountain = Mountain(input)
    data class Mountain(val map: List<String>){
        fun hasTreeAt(toboggan: Toboggan) = map[toboggan.y][provideInfiniteWidth(toboggan)] == '#'

        private fun provideInfiniteWidth(toboggan: Toboggan) = toboggan.x % width

        private val width: Int
            get() = map[0].length
        val height: Int
            get() = map.size
    }
    data class Slope(val xStep: Int, val yStep: Int)
    data class Toboggan(val x: Int, val y: Int){
        operator fun plus(slope: Slope) = Toboggan(x + slope.xStep, y + slope.yStep)
    }

    fun countTrees(slope: Slope): Int {
        return trajectory(slope)
            .filter { mountain.hasTreeAt(it) }
            .count()
    }

    private fun trajectory(slope: Slope) = sequence {
        var currentToboggan = Toboggan(0, 0)
        for (row in 0 until mountain.height step slope.yStep)
        {
            yield(currentToboggan)
            currentToboggan += slope
        }
    }

    fun multiplicationForTrajectories(slopes: List<Slope>)
            = slopes
        .map { countTrees(it) + 0L }
        .reduce(Long::times)

}

val input = File("part1.txt").readLines()
val result = TobogganRide(input).countTrees(TobogganRide.Slope(3, 1))
println(result)
