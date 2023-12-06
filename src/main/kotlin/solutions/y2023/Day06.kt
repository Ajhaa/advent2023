package solutions.y2023

import solutions.PuzzleInput
import solutions.Solution
import kotlin.math.*

class Day06 : Solution {

    data class Race(
        val time: Long,
        val distance: Long,
    )

    private fun solveQuadratic(a: Double, b: Double, c: Double) : Pair<Double, Double> {
        val sqrtPart = sqrt(b * b - 4 * a * c)
        val sol1 = (-b + sqrtPart) / 2 * a
        val sol2 = (-b - sqrtPart) / 2 * a
        return Pair(sol1, sol2)
    }

    private fun raceFromPair(pair: Pair<Long, Long>) : Race {
        return Race(pair.first, pair.second)
    }

    private fun countSolutions(race: Race) : Int {
        val (sol1, sol2) = solveQuadratic(1.0, -race.time.toDouble(), race.distance.toDouble())

        return abs(sol1.toInt() - sol2.toInt())
    }

    private val whitespaceRegex = Regex("\\s+")
    private fun parseNumberStrings(str: String): List<String> {
        return str.split(":")[1]
            .split(whitespaceRegex)
            .filter { it.isNotBlank() }
    }

    override fun answerPart1(input: PuzzleInput): Any {
        val lines = input.lines
        val times = parseNumberStrings(lines[0]).map(String::toLong)
        val distances = parseNumberStrings(lines[1]).map(String::toLong)

        val races = times.zip(distances).map(::raceFromPair)
        return races.map(::countSolutions).reduce(Int::times)
    }

    override fun answerPart2(input: PuzzleInput): Any {
        val lines = input.lines
        val time = parseNumberStrings(lines[0]).joinToString("").toLong()
        val distance = parseNumberStrings(lines[1]).joinToString("").toLong()
        return countSolutions(Race(time, distance))
    }

    override val sampleInput = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent()
}