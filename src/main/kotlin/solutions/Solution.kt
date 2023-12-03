package solutions

import util.getResource
import util.httpGet
import util.writeResource
import java.lang.RuntimeException
import kotlin.time.Duration
import kotlin.time.measureTime

abstract class Solution {

    protected lateinit var input: String
    protected lateinit var inputLines: List<String>

    fun init(input: String) {
        this.input = input
        this.inputLines = input.lines()
    }

    abstract fun answerPart1(): Any
    abstract fun answerPart2(): Any
}

data class SolutionResult(
    val day: Int,
    val results: List<Pair<Any, Duration>>
)

fun getInput(day: Int): String {
    val inputResourceName = "input$day.txt"

    return try {
        getResource(inputResourceName)
    } catch (_: Exception) {
        val cookie = getResource("cookies")
        val input = httpGet("https://adventofcode.com/2023/day/$day/input", cookie).trim()
        writeResource(inputResourceName, input)
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

fun runSolution(day: Int): SolutionResult {
    val input = getInput(day)
    val solution = getSolution(day)
    solution.init(input)

    val res1 = runAndMeasure(solution::answerPart1)
    val res2 = runAndMeasure(solution::answerPart2)

    return SolutionResult(day, listOf(res1, res2))
}

fun runAndMeasure(solution: () -> Any) : Pair<Any, Duration> {
    val answer: Any
    val time = measureTime {
        answer = solution()
    }

    return Pair(answer, time)
}
