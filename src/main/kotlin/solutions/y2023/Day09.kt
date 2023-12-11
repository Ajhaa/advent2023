package solutions.y2023

import solutions.PuzzleInput
import solutions.Solution
import util.splitMapToLong

class Day09 : Solution {
    override fun answerPart1(input: PuzzleInput): Any {
        val lines = input.lines
        return lines.map { it.splitMapToLong(" ") }
            .sumOf { lagrange(it, it.size.toLong()) }
            .toLong()
    }

    override fun answerPart2(input: PuzzleInput): Any {
        val lines = input.lines
        return lines
            .map { it.splitMapToLong(" ") }
            .sumOf { lagrange(it.reversed(), it.size.toLong()) }
            .toLong()
    }

    private fun lagrange(points: List<Long>, x: Long): Double {
        return points.withIndex().sumOf {
            var a = 1.0
            var b = 1.0

            for (xi in points.indices) {
                if (xi == it.index) continue
                a *= x - xi
                b *= it.index - xi
            }

            val c = a / b
            it.value * c
        }
    }

    private tailrec fun extrapolate(numbers: List<Long>, result: Long = numbers.last()): Long {
        if (numbers.all { it == 0L }) {
            return result
        }

        val diffs = mutableListOf<Long>()

        for (i in 1..<numbers.size) {
            diffs.add(numbers[i] - numbers[i - 1])
        }

        return extrapolate(diffs, result + diffs.last())
    }

    private fun extrapolateBackwards(numbers: List<Long>): Long {
        if (numbers.all { it == 0L }) {
            return 0
        }

        val diffs = mutableListOf<Long>()

        for (i in 1..<numbers.size) {
            diffs.add(numbers[i] - numbers[i - 1])
        }

        return numbers[0] - extrapolateBackwards(diffs)
    }


    override val sampleInput: String
        get() = """
            0 3 6 9 12 15
            1 3 6 10 15 21
            10 13 16 21 30 45
        """.trimIndent()
}