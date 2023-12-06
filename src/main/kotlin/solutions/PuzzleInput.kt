package solutions

import util.getResource
import util.httpGet
import util.writeResource

class PuzzleInput(val string: String) {
    val lines: List<String> = string.lines()

    companion object {
        fun fetch(day: Int): PuzzleInput {
            val inputResourceName = "input$day.txt"

            val inputString = try {
                getResource(inputResourceName)
            } catch (e: Exception) {
                val cookie = System.getenv("ADVENT_OF_CODE_COOKIES")
                    ?: throw RuntimeException("Could not read env variable ADVENT_OF_CODE_COOKIES")

                val input = httpGet("https://adventofcode.com/2023/day/$day/input", cookie).trim()

                if (System.getenv("CI") == null) {
                    writeResource(inputResourceName, input)
                }
                input
            }

            return PuzzleInput(inputString)
        }
    }
}