package solutions.y2022

import solutions.PuzzleInput
import solutions.Solution
import java.util.PriorityQueue
import kotlin.math.max

class Day01 : Solution {
    override fun answerPart1(input: PuzzleInput): Any {
        var mostCalories = 0
        var currentCalories = 0
        for (line in input.lines) {
            if (line.isBlank()) {
                mostCalories = max(mostCalories, currentCalories)
                currentCalories = 0
                continue
            }

            currentCalories += line.toInt()
        }

        return mostCalories
    }

    override fun answerPart2(input: PuzzleInput): Any {
        val mostCalories = PriorityQueue<Int>()
        var currentCalories = 0
        for (line in input.lines) {
            if (line.isBlank()) {
                mostCalories.add(currentCalories)
                if (mostCalories.size > 3) {
                    mostCalories.poll()
                }
                currentCalories = 0
                continue
            }

            currentCalories += line.toInt()
        }

        return mostCalories.sum()
    }

    override val sampleInput = """
        1000
        2000
        3000

        4000

        5000
        6000

        7000
        8000
        9000

        10000
    """.trimIndent()
}