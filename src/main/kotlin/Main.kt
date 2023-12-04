import solutions.runSolution
import util.getChristmasDay
import java.time.LocalDate
import kotlin.time.Duration
import kotlin.time.measureTime

fun main(args: Array<String>) {
    val programTime = measureTime {
        val doWarmup = args.contains("warm")
        val arg1 = args.getOrNull(0)

        if (arg1 == "all") {
            return@measureTime runAll(doWarmup)
        }

        val day = arg1?.toInt() ?: LocalDate.now().dayOfMonth

        val solution = runSolution(day, doWarmup)
        println(solution)
    }

    println("Total program running time $programTime")
}

fun runAll(doWarmup: Boolean = false) {
    val days = getChristmasDay(2023)

    var totalExecTime = Duration.ZERO
    for (day in 1..days) {
        val solution = try {
            runSolution(day, doWarmup)
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
