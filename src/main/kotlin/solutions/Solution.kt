package solutions

import util.httpGet
import java.lang.RuntimeException
import kotlin.time.Duration
import kotlin.time.measureTime

interface Solution {
    fun answerPart1(input: List<String>): Any
    fun answerPart2(input: List<String>): Any
}

data class SolutionResult(
    val day: Int,
    val results: List<Pair<Any, Duration>>
)

fun getResource(name: String): String {
    return ClassLoader.getSystemClassLoader().getResource(name)?.readText()
        ?: throw RuntimeException("Could not open resource $name")
}

fun getInputWithHttp(day: Int): List<String> {
    val cookie = getResource("cookie")
    val input = httpGet("https://adventofcode.com/2023/day/$day/input", cookie)

    return input.trim().lines()
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
    val solution = getSolution(day)
    val input = getInputWithHttp(day)

    val res1 = runAndMeasure(solution::answerPart1, input)
    val res2 = runAndMeasure(solution::answerPart2, input)

    return SolutionResult(day, listOf(res1, res2))
}

fun runAndMeasure(solution: (List<String>) -> Any, input: List<String>) : Pair<Any, Duration> {
    val answer: Any
    val time = measureTime {
        answer = solution(input)
    }

    return Pair(answer, time)
}
