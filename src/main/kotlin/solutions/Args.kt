package solutions

import util.currentAdventDay
import util.splitMapToInt
import java.util.TreeSet


class Args {
    var year = 2023
    var days = TreeSet<Int>()
    var doWarmup = false
    var onlyDuration: Boolean = false

    companion object {
        fun parse(argsArray: Array<String>): Args {
            val argsSet = argsArray.toMutableSet()
            val args = Args()
            val yearRegex = Regex("year=[0-9]+")

            val yearConfig = argsSet
                .find { yearRegex.matches(it) }

            val year = if (argsSet.remove(yearConfig)) {
                yearConfig!!.split("=")[1].toInt()
            } else {
                2023
            }

            val currentDay = currentAdventDay(year)

            if (argsSet.remove("duration")) {
                args.onlyDuration = true
            }

            if (argsSet.remove("warm")) {
                args.doWarmup = true
            }

            if (argsSet.remove("all")) {
                args.days.addAll(1..currentDay)
            }

            for (arg in argsSet) {
                try {
                    if (yearRegex.matches(arg)) {
                        args.year = arg.split("=")[1].toInt()
                    }

                    val dayRange = if (arg.contains("..")) {
                        val (begin, end) = arg.splitMapToInt("..")
                        (begin..end)
                    } else {
                        val day = arg.toInt()
                        (day..day)
                    }

                    args.days.addAll(dayRange)
                } catch (exception: NumberFormatException) {
                    println("Ignoring invalid argument $arg")
                }
            }


            if (args.days.isEmpty()) {
                args.days.add(currentDay)
            }
            return args
        }
    }
}