import solutions.runSolution
import util.currentAdventDay
import kotlin.time.Duration
import kotlin.time.measureTime

fun main(args: Array<String>) {
    val programTime = measureTime {
        val doWarmup = args.contains("warm")
        val yearRegex = Regex("year=[0-9]+")
        val year = args
            .find { yearRegex.matches(it) }
            ?.split("=")
            ?.get(1)?.toInt() ?: 2023

        if (args.contains("all")) {
            return@measureTime runAll(year, doWarmup)
        }

        for (arg in args) {
            runCatching {
                val dayRange = if (arg.contains("..")) {
                    val (begin, end) = arg.split("..").map { it.toInt() }
                    (begin..end)
                } else {
                    val day = arg.toInt()
                    (day..day)
                }

                for (day in dayRange) {
                    val solution = runSolution(day, year, doWarmup)
                    println(solution)
                }
            }.onFailure {
                if (arg != "warm" && !yearRegex.matches(arg)) println("Ignoring invalid argument $arg")
            }
        }
    }

    println("Total program running time $programTime")
}

fun runAll(year: Int, doWarmup: Boolean = false) {
    val days = currentAdventDay(2023)

    var totalExecTime = Duration.ZERO
    for (day in 1..days) {
        val solution = try {
            runSolution(day, year, doWarmup)
        } catch (e: Error) {
            println("could not run day $day")
            continue
        }

        solution.results.forEach {
            totalExecTime += it.second
        }
        println(solution)
    }

    println("Sum of solution execution times: $totalExecTime")
}
