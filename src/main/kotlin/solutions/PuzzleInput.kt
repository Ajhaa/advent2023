package solutions

import util.getResource
import util.httpGet
import util.writeResource

class PuzzleInput(val string: String) {
    val lines: List<String> = string.lines()

    companion object {
        fun fetch(day: Int, year: Int = 2023): PuzzleInput {
            val inputResourceName = "y$year/input$day.txt"

            val inputString = try {
                getResource(inputResourceName)
            } catch (e: Exception) {
                val cookie = System.getenv("ADVENT_OF_CODE_COOKIES")
                    ?: throw RuntimeException("Could not read env variable ADVENT_OF_CODE_COOKIES")

                val input = httpGet("https://adventofcode.com/$year/day/$day/input", cookie).trim()

                writeResource(inputResourceName, input)
                input
            }

            return PuzzleInput(inputString)
        }
    }
}