import solutions.Args
import solutions.runSolution
import kotlin.time.Duration
import kotlin.time.measureTime

fun main(argsArray: Array<String>) {
    val programTime = measureTime {
        val args = Args.parse(argsArray)
        runSolutions(args.days, args.year, args.doWarmup, args.onlyDuration)
    }

    println("Total program running time $programTime")
}

fun runSolutions(days: Iterable<Int>, year: Int, doWarmup: Boolean, onlyDurations: Boolean = false) {
    var totalExecTime = Duration.ZERO
    for (day in days) {
        val solution = runSolution(day, year, doWarmup)
//        } catch (e: Error) {
//            println("could not run day $day")
//            continue
//        }

        solution.results.forEach {
            totalExecTime += it.second
            if (onlyDurations) println(it.second.inWholeMicroseconds)
        }
        if (!onlyDurations) println(solution)
    }
    if (!onlyDurations) println("Sum of solution execution times: $totalExecTime")
}
