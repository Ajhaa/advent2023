import solutions.runSolution
import java.time.LocalDate
import kotlin.time.Duration

fun main(args: Array<String>) {
    val arg1 = args.getOrNull(0)

    if (arg1 == "all") {
        runAll()
        return runAll()
    }

    val day = arg1?.toInt() ?: LocalDate.now().dayOfMonth

    val solution = runSolution(day)
    println(solution)
}

fun runAll(doPrint: Boolean = true) {
    val currentDate = LocalDate.now()

    val days = if (currentDate.isAfter(LocalDate.of(2023, 12, 25))) {
        25
    } else {
        currentDate.dayOfMonth
    }

    var totalExecTime = Duration.ZERO
    for (day in 1..days) {
        val solution = try {
            runSolution(day)
        } catch (e: Error) {
            println("could not run day $day")
            continue
        }

        solution.results.forEach {
            totalExecTime += it.second
        }
        if (doPrint) println(solution)
    }

    println("Sum of solution execution times: $totalExecTime")
}
