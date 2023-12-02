package solutions

import util.httpGet
import java.lang.RuntimeException

interface Solution {
    fun answerPart1(input: String): String
    fun answerPart2(input: String): String
}

fun getResource(name: String): String {
    return ClassLoader.getSystemClassLoader().getResource(name)?.readText()
        ?: throw RuntimeException("Could not open resource $name")
}

fun getInputWithHttp(day: Int): String {
    val cookie = getResource("cookie")
    val input = httpGet("https://adventofcode.com/2023/day/$day/input", cookie)

    return input.trim()
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

fun runSolution(day: Int): Pair<String, String> {
    val solution = getSolution(day)
    val input = getInputWithHttp(day)

    val ans1 = solution.answerPart1(input)
    val ans2 = solution.answerPart2(input)

    return Pair(ans1, ans2)
}
