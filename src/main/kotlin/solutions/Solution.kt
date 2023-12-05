package solutions

import util.getResource
import util.httpGet
import util.writeResource
import kotlin.RuntimeException
import kotlin.time.Duration
import kotlin.time.measureTime

abstract class Solution {

    protected lateinit var input: String
    protected lateinit var inputLines: List<String>

    fun init(input: String) {
        this.input = input
        this.inputLines = input.lines()
    }

    fun warmup(amount: Int = 1000) {
        if (sampleInput == null) {
            throw RuntimeException("Cannot warmup, no sampleInput provided")
        }

        init(sampleInput!!)

        for (i in 1..amount) {
            answerPart1()
            answerPart2()
        }
    }

    protected open val sampleInput: String? = null

    abstract fun answerPart1(): Any
    abstract fun answerPart2(): Any
}

data class SolutionResult(
    val day: Int,
    val results: List<Pair<Any, Duration>>
) {
    override fun toString(): String {
        val builder = StringBuilder()
        builder.appendLine("Day $day")
        results.forEachIndexed { index, pair ->
            builder.appendLine("[Part ${index + 1}] result: ${pair.first}; runtime: ${pair.second}")
        }

        return builder.toString()
    }
}

fun getInput(day: Int): String {
    val inputResourceName = "input$day.txt"

    return try {
        getResource(inputResourceName)
    } catch (e: Exception) {
        val cookie = System.getenv("ADVENT_OF_CODE_COOKIES")
        val input = httpGet("https://adventofcode.com/2023/day/$day/input", cookie).trim()

        if (System.getenv("CI") == null) {
            writeResource(inputResourceName, input)
        }
        input
    }
}


fun getSolution(day: Int): Solution {
    val dayString = day.toString().padStart(2, '0')

    val clazz = try {
        Class.forName("solutions.Day$dayString")
    } catch (exception: ClassNotFoundException) {
        throw RuntimeException("Could not find a solution class for day $dayString")
    }

    val constructor = clazz.constructors.find { it.parameterCount == 0 }
        ?: throw RuntimeException(
            "Could not find 0 parameter constructor for class ${clazz.canonicalName}"
        )

    return constructor.newInstance() as Solution
}

fun runSolution(day: Int, doWarmup: Boolean = false): SolutionResult {
    val input = getInput(day)
    val solution = getSolution(day)
    if (doWarmup) solution.warmup()

    solution.init(input)

    val res1 = runAndMeasure(solution::answerPart1)
    val res2 = runAndMeasure(solution::answerPart2)

    return SolutionResult(day, listOf(res1, res2))
}

fun runAndMeasure(solution: () -> Any): Pair<Any, Duration> {
    val answer: Any
    val time = measureTime {
        answer = solution()
    }

    return Pair(answer, time)
}
