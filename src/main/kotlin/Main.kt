import solutions.runSolution
import java.lang.RuntimeException

fun main(args: Array<String>) {
    val day = args[0].toInt()

    val solution = runSolution(day)

    println(solution)
}