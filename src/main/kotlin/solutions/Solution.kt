package solutions

import java.lang.RuntimeException

interface Solution {
    fun answerPart1(input: String): String
    fun answerPart2(input: String): String
}

fun getResource(name: String): String {
    return ClassLoader.getSystemClassLoader().getResource(name)?.readText() ?:
        throw RuntimeException("Could not open resource $name")
}


fun getSolution(day: Int): Solution {
    val clazz = try {
        Class.forName("solutions.Day$day")
    } catch (exception: ClassNotFoundException) {
        throw RuntimeException("Could not find a solution class for day $day")
    }

    val constructor = clazz.constructors.find { it.parameterCount == 0 }
        ?: throw RuntimeException(
            "Could not find 0 parameter constructor for class ${clazz.canonicalName}"
        )

    return constructor.newInstance() as Solution
}

fun runSolution(day: Int): Pair<String, String> {
    val solution = getSolution(day)
    val input = getResource("input$day.txt")

    val ans1 = solution.answerPart1(input)
    val ans2 = solution.answerPart2(input)

    return Pair(ans1, ans2)
}
