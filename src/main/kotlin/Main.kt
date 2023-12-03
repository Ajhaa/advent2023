import solutions.runSolution
import java.time.LocalDate
import kotlin.time.Duration

fun main(args: Array<String>) {
    if (args.getOrNull(0) == "all") {
        return runAll()
    }

    val day = if (args.isNotEmpty()) {
        args[0].toInt()
    } else {
        LocalDate.now().dayOfMonth
    }

    val solution = runSolution(day)
    println(solution)
}

fun runAll() {
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
        println(solution)
    }

    println("Sum of solution execution times: $totalExecTime")
}
