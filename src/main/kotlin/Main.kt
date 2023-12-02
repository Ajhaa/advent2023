import solutions.runSolution
import java.time.LocalDate

fun main(args: Array<String>) {
    val day = if (args.isNotEmpty()) {
        args[0].toInt()
    } else {
        LocalDate.now().dayOfMonth
    }

    val solution = runSolution(day)
    println(solution)
}
