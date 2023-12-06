package solutions

import kotlin.RuntimeException
import kotlin.time.Duration
import kotlin.time.measureTime

interface Solution {
    fun warmup(amount: Int = 1000) {
        val sample = PuzzleInput(sampleInput)
        for (i in 1..amount) {
            answerPart1(sample)
            answerPart2(sample)
        }
    }

    val sampleInput: String

    fun answerPart1(input: PuzzleInput): Any
    fun answerPart2(input: PuzzleInput): Any
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

fun getSolution(day: Int): Solution {
    val dayString = day.toString().padStart(2, '0')

    val clazz = try {
        Class.forName("solutions.y2023.Day$dayString")
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
    val input = PuzzleInput.fetch(day)
    val solution = getSolution(day)
    if (doWarmup) solution.warmup()

    val res1 = runAndMeasure(input, solution::answerPart1)
    val res2 = runAndMeasure(input, solution::answerPart2)

    return SolutionResult(day, listOf(res1, res2))
}

fun runAndMeasure(input: PuzzleInput, solution: (PuzzleInput) -> Any): Pair<Any, Duration> {
    val answer: Any
    val time = measureTime {
        answer = solution(input)
    }

    return Pair(answer, time)
}
