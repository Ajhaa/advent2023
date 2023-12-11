package solutions.y2023

import solutions.PuzzleInput
import solutions.Solution
import kotlin.math.abs

class Day11 : Solution {
    override fun answerPart1(input: PuzzleInput): Any {
        val universe = input.lines
        val expanded = expand(universe)
        val coordinates = toCoordinateMap(expanded)

        return sumOfManhattanDistances(coordinates)
    }

    override fun answerPart2(input: PuzzleInput): Any {
        val universe = input.lines
        val expanded = expand(universe, expandTo = 1_000_000)
        val coordinates = toCoordinateMap(expanded)

        return sumOfManhattanDistances(coordinates)
    }

    private fun expand(universe: List<String>, expandTo: Long = 2L): Map<Long, Map<Long, Char>> {
        val expanded = mutableMapOf<Long, Map<Long, Char>>()
        val expandedColumns = mutableSetOf<Int>()

        for (i in universe[0].indices) {
            if (universe.all{ it[i] == '.' }) {
                expandedColumns.add(i)
            }
        }

        var currentRow = 0L

        for (row in universe) {
            var currentCol = 0L
            if (row.all { it == '.' }) {
                currentRow += expandTo
                continue
            }

            val newRow = mutableMapOf<Long, Char>()
            for ((j, char) in row.withIndex()) {
                if (j in expandedColumns) {
                    currentCol += expandTo
                } else {
                    newRow[currentCol] = char
                    currentCol += 1
                }
            }

            expanded[currentRow] = newRow
            currentRow += 1
        }

        return expanded
    }

    private fun toCoordinateMap(universe: Map<Long, Map<Long, Char>>): List<Pair<Long, Long>> {
        val coordinateMap = mutableListOf<Pair<Long, Long>>()
        for ((y, line) in universe) {
            for ((x, char) in line) {
                if (char == '#') {
                    coordinateMap.add(Pair(x, y))
                }
            }
        }

        return coordinateMap
    }

    private fun sumOfManhattanDistances(galaxies: List<Pair<Long, Long>>): Long {
        var sum = 0L
        for ((i, coords) in galaxies.withIndex()) {
            val (x, y) = coords
            for ((xOther, yOther) in galaxies.subList(i+1, galaxies.size)) {
                sum += abs(x - xOther) + abs(y - yOther)
            }
        }

        return sum
    }


    override val sampleInput: String
        get() = """
            ...#......
            .......#..
            #.........
            ..........
            ......#...
            .#........
            .........#
            ..........
            .......#..
            #...#.....
        """.trimIndent()
}